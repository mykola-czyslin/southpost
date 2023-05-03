package ua.southpost.resource.backup.web.controller.service.local;

import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityGridSettingsService;
import ua.southpost.resource.backup.web.controller.service.SearchFormToPayloadConverter;
import ua.southpost.resource.backup.web.controller.util.RegionDistrictOptionsUtil;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.factory.SearchFormFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

import static java.util.Optional.ofNullable;
import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;

class RegionalPageListServiceImpl<F extends SettlementSearchForm, P extends SettlementSearchRequestPayload, E extends EntityInfo<I>, I> extends PageListServiceImpl<F, P, E, I> {
	static final String MA_REGION_OPTIONS = "regionOptions";
	static final String MA_DISTRICT_OPTIONS = "districtOptions";
	@Resource
	private RegionDistrictOptionsUtil regionDistrictOptionsUtil;

	RegionalPageListServiceImpl(@Nonnull Class<E> entityType, @Nonnull SearchFormToPayloadConverter<F, P> converter, @Nonnull EntityLookupService<P, E, I> lookupService, @Nonnull EntityGridSettingsService gridSettingsService, @Nonnull SearchFormFactory<F> searchFormFactory, @Nonnull String listViewName, @Nonnull List<String> listViewScripts) {
		super(entityType, converter, lookupService, gridSettingsService, searchFormFactory, listViewName, listViewScripts);
	}

	@Override
	public void populateCustomModelAttributes(@Nonnull F searchForm, @Nonnull Locale locale, @Nonnull ModelAndView modelAndView) {
		modelAndView.addObject(MA_REGION_OPTIONS, regionDistrictOptionsUtil.regionOptions());
		modelAndView.addObject(MA_DISTRICT_OPTIONS, regionDistrictOptionsUtil.districtOptions(ofNullable(searchForm.getRegionId()).filter(StringUtils::isNotBlank).orElse(NULL_OPTION_VALUE)));
	}
}
