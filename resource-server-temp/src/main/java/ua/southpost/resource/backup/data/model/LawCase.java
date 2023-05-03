package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

public enum LawCase implements MessageKeyHolder {
	CIVIL("law.case.civil"),
	ADMINISTRATIVE("law.case.administrative"),
	CRIMINAL("law.case.criminal"),
	PROPERTY("law.case.property"),
	FAMILY("law.case.family");

	@Nonnull
	private final String messageKey;

	LawCase(@Nonnull String messageKey) {
		this.messageKey = messageKey;
	}

	@Nonnull
	@Override
	public String getMessageKey() {
		return this.messageKey;
	}
}
