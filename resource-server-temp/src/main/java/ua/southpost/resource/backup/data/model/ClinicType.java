package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

public enum ClinicType implements MessageKeyHolder {
	AMBULATORY("clinic.type.ambulatory"),
	STATIONARY("clinic.type.stationary"),
	MIXED("clinic.type.mixed");

	@Nonnull
	private final String messageKey;

	ClinicType(@Nonnull String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	@Nonnull
	public String getMessageKey() {
		return messageKey;
	}
}
