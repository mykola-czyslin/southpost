/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.PhoneSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.southpost.resource.backup.data.model.Phone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Manages API requests related to {@link Phone} entity.
 * Created by mchys on 16.09.2018.
 */
@RestController
@RequestMapping("/api/contact/phone")
public class PhoneManagementEndpoint {
	@Resource(name = "phoneEntityLookupService")
	private EntityLookupService<PhoneSearchRequestPayload, PhoneInfo, Long> lookupService;
	@Resource(name = "phoneEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, PhoneInfo, PhonePayload> entityManagementRequestHandler;

	@PostMapping(value = "/lookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LookupResponse<PhoneInfo> lookup(@RequestBody PhoneSearchRequestPayload searchPayload, Locale locale) {
		return lookupService.lookup(searchPayload, locale);
	}

	@PutMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, PhoneInfo> save(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<PhonePayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping("/{phoneId}/remove")
	public void remove(HttpServletRequest request, @PathVariable("phoneId") long phoneId) {
		entityManagementRequestHandler.handleRemoveRequest(request, phoneId);
	}
}
