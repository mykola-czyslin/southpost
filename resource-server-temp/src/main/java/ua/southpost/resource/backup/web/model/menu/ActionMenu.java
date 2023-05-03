package ua.southpost.resource.backup.web.model.menu;

/**
 * The simple action menu abstraction;
 * Created by mykola on 14.10.16.
 */
@SuppressWarnings("WeakerAccess")
public class ActionMenu extends AbstractMenu {
	private final String actionUri;

	ActionMenu(String title, String actionUri) {
		super(title);
		this.actionUri = actionUri;
	}

	public String getActionUri() {
		return actionUri;
	}
}
