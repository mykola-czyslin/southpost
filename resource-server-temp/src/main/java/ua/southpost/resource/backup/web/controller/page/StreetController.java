/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
import ua.southpost.resource.backup.web.model.forms.search.StreetSearchForm;
import ua.southpost.resource.backup.web.model.page.StreetEntityViewModel;
import ua.southpost.resource.backup.web.service.pagemodel.EntityModelService;
import ua.southpost.resource.backup.web.service.pagemodel.ModelMappingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

/**
 * The street page controller.
 * Created by mchys on 15.05.2018.
 */
@Controller
@RequestMapping("/master/streets")
public class StreetController {
	private static final String TITLE_STREET = "title.street";
	private static final long MOCK_STREET_ID = -1L;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;
	@Resource(name = "streetPageListService")
	private PageListService<StreetSearchForm, StreetInfo, Long> pageListService;
	@Resource
	private EntityModelService<StreetEntityViewModel, Long> entityModelService;

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_STREET;
	}

	@SuppressWarnings("unused")
	@ModelAttribute("settlementKindOptions")
	private Map<String, String> settlementKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(SettlementKind.class, request.getLocale(), 21, SettlementKind::getMessageKey, Comparator.comparing(e -> -e.ordinal()));
	}

	@SuppressWarnings("unused")
	@ModelAttribute("streetKindOptions")
	private Map<String, String> streetKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(StreetKind.class, request.getLocale(), 21, StreetKind::getMessageKey, Comparator.comparing(e -> -e.ordinal()));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
		binder.registerCustomEditor(StreetKind.class, new EnumEditorSupport<>(StreetKind.class));
	}

	@GetMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") StreetSearchForm searchForm) {
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

	@GetMapping("/add")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_STREET_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/{street_id}/view")
	public ModelAndView view(@PathVariable("street_id") Long streetId, Locale locale) {
		return detailsView(streetId, SubmitType.VIEW, locale);
	}

	@GetMapping("/{street_id}/edit")
	public ModelAndView edit(@PathVariable("street_id") Long streetId, Locale locale) {
		return detailsView(streetId, SubmitType.UPDATE, locale);
	}

	private ModelAndView detailsView(long streetId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		StreetEntityViewModel model = entityModelService.prepareModel(submitType, streetId, locale);

		ModelMappingUtil.transferDataToModelAndView(model, modelAndView);
		return modelAndView;
	}


}
