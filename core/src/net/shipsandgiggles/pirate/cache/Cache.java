package net.shipsandgiggles.pirate.cache;

import net.shipsandgiggles.pirate.util.Preconditions;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class Cache<K, V> {

	private final Map<K, V> cache;
	private final Predicate<V> argumentCheck;

	public Cache(Predicate<V> argumentCheck) {
		this.argumentCheck = argumentCheck;
		this.cache = new ConcurrentHashMap<>();
	}

	public void cache(K identifier, V value) {
		Preconditions.checkNotNull(identifier, "Identifier for a cache cannot be null!");

		if (this.argumentCheck != null) {
			Preconditions.checkArgument(this.argumentCheck, "Entity {val} cannot be added as it has failed the predicate!", value);
		}

		if (this.cache.containsValue(value)) {
			throw new IllegalStateException("Cache already contains value " + value + "!");
		}

		this.cache.put(identifier, value);
	}

	public void remove(K toRemove) {
		Preconditions.checkNotNull(toRemove, "Identifier for removal cannot be null!");

		if (!this.cache.containsKey(toRemove)) {
			throw new NullPointerException("The value " + toRemove + " is not currently in the cache!");
		}

		this.cache.remove(toRemove);
	}

	public Optional<V> find(K toFind) {
		Preconditions.checkNotNull(toFind, "Identifier for a cache cannot be null!");

		return Optional.ofNullable(this.cache.get(toFind));
	}
}
