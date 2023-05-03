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
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.forms.search.LocationSearchForm;
import ua.southpost.resource.backup.web.model.page.LocationEntityViewModel;
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
 * The location page controller.
 * Created by mchys on 19.05.2018.
 */
@Controller
@RequestMapping("/master/locations")
public class LocationController {
	private static final String TITLE_LOCATION = "title.location";
	private static final long MOCK_LOCATION_ID = -1L;
	@Resource(name = "locationPageListService")
	private PageListService<LocationSearchForm, LocationInfo, Long> pageListService;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;
	@Resource(name = "locationEntityModelService")
	private EntityModelService<LocationEntityViewModel, Long> entityModelService;

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_LOCATION;
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
	public void initBinder(@SuppressWarnings("unused") HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
		binder.registerCustomEditor(StreetKind.class, new EnumEditorSupport<>(StreetKind.class));
	}

	@GetMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") LocationSearchForm searchForm) {
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

	@GetMapping("/add")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_LOCATION_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/{location_id}/view")
	public ModelAndView view(@PathVariable("location_id") Long locationId, Locale locale) {
		return detailsView(locationId, SubmitType.VIEW, locale);
	}

	@GetMapping("/{location_id}/edit")
	public ModelAndView edit(@PathVariable("location_id") Long locationId, Locale locale) {
		return detailsView(locationId, SubmitType.UPDATE, locale);
	}


	private ModelAndView detailsView(long locationId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		LocationEntityViewModel entityViewModel = entityModelService.prepareModel(submitType, locationId, locale);

		ModelMappingUtil.transferDataToModelAndView(entityViewModel, modelAndView);
		return modelAndView;
	}


}
