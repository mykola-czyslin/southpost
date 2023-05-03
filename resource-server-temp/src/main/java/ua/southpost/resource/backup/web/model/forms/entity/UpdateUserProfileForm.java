/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.web.controller.service.UserProfilePayload;
import ua.southpost.resource.backup.web.model.forms.EditMailAddressUserForm;

import java.util.Set;

/**
 * The the data transfer object those provides profile data.
 * Created by mchys on 11.03.2018.
 */
public class UpdateUserProfileForm extends EditMailAddressUserForm implements UpdateUserForm, UserProfilePayload {
	private long userId;
	private Set<UserActivityKind> userActivities;

	@Override
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public Set<UserActivityKind> getUserActivities() {
		return userActivities;
	}

	public void setUserActivities(Set<UserActivityKind> userActivities) {
		this.userActivities = userActivities;
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
