package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EmployerForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.EmployerEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class EmployerEntityModelService extends ExtendedRegionalEntityModelService<EmployerEntityViewModel, EmployerInfo, EmployerForm, Long> {
	@Resource(name = "employerFormFactory")
	private EntityFormFactory<Long, EmployerInfo, EmployerForm> formFactory;
	@Resource(name = "employerViewScripts")
	private List<String> viewScripts;
	@Resource(name = "employerFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected EmployerEntityViewModel createEntityViewModel() {
		return new EmployerEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, EmployerInfo, EmployerForm> formFactory() {
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
