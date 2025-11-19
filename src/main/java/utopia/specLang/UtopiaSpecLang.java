/*
 * Copyright (C) 2025 Towzer
 */
package utopia.specLang;

import ultimate.models.IElement;

import java.util.logging.Logger;

/**
 * This class initializes UtopiaSpecLang which processes the LTL properties.
 *
 * @author TOTO (Towzer)
 *
 */
public class UtopiaSpecLang {
	// Global Java logger
    private final Logger logger;

	// Object from the Main
	private final IElement root;

	public UtopiaSpecLang(final Logger logger, final IElement root) {
		this.logger = logger;
		this.root = root;
	}

	public PropertyContainer executeObservers() {
		UtopiaSpecLangObserver specLang = new UtopiaSpecLangObserver(logger);
		logger.info("Executing the observer " + specLang.getClass().getSimpleName());
		try {
			specLang.init();
			specLang.process(root);
			specLang.finish();
		} catch (Throwable e) {
			logger.severe("Failed to execute the observer " + specLang.getClass().getSimpleName() + ": " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return specLang.getProperty();
	}
}
