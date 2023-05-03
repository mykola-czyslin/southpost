/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * Maps instance of {@link Street}
 * to {@link StreetInfo}.
 * Created by mchys on 26.05.2018.
 */
@Component
public class StreetInfoMapper implements LocalizedEntityMapper<Street, StreetInfo, Long> {
	@Resource
	private SettlementInfoMapper settlementInfoMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;

	@Nonnull
	@Override
	public StreetInfo map(@Nonnull Street street, @Nonnull Locale locale) {
		StreetInfo streetInfo = new StreetInfo();
		streetInfo.setId(street.getId());
		streetInfo.setSettlement(settlementInfoMapper.map(street.getSettlement(), locale));
		streetInfo.setKind(keyHolderMapper.map(street.getKind(), locale));
		streetInfo.setName(street.getDisplayName());
		streetInfo.setTextValue(
				String.format("%s %s, %s",
						streetInfo.getKind().getTextValue(),
						street.getDisplayName(),
						streetInfo.getSettlement().getTextValue()
				)
		);
		return streetInfo;
	}

}
