package ua.southpost.resource.commons.service;

import lombok.Data;
import ua.southpost.resource.commons.model.entity.Identity;

@Data
public class TestIdentity implements Identity<Long> {
	private Long id;
}
