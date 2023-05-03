package ua.southpost.resource.backup.web.security;

import com.google.common.collect.ImmutableSet;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableSet.of;
import static java.util.Optional.ofNullable;

public class DomainRole {
	static final String ERR_NO_AUTHORITY_PREFIX = String.format("Authority name must starts with \"%1$s\": ", SecurityUtils.ROLE_PREFIX);
	static final String ERR_USER_KIND_DETERMINING_DOMAIN_REQUIRED = "User kind determining domain required";
	static final String ERR_NO_KIND_PREFIX = "Its expected that role name should starts with ";
	static final String ERR_USER_ROLE_PARSE_FAILED = "Fail to parse \"%1$s\" into role";

	@Nonnull
	private final UserRole userRole;
	private final UserActivityKind userActivityKind;
	private final String authorityName;
	private final String roleName;

	private DomainRole(@Nonnull UserRole userRole, @Nullable UserActivityKind userActivityKind) {
		this.userRole = userRole;
		this.userActivityKind = userActivityKind;
		this.authorityName = buildName(SecurityUtils.ROLE_PREFIX);
		this.roleName = buildName("");
	}

	private DomainRole(@Nonnull UserRole userRole) {
		this(userRole, null);
	}

	@Nonnull
	public static Set<DomainRole> fromUser(@Nonnull User user) {
		return ofNullable(user.getRoles()).orElseGet(Collections::emptySet).stream()
				.map(Builder::new).flatMap(b -> b.buildSet(user.getConfirmedActivities()).stream())
				.collect(Collectors.toSet());
	}

	public static DomainRole fromUserRole(@Nonnull UserRole userRole) {
		if(userRole.isDomainDependent()) {
			throw new IllegalArgumentException(ERR_USER_KIND_DETERMINING_DOMAIN_REQUIRED);
		}
		return new DomainRole(userRole);
	}

	public static DomainRole parseAuthorityName(@Nonnull String authorityName) {
		if(!StringUtils.startsWith(authorityName, SecurityUtils.ROLE_PREFIX)) {
			throw new IllegalArgumentException(ERR_NO_AUTHORITY_PREFIX + authorityName);
		}
		final String meaningful = StringUtils.substringAfter(authorityName, SecurityUtils.ROLE_PREFIX);
		return Arrays.stream(UserActivityKind.values())
				.filter(k -> meaningful.startsWith(k.name()))
				.findFirst()
				.map(k -> DomainRole.fromUserRoleAndKind(parseAfter(meaningful, k), k))
				.orElseGet(() -> DomainRole.fromUserRole(parseUserRole(meaningful)));
	}

	private static UserRole parseAfter(@Nonnull String meaningfulAuthorityName, @Nonnull UserActivityKind kind) {
		if(!StringUtils.startsWith(meaningfulAuthorityName, kind.name() + SecurityUtils.DELIMITER)) {
			throw new IllegalArgumentException(ERR_NO_KIND_PREFIX + kind.name() + SecurityUtils.DELIMITER);
		}
		return parseUserRole(StringUtils.substringAfter(meaningfulAuthorityName, kind.name() + SecurityUtils.DELIMITER));
	}

	private static UserRole parseUserRole(@Nonnull String userRoleName) {
		return Arrays.stream(UserRole.values())
				.filter(r -> r.name().equalsIgnoreCase(userRoleName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format(ERR_USER_ROLE_PARSE_FAILED, userRoleName)));
	}

	public static DomainRole fromUserRoleAndKind(@Nonnull UserRole userRole, @Nonnull UserActivityKind userActivityKind) {
		return new Builder(userRole).build(userActivityKind);
	}

	public boolean covers(@Nonnull DomainRole role) {
		return this.userRole.ordinal() > role.userRole.ordinal()
				|| (Objects.equals(this.userActivityKind, role.userActivityKind) && this.userRole == role.userRole);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof DomainRole)) return false;

		DomainRole that = (DomainRole) o;

		return new EqualsBuilder()
				.append(userRole, that.userRole)
				.append(userActivityKind, that.userActivityKind)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(userRole)
				.append(userActivityKind)
				.toHashCode();
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public String getRoleName() {
		return roleName;
	}

	private String buildName(final String prefix) {
		return of(this).stream().filter(dr -> dr.userActivityKind != null).map(dr -> dr.userActivityKind.name()).reduce(prefix, (u, u2) -> u + u2 + SecurityUtils.DELIMITER) +
						userRole.name();
	}

	@Override
	public String toString() {
		return "{" +
				"authorityName: " +
				authorityName + "; " +
				"roleName: " +
				roleName +
				"}";
	}

	public static class Builder {
		@Nonnull
		private final UserRole userRole;


		private Builder(@Nonnull UserRole userRole) {
			this.userRole = userRole;
		}

		public static Builder of(@Nonnull UserRole userRole) {
			return new Builder(userRole);
		}

		public DomainRole build(@Nonnull UserActivityKind kind) {
			return userRole.isDomainDependent() && kind.isRoleDeterminant() ?
					new DomainRole(userRole, kind) : new DomainRole(userRole);
		}

		public Set<DomainRole> buildSet(@Nullable Set<UserActivityKind> kinds) {
			return !userRole.isDomainDependent() ?
					ImmutableSet.of(new DomainRole(this.userRole, null)) :
					ofNullable(kinds).orElseGet(Collections::emptySet)
							.stream()
							.filter(UserActivityKind::isRoleDeterminant)
							.map(k -> new DomainRole(this.userRole, k))
							.collect(Collectors.toSet());
		}
	}
}
