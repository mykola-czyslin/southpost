package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo;
import ua.southpost.resource.backup.web.model.forms.entity.LawyerAgencyForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.LawyerAgencyEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class LawyerAgencyEntityModelService extends ExtendedRegionalEntityModelService<LawyerAgencyEntityViewModel, LawyerAgencyInfo, LawyerAgencyForm, Long> {

	@Resource(name = "lawyerAgencyFormFactory")
	private EntityFormFactory<Long, LawyerAgencyInfo, LawyerAgencyForm> entityFormFactory;
	@Resource(name = "lawyerViewScripts")
	private List<String> viewScripts;
	@Resource(name = "lawyerFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected LawyerAgencyEntityViewModel createEntityViewModel() {
		return new LawyerAgencyEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, LawyerAgencyInfo, LawyerAgencyForm> formFactory() {
		return entityFormFactory;
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
