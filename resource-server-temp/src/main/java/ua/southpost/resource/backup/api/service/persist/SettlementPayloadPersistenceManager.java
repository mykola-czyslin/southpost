/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.DistrictRepository;
import ua.southpost.resource.backup.data.repo.SettlementRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * Performs settlement form transformation
 * Created by mchys on 17.03.2018.
 */
@Component
@NotFoundMessage(key = NotFoundException.ERR_SETTLEMENT_NOT_FOUND_BY_ID)
class SettlementPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, Settlement, SettlementPayload, SettlementRepository> {
	@Resource
	private SettlementRepository repository;
	@Resource
	private DistrictRepository districtRepository;

	@Override
	protected SettlementRepository getRepository() {
		return repository;
	}

	@Override
	protected Settlement createEntityInstance() {
		return new Settlement();
	}

	@Override
	protected void populateEntity(SettlementPayload payload, Settlement entity, boolean merge, User modifiedBy) {
		entity.setDisplayName(payload.getName());
		entity.setSearchValue(payload.getName());
		entity.setKind(payload.getKind());
		Optional.ofNullable(payload.getDistrictId())
				.filter(id -> id > 0)
				.map(districtRepository::findById)
				.flatMap(o -> o)
				.ifPresent(entity::setDistrict);
	}

	@Override
	protected Optional<Settlement> findUnique(Settlement entity) {
		return repository.findUnique(entity.getDistrict().getId(), entity.getKind(), entity.getSearchValue());
	}

	@Nonnull
	@Override
	protected Settlement merge(@Nonnull Settlement from, @Nonnull Settlement to) {
		to.setDisplayName(from.getDisplayName());
		to.setSearchValue(from.getSearchValue());
		to.setKind(from.getKind());
		to.setDistrict(from.getDistrict());
		return to;
	}


}
