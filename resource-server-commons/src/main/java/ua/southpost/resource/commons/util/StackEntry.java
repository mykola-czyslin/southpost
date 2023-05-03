package ua.southpost.resource.commons.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StackEntry<T> {
	@Nonnull
	private final T value;
	private final StackEntry<T> prev;

	public StackEntry(@Nonnull T value) {
		this(value, null);
	}

	public StackEntry(@Nonnull T value, @Nullable StackEntry<T> prev) {
		this.value = value;
		this.prev = prev;
	}

	@Nonnull
	public T getValue() {
		return value;
	}

	@Nonnull
	public StackEntry<T> push(T value) {
		return new StackEntry<>(value, this);
	}

	@Nullable
	public StackEntry<T> pop() {
		return this.prev;
	}
}
