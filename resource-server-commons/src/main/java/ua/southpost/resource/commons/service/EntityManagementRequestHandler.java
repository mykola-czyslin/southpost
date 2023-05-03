package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

public interface EntityManagementRequestHandler<I, T extends EntityInfo<I>, P extends EntityPayload<I>> {

	@Transactional(propagation = Propagation.REQUIRED)
    AbstractSubmitResponse<I, T> handleSubmitRequest(@Nonnull HttpServletRequest servletRequest, @Nonnull AbstractSubmitRequest<P, I> requestPayload, @Nonnull BindingResult bindingResult);

	@Transactional(propagation = Propagation.REQUIRED)
	void handleRemoveRequest(@Nonnull HttpServletRequest servletRequest, I entityId);
}
