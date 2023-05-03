/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;
import ua.southpost.resource.backup.web.ApplicationURI;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm;
import ua.southpost.resource.backup.web.model.page.VacancyEntityViewModel;
import ua.southpost.resource.backup.web.service.pagemodel.EntityModelService;
import ua.southpost.resource.backup.web.service.pagemodel.ModelMappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Handles requests related with vacancy exploration.
 * Created by mchys on 08.03.2018.
 */
@Controller
@RequestMapping("/vacancy")
public class VacancyController {
	private static final String TITLE_VACANCY = "title.vacancy";
	private static final long MOCK_VACANCY_ID = -1L;
	@Resource(name = "vacancyPageListService")
	private PageListService<VacancySearchForm, VacancyInfo, Long> pageListService;
	@Resource(name = "vacancyEntityModelService")
	private EntityModelService<VacancyEntityViewModel, Long> entityModelService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_VACANCY;
	}


	@InitBinder
	public void initBinder(@SuppressWarnings("unused") HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
		binder.registerCustomEditor(NoYes.class, new EnumEditorSupport<>(NoYes.class));
	}

	@RequestMapping(value = {"/list", "/manage/list"}, method = RequestMethod.GET)
	public ModelAndView listVacancies(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping(value = {"/list"})
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") VacancySearchForm searchForm) {
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

	@RequestMapping(value = "/manage/create", method = RequestMethod.GET)
	public ModelAndView create(Locale locale) {
		return getDetailsView(MOCK_VACANCY_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/manage/{vacancyId}/edit")
	public ModelAndView edit(@PathVariable(name = "vacancyId") long vacancyId, Locale locale) {
		return getDetailsView(vacancyId, SubmitType.UPDATE, locale);
	}

	@GetMapping("/{vacancyId}/view")
	public ModelAndView view(@PathVariable(name = "vacancyId") long vacancyId, Locale locale) {
		return getDetailsView(vacancyId, SubmitType.VIEW, locale);
	}


	private ModelAndView getDetailsView(long vacancyId, SubmitType submitType, Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		final VacancyEntityViewModel entityViewModel = entityModelService.prepareModel(submitType, vacancyId, locale);

		logger.debug("Obtained\n entityViewModel for clinic id = {} and submitType = {}:\n {}", vacancyId, submitType, entityViewModel);

		ModelMappingUtil.transferDataToModelAndView(entityViewModel, modelAndView);

		return modelAndView;
	}

	@RequestMapping(value = "/manage/upload", method = RequestMethod.GET)
	public ModelAndView upload() {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		modelAndView.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, ApplicationURI.UNDER_CONSTRUCTION);
		return modelAndView;
	}
}
