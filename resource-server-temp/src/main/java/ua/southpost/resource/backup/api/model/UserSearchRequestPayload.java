package ua.southpost.resource.backup.api.model;

import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;

import java.util.Date;
import java.util.Set;

public interface UserSearchRequestPayload extends PagedSearchRequestPayload {
	boolean anyRoleAcceptable();

	Set<UserRole> userRoles();

	boolean anyDeclaredActivityAcceptable();

	Set<UserActivityKind> userDeclaredActivities();

	boolean anyConfirmedActivityAcceptable();

	Set<UserActivityKind> userConfirmedActivities();

	String getLoginPattern();

	String getEmailPattern();

	String getFirstNamePattern();

	String getLastNamePattern();

	Date getRegisteredAtFrom();

	Date getRegisteredAtTo();
}
