package ua.southpost.resource.commons.service;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.PagedSearchResponsePayload;
import ua.southpost.resource.commons.model.dto.SortOption;

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
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static ua.southpost.resource.commons.util.PageUtils.toGridPage;

class PageSearchServiceImpl<R extends PagedSearchRequestPayload, E extends EntityInfo<I>, I> implements PageSearchService<R, E, I> {
	private static final String SORT_DIRECTION_MESSAGE_KEY_PREFIX = "sort.direction.";

	private static final Object[] EMPTY_ARGS = new Object[0];

	private static final Logger LOGGER = LoggerFactory.getLogger(PageSearchServiceImpl.class);


	@Nonnull
	private final Class<E> entityType;
	@Nonnull
	private final EntityLookupService<R, E, I> lookupService;
	@Nonnull
	private final EntityGridSettingsService gridSettingsService;
	@Nonnull
	private final SearchPayloadFactory<R> searchPayloadFactory;

	@Resource
	private MessageSource messageSource;
	@Resource
	private EntitySortOptionsService sortOptionsService;
	@Value("${view.grid.paging.page-labels-count}")
	private int pageLabelsCount;

	PageSearchServiceImpl(@Nonnull Class<E> entityType, @Nonnull EntityLookupService<R, E, I> lookupService, @Nonnull EntityGridSettingsService gridSettingsService, @Nonnull SearchPayloadFactory<R> searchPayloadFactory) {
		this.entityType = entityType;
		this.lookupService = lookupService;
		this.gridSettingsService = gridSettingsService;
		this.searchPayloadFactory = searchPayloadFactory;
	}

	@Override
	public final PagedSearchResponsePayload<R, E, I> prepareListModelAndView(@Nullable R searchRequestPayload, @Nonnull Locale locale) {
		LOGGER.debug("**** Preparing model and View for search payload {} and locale {}", searchRequestPayload, locale);
		R effectiveSearchPayload = ofNullable(searchRequestPayload)
				.map(this::acceptPageSize)
				.orElseGet(() -> searchPayloadFactory.create(getEntityGridSettings()));
		final EntityPage<E,I> lookupResponse = lookupService.search(effectiveSearchPayload, locale);
		final PagedSearchResponsePayload<R, E, I> responsePayload = new PagedSearchResponsePayload<>();
		responsePayload.setRequest(effectiveSearchPayload);
		responsePayload.setPageData(toGridPage(lookupResponse, pageLabelsCount));
		responsePayload.setEntitySortOptions(sortOptionsService.getSortOptions(entityType, locale).asMap());
		responsePayload.setSortDirections(sortDirections(locale));
		return responsePayload;
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
	private R acceptPageSize(@Nonnull R searchRequestPayload) {
		final EntityGridSettings gridSettings = new EntityGridSettings(searchRequestPayload.getLinesPerPage(), searchRequestPayload.getSortOptions());
		gridSettingsService.applyGridSettingsFor(entityType, gridSettings);
		return normalizeSort(searchRequestPayload);
	}

	private R normalizeSort(@Nonnull R searchRequestPayload) {
		searchRequestPayload.setSortOptions(collectSortOptions(searchRequestPayload));
		return searchRequestPayload;
	}

	private List<SortOption> collectSortOptions(@Nonnull R searchRequestPayload) {
		return ofNullable(searchRequestPayload.getSortOptions()).orElseGet(Collections::emptyList).stream()
				.filter(o -> isNotBlank(o.getFieldName()) && o.getDirection() != null)
				.collect(Collectors.toList());
	}

}
