package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.ClinicPayload;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.ClinicRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Component
@NotFoundMessage(key = NotFoundException.ERR_CLINIC_NOT_FOUND)
class ClinicPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, Clinic, ClinicPayload, ClinicRepository> {
	@Resource
	private ClinicRepository repository;
	@Resource
	private ContactPayloadPersistenceManager contactPersistenceManager;

	@Override
	protected ClinicRepository getRepository() {
		return repository;
	}

	@Override
	protected Clinic createEntityInstance() {
		return new Clinic();
	}

	@Override
	protected void populateEntity(ClinicPayload payload, Clinic entity, boolean merge, User modifiedBy) {
		entity.setDisplayName(payload.getClinicName());
		entity.setSearchName(payload.getClinicName());
		entity.setClinicType(payload.getClinicType());
		entity.setDescription(payload.getDescription());
		contactPersistenceManager.populateEntity(payload.getContact(), entity, merge, modifiedBy);
		entity.setServices(new HashSet<>(Optional.ofNullable(payload.getServices()).orElseGet(Collections::emptyList)));
		entity.setModifiedBy(modifiedBy);
		entity.setModificationTime(new Date());
	}

	@Override
	protected Optional<Clinic> findUnique(Clinic entity) {
		return repository.findByName(entity.getDisplayName());
	}


	@Nonnull
	@Override
	protected Clinic merge(@Nonnull Clinic from, @Nonnull Clinic to) {
		to.setDisplayName(from.getDisplayName());
		to.setSearchName(from.getSearchName());
		to.setClinicType(from.getClinicType());
		to.setDescription(from.getDescription());
		to.setServices(from.getServices());
		contactPersistenceManager.merge(from, to);
		to.setModifiedBy(from.getModifiedBy());
		to.setModificationTime(from.getModificationTime());
		return to;
	}
}
