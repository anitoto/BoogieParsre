/*
 * Copyright (C) 2025 Towzer
 */
package ultimate.rcfgbuilder;

import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.models.IElement;
import ultimate.models.IGenerator;
import ultimate.rcfgbuilder.cfg.BoogieIcfgLocation;
import ultimate.util.cfg.structure.IIcfg;

/**
 * Main class of Plug-In RCFGBuilder
 *
 * @author Ultimate
 * @author TOTO (Towzer)
 */
public class RCFGBuilder implements IGenerator {
	// Global Java logger
	private final Logger logger;

	private final IElement root;

	private RCFGBuilderObserver rcfgBuilderObs;

	public RCFGBuilder(final Logger logger, final Storage storage, final IElement root) {
		this.logger = logger;
		this.root = root;
		rcfgBuilderObs = new RCFGBuilderObserver(logger, storage);
	}

	public IIcfg<BoogieIcfgLocation> executeObservers() {
		logger.info("Executing the observer " + rcfgBuilderObs.getClass().getSimpleName());
		try {
			rcfgBuilderObs.process(root);
			rcfgBuilderObs.finish();
		} catch (Exception e) {
			logger.severe("Failed to execute the observer " + rcfgBuilderObs.getClass().getSimpleName() + ": "
					+ e.getMessage());
		}
		return rcfgBuilderObs.getRoot();
	}

	@Override
	public IElement getModel() {
		return rcfgBuilderObs.getRoot();
	}
}
