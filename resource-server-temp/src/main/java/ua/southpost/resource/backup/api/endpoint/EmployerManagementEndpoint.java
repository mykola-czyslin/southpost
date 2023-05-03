/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.EmployerLookupRequest;
import ua.southpost.resource.backup.api.model.search.EmployerSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Handles requests to {@link Employer} entity;
 * Created by mchys on 18.03.2018.
 */
@RestController
public class EmployerManagementEndpoint {
	@Resource(name = "employerEntityLookupService")
	private EntityLookupService<EmployerSearchRequestPayload, EmployerInfo, Long> lookupService;
	@Resource(name = "employerEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, EmployerInfo, EmployerPayload> entityManagementRequestHandler;


	@PostMapping(value = "/api/employer/lookup", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public LookupResponse<EmployerInfo> lookup(@RequestBody EmployerLookupRequest lookupRequestPayload, Locale locale) {
		return lookupService.lookup(lookupRequestPayload.toSearchPayload(), locale);
	}


	@PutMapping(value = "/api/employer/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public AbstractSubmitResponse<Long, EmployerInfo> submit(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<EmployerPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}


	@DeleteMapping(value = "/api/employer/{employer_id}/remove")
	public void remove(HttpServletRequest request, @PathVariable(name = "employer_id") Long employerId) {
		entityManagementRequestHandler.handleRemoveRequest(request, employerId);
	}
}
