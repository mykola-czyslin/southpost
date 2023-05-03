package ua.southpost.resource.backup.data.model;

import ua.southpost.resource.commons.model.entity.Identity;

import java.util.Date;

public interface ModificationTrackingEntity extends Identity<Long> {

	Date getModificationTime();

	User getModifiedBy();
}
