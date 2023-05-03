package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;

import java.util.Set;

/**
 * Edit user form
 * Created by mykola on 16.11.16.
 */
public class UserPermissionManagementForm implements UpdateUserForm {
	private long userId;
	private Set<UserRole> roles;
	private Set<UserActivityKind> declaredUserActivities;
	private Set<UserActivityKind> confirmedUserActivities;


	@Override
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}


	@SuppressWarnings("unused")
	public Set<UserRole> getRoles() {
		return roles;
	}

	@SuppressWarnings("WeakerAccess")
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	@SuppressWarnings("unused")
	public Set<UserActivityKind> getDeclaredUserActivities() {
		return declaredUserActivities;
	}

	public void setDeclaredUserActivities(Set<UserActivityKind> declaredUserActivities) {
		this.declaredUserActivities = declaredUserActivities;
	}

	@SuppressWarnings("unused")
	public Set<UserActivityKind> getConfirmedUserActivities() {
		return confirmedUserActivities;
	}

	public void setConfirmedUserActivities(Set<UserActivityKind> confirmedUserActivities) {
		this.confirmedUserActivities = confirmedUserActivities;
	}

	@Override
	public Long getId() {
		return getUserId();
	}

	@Override
	public void setId(Long id) {
		setUserId(id);
	}
}
