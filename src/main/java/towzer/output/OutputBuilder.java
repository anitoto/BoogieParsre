/*
 * Copyright (C) 2025 towzer
 *
 * This file is part of the towzer Output Builder plug-in.
*/
package towzer.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import towzer.ltlprocessor.LTLProcessorObserver;

import ultimate.models.IElement;
import ultimate.models.annotation.LTLPropertyCheck.CheckableExpression;
import ultimate.rcfgbuilder.cfg.Summary;
import ultimate.symboltable.BoogieSymbolTable;
import ultimate.util.cfg.structure.IIcfg;
import ultimate.util.cfg.structure.IIcfgCallTransition;
import ultimate.util.cfg.structure.IIcfgForkTransitionThreadCurrent;
import ultimate.util.cfg.structure.IIcfgInternalTransition;
import ultimate.util.cfg.structure.IIcfgJoinTransitionThreadCurrent;
import ultimate.util.cfg.structure.IIcfgReturnTransition;
import ultimate.util.cfg.structure.IcfgEdge;
import ultimate.util.cfg.structure.IcfgLocation;
import ultimate.util.cfg.structure.IcfgLocationIterator;

/**
 */
public class OutputBuilder {
	// Global Java logger
	private final Logger logger;

	private final boolean interprocedural; // flag
	private Map<String, CheckableExpression> varMap;

	public OutputBuilder(final boolean interprocedural, final Logger logger) {
		this.logger = logger;
		this.interprocedural = interprocedural;
	}

	public String formatProperty(final IElement root) {
		final LTLProcessorObserver ltlProcObs = new LTLProcessorObserver(logger);
		logger.info("Executing the observer " + ltlProcObs.getClass().getSimpleName());
		ltlProcObs.process(root);

		// retrieve Boogie program variables Map
		varMap = ltlProcObs.getVarMap();
		if (varMap == null) {
			logger.severe("LTL property variables-letters mapping missing from LTLProcessorObserver.");
			throw new RuntimeException("Boogie variables - letters mapping missing");
		}

		// retrieve LTL property phi = fairness && !verification
		final String phiProperty = ltlProcObs.getProperty();
		if (phiProperty == null) {
			logger.severe("LTL property missing from LTLProcessorObserver.");
			throw new RuntimeException("LTL property missing");
		}

		return phiProperty;
	}

	/** Get program automaton description for Spot */
	public String formatProgramAut(final IElement root) {
		if (!(root instanceof IIcfg<?>)) {
			return null;
		}

		final List<IIcfg<?>> icfgs = new ArrayList<>();
		icfgs.add((IIcfg<?>) root);

		@SuppressWarnings("unchecked")
		final IIcfg<IcfgLocation> rcfgRootNode = (IIcfg<IcfgLocation>) icfgs.stream()
				.filter(a -> IcfgLocation.class.isAssignableFrom(a.getLocationClass())).reduce((a, b) -> b)
				.orElseThrow(UnsupportedOperationException::new);

		if (rcfgRootNode == null) {
			throw new UnsupportedOperationException("TraceAbstraction needs an RCFG");
		}
		logger.fine("Analyzing ICFG " + rcfgRootNode.getIdentifier());

		String programAutDescription;
		try {
			programAutDescription = getAutomatonDescription(rcfgRootNode);
		} catch (UnsupportedOperationException e) {
			logger.severe("Couldn't make Boogie program automaton description.");
			throw new RuntimeException(e);
		}

		return programAutDescription;
	}

	public String formatBoogieSymbTable(BoogieSymbolTable symbolTable) {
		// Build serialized symbol table data
		StringBuilder symbolTableData = new StringBuilder();

		// Get function mappings
		Map<String, String> boogieToSmt = symbolTable.getBoogieFunction2SmtFunction();
		Map<String, String> smtToBoogie = symbolTable.getSmtFunction2BoogieFunction();

		// Serialize function mappings
		symbolTableData.append("functions:");

		// Boogie to SMT mappings
		boolean first = true;
		for (Map.Entry<String, String> entry : boogieToSmt.entrySet()) {
			if (!first) {
				symbolTableData.append(",");
			}
			symbolTableData.append(entry.getKey()).append("->").append(entry.getValue());
			first = false;
		}

		symbolTableData.append("|");

		// SMT to Boogie mappings
		first = true;
		for (Map.Entry<String, String> entry : smtToBoogie.entrySet()) {
			if (!first) {
				symbolTableData.append(",");
			}
			symbolTableData.append(entry.getKey()).append("->").append(entry.getValue());
			first = false;
		}

		// Add variable information
		symbolTableData.append(";variables:");
		Map<String, String> globalVars = symbolTable.getGlobalVariableTypes();
		first = true;
		for (Map.Entry<String, String> entry : globalVars.entrySet()) {
			if (!first) {
				symbolTableData.append(",");
			}
			// Format: varname:type:scope
			symbolTableData.append(entry.getKey()).append(":").append(entry.getValue()).append(":GLOBAL");
			first = false;
		}

		// Add constant information
		symbolTableData.append(";constants:");
		Map<String, String> constants = symbolTable.getConstantDeclarations();
		first = true;
		for (Map.Entry<String, String> entry : constants.entrySet()) {
			if (!first) {
				symbolTableData.append(",");
			}
			// Format: constname:type:value (entry.getValue() already contains "type:value")
			symbolTableData.append(entry.getKey()).append(":").append(entry.getValue());
			first = false;
		}

		return symbolTableData.toString();
	}

	private String getAutomatonDescription(final IIcfg<?> icfg) throws UnsupportedOperationException {
		StringJoiner initialStates = new StringJoiner("$");
		StringJoiner acceptingStates = new StringJoiner("$");
		StringJoiner transitions = new StringJoiner("\n");

		final IcfgLocationIterator<?> iter = new IcfgLocationIterator<>(icfg);
		final Set<IcfgLocation> allNodes = iter.asStream().collect(Collectors.toSet());
		final Set<? extends IcfgLocation> initialNodes = icfg.getInitialNodes();
		Set<IcfgLocation> states = Collections.newSetFromMap(new IdentityHashMap<>());

		// list states
		{
			for (final IcfgLocation locNode : allNodes) {
				states.add(locNode);

				// initial states list
				if (initialNodes.contains(locNode)) {
					initialStates.add(locNode.toString());
				}
			}
		}

		// transitions list ProgramPoint£Edge£ProgramPoint / state£label£state
		int nbTransitions = 0;
		int nbAccepting = 0;
		int nbEdges = 0;
		for (final IcfgLocation locNode : allNodes) {
			if (locNode.getOutgoingNodes() != null) {
				nbAccepting = 0;
				nbEdges = 0;
				for (final IcfgEdge edge : locNode.getOutgoingEdges()) {
					nbTransitions++;
					nbEdges++;

					final IcfgLocation succLoc = edge.getTarget();

					if (edge instanceof IIcfgCallTransition<?>) {
						// accepting states list
						nbAccepting++;

						if (interprocedural) {
							transitions.add(locNode.toString() + "£"
									+ extractFormula(edge.toString()) + "£"
									+ succLoc.toString());
						}

					} else if (edge instanceof IIcfgReturnTransition<?, ?>) {
						// accepting states list
						nbAccepting++;

						if (interprocedural) {
							final IIcfgReturnTransition<?, ?> returnEdge = (IIcfgReturnTransition<?, ?>) edge;
							final IcfgLocation callerLocNode = returnEdge.getCallerProgramPoint();
							if (states.contains(callerLocNode)) {
								transitions.add(locNode.toString() + "£"
										+ extractFormula(returnEdge.toString()) + "£"
										+ succLoc.toString());
							} else {
								// did not add returnEdge because the corresponding call predecessor location is
								// not graph-reachable in the ICFG
							}
						}

					} else if (edge instanceof Summary) {
						final Summary summaryEdge = (Summary) edge;
						if (summaryEdge.calledProcedureHasImplementation()) {
							if (!interprocedural) {
								transitions.add(locNode.toString() + "S£"
										+ extractFormula(summaryEdge.toString()) + "£"
										+ succLoc.toString());
							}
						} else {
							transitions.add(
									locNode.toString() + "£"
											+ extractFormula(summaryEdge.toString()) + "SE£"
											+ succLoc.toString());
						}

					} else if (edge instanceof IIcfgInternalTransition<?>) {
						transitions.add(locNode.toString() + "T£"
								+ extractFormula(edge.toString()) + "£"
								+ succLoc.toString());

					} else if (edge instanceof IIcfgForkTransitionThreadCurrent<?>) {
						throw new UnsupportedOperationException(
								"analysis for sequential programs does not support fork/join");
					} else if (edge instanceof IIcfgJoinTransitionThreadCurrent<?>) {
						throw new UnsupportedOperationException(
								"analysis for sequential programs does not support fork/join");
					} else {
						throw new UnsupportedOperationException("unsupported edge " + edge.getClass().getSimpleName());
					}
				}

				// accepting states list
				if (nbAccepting == nbEdges) {
					acceptingStates.add(locNode.toString());
				}
			}
		}

		String stats = "Stats automaton description\n" +
				"States: " + allNodes.size() + "\n" +
				"Edges: " + nbTransitions;

		logger.fine(stats);

		return "initial states:\n" + initialStates.toString() + "\n\n" +
				"accepting states:\n" + acceptingStates.toString() + "\n\n" +
				"transitions:\n" + transitions.toString();
	}

	private String extractFormula(String edgeString) {
		// String formulaStart = ": ";
		// // String formulaEnd = " InVars";

		// int startIndex = edgeString.indexOf(formulaStart);
		// if (startIndex == -1) {
		// 	return edgeString; // Return original if no formula found
		// }
		// startIndex += formulaStart.length();

		// // int endIndex = edgeString.indexOf(formulaEnd, startIndex);
		// // if (endIndex == -1) {
		// // 	return edgeString; // Return original if no InVars found
		// // }

		// String formula = edgeString.substring(startIndex, edgeString.length()).trim();

		return edgeString;//formula;
	}
}
