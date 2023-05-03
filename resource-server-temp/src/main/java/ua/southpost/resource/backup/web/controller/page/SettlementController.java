/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm;
import ua.southpost.resource.backup.web.model.page.SettlementEntityViewModel;
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
 * Controls settlement page requests.
 * Created by mchys on 10.05.2018.
 */
@Controller
@RequestMapping("/master/settlement")
public class SettlementController {
	private static final String TITLE_SETTLEMENT = "title.settlement";
	private static final long MOCK_SETTLEMENT_ID = -1L;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;
	@Resource(name = "settlementPageListService")
	private PageListService<SettlementSearchForm, SettlementInfo, Long> pageListService;
	@Resource(name = "settlementEntityModelService")
	private EntityModelService<SettlementEntityViewModel, Long> entityModelService;


	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_SETTLEMENT;
	}

	@SuppressWarnings("unused")
	@ModelAttribute("settlementKindOptions")
	private Map<String, String> settlementKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(SettlementKind.class, request.getLocale(), 21, SettlementKind::getMessageKey, Comparator.comparing(e -> -e.ordinal()));
	}

	@InitBinder
	public void initBinder(@SuppressWarnings("unused") HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
	}

	@GetMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") SettlementSearchForm searchForm) {
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}


	@GetMapping("/add")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_SETTLEMENT_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/{settlement_id}/view")
	public ModelAndView view(@PathVariable("settlement_id") Long settlementId, Locale locale) {
		return detailsView(settlementId, SubmitType.VIEW, locale);
	}

	@GetMapping("/{settlement_id}/edit")
	public ModelAndView edit(@PathVariable("settlement_id") Long settlementId, Locale locale) {
		return detailsView(settlementId, SubmitType.UPDATE, locale);
	}

	private ModelAndView detailsView(long settlementId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		SettlementEntityViewModel model = entityModelService.prepareModel(submitType, settlementId, locale);

		ModelMappingUtil.transferDataToModelAndView(model, modelAndView);

		return modelAndView;
	}

}
