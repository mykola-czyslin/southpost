/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

/**
 * The surrogate of the boolean type.
 * Created by mchys on 24.02.2018.
 */
public enum NoYes implements MessageKeyHolder {
	NO(false, "flag.NO"), YES(true, "flag.YES");

	private final boolean value;
	@Nonnull
	private final String messageKey;

	NoYes(boolean value, @Nonnull String messageKey) {
		this.value = value;
		this.messageKey = messageKey;
	}

	public boolean isValue() {
		return value;
	}

	@Override
	@Nonnull
	public String getMessageKey() {
		return messageKey;
	}
}
