package ua.southpost.resource.backup.web.model.forms;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.web.model.forms.entity.validate.NewUserPasswordRule;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * User registration form;
 * Created by mykola on 22.10.16.
 */
@NewUserPasswordRule(confirmPasswordProperty = "passwordRetype")
public class RegisterUserForm extends EditMailAddressUserForm {

	@NotEmpty
	private String password;
	@NotEmpty
	private String passwordRetype;
	private Set<UserActivityKind> userActivityKinds;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRetype() {
		return passwordRetype;
	}

	public void setPasswordRetype(String passwordRetype) {
		this.passwordRetype = passwordRetype;
	}

	public Set<UserActivityKind> getUserActivityKinds() {
		return userActivityKinds;
	}

	public void setUserActivityKinds(Set<UserActivityKind> userActivityKinds) {
		this.userActivityKinds = userActivityKinds;
	}
}
