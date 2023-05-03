package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.backup.data.model.ModificationTrackingEntity;
import ua.southpost.resource.commons.model.dto.AbstractModificationTrackingEntityInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

@Component
public class ModificationTrackingEntityInfoMapperHelper {
	@Resource
	private BriefUserInfoMapper briefUserInfoMapper;

	public <E extends ModificationTrackingEntity, EI extends AbstractModificationTrackingEntityInfo<Long>> void populateTrackingFields(@Nonnull E entity, @Nonnull EI entityInfo, @Nonnull Locale locale) {
		entityInfo.setModificationTime(entity.getModificationTime());
		entityInfo.setModifiedBy(briefUserInfoMapper.map(entity.getModifiedBy(), locale));
	}
}
