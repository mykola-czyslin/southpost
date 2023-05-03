package ua.southpost.resource.commons.util;

import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.dto.EntityGridPage;
import ua.southpost.resource.commons.model.dto.EntityInfo;

import javax.annotation.Nonnull;

public class PageUtils {
	private PageUtils() {
	}

	private static final int MIN_WIDTH = 3;

	public static <E extends EntityInfo<I>, I> EntityGridPage<E,I> toGridPage(EntityPage<E,I> entityPage, int pageLabelsCount) {
		final EntityGridPage.Model model = EntityGridPage.Model.builder()
				.currentPage(entityPage.getCurrentPage())
				.pages(generatePageOptions(entityPage.getPageCount(), entityPage.getCurrentPage(), pageLabelsCount))
				.build();
		return EntityGridPage.<E,I>builder()
				.entityList(entityPage.getEntityList())
				.model(model)
				.pageSize(entityPage.getPageSize())
				.build();
	}

	@Nonnull
	public static int[] generatePageOptions(int pageCount, int currentPage, int width) {
		if (pageCount < 2) {
			return new int[0];
		}
		width = Math.max(MIN_WIDTH, width);
		currentPage = Math.max(currentPage, 1);
		int[] options;
		if (pageCount <= width) {
			options = new int[pageCount];
			for (int i = 0; i < pageCount; i++) {
				options[i] = i + 1;
			}
		} else {

			options = new int[width];
			options[0] = 1;
			options[width - 1] = pageCount;
			// for currentPage starting from 1 till to width - 1 value for 1 should starts from 2
			// when current page starts from width to
			final int halfOfWidth = (width / 2) - 1;
			// let offset = pageCount - (width - 1)
			// then when j = width - 2 we should have option value = width - 2 + pageCount - (width - 1)
			// = pageCount - 1 as expected
			// when width = 3 then there should be only one element equal to current page:
			final int offset;
			if (currentPage <= halfOfWidth + 1) {
				offset = 1;
			} else if (currentPage > pageCount - (halfOfWidth + 1)) {
				offset = pageCount - width + 1;
			} else {
				offset = currentPage - (halfOfWidth + 1);
			}
			for (int j = 1; j < width - 1; j++) {
				options[j] = j + offset;
			}
		}
		return options;
	}

}
