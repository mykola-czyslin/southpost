package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.support.EnumEditorSupport;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import ua.southpost.resource.backup.web.model.forms.search.ClinicSearchForm;
import ua.southpost.resource.backup.web.model.page.ClinicEntityViewModel;
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
@RequestMapping("/clinic")
public class ClinicController {
	private static final String TITLE_CLINIC = "title.clinic";
	private static final long MOCK_CLINIC_ID =  -1L;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;
	@Resource(name = "clinicPageListService")
	private PageListService<ClinicSearchForm, ClinicInfo, Long> pageListService;
	@Resource(name = "clinicEntityModelService")
	private EntityModelService<ClinicEntityViewModel, Long> entityModelService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	public void initBinder(@SuppressWarnings("unused") HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(SettlementKind.class, new EnumEditorSupport<>(SettlementKind.class));
		binder.registerCustomEditor(NoYes.class, new EnumEditorSupport<>(NoYes.class));
		binder.registerCustomEditor(ClinicType.class, new EnumEditorSupport<>(ClinicType.class));
		binder.registerCustomEditor(MedicalService.class, new EnumEditorSupport<>(MedicalService.class));
	}

	@SuppressWarnings({"unused", "SameReturnValue"})
	@ModelAttribute(name = "title")
	private String title() {
		return TITLE_CLINIC;
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
	@ModelAttribute("clinicTypeOptions")
	private Map<String, String> clinicTypeOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(ClinicType.class, request.getLocale(), 15, ClinicType::getMessageKey, null);
	}

	@ModelAttribute("medicalServiceOptions")
	private Map<String, String> medicalServiceOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(MedicalService.class, request.getLocale(), MedicalService::getMessageKey, null);
	}

	@ModelAttribute("medicalServiceSelectOptions")
	private Map<String, String> medicalServiceSelectOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(MedicalService.class, request.getLocale(), 24, MedicalService::getMessageKey, null);
	}

	@GetMapping("/manage/create")
	public ModelAndView create(Locale locale) {
		return detailsView(MOCK_CLINIC_ID, SubmitType.CREATE, locale);
	}

	@GetMapping("/manage/{clinicId}/edit")
	public ModelAndView edit(@PathVariable("clinicId") long clinicId, Locale locale) {
		return detailsView(clinicId, SubmitType.UPDATE, locale);
	}

	@GetMapping("/{clinicId}/view")
	public ModelAndView view(@PathVariable("clinicId") long clinicId, Locale locale) {
		return detailsView(clinicId, SubmitType.VIEW, locale);
	}


	private ModelAndView detailsView(@Nonnull Long clinicId, @Nonnull SubmitType submitType, @Nonnull Locale locale) {
		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);

		final ClinicEntityViewModel entityViewModel = entityModelService.prepareModel(submitType, clinicId, locale);

		logger.debug("Obtained\n entityViewModel for clinic id = {} and submitType = {}:\n {}", clinicId, submitType, entityViewModel);

		ModelMappingUtil.transferDataToModelAndView(entityViewModel, modelAndView);
		return modelAndView;
	}

	@GetMapping("/list")
	public ModelAndView listDefault(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping("/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @ModelAttribute("searchForm") ClinicSearchForm searchForm) {
		logger.info("Going to perform search of {}", searchForm);
		return pageListService.prepareListModelAndView(searchForm, request.getLocale());
	}

}
