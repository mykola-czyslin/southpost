package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import ua.southpost.resource.backup.web.model.forms.entity.VacancyForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.VacancyEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class VacancyEntityModelService extends ExtendedRegionalEntityModelService<VacancyEntityViewModel, VacancyInfo, VacancyForm, Long> {
	@Resource(name = "vacancyFormFactory")
	private EntityFormFactory<Long, VacancyInfo, VacancyForm> formFactory;
	@Resource(name = "vacancyViewScripts")
	private List<String> viewScripts;
	@Resource(name = "vacancyFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected VacancyEntityViewModel createEntityViewModel() {
		return new VacancyEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, VacancyInfo, VacancyForm> formFactory() {
		return formFactory;
	}

	@Nonnull
	@Override
	protected List<String> viewScripts() {
		return viewScripts;
	}

	@Nonnull
	@Override
	protected List<String> formScripts() {
		return formScripts;
	}
}
