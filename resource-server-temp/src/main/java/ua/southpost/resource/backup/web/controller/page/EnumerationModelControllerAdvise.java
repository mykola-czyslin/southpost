package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Map;

@ControllerAdvice(assignableTypes = {VacancyController.class})
public class EnumerationModelControllerAdvise {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnumerationModelControllerAdvise.class);
	@Resource
	private EnumOptionsUtil enumOptionsUtil;

	@SuppressWarnings("unused")
	@ModelAttribute("yesNoOptions")
	private Map<String, String> yesNoOptions(HttpServletRequest request) {
		System.err.printf("\n\tenumOptionsUtil: %s;\n\trequest: %s\n", enumOptionsUtil, request);
		LOGGER.info("\n\tenumOptionsUtil: {};\n\trequest: {}", enumOptionsUtil, request);
		return enumOptionsUtil.options(NoYes.class, request.getLocale(), 5, NoYes::getMessageKey, null);
	}

	@ModelAttribute("settlementKindOptions")
	private Map<String, String> settlementKindOptions(HttpServletRequest request) {
		System.err.printf("\n\tenumOptionsUtil: %s;\n\trequest: %s\n", enumOptionsUtil, request);
		LOGGER.info("\n\tenumOptionsUtil: {};\n\trequest: {}", enumOptionsUtil, request);
		return enumOptionsUtil.options(SettlementKind.class, request.getLocale(), 21, e -> "settlement.kind." + e.name(), Comparator.comparing(e -> -e.ordinal()));
	}

	@ModelAttribute("streetKindOptions")
	private Map<String, String> streetKindOptions(HttpServletRequest request) {
		System.err.printf("\n\tenumOptionsUtil: %s;\n\trequest: %s\n", enumOptionsUtil, request);
		LOGGER.info("\n\tenumOptionsUtil: {};\n\trequest: {}", enumOptionsUtil, request);
		return enumOptionsUtil.options(StreetKind.class, request.getLocale(), 21, StreetKind::getMessageKey, Comparator.comparing(e -> -e.ordinal()));
	}
}
