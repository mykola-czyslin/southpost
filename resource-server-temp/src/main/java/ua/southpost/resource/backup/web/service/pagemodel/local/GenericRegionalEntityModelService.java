package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.controller.util.RegionDistrictOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.entity.RegionalEntityForm;
import ua.southpost.resource.backup.web.model.page.RegionalEntityViewModel;

import javax.annotation.Nonnull;
import javax.annotation.Resource;


abstract class GenericRegionalEntityModelService<T extends RegionalEntityViewModel<I>, E extends EntityInfo<I>, S extends RegionalEntityForm<I>, I> extends GenericEntityModelService<T, E, S, I> {
	@Resource
	protected RegionDistrictOptionsUtil regionDistrictOptionsUtil;

	@Override
	protected void populateEntityViewModelExtensions(@Nonnull T entityVieModel, @Nonnull S form, @Nonnull SubmitType submitType) {
		entityVieModel.setRegionOptions(regionDistrictOptionsUtil.regionOptions());
		entityVieModel.setDistrictOptions(regionDistrictOptionsUtil.districtOptions(form.regionId()));
	}

}
