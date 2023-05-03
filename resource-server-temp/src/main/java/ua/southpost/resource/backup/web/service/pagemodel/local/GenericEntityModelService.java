package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EntityForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.EntityViewModel;
import ua.southpost.resource.backup.web.service.pagemodel.EntityModelService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;

abstract class GenericEntityModelService<T extends EntityViewModel<I>, E extends EntityInfo<I>, S extends EntityForm<I>, I> implements EntityModelService<T, I> {

	@Nonnull
	@Override
	public T prepareModel(@Nonnull SubmitType submitType, @Nonnull I entityId, @Nonnull Locale locale) {
		final List<String> scripts = resolveScripts(submitType);
		return submitType == SubmitType.CREATE ? buildCreateNewEntityFormModel(submitType, scripts) : buildEntityFormModel(entityId, submitType, locale, scripts);
	}


	@Nonnull
	private List<String> resolveScripts(@Nonnull SubmitType submitType) {
		return submitType == SubmitType.VIEW ? viewScripts() : formScripts();
	}

	private T buildCreateNewEntityFormModel(@Nonnull SubmitType submitType, @Nonnull List<String> scripts) {
		final S form = formFactory().createNewFormInstance();
		return buildEntityViewModel(form, submitType, scripts);
	}


	@Nonnull
	private T buildEntityFormModel(@Nonnull I entityId, @Nonnull SubmitType submitType, Locale locale, @Nonnull List<String> scripts) {
		S form = formFactory().formByEntityId(entityId, locale);
		return buildEntityViewModel(form, submitType, scripts);
	}

	@Nonnull
	private T buildEntityViewModel(@Nonnull S form, @Nonnull SubmitType submitType, @Nonnull List<String> scripts){
		final T model = createEntityViewModel();
		model.setEntityForm(form);
		model.setSubmitType(submitType);
		model.setScripts(scripts);
		populateEntityViewModelExtensions(model, form, submitType);
		return model;
	}

	@Nonnull
	protected abstract T createEntityViewModel();

	@SuppressWarnings("unused")
	protected void populateEntityViewModelExtensions(@Nonnull T entityVieModel, @Nonnull S form, @Nonnull SubmitType submitType) {
		// reserved for extensions
	}

	@Nonnull
	protected abstract EntityFormFactory<I, E, S> formFactory();

	@Nonnull
	protected abstract List<String> viewScripts();

	@Nonnull
	protected abstract List<String> formScripts();

}
