package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.forms.entity.LocationForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.LocationEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class LocationEntityModelService extends GenericRegionalEntityModelService<LocationEntityViewModel, LocationInfo, LocationForm, Long> {
	@Resource(name = "locationFormFactory")
	private EntityFormFactory<Long, LocationInfo, LocationForm> formFactory;
	@Resource(name = "locationViewScripts")
	private List<String> viewScripts;
	@Resource(name = "locationFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected LocationEntityViewModel createEntityViewModel() {
		return new LocationEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, LocationInfo, LocationForm> formFactory() {
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
