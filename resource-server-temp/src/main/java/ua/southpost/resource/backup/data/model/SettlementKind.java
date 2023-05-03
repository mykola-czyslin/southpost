/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

/**
 * Defines a set of supported settlement kinds.
 * Created by mchys on 23.02.2018.
 */
public enum SettlementKind implements MessageKeyHolder {
	farmstead("хутір", "settlement.kind.farmstead"),
	village("село", "settlement.kind.village"),
	town("селище", "settlement.kind.town"),
	city("місто", "settlement.kind.city");

	@Nonnull
	private final String nativeName;
	@Nonnull
	private final String messageKey;

	SettlementKind(@Nonnull String nativeName, @Nonnull String messageKey) {
		this.nativeName = nativeName;
		this.messageKey = messageKey;
	}

	@Nonnull
	public String getNativeName() {
		return nativeName;
	}

	@Override
	@Nonnull
	public String getMessageKey() {
		return messageKey;
	}
}
