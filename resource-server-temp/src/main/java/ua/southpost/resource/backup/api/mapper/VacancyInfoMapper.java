package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

@Component
public class VacancyInfoMapper implements LocalizedEntityMapper<Vacancy, VacancyInfo, Long> {
	@Resource
	private ModificationTrackingEntityInfoMapperHelper trackingInfoMapperHelper;
	@Resource
	private EmployerInfoMapper employerInfoMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;

	@Nonnull
	@Override
	public VacancyInfo map(@Nonnull Vacancy entity, @Nonnull Locale locale) {
		final VacancyInfo vacancyInfo = new VacancyInfo();
		vacancyInfo.setId(entity.getId());
		vacancyInfo.setSummary(entity.getSummary());
		vacancyInfo.setDescription(entity.getDescription());
		vacancyInfo.setEmployer(employerInfoMapper.map(entity.getEmployer(), locale));
		vacancyInfo.setHostingAvailable(keyHolderMapper.map(entity.getHostingAvailable(), locale));
		vacancyInfo.setSalaryLow(entity.getSalaryLow());
		vacancyInfo.setSalaryHigh(entity.getSalaryHigh());
		trackingInfoMapperHelper.populateTrackingFields(entity, vacancyInfo, locale);
		return vacancyInfo;
	}
}
