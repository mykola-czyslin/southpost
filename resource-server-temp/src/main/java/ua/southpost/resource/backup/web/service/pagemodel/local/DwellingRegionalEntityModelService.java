package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import ua.southpost.resource.backup.web.model.forms.entity.DwellingForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.DwellingEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class DwellingRegionalEntityModelService extends ExtendedRegionalEntityModelService<DwellingEntityViewModel, DwellingInfo, DwellingForm, Long> {
	@Resource(name = "dwellingFormFactory")
	private EntityFormFactory<Long, DwellingInfo, DwellingForm> formFactory;
	@Resource(name = "dwellingViewScripts")
	private List<String> viewScripts;
	@Resource(name = "dwellingFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected DwellingEntityViewModel createEntityViewModel() {
		return new DwellingEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, DwellingInfo, DwellingForm> formFactory() {
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
