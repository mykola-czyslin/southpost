package ua.southpost.resource.backup.web.model.menu;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Nonnull;

/**
 * This menu is intended to be handled as form action.
 * Created by mykola on 16.11.16.
 */
public class FormActionMenu extends ActionMenu {
	private final String formId;
	@Nonnull
	private final RequestMethod method;

	FormActionMenu(String formId, String title, String actionUri, @Nonnull RequestMethod requestMethod) {
		super(title, actionUri);
		this.formId = formId;
		this.method = requestMethod;
	}

	@SuppressWarnings("unused")
	public String getFormId() {
		return formId;
	}

	@Nonnull
	public RequestMethod getMethod() {
		return method;
	}
}
