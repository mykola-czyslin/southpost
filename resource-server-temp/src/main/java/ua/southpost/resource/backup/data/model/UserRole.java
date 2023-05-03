package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

/**
 * Defines a set of supported user roles.
 * Created by mchys on 16.02.2018.
 */
public enum UserRole implements MessageKeyHolder {
	VIEWER("role.VIEWER"),
	OPERATOR("role.OPERATOR", true),
	ADMINISTRATOR("role.ADMINISTRATOR"),
	SUPERVISOR("role.SUPERVISOR");

	@Nonnull
	private final String messageKey;
	private final boolean domainDependent;


	UserRole(@Nonnull String messageKey) {
		this(messageKey, false);
	}

	UserRole(@Nonnull String messageKey, boolean domainDependent) {
		this.messageKey = messageKey;
		this.domainDependent = domainDependent;
	}

	@Nonnull
	@Override
	public String getMessageKey() {
		return messageKey;
	}

	public boolean isDomainDependent() {
		return domainDependent;
	}
}
