/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Handles requests to settlement data.
 * Created by mchys on 17.03.2018.
 */
@RestController
@RequestMapping(value = "/api/settlement")
public class SettlementManagementEndpoint {
	@Resource(name = "settlementEntityLookupService")
	private EntityLookupService<SettlementSearchRequestPayload, SettlementInfo, Long> lookupService;
	@Resource(name = "settlementEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, SettlementInfo, SettlementPayload> entityManagementRequestHandler;

	@PostMapping(value = "/lookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LookupResponse<SettlementInfo> lookup(@RequestBody SettlementSearchRequestPayload searchPayload, Locale locale) {
		return lookupService.lookup(searchPayload, locale);
	}

	@PutMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, SettlementInfo> save(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<SettlementPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping("/{settlement_id}/remove")
	public void remove(HttpServletRequest request, @PathVariable("settlement_id") long settlementId) {
		entityManagementRequestHandler.handleRemoveRequest(request, settlementId);
	}
}
