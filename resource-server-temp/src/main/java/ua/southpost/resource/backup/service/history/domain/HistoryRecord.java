/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.service.history.domain;

import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * The envelope of some {@link Identity} history record.
 * Created by mchys on 08.12.2018.
 */
public class HistoryRecord<I, E extends Identity<I>> {
	private long id;
	private E entity;
	private ChangeType changeType;
	private Date changeDateTime;
	private User changedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

	public Date getChangeDateTime() {
		return changeDateTime;
	}

	public void setChangeDateTime(Date changeDateTime) {
		this.changeDateTime = changeDateTime;
	}

	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof HistoryRecord)) return false;

		HistoryRecord<?, ?> that = (HistoryRecord<?, ?>) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(entity, that.entity)
				.append(changeType, that.changeType)
				.append(changeDateTime, that.changeDateTime)
				.append(changedBy, that.changedBy)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(entity)
				.append(changeType)
				.append(changeDateTime)
				.append(changedBy)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("entity", entity)
				.append("changeType", changeType)
				.append("changeDateTime", changeDateTime)
				.append("changedBy", changedBy)
				.toString();
	}
}
