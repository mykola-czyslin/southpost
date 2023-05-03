package ua.southpost.resource.commons.service;

import lombok.Data;
import ua.southpost.resource.commons.model.dto.EntityInfo;

@Data
public class TestEntityInfo implements EntityInfo<Long> {
	private Long id;
	private String textValue;
}
