/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.utils.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;

/**
 * Converts string value into {@link SettlementKind}.
 * Created by mchys on 26.05.2018.
 */
public class SettlementKindConverter extends StdConverter<String, SettlementKind> {
	@Override
	public SettlementKind convert(String value) {
		return EnumEditorSupport.parse(SettlementKind.class, value);
	}
}
