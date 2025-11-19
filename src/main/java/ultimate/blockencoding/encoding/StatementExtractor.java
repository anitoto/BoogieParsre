/*
 * Copyright (C) 2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 *
 * This file is part of the ULTIMATE BlockEncodingV2 plug-in.
 *
 * The ULTIMATE BlockEncodingV2 plug-in is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE BlockEncodingV2 plug-in is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE BlockEncodingV2 plug-in. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE BlockEncodingV2 plug-in, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE BlockEncodingV2 plug-in grant you additional permission
 * to convey the resulting work.
 */
package ultimate.blockencoding.encoding;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ultimate.ast.Statement;
import ultimate.rcfgbuilder.cfg.CodeBlock;
import ultimate.rcfgbuilder.cfg.ParallelComposition;
import ultimate.rcfgbuilder.cfg.StatementSequence;
import ultimate.rcfgbuilder.cfg.Summary;
import ultimate.rcfgbuilder.util.RCFGEdgeVisitor;

public class StatementExtractor extends RCFGEdgeVisitor {
	// Global Java logger
	private final Logger logger;

	private List<Statement> mStatements;
	private boolean mHasSummary;

	public StatementExtractor(final Logger logger) {
		this.logger = logger;
	}

	public List<Statement> process(final CodeBlock edge) {
		mStatements = new ArrayList<>();
		mHasSummary = false;
		visit(edge);
		if (mHasSummary) {
			logger.fine(edge + " contains summaries, skipping...");
			return new ArrayList<>();
		}
		return mStatements;
	}

	public boolean hasSummary() {
		return mHasSummary;
	}

	@Override
	protected void visit(final ParallelComposition c) {
		throw new UnsupportedOperationException("Cannot merge ParallelComposition");
	}

	@Override
	protected void visit(final StatementSequence c) {
		mStatements.addAll(c.getStatements());
		super.visit(c);
	}

	@Override
	protected void visit(final Summary c) {
		mHasSummary = true;
		super.visit(c);
	}
}
