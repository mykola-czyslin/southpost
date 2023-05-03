/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.model.menu.AbstractMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static ua.southpost.resource.backup.web.controller.ControllerConstants.CONTAINER_PAGE_VIEW;
import static ua.southpost.resource.backup.web.controller.ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Global error handler
 * Created by mykola on 02.11.16.
 */
@ControllerAdvice
@RequestScope
public class GlobalExceptionHandler {
	private static final String DEFAULT_VIEW = "exception";
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());
	private ResourceBundle messageBundle = null;

	@Resource(name = "mainMenu")
	private AbstractMenu mainMenu;

	@ExceptionHandler(value = NotFoundException.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) {
		logger.error(String.format("Exception was intercepted for request: %s", request.getRequestURI()), exception);
		ModelAndView modelAndView = new ModelAndView();
		Throwable t = exception;
		List<Throwable> causes = new ArrayList<>();
		while (t.getCause() != null) {
			causes.add((t = t.getCause()));
		}
		modelAndView.addObject("title", "title.error");
		modelAndView.addObject("message", localizedMessage(exception));
		modelAndView.addObject("exception", exception);
		modelAndView.addObject("causes", causes);
		modelAndView.addObject("url", request.getRequestURL().toString());
		modelAndView.addObject("menu", mainMenu);
		if (request.getRequestURI().contains("/api/") || isNotBlank(request.getParameter("ajax"))) {
			modelAndView.setViewName(DEFAULT_VIEW);
		} else {
			modelAndView.addObject(CONTENT_VIEW_MODEL_ATTRIBUTE, DEFAULT_VIEW);
			modelAndView.setViewName(CONTAINER_PAGE_VIEW);
		}

		return modelAndView;
	}

	private String localizedMessage(Exception e) {
		if (e instanceof NotFoundException) {
			NotFoundException notFoundException = (NotFoundException) e;
			return MessageFormat.format(
					getMessageBundle().getString(notFoundException.getMessageKey()),
					notFoundException.getArguments()
			);
		}
		return e.getLocalizedMessage();
	}

	private synchronized ResourceBundle getMessageBundle() {
		return Optional.ofNullable(messageBundle)
				.orElseGet(() -> (messageBundle = ResourceBundle.getBundle("messages.web-app")));
	}

}
