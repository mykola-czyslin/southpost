package ua.southpost.resource.backup.data.model;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public enum UserActivityKind implements MessageKeyHolder {
	EMPLOYER("user.activity.employer", Pattern.compile("[/a-zA-Z.\\-_:0-9]*/vacancy/[/a-zA-Z.\\-_:0-9]*")),
	PROPERTY_AGENT("user.activity.property.agent", Pattern.compile("[/a-zA-Z.\\-_:0-9]*/dwelling/[/a-zA-Z.\\-_:0-9]*")),
	LAW_ASSISTANT("user.activity.lawyer", Pattern.compile("[/a-zA-Z.\\-_:0-9]*/lawyer/[/a-zA-Z.\\-_:0-9]*")),
	CLINIC_ADVISOR("user.activity.clinic.advisor", Pattern.compile("[/a-zA-Z.\\-_:0-9]*/clinic/[/a-zA-Z.\\-_:0-9]*")),
	VISITOR("user.activity.visitor",Pattern.compile("[/a-zA-Z.\\-_:0-9]*/\\*\\*\\*This pattern don't matches any URI\\*\\*\\*/[/a-zA-Z.\\-_:0-9]*"), false);

	@Nonnull
	private final String messageKey;
	@Nonnull
	private final Pattern uriPattern;
	private final boolean roleDeterminant;

	UserActivityKind(@Nonnull String messageKey, @Nonnull Pattern uriPattern) {
		this(messageKey, uriPattern, true);
	}

	UserActivityKind(@Nonnull String messageKey, @Nonnull Pattern uriPattern, boolean roleDeterminant) {
		this.messageKey = messageKey;
		this.uriPattern = uriPattern;
		this.roleDeterminant = roleDeterminant;
	}

	@Nonnull
	@Override
	public String getMessageKey() {
		return messageKey;
	}

	public boolean isRoleDeterminant() {
		return roleDeterminant;
	}

	public boolean isStaffArea(@Nonnull String uri) {
		return this.uriPattern.matcher(uri).matches();
	}
}
