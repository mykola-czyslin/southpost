/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.EmailSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
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
import ua.southpost.resource.backup.data.model.EmailAddress;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Manages API requests related to the {@link EmailAddress} entity.
 * Created by mchys on 16.09.2018.
 */
@RestController
@RequestMapping("/api/contact/email")
public class EmailManagementEndpoint {
	@Resource(name = "emailEntityLookupService")
	private EntityLookupService<EmailSearchRequestPayload, EmailInfo, Long> lookupService;
	@Resource(name = "emailEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, EmailInfo, EmailPayload> entityManagementRequestHandler;

	@PostMapping(value = "/lookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LookupResponse<EmailInfo> lookup(@RequestBody EmailSearchRequestPayload emailSearchPayload, Locale locale) {
		return lookupService.lookup(emailSearchPayload, locale);
	}

	@PutMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, EmailInfo> save(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<EmailPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping("/{emailId}/remove")
	public void remove(HttpServletRequest request, @PathVariable("emailId") long emailId) {
		entityManagementRequestHandler.handleRemoveRequest(request, emailId);
	}

}
