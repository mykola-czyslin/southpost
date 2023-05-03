package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EmailAddressForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.EmailEntityViewModel;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

@Service
class EmailEntityModelService extends GenericEntityModelService<EmailEntityViewModel, EmailInfo, EmailAddressForm, Long> {
	@Resource(name = "emailAddressFormFactory")
	private EntityFormFactory<Long, EmailInfo, EmailAddressForm> formFactory;
	@Resource(name = "emailViewScripts")
	private List<String> viewScripts;
	@Resource(name = "emailFormScripts")
	private List<String> formScripts;

	@Nonnull
	@Override
	protected EmailEntityViewModel createEntityViewModel() {
		return new EmailEntityViewModel();
	}

	@Nonnull
	@Override
	protected EntityFormFactory<Long, EmailInfo, EmailAddressForm> formFactory() {
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
