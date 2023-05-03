/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.LocationSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import org.springframework.http.MediaType;
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

/**
 * Manages API request related to location entity.
 * Created by mchys on 22.05.2018.
 */
@RestController
public class LocationManagementEndpoint {
	@Resource(name = "locationEntityLookupService")
	private EntityLookupService<LocationSearchRequestPayload, LocationInfo, Long> lookupService;
	@Resource(name = "locationEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, LocationInfo, LocationPayload> entityManagementRequestHandler;


	@PostMapping(value = "/api/locations/lookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LookupResponse<LocationInfo> lookup(@RequestBody LocationSearchRequestPayload searchPayload, Locale locale) {
		return lookupService.lookup(searchPayload, locale);
	}

	@PutMapping(value = "/api/locations/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, LocationInfo> save(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<LocationPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping(value = "/api/locations/{location_id}/remove")
	public void remove(HttpServletRequest request, @PathVariable(name = "location_id") Long locationId) {
		entityManagementRequestHandler.handleRemoveRequest(request, locationId);
	}

}
