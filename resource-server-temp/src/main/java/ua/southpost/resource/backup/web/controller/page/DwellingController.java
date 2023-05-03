/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.DwellingKind;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import ua.southpost.resource.backup.web.model.forms.search.DwellingSearchForm;
import ua.southpost.resource.backup.web.model.page.DwellingEntityViewModel;
import ua.southpost.resource.backup.web.service.pagemodel.EntityModelService;
import ua.southpost.resource.backup.web.service.pagemodel.ModelMappingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
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
 * Handles request to {@link Dwelling} entity related pages.
 * Created by mchys on 19.04.2018.
 */
@Controller
@RequestMapping("/dwelling")
public class DwellingController {
	private static final String TITLE_DWELLING = "title.dwelling";
	private static final long MOCK_DWELLING_ID = -1L;
	@Resource(name = "dwellingPageListService")
	private PageListService<DwellingSearchForm, DwellingInfo, Long> pageListService;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;
	@Resource(name = "dwellingRegionalEntityModelService")
	private EntityModelService<DwellingEntityViewModel, Long> entityModelService;

	@InitBinder
	public void initBinder(@SuppressWarnings("unused") HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
		binder.registerCustomEditor(NoYes.class, new EnumEditorSupport<>(NoYes.class));
		binder.registerCustomEditor(DwellingKind.class, new EnumEditorSupport<>(DwellingKind.class));
		binder.registerCustomEditor(BillingPeriod.class, new EnumEditorSupport<>(BillingPeriod.class));
	}

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_DWELLING;
	}

	@SuppressWarnings("unused")
	@ModelAttribute("settlementKindOptions")
	private Map<String, String> settlementKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(SettlementKind.class, request.getLocale(), 21, e -> "settlement.kind." + e.name(), Comparator.comparing(e -> -e.ordinal()));
	}

	@SuppressWarnings("unused")
	@ModelAttribute("streetKindOptions")
	private Map<String, String> streetKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(StreetKind.class, request.getLocale(), 21, StreetKind::getMessageKey, Comparator.comparing(e -> -e.ordinal()));
	}

	@SuppressWarnings("unused")
	@ModelAttribute("dwellingKindOptions")
	private Map<String, String> dwellingKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(DwellingKind.class, request.getLocale(), 15, DwellingKind::getMessageKey, null);
	}

	@SuppressWarnings("unused")
	@ModelAttribute("billingPeriodOptions")
	private Map<String, String> billingPeriod(HttpServletRequest request) {
		return enumOptionsUtil.options(BillingPeriod.class, request.getLocale(), 21, BillingPeriod::getMessageKey, null);
	}

	@GetMapping("/manage/create")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_DWELLING_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/manage/{dwellingId}/edit")
	public ModelAndView edit(@PathVariable("dwellingId") long dwellingId, Locale locale) {
		return detailsView(dwellingId, SubmitType.UPDATE, locale);
	}

	@GetMapping("/{dwellingId}/view")
	public ModelAndView view(@PathVariable("dwellingId") long dwellingId, Locale locale) {
		return detailsView(dwellingId, SubmitType.VIEW, locale);
	}

	private ModelAndView detailsView(long dwellingId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		final DwellingEntityViewModel dwellingEntityViewModel = entityModelService.prepareModel(submitType, dwellingId, locale);

		ModelMappingUtil.transferDataToModelAndView(dwellingEntityViewModel, modelAndView);

		return modelAndView;
	}

	@GetMapping("/list")
	public ModelAndView listDefault(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") DwellingSearchForm searchForm) {
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

}
