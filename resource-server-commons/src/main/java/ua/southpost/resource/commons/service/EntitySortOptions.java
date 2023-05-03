package ua.southpost.resource.commons.service;

import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.Map;

@ToString
public class EntitySortOptions {
	@Nonnull
	private final Map<String, String> options;

	public EntitySortOptions(@Nonnull Map<String, String> options) {
		this.options = options;
	}

	@Nonnull
	public Map<String, String> asMap() {
		return options;
	}
}
