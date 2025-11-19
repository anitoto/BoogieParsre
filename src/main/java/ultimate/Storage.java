/*
 * Copyright (C) 2014-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 *
 * This file is part of the ULTIMATE Core.
 *
 * The ULTIMATE Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE Core. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE Core, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE Core grant you additional permission
 * to convey the resulting work.
 */

package ultimate;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.models.IStorable;
import ultimate.util.datastructures.Pair;

/**
 *
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class Storage {
	// Global Java logger
	private final Logger logger;

	private final Deque<Pair<Object, Set<String>>> mMarker;
	private final Map<String, IStorable> mStorage;
	private final ProgressMonitorService monitorService;

	private final Object mLock;

	public Storage(final Logger logger) {
		this(new LinkedHashMap<>(), new ArrayDeque<>(), new Object(), logger);
		pushMarker(this);
	}

	private Storage(final Map<String, IStorable> storage, final Deque<Pair<Object, Set<String>>> marker,
			final Object lock, final Logger logger) {
		this.logger = logger;
		mLock = Objects.requireNonNull(lock);
		mStorage = Objects.requireNonNull(storage);
		monitorService = new ProgressMonitorService();
		mMarker = Objects.requireNonNull(marker);
	}

	public IStorable getStorable(final String key) {
		synchronized (mLock) {
			return mStorage.get(key);
		}
	}

	public IStorable putStorable(final String key, final IStorable value) {
		if (value == null || key == null) {
			throw new IllegalArgumentException("Cannot store nothing");
		}
		synchronized (mLock) {
			final Pair<Object, Set<String>> currentMarker = mMarker.peek();
			currentMarker.getSecond().add(key);
			return mStorage.put(key, value);
		}
	}

	public IStorable removeStorable(final String key) {
		synchronized (mLock) {
			return mStorage.remove(key);
		}
	}

	public void clear() {
		synchronized (mLock) {
			final Collection<IStorable> values = mStorage.values();
			if (values.isEmpty()) {
				return;
			}
			final List<IStorable> current = new ArrayList<>(values);

			if (current.isEmpty()) {
				return;
			}

			// destroy storables in reverse order s.t., e.g., scripts are destroyed
			// before the solver is destroyed.
			// this is done because we assume that instances created later may
			// depend on instances created earlier.
			Collections.reverse(current);

			logger.fine("Clearing " + current.size() + " storables from " + getClass().getSimpleName());
			for (final IStorable storable : current) {
				if (storable == null) {
					logger.warning("Found NULL storable, ignoring");
					continue;
				}
				try {
					storable.destroy();
				} catch (final Throwable t) {
					if (logger == null) {
						continue;
					}
					logger.severe("There was an exception during clearing of toolchain storage while destroying "
							+ storable.getClass().toString() + ": " + t.getMessage());
				}
			}
			mStorage.clear();
			mMarker.clear();
			pushMarker(this);
		}
	}

	public boolean destroyStorable(final String key) {
		final IStorable storable = removeStorable(key);
		if (storable != null) {
			storable.destroy();
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return mStorage.toString();
	}

	public Set<String> keys() {
		final Set<String> keys;
		synchronized (mLock) {
			keys = new HashSet<>(mStorage.keySet());
		}
		return keys;
	}

	public void pushMarker(final Object marker) throws IllegalArgumentException {
		if (marker == null) {
			throw new IllegalArgumentException("marker may not be null");
		}
		if (hasMarker(marker)) {
			throw new IllegalArgumentException("duplicate marker");
		}
		synchronized (mLock) {
			mMarker.push(new Pair<>(marker, new HashSet<>()));
		}
	}

	public Set<String> destroyMarker(final Object marker) {
		if (mMarker.isEmpty() || !hasMarker(marker)) {
			return Collections.emptySet();
		}
		synchronized (mLock) {
			final Set<String> rtr = new HashSet<>();
			final Iterator<Pair<Object, Set<String>>> iter = mMarker.iterator();
			while (iter.hasNext()) {
				final Pair<Object, Set<String>> markerPair = iter.next();
				iter.remove();
				for (final String key : markerPair.getSecond()) {
					if (destroyStorable(key)) {
						rtr.add(key);
					}
				}
				if (markerPair.getFirst() == marker) {
					return rtr;
				}
			}
			return rtr;
		}
	}

	private boolean hasMarker(final Object marker) {
		assert marker != null;
		synchronized (mLock) {
			return mMarker.stream().map(a -> a.getFirst()).anyMatch(a -> a == marker);
		}
	}

	// Service to handle MonitoredProcess of the SMT solver
	public ProgressMonitorService getProgressMonitorService() {
		return monitorService;
	}

}
