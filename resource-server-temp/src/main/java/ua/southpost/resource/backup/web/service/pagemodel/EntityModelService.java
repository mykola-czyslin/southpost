package ua.southpost.resource.backup.web.service.pagemodel;

import ua.southpost.resource.backup.web.model.SubmitType;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface EntityModelService<T, I> {

	@Nonnull
	T prepareModel(@Nonnull SubmitType submitType, @Nonnull I entityId, @Nonnull Locale locale);

}
