package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo;
import ua.southpost.resource.backup.web.model.forms.search.LawyerAgencySearchForm;
import ua.southpost.resource.backup.web.model.page.LawyerAgencyEntityViewModel;
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
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/lawyer")
public class LawyerAgencyController {
	private static final String TITLE_LAWYER = "title.lawyer";
	private static final long MOCK_LAWYER_ID =  -1L;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;
	@Resource(name = "lawyerAgencyPageListService")
	private PageListService<LawyerAgencySearchForm, LawyerAgencyInfo, Long> pageListService;
	@Resource(name = "lawyerAgencyEntityModelService")
	private EntityModelService<LawyerAgencyEntityViewModel, Long> entityModelService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	public void initBinder(@SuppressWarnings("unused") HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
		binder.registerCustomEditor(NoYes.class, new EnumEditorSupport<>(NoYes.class));
		binder.registerCustomEditor(LawCase.class, new EnumEditorSupport<>(LawCase.class));
	}

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_LAWYER;
	}

	@SuppressWarnings("unused")
	@ModelAttribute("settlementKindOptions")
	private Map<String, String> settlementKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(SettlementKind.class, request.getLocale(), 21, SettlementKind::getMessageKey, Comparator.comparing(SettlementKind::ordinal).reversed());
	}

	@SuppressWarnings("unused")
	@ModelAttribute("streetKindOptions")
	private Map<String, String> streetKindOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(StreetKind.class, request.getLocale(), 21, StreetKind::getMessageKey, Comparator.comparing(e -> -e.ordinal()));
	}

	@SuppressWarnings("unused")
	@ModelAttribute("lawCaseOptions")
	private Map<String, String> LawyerAgencyTypeOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(LawCase.class, request.getLocale(), LawCase::getMessageKey, null);
	}

	@GetMapping("/manage/create")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_LAWYER_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/manage/{lawyerAgencyId}/edit")
	public ModelAndView edit(@PathVariable("lawyerAgencyId") long LawyerAgencyId, Locale locale) {
		return detailsView(LawyerAgencyId, SubmitType.UPDATE, locale);
	}

	@GetMapping("/{lawyerAgencyId}/view")
	public ModelAndView view(@PathVariable("lawyerAgencyId") long LawyerAgencyId, Locale locale) {
		return detailsView(LawyerAgencyId, SubmitType.VIEW, locale);
	}


	private ModelAndView detailsView(@Nonnull Long agencyId, @Nonnull SubmitType submitType, @Nonnull Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		final LawyerAgencyEntityViewModel entityViewModel = entityModelService.prepareModel(submitType, agencyId, locale);

		logger.debug("Obtained\n entityViewModel for LawyerAgency id = {} and submitType = {}:\n {}", agencyId, submitType, entityViewModel);

		ModelMappingUtil.transferDataToModelAndView(entityViewModel, modelAndView);
		return modelAndView;
	}

	@GetMapping("/list")
	public ModelAndView listDefault(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") LawyerAgencySearchForm searchForm) {
		logger.info("Going to perform search of {}", searchForm);
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}
	
}
