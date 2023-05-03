/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The abstract submit response.
 * Created by mchys on 29.04.2018.
 */
public class AbstractSubmitResponse<I, T extends EntityInfo<I>> {
	@JsonProperty("succeeded")
	private boolean succeeded;
	@JsonProperty("entity")
	private T entityInfo;
	@JsonProperty("validation_errors")
	private List<ValidationError> validationErrors;

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(@SuppressWarnings("SameParameterValue") boolean succeeded) {
		this.succeeded = succeeded;
	}

	public T getEntityInfo() {
		return entityInfo;
	}

	public void setEntityInfo(T entityInfo) {
		this.entityInfo = entityInfo;
	}

	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
