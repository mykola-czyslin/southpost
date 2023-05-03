package ua.southpost.resource.backup.web.service.pagemodel.local;

import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.entity.RegionalEntityForm;
import ua.southpost.resource.backup.web.model.page.ExtendedRegionalEntityViewModel;
import ua.southpost.resource.backup.web.model.page.RegionDistrictOptions;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

abstract class ExtendedRegionalEntityModelService<M extends ExtendedRegionalEntityViewModel<I>, E extends EntityInfo<I>, F extends RegionalEntityForm<I>, I> extends GenericRegionalEntityModelService<M, E, F, I> {

	@Override
	protected void populateEntityViewModelExtensions(@Nonnull M entityVieModel, @Nonnull F form, @Nonnull SubmitType submitType) {
		super.populateEntityViewModelExtensions(entityVieModel, form, submitType);
		entityVieModel.setRegionDistrictOptions(new RegionDistrictOptions(collectRegionDistrictOptions()));
	}

	protected Map<String, Map<String, String>> collectRegionDistrictOptions() {
		return regionDistrictOptionsUtil.regionOptions().keySet()
				.stream()
				.collect(Collectors.toMap(rid -> rid, regionDistrictOptionsUtil::districtOptions));
	}
}
