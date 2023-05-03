package ua.southpost.resource.backup.web.utils.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;

public class ClinicTypeConverter extends StdConverter<String, ClinicType> {

	@Override
	public ClinicType convert(String value) {
		return EnumEditorSupport.parse(ClinicType.class, value);
	}
}
