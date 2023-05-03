/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * The search form abstraction for the {@link User} entity.
 * Created by mchys on 22.09.2018.
 */
public class UserSearchFormImpl extends AbstractPagedSearchForm implements UserSearchForm {
	private static final Set<UserRole> EFFECTIVE_NO_ROLES = Collections.singleton(UserRole.VIEWER);
	private static final Set<UserActivityKind> EFFECTIVE_NO_ACTIVITIES = Collections.singleton(UserActivityKind.VISITOR);
	private String loginPattern;
	private String emailPattern;
	private String firstNamePattern;
	private String lastNamePattern;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date registeredAtFrom;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date registeredAtTo;
	private Set<UserRole> roles;
	private Set<UserActivityKind> declaredActivities;
	private Set<UserActivityKind> confirmedActivities;
	private boolean anyRole;
	private boolean anyDeclaredActivity;
	private boolean anyConfirmedActivity;

	@Override
	public boolean anyRoleAcceptable() {
		return anyRole || isEmpty(roles);
	}

	@Override
	public Set<UserRole> userRoles() {
		return anyRoleAcceptable() ? EFFECTIVE_NO_ROLES : roles;
	}

	@Override
	public boolean anyDeclaredActivityAcceptable() {
		return anyDeclaredActivity || isEmpty(declaredActivities);
	}

	@Override
	public Set<UserActivityKind> userDeclaredActivities() {
		return anyDeclaredActivityAcceptable() ? EFFECTIVE_NO_ACTIVITIES : declaredActivities;
	}

	@Override
	public boolean anyConfirmedActivityAcceptable() {
		return anyConfirmedActivity || isEmpty(confirmedActivities);
	}

	@Override
	public Set<UserActivityKind> userConfirmedActivities() {
		return anyConfirmedActivityAcceptable() ? EFFECTIVE_NO_ACTIVITIES : confirmedActivities;
	}

	public UserSearchFormImpl() {
	}

	public UserSearchFormImpl(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	@Override
	public String getLoginPattern() {
		return loginPattern;
	}

	public void setLoginPattern(String loginPattern) {
		this.loginPattern = loginPattern;
	}

	@Override
	public String getEmailPattern() {
		return emailPattern;
	}

	public void setEmailPattern(String emailPattern) {
		this.emailPattern = emailPattern;
	}

	@Override
	public String getFirstNamePattern() {
		return firstNamePattern;
	}

	public void setFirstNamePattern(String firstNamePattern) {
		this.firstNamePattern = firstNamePattern;
	}

	@Override
	public String getLastNamePattern() {
		return lastNamePattern;
	}

	public void setLastNamePattern(String lastNamePattern) {
		this.lastNamePattern = lastNamePattern;
	}

	@Override
	public Date getRegisteredAtFrom() {
		return registeredAtFrom;
	}

	public void setRegisteredAtFrom(Date registeredAtFrom) {
		this.registeredAtFrom = registeredAtFrom;
	}

	@Override
	public Date getRegisteredAtTo() {
		return registeredAtTo;
	}

	public void setRegisteredAtTo(Date registeredAtTo) {
		this.registeredAtTo = registeredAtTo;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Set<UserActivityKind> getDeclaredActivities() {
		return declaredActivities;
	}

	public void setDeclaredActivities(Set<UserActivityKind> declaredActivities) {
		this.declaredActivities = declaredActivities;
	}

	public Set<UserActivityKind> getConfirmedActivities() {
		return confirmedActivities;
	}

	public void setConfirmedActivities(Set<UserActivityKind> confirmedActivities) {
		this.confirmedActivities = confirmedActivities;
	}

	public boolean isAnyRole() {
		return anyRole;
	}

	public void setAnyRole(boolean anyRole) {
		this.anyRole = anyRole;
	}

	public boolean isAnyDeclaredActivity() {
		return anyDeclaredActivity;
	}

	public void setAnyDeclaredActivity(boolean anyDeclaredActivity) {
		this.anyDeclaredActivity = anyDeclaredActivity;
	}

	public boolean isAnyConfirmedActivity() {
		return anyConfirmedActivity;
	}

	public void setAnyConfirmedActivity(boolean anyConfirmedActivity) {
		this.anyConfirmedActivity = anyConfirmedActivity;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.appendSuper(super.toString())
				.append("loginPattern", loginPattern)
				.append("emailPattern", emailPattern)
				.append("firstNamePattern", firstNamePattern)
				.append("lastNamePattern", lastNamePattern)
				.append("registeredAtFrom", registeredAtFrom)
				.append("registeredAtTo", registeredAtTo)
				.append("roles", roles)
				.append("declaredActivities", declaredActivities)
				.append("confirmedActivities", confirmedActivities)
				.append("anyRole", anyRole)
				.append("anyDeclaredActivity", anyDeclaredActivity)
				.append("anyConfirmedActivity", anyConfirmedActivity)
				.toString();
	}
}
