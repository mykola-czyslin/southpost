/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.PageListData;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility component that aggregates data required to create lookup response.
 * Created by mchys on 26.05.2018.
 */
@Component
public class EntitySearchAndLookupUtil {
	private static final int FIRST_PAGE_NUM = 1;

	@Transactional(propagation = Propagation.REQUIRED)
	public <P extends PagedSearchRequestPayload, I, T extends EntityInfo<I>, S extends Identity<I>>
	EntityPage<T,I> search(P searchPayload, Function<P, Long> countFunc, BiFunction<P, Pageable, List<S>> listFunc, Sort sort, Function<S, T> entityInfoMapper) {
		PageListData<S> pageListData = PageListData.createPagingListData(
				searchPayload.getPageNum(), searchPayload.getLinesPerPage(),
				searchPayload, countFunc, listFunc, sort
		);

		return EntityPage.<T,I>builder()
				.entityList(pageListData.getList().stream().map(entityInfoMapper).collect(Collectors.toList()))
				.pageCount(pageListData.getPageCount())
				.currentPage(pageListData.getCurrentPage())
				.pageSize(pageListData.getPageSize())
				.build();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <P extends PagedSearchRequestPayload, I, T extends EntityInfo<I>, S extends Identity<I>> LookupResponse<T> lookup(
			P searchPayload,
			Function<P, Long> countFunction,
			BiFunction<P, Pageable, List<S>> listFunction,
			Sort sort,
			Function<S, T> lookupInfoMapper) {
		PageListData<S> pageListData = PageListData.createPagingListData(
				searchPayload.getPageNum(), searchPayload.getLinesPerPage(),
				searchPayload, countFunction, listFunction, sort
		);

		final boolean firstPage = pageListData.getCurrentPage() == FIRST_PAGE_NUM;
		final boolean lastPage = pageListData.getCurrentPage() == pageListData.getPageCount();

		return new LookupResponse<>(
				pageListData.getList().stream().map(lookupInfoMapper).collect(Collectors.toList()),
				firstPage ? null : pageListData.getCurrentPage() - 1,
				lastPage ? null : pageListData.getCurrentPage() + 1
		);
	}

}
