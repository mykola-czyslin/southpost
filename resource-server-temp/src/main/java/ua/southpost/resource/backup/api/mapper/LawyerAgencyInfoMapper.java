package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class LawyerAgencyInfoMapper implements LocalizedEntityMapper<LawyerAgency, LawyerAgencyInfo, Long> {
	@Resource
	private SettlementInfoMapper settlementInfoMapper;
	@Resource
	private ContactInfoMapper contactInfoMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;
	@Resource
	private ModificationTrackingEntityInfoMapperHelper trackingInfoMapperHelper;

	@Nonnull
	@Override
	public LawyerAgencyInfo map(@Nonnull LawyerAgency entity, @Nonnull Locale locale) {
		final LawyerAgencyInfo info = new LawyerAgencyInfo();
		info.setId(entity.getId());
		info.setSettlement(settlementInfoMapper.map(entity.getSettlement(), locale));
		info.setAgencyName(entity.getDisplayName());
		info.setWebSite(entity.getWebSite());
		ofNullable(entity.getCases()).ifPresent(cases -> info.setCases(cases.stream().map(lawCase -> keyHolderMapper.map(lawCase, locale)).collect(Collectors.toSet())));
		contactInfoMapper.mapContactData(entity, info, locale);
		trackingInfoMapperHelper.populateTrackingFields(entity, info, locale);
		return info;
	}
}
