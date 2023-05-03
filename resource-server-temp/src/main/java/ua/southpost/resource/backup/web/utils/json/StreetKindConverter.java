/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.utils.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;

/**
 * Converts string value into instance of {@link StreetKind}.
 * Created by mchys on 26.05.2018.
 */
public class StreetKindConverter extends StdConverter<String, StreetKind> {
	@Override
	public StreetKind convert(String value) {
		return EnumEditorSupport.parse(StreetKind.class, value);
	}
}
