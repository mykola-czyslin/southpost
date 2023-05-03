package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
import ua.southpost.resource.backup.web.model.forms.entity.StreetForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.StreetEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class StreetEntityModelService extends GenericRegionalEntityModelService<StreetEntityViewModel, StreetInfo, StreetForm, Long> {
	@Resource(name = "streetFormFactory")
	private EntityFormFactory<Long, StreetInfo, StreetForm> formFactory;
	@Resource(name = "streetsViewScripts")
	private List<String> viewScripts;
	@Resource(name = "streetsFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected StreetEntityViewModel createEntityViewModel() {
		return new StreetEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, StreetInfo, StreetForm> formFactory() {
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
