package ua.southpost.resource.backup.web.model.menu;

/**
 * The generic menu abstraction
 * Created by mykola on 14.10.16.
 */
public abstract class AbstractMenu {
	private final String title;
	private boolean enabled;
	private int level = 0;

	AbstractMenu(String title) {
		this.title = title;
		this.enabled = true;
	}

	public String getTitle() {
		return title;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if (enabled) {
			enable();
		} else {
			disable();
		}
	}

	public void disable() {
		enabled = false;
	}

	public void enable() {
		enabled = true;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
