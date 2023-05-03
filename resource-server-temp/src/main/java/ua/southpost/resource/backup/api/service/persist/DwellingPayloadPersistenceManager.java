/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.DwellingPayload;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.DwellingRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static ua.southpost.resource.backup.service.NotFoundException.ERR_DWELLING_NOT_FOUND_BY_ID;

/**
 * Performs transformations from {@link DwellingPayload}
 * to {@link Dwelling} entity and vice versa.
 * Created by mchys on 21.04.2018.
 */
@Component
@NotFoundMessage(key = ERR_DWELLING_NOT_FOUND_BY_ID)
class DwellingPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, Dwelling, DwellingPayload, DwellingRepository> {
	@Resource
	private DwellingRepository repository;
	@Resource
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Resource
	private ContactPayloadPersistenceManager contactPersistenceManager;

	@Override
	protected DwellingRepository getRepository() {
		return repository;
	}

	@Override
	protected Dwelling createEntityInstance() {
		return new Dwelling();
	}

	@Override
	protected void populateEntity(DwellingPayload payload, Dwelling entity, boolean merge, User modifiedBy) {
		entity.setSettlement(settlementPersistenceManager.persist(payload.getSettlement(), merge, modifiedBy));
		entity.setSettlementArea(payload.getSettlementArea());
		entity.setSettlementAreaSearch(payload.getSettlementArea());
		entity.setKind(payload.getDwellingKind());
		entity.setNumberOfRooms(Math.max(payload.getNumberOfRooms(), 0));
		entity.setTotalArea(ofNullable(payload.getTotalArea()).orElse(BigDecimal.ZERO));
		entity.setLivingArea(ofNullable(payload.getLivingArea()).orElse(BigDecimal.ZERO));
		entity.setPrice(payload.getPrice());
		entity.setBillingPeriod(payload.getBillingPeriod());
		entity.setDescription(payload.getDescription());
		contactPersistenceManager.populateEntity(payload.getContact(), entity, merge, modifiedBy);
		entity.setModifiedBy(modifiedBy);
		entity.setModificationTime(new Date());
	}

	@Override
	protected Optional<Dwelling> findUnique(Dwelling entity) {
		return repository.findUnique(
				entity.getSettlement().getId(),
				entity.getSettlementArea(),
				entity.getKind(),
				ofNullable(entity.getLocationAddress())
						.map(Location::getId)
						.orElse(Long.MIN_VALUE)
		);
	}

	@Nonnull
	@Override
	protected Dwelling merge(@Nonnull Dwelling from, @Nonnull Dwelling to) {
		to.setSettlement(settlementPersistenceManager.merge(from.getSettlement(), to.getSettlement()));
		to.setSettlementArea(from.getSettlementArea());
		to.setSettlementAreaSearch(from.getSettlementAreaSearch());
		to.setKind(from.getKind());
		to.setNumberOfRooms(from.getNumberOfRooms());
		to.setTotalArea(from.getTotalArea());
		to.setLivingArea(from.getLivingArea());
		to.setPrice(from.getPrice());
		to.setBillingPeriod(from.getBillingPeriod());
		to.setDescription(from.getDescription());
		contactPersistenceManager.merge(from, to);
		to.setModifiedBy(from.getModifiedBy());
		to.setModificationTime(from.getModificationTime());
		return to;
	}
}
