package ua.southpost.resource.backup.web.utils.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;

public class MedicalServiceConverter extends StdConverter<String, MedicalService> {
	@Override
	public MedicalService convert(String value) {
		return EnumEditorSupport.parse(MedicalService.class, value);
	}
}
