package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface EntityPayloadPersistenceManager<I, E extends Identity<I>, S extends EntityPayload<I>> {
	@Transactional(propagation = Propagation.REQUIRED)
	E persist(S payload, boolean merge, User modifiedBy);

	@Transactional(propagation = Propagation.REQUIRED)
	E remove(I id);
}
