/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Generic lookup response.
 * Created by mchys on 26.05.2018.
 */
public class LookupResponse<T> {
	@JsonProperty("options")
	private final List<T> options;
	@JsonProperty("prev_page")
	private final Integer prevPage;
	@JsonProperty("next_page")
	private final Integer nextPage;

	public LookupResponse(List<T> options, Integer prevPage, Integer nextPage) {
		this.options = options;
		this.prevPage = prevPage;
		this.nextPage = nextPage;
	}

	public List<T> getOptions() {
		return options;
	}

	public Integer getPrevPage() {
		return prevPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}
}
