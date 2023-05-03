package ua.southpost.resource.commons.model;

import ua.southpost.resource.commons.model.ChangeType;

public enum SubmitActionType {
	CREATE(false, ChangeType.CREATE), UPDATE(true, ChangeType.UPDATE);

	private final boolean merging;
	private final ChangeType changeType;

	SubmitActionType(boolean merging, ChangeType changeType) {
		this.merging = merging;
		this.changeType = changeType;
	}

	public boolean isMerging() {
		return merging;
	}

	public ChangeType getChangeType() {
		return changeType;
	}
}
