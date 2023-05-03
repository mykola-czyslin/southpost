/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

/**
 * Defines set of supported street kinds.
 * Created by mchys on 23.02.2018.
 */
public enum StreetKind implements MessageKeyHolder {

	block("квартал", "street.kind.block"),
	square("площа", "street.kind.square"),
	avenue("проспект", "street.kind.avenue"),
	boulevard("бульвар", "street.kind.boulevard"),
	lane("провулок", "street.kind.lane"),
	road("шлях", "street.kind.road"),
	drive("шосе", "street.kind.drive"),
	upway("в'їзд", "street.kind.upway"),
	downway("спуск", "street.kind.downway"),
	street("вулиця", "street.kind.street"),
	embankment("набережна", "street.kind.embankment");

	@Nonnull
	private final String nativeName;
	@Nonnull
	private final String messageKey;

	StreetKind(@Nonnull String nativeName, @Nonnull String messageKey) {
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
