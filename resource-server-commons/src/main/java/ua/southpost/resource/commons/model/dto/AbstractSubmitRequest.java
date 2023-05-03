/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.commons.model.SubmitActionType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Abstract submit entity form request.
 * Created by mchys on 29.04.2018.
 */
public class AbstractSubmitRequest<T extends EntityPayload<I>, I> {
	@JsonProperty("action")
	@NotNull(message = "err.submit.empty.action")
	private SubmitActionType action;
	@JsonProperty("data")
	@Valid
	@NotNull(message = "err.form.data.absent")
	private T data;

	public SubmitActionType getAction() {
		return action;
	}

	public void setAction(SubmitActionType action) {
		this.action = action;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
