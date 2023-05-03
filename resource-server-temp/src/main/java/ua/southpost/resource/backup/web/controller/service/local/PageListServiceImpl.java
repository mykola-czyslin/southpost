package ua.southpost.resource.backup.web.controller.service.local;

import com.google.common.collect.Maps;
import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.commons.service.EntityGridSettingsService;
import ua.southpost.resource.commons.service.EntitySortOptionsService;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.service.SearchFormToPayloadConverter;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.search.PagedSearchForm;
import ua.southpost.resource.commons.model.dto.SortOption;
import ua.southpost.resource.backup.web.model.forms.search.factory.SearchFormFactory;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static ua.southpost.resource.commons.util.PageUtils.toGridPage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

class PageListServiceImpl<F extends PagedSearchForm, P extends PagedSearchRequestPayload, E extends EntityInfo<I>, I> implements PageListService<F, E, I> {
	private static final String MA_PAGE_LIST_DATA = "pageListData";
	private static final String MA_SCRIPTS = "scripts";
	private static final String MA_SORT_FIELDS = "sortFields";
	private static final String MA_SORT_DIRECTIONS = "sortDirections";
	private static final String SORT_DIRECTION_MESSAGE_KEY_PREFIX = "sort.direction.";

	private static final Object[] EMPTY_ARGS = new Object[0];

	private static final Logger LOGGER = LoggerFactory.getLogger(PageListServiceImpl.class);


	@Nonnull
	private final Class<E> entityType;
	@Nonnull
	private final SearchFormToPayloadConverter<F, P> converter;
	@Nonnull
	private final EntityLookupService<P, E, I> lookupService;
	@Nonnull
	private final EntityGridSettingsService gridSettingsService;
	@Nonnull
	private final SearchFormFactory<F> searchFormFactory;
	@Nonnull
	private final String listViewName;
	@Nonnull
	private List<String> listViewScripts;

	@Resource
	private MessageSource messageSource;
	@Resource
	private EntitySortOptionsService sortOptionsService;
	@Value("${view.grid.paging.page-labels-count}")
	private int pageLabelsCount;

	PageListServiceImpl(@Nonnull Class<E> entityType, @Nonnull SearchFormToPayloadConverter<F, P> converter, @Nonnull EntityLookupService<P, E, I> lookupService, @Nonnull EntityGridSettingsService gridSettingsService, @Nonnull SearchFormFactory<F> searchFormFactory, @Nonnull String listViewName, @Nonnull List<String> listViewScripts) {
		this.entityType = entityType;
		this.converter = converter;
		this.lookupService = lookupService;
		this.gridSettingsService = gridSettingsService;
		this.searchFormFactory = searchFormFactory;
		this.listViewName = listViewName;
		this.listViewScripts = listViewScripts;
	}

	@Override
	public final ModelAndView prepareListModelAndView(@Nullable F searchForm, @Nonnull Locale locale) {
		LOGGER.debug("**** Preparing model and View for search form {} and locale {}", searchForm, locale);
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		F effectiveSearchForm = ofNullable(searchForm)
				.map(this::acceptPageSize)
				.orElseGet(() -> searchFormFactory.create(getEntityGridSettings()));
		final EntityPage<E,I> lookupResponse = lookupService.search(converter.convert(effectiveSearchForm), locale);
		modelAndView.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, listViewName);
		modelAndView.addObject(MA_PAGE_LIST_DATA, toGridPage(lookupResponse, pageLabelsCount));
		modelAndView.addObject(MA_SEARCH_FORM, effectiveSearchForm);
		modelAndView.addObject(MA_SCRIPTS, listViewScripts);
		modelAndView.addObject(MA_SORT_FIELDS, sortOptionsService.getSortOptions(entityType, locale).asMap());
		modelAndView.addObject(MA_SORT_DIRECTIONS, sortDirections(locale));
		populateCustomModelAttributes(effectiveSearchForm, locale, modelAndView);
		return modelAndView;
	}

	protected void populateCustomModelAttributes(@Nonnull F searchForm, @Nonnull Locale locale, @Nonnull ModelAndView modelAndView) {
		// for custom implementations reserved
	}


	private Map<String, String> sortDirections(Locale locale) {
		return Arrays.stream(Sort.Direction.values())
				.map(d -> Pair.of(d.name(), messageSource.getMessage(SORT_DIRECTION_MESSAGE_KEY_PREFIX + d.name(), EMPTY_ARGS, locale)))
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue, (p1, p2) -> p1, Maps::newLinkedHashMap));
	}

	@Nonnull
	private EntityGridSettings getEntityGridSettings() {
		return this.gridSettingsService.obtainEntityGridSettingsFor(this.entityType);
	}

	@Nonnull
	private F acceptPageSize(@Nonnull F searchForm) {
		final EntityGridSettings gridSettings = new EntityGridSettings(searchForm.getLinesPerPage(), searchForm.getSortOptions());
		gridSettingsService.applyGridSettingsFor(entityType, gridSettings);
		return normalizeSort(searchForm);
	}

	private F normalizeSort(@Nonnull F searchForm) {
		searchForm.setSortOptions(collectSortOptions(searchForm));
		return searchForm;
	}

	private List<SortOption> collectSortOptions(@Nonnull F searchForm) {
		return ofNullable(searchForm.getSortOptions()).orElseGet(Collections::emptyList).stream()
				.filter(o -> isNotBlank(o.getFieldName()) && o.getDirection() != null)
				.collect(Collectors.toList());
	}

}
