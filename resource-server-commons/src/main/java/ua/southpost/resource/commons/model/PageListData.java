/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model;

import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The container for page list data.
 * Created by mchys on 04.04.2018.
 */
public class PageListData<T> {
	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final Logger logger = LoggerFactory.getLogger(PageListData.class);
	private final int pageCount;
	private final int pageSize;
	private final int currentPage;
	private final List<T> list;

	private PageListData(int pageCount, int pageSize, int currentPage, List<T> list) {
		this.pageCount = pageCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.list = list;
	}

	public static <S extends PagedSearchRequestPayload, T> PageListData<T> createPagingListData(
			int pageNo, int pageSize,
			@Nonnull S searchForm,
			@Nonnull Function<S, Long> countFunction,
			@Nonnull BiFunction<S, Pageable, List<T>> listFunction,
			@Nonnull Sort sort
	) {
		if (pageNo <= 0) pageNo = 1;
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		long count = countFunction.apply(searchForm);

		logger.info(
				"There are {} records those satisfy query criteria.\nSearch criteria:\n\t{}",
				count, searchForm
		);

		int pageCount = (int) ((count + pageSize - 1) / pageSize);
		List<T> data;
		if (pageCount > 0) {
			if (pageNo > pageCount) pageNo = pageCount;

			Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

			data = listFunction.apply(searchForm, pageable);
			logger.info(
					"The {} records were fetched from the total count of {} satisfying the query criteria for page number {}\n" +
							"query criteria:\n\t{}",
					data.size(),
					count,
					pageNo,
					searchForm
			);
		} else {
			pageNo = 1;
			data = Collections.emptyList();
		}
		return new PageListData<>(pageCount, pageSize, pageNo, data);
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<T> getList() {
		return list;
	}
}
