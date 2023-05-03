package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import ua.southpost.resource.backup.web.model.forms.entity.ClinicForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
class ClinicFormFactory extends GenericEntityFormFactory<Long, ClinicInfo, ClinicForm> {
	@Resource(name = "clinicEntityDataService")
	private EntityDataService<ClinicInfo, Long> entityDataService;
	@Resource
	private ContactFormFactory contactFormFactory;

	@Override
	protected EntityDataService<ClinicInfo, Long> getDataService() {
		return entityDataService;
	}

	@Nonnull
	@Override
	public ClinicForm createNewFormInstance() {
		return new ClinicForm();
	}


	@Nonnull
	@Override
	public ClinicForm formFromEntity(@Nonnull ClinicInfo entity) {
		final ClinicForm clinicForm = new ClinicForm();
		clinicForm.setId(entity.getId());
		clinicForm.setClinicName(entity.getClinicName());
		clinicForm.setClinicType(entity.getClinicType().getId());
		clinicForm.setContact(contactFormFactory.formFromEntity(entity));
		clinicForm.setServices(
				ofNullable(entity.getServices())
						.orElseGet(Collections::emptySet)
						.stream()
						.map(AbstractEntityInfo::getId)
						.map(Enum::name)
						.collect(Collectors.toList())
		);
		return clinicForm;
	}

}
