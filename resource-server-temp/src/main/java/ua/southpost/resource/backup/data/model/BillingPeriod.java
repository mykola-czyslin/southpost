/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

/**
 * Defines a set of the supported billing periods.
 * Created by mchys on 15.04.2018.
 */
public enum BillingPeriod implements MessageKeyHolder {
	DAY("billing.period.day"),
	WEEK("billing.period.week"),
	MONTH("billing.period.month"),
	QUARTER("billing.period.quarter"),
	HALF_YEAR("billing.period.half.of.year"),
	YEAR("billing.period.year");

	@Nonnull
	private final String messageKey;

	BillingPeriod(@Nonnull String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	@Nonnull
	public String getMessageKey() {
		return messageKey;
	}

}
