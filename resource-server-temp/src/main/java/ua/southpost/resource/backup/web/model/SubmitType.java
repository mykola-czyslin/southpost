package ua.southpost.resource.backup.web.model;

public enum SubmitType {
	VIEW(false),
	CREATE(true),
	UPDATE(true);

	private final boolean formEditable;


	SubmitType(boolean formEditable) {
		this.formEditable = formEditable;
	}

	public boolean isFormEditable() {
		return formEditable;
	}
}
