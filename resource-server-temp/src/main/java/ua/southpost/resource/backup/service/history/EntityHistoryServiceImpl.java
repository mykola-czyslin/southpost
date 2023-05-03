/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.service.history;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.backup.data.model.EntityHistory;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.EntityHistoryRepository;
import ua.southpost.resource.backup.service.history.domain.HistoryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An implementation of the {@link EntityHistoryService} using {@link EntityHistoryRepository}.
 * Created by mchys on 03.12.2018.
 */
@Component
class EntityHistoryServiceImpl implements EntityHistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityHistoryService.class.getSimpleName());

	@Resource
	private EntityHistoryRepository repository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public <I, E extends Identity<I>> void add(@Nonnull E entity, @Nonnull ChangeType changeType, @Nonnull User changedBy) {
		EntityHistory history = new EntityHistory();
		history.setChangeType(changeType);
		history.setEntityType(entity.getClass().getName());
		history.setEntityId(idString(entity.getId()));
		history.setChangedBy(changedBy);
		history.setChangeTime(new Date());
		try {
			history.setEntityValue(objectMapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			LOGGER.warn("Fail to serialize entity", e);
			history.setEntityValue(entity.toString());
		}
		repository.save(history);

	}

	@Override
	public <I, E extends Identity<I>> long historyDepth(@Nonnull E entity, @Nullable Long changeUserId, @Nullable ChangeType changeType) {
		return repository.count(entity.getClass().getName(), idString(entity.getId()), changeUserId, changeType);
	}

	@Override
	public <I, E extends Identity<I>> List<HistoryRecord<I, E>> historyOf(@Nonnull E entity, @Nullable Long changeUserId, @Nullable ChangeType changeType, Pageable pageable) {
		@SuppressWarnings("unchecked") final Class<E> entityType = (Class<E>) entity.getClass();
		return repository.list(entityType.getName(), idString(entity.getId()), changeUserId, changeType, pageable)
				.stream()
				.map(h -> this.transform(h, entityType))
				.collect(Collectors.toList());
	}

	@Override
	public <I, E extends Identity<I>> long historyDepth(@Nonnull Class<E> entityType, @Nullable Long changeUserId, @Nullable ChangeType changeType) {
		return repository.count(entityType.getName(), changeUserId, changeType);
	}

	@Override
	public <I, E extends Identity<I>> List<HistoryRecord<I, E>> historyOf(@Nonnull Class<E> entityType, @Nullable Long changeUserId, @Nullable ChangeType changeType, Pageable pageable) {
		return repository.list(entityType.getName(), changeUserId, changeType, pageable)
				.stream()
				.map(h -> this.transform(h, entityType))
				.collect(Collectors.toList());
	}

	private <I> String idString(@Nonnull I id) {
		if (id instanceof String) {
			return (String) id;
		} else if (id instanceof Long) {
			return String.format("%d", id);
		} else {
			return id.toString();
		}
	}

	private <I, E extends Identity<I>> E deserialize(@Nonnull EntityHistory history, @Nonnull Class<E> dataType) {
		try {
			return objectMapper.readValue(history.getEntityValue(), dataType);
		} catch (IOException e) {
			LOGGER.error(
					String.format("Fail to deserialize to the entity of %1$s type:\n%2$s", dataType, history),
					e
			);
			return null;
		}
	}

	private <I, E extends Identity<I>> HistoryRecord<I, E> transform(@Nonnull EntityHistory history, @Nonnull Class<E> dataType) {
		final HistoryRecord<I, E> record = new HistoryRecord<>();
		record.setId(history.getId());
		record.setChangeType(history.getChangeType());
		record.setChangeDateTime(history.getChangeTime());
		record.setChangedBy(history.getChangedBy());
		record.setEntity(deserialize(history, dataType));
		return record;
	}
}
