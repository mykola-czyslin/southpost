/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.southpost.resource.commons.model.ChangeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

/**
 * The entity history entity.
 * Created by mchys on 15.10.2018.
 */
@Entity
@Table(name = "entity_data_history")
@NamedQueries({
		@NamedQuery(
				name = "entityHistory.particularEntity.list",
				query = "select h from EntityHistory as h\n" +
						"where h.entityType = :entityType\n" +
						"  and h.entityId = :entityId\n " +
						"  and (:userId is null or h.changedBy.id = :userId)\n" +
						"  and (:changeType is null or h.changeType = :changeType)\n" +
						"order by h.changeTime desc"
		),
		@NamedQuery(
				name = "entityHistory.particularEntity.count",
				query = "select count(h) from EntityHistory as h\n" +
						"where h.entityType = :entityType\n" +
						"  and h.entityId = :entityId\n " +
						"  and (:userId is null or h.changedBy.id = :userId)\n" +
						"  and (:changeType is null or h.changeType = :changeType)"
		),
		@NamedQuery(
				name = "entityHistory.entityType.list",
				query = "select h from EntityHistory as h\n" +
						"where h.entityType = :entityType\n" +
						"  and (:userId is null or h.changedBy.id = :userId)\n" +
						"  and (:changeType is null or h.changeType = :changeType)\n" +
						"order by h.changeTime desc"
		),
		@NamedQuery(
				name = "entityHistory.entityType.count",
				query = "select count(h) from EntityHistory as h\n" +
						"where h.entityType = :entityType\n" +
						"  and (:userId is null or h.changedBy.id = :userId)\n" +
						"  and (:changeType is null or h.changeType = :changeType)"
		)
})
public class EntityHistory {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "ENTITY_TYPE")
	private String entityType;
	@Column(name = "ENTITY_ID")
	private String entityId;
	@Enumerated(EnumType.STRING)
	@Column(name = "CHANGE_TYPE")
	private ChangeType changeType;
	@Column(name = "CHANGE_TIME")
	private Date changeTime;
	@Column(name = "ENTITY_VALUE")
	private String entityValue;
	@ManyToOne
	@JoinColumn(name = "CHANGED_BY")
	private User changedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getEntityValue() {
		return entityValue;
	}

	public void setEntityValue(String nextValue) {
		this.entityValue = nextValue;
	}

	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("entityType", entityType)
				.append("entityId", entityId)
				.append("changeType", changeType)
				.append("changeTime", changeTime)
				.append("nextValue", entityValue)
				.append("changedBy", changedBy)
				.toString();
	}
}
