package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class ClinicInfoMapper implements LocalizedEntityMapper<Clinic, ClinicInfo, Long> {
	@Resource
	private ContactInfoMapper contactInfoMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;
	@Resource
	private ModificationTrackingEntityInfoMapperHelper trackingInfoMapperHelper;

	@Nonnull
	@Override
	public ClinicInfo map(@Nonnull Clinic clinic, @Nonnull Locale locale) {
		ClinicInfo clinicInfo = new ClinicInfo();
		clinicInfo.setId(clinic.getId());
		clinicInfo.setTextValue(clinic.getDisplayName());
		clinicInfo.setClinicName(clinic.getDisplayName());
		clinicInfo.setDescription(clinic.getDescription());
		clinicInfo.setClinicType(keyHolderMapper.map(clinic.getClinicType(), locale));
		contactInfoMapper.mapContactData(clinic, clinicInfo, locale);
		ofNullable(clinic.getServices()).ifPresent(services -> clinicInfo.setServices(services.stream().map(service -> keyHolderMapper.map(service, locale)).collect(Collectors.toSet())));
		trackingInfoMapperHelper.populateTrackingFields(clinic, clinicInfo, locale);
		return clinicInfo;
	}

}
