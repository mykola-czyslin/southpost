package ua.southpost.resource.backup.web.controller.service;

import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.search.PagedSearchForm;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public interface PageListService<F extends PagedSearchForm, E extends EntityInfo<I>, I> {
	String MA_SEARCH_FORM = "searchForm";

	ModelAndView prepareListModelAndView(@Nullable F searchForm, @Nonnull Locale locale);
}
