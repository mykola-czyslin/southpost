/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.service.history;

import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.service.history.domain.HistoryRecord;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Defines a contract on performing entity history operation.
 * Created by mchys on 03.12.2018.
 */
public interface EntityHistoryService {

	<I, E extends Identity<I>> void add(@Nonnull E entity, @Nonnull ChangeType changeType, @Nonnull User changedBy);

	<I, E extends Identity<I>> long historyDepth(@Nonnull E entity, @Nullable Long changeUserId, @Nullable ChangeType changeType);

	<I, E extends Identity<I>> List<HistoryRecord<I, E>> historyOf(@Nonnull E entity, @Nullable Long changeUserId, @Nullable ChangeType changeType, Pageable pageable);

	<I, E extends Identity<I>> long historyDepth(@Nonnull Class<E> entityType, @Nullable Long changeUserId, @Nullable ChangeType changeType);

	<I, E extends Identity<I>> List<HistoryRecord<I, E>> historyOf(@Nonnull Class<E> entityType, @Nullable Long changeUserId, @Nullable ChangeType changeType, Pageable pageable);
}
