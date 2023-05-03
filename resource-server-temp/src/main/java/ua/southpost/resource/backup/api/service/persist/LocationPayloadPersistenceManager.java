/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import org.springframework.stereotype.Service;
import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.commons.model.entity.AddressEntity;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.LocationRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.model.forms.entity.LocationForm;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * Transforms instance of the {@link LocationForm}
 * into respective instance of the {@link Location} entity.
 * Created by mchys on 17.03.2018.
 */
@Service
@NotFoundMessage(key = NotFoundException.ERR_LOCATION_NOT_FOUND_BY_ID)
class LocationPayloadPersistenceManager<AI>
        extends AbstractEntityPayloadPersistenceManager<AI, AddressEntity<AI>, LocationPayload<AI>, LocationRepository> {

    @Resource
    private LocationRepository repository;
    @Resource
    private StreetPayloadPersistenceManager streetPersistenceManager;

    @Override
    protected LocationRepository getRepository() {
        return repository;
    }

    @Override
    protected AddressEntity<Long> createEntityInstance() {
        return new Location();
    }

    @Override
    protected void populateEntity(LocationPayload payload, Location entity, boolean merge, User modifiedBy) {
        entity.setStreet(streetPersistenceManager.persist(payload.getStreet(), merge, modifiedBy));
        entity.setPostalCode(payload.getPostalCode());
        entity.setStreetNumber(payload.getStreetNumber());
        entity.setSearchStreetNumber(payload.getStreetNumber());
        entity.setBlockNumber(payload.getBlockNumber());
        entity.setSearchBlockNumber(payload.getBlockNumber());
        entity.setRoomNumber(payload.getRoomNumber());
        entity.setSearchRoomNumber(payload.getRoomNumber());
    }

    @Override
    protected Optional<Location> findUnique(Location entity) {
        return repository.findByIdentity(
                entity.getStreet().getId(),
                entity.getStreetNumber(),
                entity.getBlockNumber(),
                entity.getRoomNumber()
        );
    }


    @Nonnull
    @Override
    protected Location merge(@Nonnull Location from, @Nonnull Location to) {
        to.setRoomNumber(from.getRoomNumber());
        to.setSearchRoomNumber(from.getSearchRoomNumber());
        to.setBlockNumber(from.getBlockNumber());
        to.setSearchBlockNumber(from.getSearchBlockNumber());
        to.setStreetNumber(from.getStreetNumber());
        to.setSearchStreetNumber(from.getSearchStreetNumber());
        to.setPostalCode(from.getPostalCode());
        to.setStreet(streetPersistenceManager.merge(from.getStreet(), to.getStreet()));
        return to;
    }
}
