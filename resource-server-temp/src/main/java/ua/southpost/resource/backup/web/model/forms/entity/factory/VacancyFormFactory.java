/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import ua.southpost.resource.backup.web.model.forms.entity.VacancyForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Transforms instance of {@link VacancyForm} into (persistent)
 * entity of the {@link Vacancy} type.
 * Created by mchys on 17.03.2018.
 */
@Component
class VacancyFormFactory extends GenericEntityFormFactory<Long, VacancyInfo, VacancyForm> {
	@Resource(name = "vacancyEntityDataService")
	private EntityDataService<VacancyInfo, Long> entityDataService;
	@Resource
	private EmployerFormFactory employerFormFactory;

	@Override
	protected EntityDataService<VacancyInfo, Long> getDataService() {
		return entityDataService;
	}


	@Nonnull
	@Override
	public VacancyForm formFromEntity(@Nonnull VacancyInfo entity) {

		final VacancyForm vacancyForm = new VacancyForm();
		vacancyForm.setEmployer(employerFormFactory.formFromEntity(entity.getEmployer()));
		vacancyForm.setSummary(entity.getSummary());
		vacancyForm.setHosting(entity.getHostingAvailable().getId().isValue());
		vacancyForm.setSalaryLow(entity.getSalaryLow());
		vacancyForm.setSalaryHigh(entity.getSalaryHigh());
		vacancyForm.setDescription(entity.getDescription());
		vacancyForm.setId(entity.getId());

		return vacancyForm;
	}

	@Nonnull
	@Override
	public VacancyForm createNewFormInstance() {
		return new VacancyForm();
	}

}
