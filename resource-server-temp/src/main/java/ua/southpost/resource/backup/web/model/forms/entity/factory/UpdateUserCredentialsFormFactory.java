package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.forms.entity.UpdateUserCredentialsForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

@Component
class UpdateUserCredentialsFormFactory extends GenericEntityFormFactory<Long, DetailedUserInfo, UpdateUserCredentialsForm> {
	@Resource(name = "userDetailsEntityDataService")
	private EntityDataService<DetailedUserInfo, Long> entityDataService;

	@Override
	protected EntityDataService<DetailedUserInfo, Long> getDataService() {
		return entityDataService;
	}

	@Nonnull
	@Override
	public UpdateUserCredentialsForm formFromEntity(@Nonnull DetailedUserInfo entity) {
		final UpdateUserCredentialsForm newFormInstance = createNewFormInstance();
		newFormInstance.setId(entity.getId());
		newFormInstance.setLogin(entity.getLogin());
		newFormInstance.setFirstName(entity.getFirstName());
		newFormInstance.setLastName(entity.getLastName());
		return newFormInstance;
	}

	@Nonnull
	@Override
	public UpdateUserCredentialsForm createNewFormInstance() {
		return new UpdateUserCredentialsForm();
	}
}
