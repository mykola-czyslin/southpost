package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.LookupResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;


class EntityLookupServiceImpl<P extends PagedSearchRequestPayload, E extends Identity<I>, S extends EntityInfo<I>, I> implements EntityLookupService<P, S, I> {
	@Nonnull
	private final EntitySearchAndLookupUtil entitySearchAndLookupUtil;
	@Nonnull
	private final EntitySearchService<P, E, I> entitySearchService;
	@Nonnull
	private final SortService<P> sortService;
	@Nonnull
	private final LocalizedEntityMapper<E, S, I> mapper;

	EntityLookupServiceImpl(@Nonnull EntitySearchAndLookupUtil entitySearchAndLookupUtil, @Nonnull EntitySearchService<P, E, I> entitySearchService, @Nonnull SortService<P> sortService, @Nonnull LocalizedEntityMapper<E, S, I> mapper) {
		this.entitySearchAndLookupUtil = entitySearchAndLookupUtil;
		this.entitySearchService = entitySearchService;
		this.sortService = sortService;
		this.mapper = mapper;
	}

	@Override
	public EntityPage<S, I> search(@Nullable P searchPayload, Locale locale) {
		return entitySearchAndLookupUtil.search(
				searchPayload,
				this.entitySearchService::count,
				this.entitySearchService::list,
				sortService.sort(searchPayload),
				e -> mapper.map(e, locale)
		);
	}

	@Override
	public LookupResponse<S> lookup(P lookupPayload, Locale locale) {
		return entitySearchAndLookupUtil.lookup(
				lookupPayload,
				this.entitySearchService::count,
				this.entitySearchService::list,
				sortService.sort(lookupPayload),
				e -> mapper.map(e, locale)
		);
	}
}
