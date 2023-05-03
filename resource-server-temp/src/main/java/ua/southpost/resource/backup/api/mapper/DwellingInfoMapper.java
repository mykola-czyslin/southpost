package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

@Component
public class DwellingInfoMapper implements LocalizedEntityMapper<Dwelling, DwellingInfo, Long> {
	@Resource
	private SettlementInfoMapper settlementInfoMapper;
	@Resource
	private ContactInfoMapper contactMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;
	@Resource
	private ModificationTrackingEntityInfoMapperHelper trackingInfoMapperHelper;

	@Nonnull
	@Override
	public DwellingInfo map(@Nonnull Dwelling entity, @Nonnull Locale locale) {
		final DwellingInfo entityInfo = new DwellingInfo();
		entityInfo.setId(entity.getId());
		entityInfo.setSettlement(settlementInfoMapper.map(entity.getSettlement(), locale));
		entityInfo.setSettlementArea(entity.getSettlementArea());
		entityInfo.setDescription(entity.getDescription());
		contactMapper.mapContactData(entity, entityInfo, locale);
		entityInfo.setKind(keyHolderMapper.map(entity.getKind(), locale));
		entityInfo.setNumberOfRooms(entity.getNumberOfRooms());
		entityInfo.setTotalArea(entity.getTotalArea());
		entityInfo.setLivingArea(entity.getLivingArea());
		entityInfo.setPrice(entity.getPrice());
		entityInfo.setBillingPeriod(keyHolderMapper.map(entity.getBillingPeriod(), locale));
		trackingInfoMapperHelper.populateTrackingFields(entity, entityInfo, locale);
		return entityInfo;
	}

}
