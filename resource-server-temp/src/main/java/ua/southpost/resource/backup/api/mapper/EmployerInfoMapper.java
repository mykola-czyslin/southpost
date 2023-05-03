/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * Maps {@link Employer} to {@link EmployerInfo}.
 * Created by mchys on 18.03.2018.
 */
@Component
public class EmployerInfoMapper implements LocalizedEntityMapper<Employer, EmployerInfo, Long> {
	@Resource
	private SettlementInfoMapper settlementInfoMapper;
	@Resource
	private ContactInfoMapper contactInfoMapper;
	@Resource
	private ModificationTrackingEntityInfoMapperHelper trackingInfoMapperHelper;

	@Nonnull
	@Override
	public EmployerInfo map(@Nonnull Employer employer, @Nonnull Locale locale) {
		EmployerInfo employerInfo = new EmployerInfo();
		employerInfo.setId(employer.getId());
		employerInfo.setName(employer.getDisplayName());
		employerInfo.setWebsite(employer.getWebsite());
		employerInfo.setSettlement(settlementInfoMapper.map(employer.getSettlement(), locale));
		contactInfoMapper.mapContactData(employer, employerInfo, locale);
		employerInfo.setTextValue(employer.getDisplayName());
		trackingInfoMapperHelper.populateTrackingFields(employer, employerInfo, locale);
		return employerInfo;
	}

}
