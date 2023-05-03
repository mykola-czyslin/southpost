/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EmployerForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Resolves {@link EmployerForm}
 * to {@link EmployerInfo}
 * Created by mchys on 17.03.2018.
 */
@Component
class EmployerFormFactory extends GenericEntityFormFactory<Long, EmployerInfo, EmployerForm> {
	@Resource(name = "employerEntityDataService")
	private EntityDataService<EmployerInfo, Long> entityDataService;
	@Resource
	private SettlementFormFactory settlementFormFactory;
	@Resource
	private ContactFormFactory contactFormFactory;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected EntityDataService<EmployerInfo, Long> getDataService() {
		return entityDataService;
	}

	@Nonnull
	@Override
	public EmployerForm formFromEntity(@Nonnull EmployerInfo entity) {
		final EmployerForm employerForm = new EmployerForm();
		employerForm.setId(entity.getId());
		employerForm.setName(entity.getName());
		employerForm.setWebsite(entity.getWebsite());
		employerForm.setSettlement(settlementFormFactory.formFromEntity(entity.getSettlement()));
		employerForm.setContact(contactFormFactory.formFromEntity(entity));
		logger.debug("Employer form from entity:\n\tentity: {};\n\tform: {}", entity, employerForm);
		return employerForm;
	}

	@Nonnull
	@Override
	public EmployerForm createNewFormInstance() {
		return new EmployerForm();
	}

}
