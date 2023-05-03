/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

/**
 * Defines a set of supported dwelling kinds.
 * Created by mchys on 19.04.2018.
 */
public enum DwellingKind implements MessageKeyHolder {
	HOSTEL("dwelling.kind.hostel"),
	ROOM("dwelling.kind.room"),
	BACHELOR_FLAT("dwelling.kind.bachelor.flat"),
	FLAT("dwelling.kind.flat"),
	HOUSE("dwelling.kind.house");

	@Nonnull
	private final String messageKey;

	DwellingKind(@Nonnull String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	@Nonnull
	public String getMessageKey() {
		return messageKey;
	}
}
