/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.forms.search.EmailSearchForm;
import ua.southpost.resource.backup.web.model.page.EmailEntityViewModel;
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
 * Controls email management page requests.
 * Created by mchys on 16.09.2018.
 */
@Controller
@RequestMapping("/master/emails")
public class EmailsController {
	private static final String TITLE_EMAIL = "title.email";
	private static final long MOCK_EMAIL_ID = -1L;
	@Resource(name = "emailPageListService")
	private PageListService<EmailSearchForm, EmailInfo, Long> pageListService;
	@Resource(name = "emailEntityModelService")
	private EntityModelService<EmailEntityViewModel,Long> entityModelService;

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_EMAIL;
	}

	@GetMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") EmailSearchForm searchForm) {
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

	@GetMapping("/add")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_EMAIL_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/{email_id}/view")
	public ModelAndView view(@PathVariable("email_id") long emailId, Locale locale) {
		return detailsView(emailId, SubmitType.VIEW, locale);
	}

	@GetMapping("/{email_id}/edit")
	public ModelAndView edit(@PathVariable("email_id") long emailId, Locale locale) {
		return detailsView(emailId, SubmitType.UPDATE, locale);
	}

	private ModelAndView detailsView(long emailId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		final EmailEntityViewModel emailEntityViewModel = entityModelService.prepareModel(submitType, emailId, locale);

		ModelMappingUtil.transferDataToModelAndView(emailEntityViewModel, modelAndView);

		return modelAndView;
	}

}
