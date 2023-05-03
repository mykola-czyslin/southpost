/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.DwellingPayload;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Manages dwelling API requests.
 * Created by mchys on 29.04.2018.
 */
@RestController
public class DwellingManagementEndpoint {
	@Resource(name = "dwellingEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, DwellingInfo, DwellingPayload> entityManagementRequestHandler;

	@PutMapping(value = "/api/dwelling/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, DwellingInfo> submit(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<DwellingPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping("/api/dwelling/{dwelling_id}/delete")
	@ResponseBody
	public void remove(HttpServletRequest servletRequest, @PathVariable(name = "dwelling_id") long dwellingId) {
		entityManagementRequestHandler.handleRemoveRequest(servletRequest, dwellingId);
	}

}
