package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.UserSearchRequestPayloadImpl;
import ua.southpost.resource.backup.web.model.forms.search.UserSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class UserSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<UserSearchForm, UserSearchRequestPayloadImpl> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull UserSearchForm form, @Nonnull UserSearchRequestPayloadImpl payload) {
		payload.setLoginPattern(form.getLoginPattern());
		payload.setEmailPattern(form.getEmailPattern());
		payload.setFirstNamePattern(form.getFirstNamePattern());
		payload.setLastNamePattern(form.getLastNamePattern());
		payload.setAnyRole(form.anyRoleAcceptable());
		payload.setRoles(form.userRoles());
		payload.setAnyDeclaredActivity(form.anyDeclaredActivityAcceptable());
		payload.setDeclaredActivities(form.userDeclaredActivities());
		payload.setAnyConfirmedActivity(form.anyConfirmedActivityAcceptable());
		payload.setConfirmedActivities(form.userConfirmedActivities());
		payload.setRegisteredAtFrom(form.getRegisteredAtFrom());
		payload.setRegisteredAtTo(form.getRegisteredAtTo());
	}

	@Nonnull
	@Override
	protected UserSearchRequestPayloadImpl createPayload() {
		return new UserSearchRequestPayloadImpl();
	}
}
