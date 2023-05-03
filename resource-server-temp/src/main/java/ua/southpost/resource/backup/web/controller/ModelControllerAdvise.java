package ua.southpost.resource.backup.web.controller;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import ua.southpost.resource.backup.web.model.menu.CatalogMenu;
import ua.southpost.resource.backup.web.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * web application config
 * Created by mykola on 03.11.16.
 */
@ControllerAdvice
public class ModelControllerAdvise {
	private static final int[] LINES_PER_PAGE_OPTIONS = {5, 10, 15, 20, 25, 30, 50};
	private static final String timestamp = String.format("%d", System.currentTimeMillis());
	@Resource
	private CatalogMenu mainMenu;
	@Value("${static.media.url.prefix}")
	private String staticMediaUrl;


	@SuppressWarnings("SameReturnValue")
	@ModelAttribute("launchTimestamp")
	private String timestamp() {
		return timestamp;
	}

	@ModelAttribute(name = "menu")
	CatalogMenu menu() {
		return mainMenu;
	}

	@ModelAttribute(name = "staff")
	private boolean staffUser(HttpServletRequest request) {
		return SecurityUtils.isStaff(request);
	}

	@ModelAttribute(name = "adminUser")
	private boolean adminUser(HttpServletRequest request) {
		return SecurityUtils.isAdmin(request);
	}

	@ModelAttribute(name = "statics")
	private TemplateHashModel statics() {
		BeansWrapper w = new BeansWrapperBuilder(Configuration.VERSION_2_3_25).build();
		return w.getStaticModels();
	}

	@SuppressWarnings("SameReturnValue")
	@ModelAttribute(name = "linesPerPageOptions")
	private int[] linesPerPageOptions() {
		return LINES_PER_PAGE_OPTIONS;
	}

	@ModelAttribute(name = "staticMediaUrl")
	private String staticMediaUrl() {
		return staticMediaUrl;
	}

}
