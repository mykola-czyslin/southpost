/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import ua.southpost.resource.backup.web.model.forms.search.PhoneSearchForm;
import ua.southpost.resource.backup.web.model.page.PhoneEntityViewModel;
import ua.southpost.resource.backup.web.service.pagemodel.EntityModelService;
import ua.southpost.resource.backup.web.service.pagemodel.ModelMappingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Control phones management pages.
 * Created by mchys on 15.09.2018.
 */
@Controller
@RequestMapping("/master/phones")
public class PhonesController {
	private static final String TITLE_PHONE = "title.phone";
	private static final long MOCK_PHONE_ID = -1L;
	@Resource(name = "phonePageListService")
	private PageListService<PhoneSearchForm, PhoneInfo, Long> phonePageListService;
	@Resource(name = "phoneEntityModelService")
	private EntityModelService<PhoneEntityViewModel, Long> entityModelService;

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_PHONE;
	}

	@GetMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request) {
		return phonePageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") PhoneSearchForm searchForm) {
		return phonePageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

	@GetMapping("/add")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_PHONE_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/{phoneId}/view")
	public ModelAndView view(@PathVariable("phoneId") long phoneId, Locale locale) {
		return detailsView(phoneId, SubmitType.VIEW, locale);
	}

	@GetMapping("/{phoneId}/edit")
	public ModelAndView edit(@PathVariable("phoneId") long phoneId, Locale locale) {
		return detailsView(phoneId, SubmitType.UPDATE, locale);
	}

	private ModelAndView detailsView(long phoneId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		PhoneEntityViewModel model = entityModelService.prepareModel(submitType, phoneId, locale);

		ModelMappingUtil.transferDataToModelAndView(model, modelAndView);
		return modelAndView;
	}


}
