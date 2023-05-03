package ua.southpost.resource.backup.web.controller.service;

import ua.southpost.resource.backup.data.model.UserActivityKind;

import java.util.Set;

public interface UserProfilePayload {
	long getUserId();

	String getLogin();

	String getEmail();

	String getFirstName();

	String getLastName();

	Set<UserActivityKind> getUserActivities();
}
