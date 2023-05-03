package ua.southpost.resource.backup.web.service.user;

import lombok.Builder;
import lombok.Data;
import ua.southpost.resource.backup.data.model.UserActivityKind;

import java.util.Set;

@Data
@Builder
public class UserRegistrationRequest {
	private final String login;
	private final String password;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final Set<UserActivityKind> declaredActivities;
}
