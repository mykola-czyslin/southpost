package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.PhoneEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class PhoneEntityModelService extends GenericEntityModelService<PhoneEntityViewModel, PhoneInfo, PhoneForm, Long> {
	@Resource(name = "phoneFormFactory")
	private EntityFormFactory<Long, PhoneInfo, PhoneForm> formFactory;
	@Resource(name = "phoneViewScripts")
	private List<String> viewScripts;
	@Resource(name = "phoneFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected PhoneEntityViewModel createEntityViewModel() {
		return new PhoneEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, PhoneInfo, PhoneForm> formFactory() {
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
