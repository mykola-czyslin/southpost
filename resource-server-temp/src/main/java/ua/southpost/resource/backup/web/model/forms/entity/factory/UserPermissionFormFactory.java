package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.forms.entity.UserPermissionManagementForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import ua.southpost.resource.backup.web.service.entity.UserDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
class UserPermissionFormFactory extends GenericEntityFormFactory<Long, DetailedUserInfo, UserPermissionManagementForm> {
	@Resource(name = "localUserDataService")
	private UserDataService entityDataService;

	@Override
	protected EntityDataService<DetailedUserInfo, Long> getDataService() {
		return entityDataService;
	}

	@Nonnull
	@Override
	public UserPermissionManagementForm formFromEntity(@Nonnull DetailedUserInfo entity) {
		UserPermissionManagementForm form = new UserPermissionManagementForm();
		form.setId(entity.getId());
		ofNullable(entity.getRoles()).map(this::flatten).ifPresent(form::setRoles);
		ofNullable(entity.getDeclaredActivities()).map(this::flatten).ifPresent(form::setDeclaredUserActivities);
		ofNullable(entity.getConfirmedActivities()).map(this::flatten).ifPresent(form::setConfirmedUserActivities);
		return form;
	}

	@Nonnull
	private <E extends Enum<?>> Set<E> flatten(@Nonnull Set<AbstractEntityInfo<E>> wrapped) {
		return wrapped.stream().map(AbstractEntityInfo::getId).collect(Collectors.toSet());
	}

	@Nonnull
	@Override
	public UserPermissionManagementForm createNewFormInstance() {
		return new UserPermissionManagementForm();
	}
}
