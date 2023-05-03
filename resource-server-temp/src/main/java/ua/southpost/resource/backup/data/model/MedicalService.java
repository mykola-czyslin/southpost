package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;

public enum MedicalService implements MessageKeyHolder {
	SURGERY("medical.service.surgery"),
	AESTHETIC_SURGERY("medical.service.aesthetic.surgery"),
	PROSTHESIS("medical.service.prosthesis"),
	ADDICTION_THERAPY("medical.service.addiction.therapy"),
	PSYCHIATRY("medical.service.psychiatry"),
	GYNECOLOGY("medical.service.gynecology"),
	TRAUMATOLOGY("medical.service.traumatology"),
	OPHTHALMOLOGY("medical.service.ophthalmology"),
	ENDOCRINOLOGY("medical.service.endocrinology"),
	GASTROENTEROLOGY("medical.service.gastroenterology"),
	UROLOGY("medical.service.urology"),
	PULMONOLOGY("medical.service.pulmonology"),
	OTOLARYNGOLOGY("medical.service.otolaryngology"),
	NEUROPATHOLOGY("medical.service.neuropathology"),
	GENERAL_THERAPY("medical.service.general.therapy"),
	INFECTION_DECEASE_THERAPY("medical.service.infection.therapy"),
	VENEREOLOGY("medical.service.venereology"),
	DENTISTRY("medical.service.dentistry"),
	DENTAL_SURGERY("medical.service.dental.surgery"),
	DENTAL_PROSTHESIS("medical.service.dental.prosthesis");

	@Nonnull
	private final String messageKey;

	MedicalService(@Nonnull String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	@Nonnull
	public String getMessageKey() {
		return messageKey;
	}

}
