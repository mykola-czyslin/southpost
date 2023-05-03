/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.support;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;

/**
 * The editor support
 * Created by mchys on 19.04.2018.
 */
public class EnumEditorSupport<T extends Enum<?>> extends PropertyEditorSupport {

	private final Class<T> enumType;

	public EnumEditorSupport(Class<T> enumType) {
		if (enumType == null) {
			throw new IllegalArgumentException("EnumType must be provided");
		}
		this.enumType = enumType;
	}

	public static <S extends Enum<?>> S parse(Class<S> enumType, String value) {
		return Arrays.stream(enumType.getEnumConstants())
				.filter(c -> c.name().equalsIgnoreCase(value))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(parse(this.enumType, text));
	}
}
