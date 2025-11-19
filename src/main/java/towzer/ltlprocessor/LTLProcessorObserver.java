/*
 * Copyright (C) 2013-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 * Copyright (C) 2015 Vincent Langenfeld (langenfv@informatik.uni-freiburg.de)
 * Copyright (C) 2025 Towzer
 *
 * This file is part of the Towzer LTLProcessor plug-in.
 */
package towzer.ltlprocessor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// To replace org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import ultimate.ast.AssignmentStatement;
import ultimate.ast.DeclarationInformation;
import ultimate.ast.Expression;
import ultimate.ast.GeneratedBoogieAstTransformer;
import ultimate.ast.IdentifierExpression;
import ultimate.ast.Procedure;
import ultimate.ast.Unit;
import ultimate.models.IElement;
import ultimate.models.annotation.LTLPropertyCheck;
import ultimate.models.annotation.LTLPropertyCheck.CheckableExpression;
import ultimate.models.observers.IUnmanagedObserver;
import ultimate.parser.BoogieSymbolFactory;

import utopia.specLang.PropertyContainer;
import utopia.specLang.PropertyContainer.VerificationProperty;

/**
 * This class reads a definition of a property in LTL and returns a String of
 * the LTL formula in a format compatible with Spot.
 *
 * @author Langenfeld
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * @author TOTO
 */

public class LTLProcessorObserver implements IUnmanagedObserver {
	// Global Java logger
	private final Logger logger;

	private Map<String, CheckableExpression> boogieVarMap;
	private String phiProperty;

	// alphabet without F, G, M, R, U, W, X (LTL operators)
	private static final String ALPHABET = "ABCDEHIJKLNOPQSTVYZ";

	public LTLProcessorObserver(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void init() {
		// not needed
	}

	@Override
	public boolean process(final IElement root) {
		if (!(root instanceof PropertyContainer)) {
			return false;
		}

		VerificationProperty property;
		logger.fine("Found PropertyContainer");
		property = (VerificationProperty) ((PropertyContainer) root).getPayload();

		// Extract fairness and verification properties
		phiProperty = property.hasFairness()
				? property.getFairness() + " && ! ( " + property.getLiveness() + " )"
				: "! ( " + property.getLiveness() + " )";

		// Format fairness and verification properties
		LTLPropertyCheck phiCheck;
		try {
			// createCheckFromPropertyString() rename LTL variables to make them
			// usable by Spot
			phiCheck = createCheckFromPropertyString(phiProperty);
			phiProperty = phiCheck.getLTL2BALTLProperty(); // SmartLTL to LTL
		} catch (Throwable e) {
			logger.severe("LTLProcessor encountered an error while formatting LTL properties.");
			throw new RuntimeException(e);
		}

		// Store Map
		boogieVarMap = phiCheck.getCheckableAtomicPropositions();

		return true;
	}

	@Override
	public void finish() {
		// not needed
	}

	@Override
	public boolean performedChanges() {
		return false;
	}

	public String getProperty() {
		return phiProperty;
	}

	public Map<String, CheckableExpression> getVarMap() {
		return boogieVarMap;
	}

	private LTLPropertyCheck createCheckFromPropertyString(final String ltlProperty) throws Throwable {
		final Map<String, CheckableExpression> apIrs = new LinkedHashMap<>();
		final Pattern pattern = Pattern.compile("AP\\((.*?)\\)");
		final Matcher matcher = pattern.matcher(ltlProperty);

		while (matcher.find()) {
			final String key = matcher.group(0);
			final CheckableExpression expr = createCheckableExpression(matcher.group(1));
			apIrs.put(key, expr);
		}
		if (apIrs.isEmpty()) {
			throw new IllegalArgumentException("No atomic propositions in " + ltlProperty);
		}

		// we need to rename the AP(...) expressions to symbols s.t. ltl2ba does not get
		// confused
		final Map<String, CheckableExpression> irs = new LinkedHashMap<>();
		String newLtlProperty = ltlProperty;
		int i = 0;
		for (final Entry<String, CheckableExpression> entry : apIrs.entrySet()) {
			final String freshSymbol = getAPSymbol(i);
			++i;
			newLtlProperty = newLtlProperty.replaceAll(Pattern.quote(entry.getKey()), freshSymbol);
			irs.put(freshSymbol, entry.getValue());
		}

		return new LTLPropertyCheck(newLtlProperty, irs, null);
	}

	private InputStream stringToInputStream(String s) {
		return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
	}

	private CheckableExpression createCheckableExpression(final String expr) {

		final String niceProgram = "procedure main() { #thevar := %s ;}";

		final String localProg = String.format(niceProgram, expr.trim());
		final BoogieSymbolFactory symFac = new BoogieSymbolFactory();
		@SuppressWarnings("deprecation")
		final ultimate.parser.Lexer lexer = new ultimate.parser.Lexer(stringToInputStream(localProg));
		lexer.setSymbolFactory(symFac);
		final ultimate.parser.Parser p = new ultimate.parser.Parser(lexer, symFac);

		try {
			final Unit x = (Unit) p.parse().value;
			final Procedure proc = (Procedure) x.getDeclarations()[0];
			final AssignmentStatement stmt = (AssignmentStatement) proc.getBody().getBlock()[0];
			final Expression bExpr = stmt.getRhs()[0];
			final Expression newBExpr = bExpr.accept(new DeclarationInformationAdder());
			return new CheckableExpression(newBExpr, Collections.emptyList());
		} catch (final Exception e) {
			logger.severe(String.format("Exception while parsing the atomic proposition \"%s\": %s", expr, e));
			throw new RuntimeException(e);
		}
	}

	public static String getAPSymbol(final int i) {
		if (i < ALPHABET.length()) {
			return String.valueOf(ALPHABET.charAt(i));
		}

		String rtr = "A";
		int idx = i;
		while (idx > ALPHABET.length()) {
			idx = idx - ALPHABET.length();
			rtr += String.valueOf(ALPHABET.charAt(idx % ALPHABET.length()));
		}
		return rtr;
	}

	private static final class DeclarationInformationAdder extends GeneratedBoogieAstTransformer {
		@Override
		public Expression transform(final IdentifierExpression node) {
			return new IdentifierExpression(node.getLocation(), node.getType(), node.getIdentifier(),
					DeclarationInformation.DECLARATIONINFO_GLOBAL);
		}
	}
}
