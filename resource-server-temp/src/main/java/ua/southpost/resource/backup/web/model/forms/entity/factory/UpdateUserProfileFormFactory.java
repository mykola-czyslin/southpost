package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.forms.entity.UpdateUserProfileForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import ua.southpost.resource.backup.web.service.entity.UserDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
class UpdateUserProfileFormFactory extends GenericEntityFormFactory<Long, DetailedUserInfo, UpdateUserProfileForm> {
	@Resource(name = "localUserDataService")
	private UserDataService userDataService;

	@Override
	protected EntityDataService<DetailedUserInfo, Long> getDataService() {
		return userDataService;
	}

	@Nonnull
	@Override
	public UpdateUserProfileForm createNewFormInstance() {
		return new UpdateUserProfileForm();
	}

	@Nonnull
	@Override
	public UpdateUserProfileForm formFromEntity(@Nonnull DetailedUserInfo entity) {
		final UpdateUserProfileForm newFormInstance = createNewFormInstance();
		newFormInstance.setId(entity.getId());
		newFormInstance.setLogin(entity.getLogin());
		newFormInstance.setEmail(entity.getEmail());
		newFormInstance.setFirstName(entity.getFirstName());
		newFormInstance.setLastName(entity.getLastName());
		newFormInstance.setUserActivities(ofNullable(entity.getDeclaredActivities()).map(this::flatten).orElseGet(Collections::emptySet));
		return newFormInstance;
	}

	@Nonnull
	private <E extends Enum<?>> Set<E> flatten(@Nonnull Set<AbstractEntityInfo<E>> wrapped) {
		return wrapped.stream().map(AbstractEntityInfo::getId).collect(Collectors.toSet());
	}


}
