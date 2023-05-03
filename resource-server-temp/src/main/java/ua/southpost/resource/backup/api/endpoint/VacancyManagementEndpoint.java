/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.backup.api.model.submit.VacancyPayload;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
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
 * The API endpoint those handles vacation management requests.
 * Created by mchys on 31.03.2018.
 */
@RestController
public class VacancyManagementEndpoint {
	@Resource(name = "vacancyEntityManagementRequestHandler")
	private EntityManagementRequestHandler<Long, VacancyInfo, VacancyPayload> entityManagementRequestHandler;


	@PutMapping(value = "/api/vacancy/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public AbstractSubmitResponse<Long, VacancyInfo> submit(HttpServletRequest request, @RequestBody @Valid AbstractSubmitRequest<VacancyPayload, Long> submitRequest, BindingResult bindingResult) {
		return entityManagementRequestHandler.handleSubmitRequest(request, submitRequest, bindingResult);
	}

	@DeleteMapping("/api/vacancy/{vacancy_id}/delete")
	public void remove(HttpServletRequest servletRequest, @PathVariable(name = "vacancy_id") long vacancyId) {
		entityManagementRequestHandler.handleRemoveRequest(servletRequest, vacancyId);
	}

}
