/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.StreetSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
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
 * The street API endpoint.
 * Created by mchys on 15.05.2018.
 */
@RestController
public class StreetManagementEndpoint {
	@Resource(name = "streetEntityLookupService")
	private EntityLookupService<StreetSearchRequestPayload, StreetInfo, Long> lookupService;
	@Resource(name = "streetEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, StreetInfo, StreetPayload> entityManagementRequestHandler;

	@PutMapping(value = "/api/streets/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, StreetInfo> save(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<StreetPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping(value = "/api/streets/{street_id}/remove")
	public void remove(HttpServletRequest request, @PathVariable(name = "street_id") Long streetId) {
		entityManagementRequestHandler.handleRemoveRequest(request, streetId);
	}

	@PostMapping(value = "/api/street/lookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LookupResponse<StreetInfo> lookup(@RequestBody StreetSearchRequestPayload searchPayload, Locale locale) {
		return lookupService.lookup(searchPayload, locale);
	}
}
