package ua.southpost.resource.backup.web.model.forms.search;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;

import java.util.Date;
import java.util.Set;

public interface UserSearchForm extends PagedSearchForm {
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
