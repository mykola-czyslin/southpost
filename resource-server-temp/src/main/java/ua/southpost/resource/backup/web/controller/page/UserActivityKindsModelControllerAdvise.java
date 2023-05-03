package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice(assignableTypes = {UserProfileController.class, UserAdminController.class, AuthenticationController.class})
public class UserActivityKindsModelControllerAdvise {
	@Resource
	private EnumOptionsUtil enumOptionsUtil;


	@ModelAttribute("userActivityKindOptions")
	public Map<String, String> userKindOptions(Locale locale) {
		return enumOptionsUtil.options(UserActivityKind.class, locale, UserActivityKind::getMessageKey, Comparator.comparing(UserActivityKind::ordinal));
	}

}
