package ua.southpost.resource.backup.api.model.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.UserSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractPagedSearchRequestPayload;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserSearchRequestPayloadImpl extends AbstractPagedSearchRequestPayload implements UserSearchRequestPayload {
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
}
