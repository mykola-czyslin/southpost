/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.service.mail.GmailSender;
import ua.southpost.resource.backup.util.MessageBuilder;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.model.forms.MailForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Controls mailing requests.
 * Created by mchys on 13.03.2018.
 */
@Controller
@RequestMapping("/mail")
public class MailController {
	@Resource
	private GmailSender gmailSender;

	@PostMapping(value = "send")
	public ModelAndView send(@ModelAttribute("mailForm") @Valid MailForm mailForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.AJAX_MAILING_RESPONSE);
		if (!bindingResult.hasErrors()) {
			new MessageBuilder()
					.withTo(mailForm.getRecipient())
					.withSubj(mailForm.getSubject())
					.withText(mailForm.getContent())
					.buildAndSendWith(gmailSender);
			modelAndView.addObject("success", true);
		}
		return modelAndView;
	}
}
