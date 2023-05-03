package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.ClinicPayload;
import ua.southpost.resource.backup.data.repo.ClinicRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClinicEntityValidator implements ConstraintValidator<ClinicEntity, ClinicPayload> {
	@Resource
	private ClinicRepository repository;

	@Override
	public boolean isValid(ClinicPayload value, ConstraintValidatorContext context) {
		return value != null && (value.getId() == null || repository.findById(value.getId()).isPresent());
	}
}
