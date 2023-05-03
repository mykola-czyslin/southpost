package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.web.model.forms.AbstractUserForm;
import ua.southpost.resource.backup.web.model.forms.entity.validate.LoginUserRule;
import ua.southpost.resource.backup.web.model.forms.entity.validate.NewUserPasswordRule;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Provides ability to update user credentials.
 * Created by mchys on 17.11.2016.
 */
@NewUserPasswordRule(confirmPasswordProperty = "passwordRetype")
@LoginUserRule(passwordProperty = "oldPassword")
public class UpdateUserCredentialsForm extends AbstractUserForm implements UpdateUserForm {
	@NotNull
	private long userId;
	@NotEmpty
	private String oldPassword;
	@NotEmpty
	private String password;
	@NotEmpty
	private String passwordRetype;

	public static UpdateUserCredentialsForm fromUser(@NotNull User user) {
		UpdateUserCredentialsForm updateForm = new UpdateUserCredentialsForm();
		updateForm.setUserId(user.getId());
		updateForm.setLogin(user.getLogin());
		updateForm.setFirstName(user.getFirstName());
		updateForm.setLastName(user.getLastName());
		return updateForm;
	}

	public void mergeTo(@NotNull User user, @NotNull PasswordEncoder encoder) {
		Optional.ofNullable(this.getLogin()).ifPresent(user::setLogin);
		Optional.ofNullable(this.password).map(encoder::encode).ifPresent(user::setPassword);
		Optional.ofNullable(this.getFirstName()).ifPresent(user::setFirstName);
		Optional.ofNullable(this.getLastName()).ifPresent(user::setLastName);
	}

	@Override
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

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

	@Override
	public Long getId() {
		return getUserId();
	}

	@Override
	public void setId(Long id) {
		setUserId(id);
	}
}
