/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.web.model.dto.address.DistrictInfo;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * Maps {@link Settlement} to {@link SettlementInfo}.
 * Created by mchys on 18.03.2018.
 */
@Component
public class SettlementInfoMapper implements LocalizedEntityMapper<Settlement, SettlementInfo, Long> {
	@Resource
	private DistrictInfoMapper districtInfoMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;

	@Nonnull
	@Override
	public SettlementInfo map(@Nonnull Settlement settlement, @Nonnull Locale locale) {
		return map(settlement, districtInfoMapper.map(settlement.getDistrict(), locale), locale);
	}

	private SettlementInfo map(Settlement settlement, DistrictInfo districtInfo, Locale locale) {
		SettlementInfo settlementInfo = new SettlementInfo();
		settlementInfo.setId(settlement.getId());
		settlementInfo.setName(settlement.getDisplayName());
		settlementInfo.setKind(keyHolderMapper.map(settlement.getKind(), locale));
		settlementInfo.setDistrict(districtInfo);
		settlementInfo.setTextValue(
				String.format(
						"%1$s %2$s ",
						settlementInfo.getKind().getTextValue(),
						settlement.getQualifiedDisplayName()
				)
		);
		return settlementInfo;
	}

}
