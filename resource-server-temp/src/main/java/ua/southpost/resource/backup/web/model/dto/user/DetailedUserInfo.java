package ua.southpost.resource.backup.web.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import org.springframework.format.annotation.DateTimeFormat;
import ua.southpost.resource.commons.model.dto.BriefUserInfo;

import java.util.Date;
import java.util.Set;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DetailedUserInfo extends BriefUserInfo {
	@JsonProperty("password")
	private String password;
	@JsonProperty("roles")
	private Set<AbstractEntityInfo<UserRole>> roles;
	@JsonProperty("declared_activities")
	private Set<AbstractEntityInfo<UserActivityKind>> declaredActivities;
	@JsonProperty("confirmed_activities")
	private Set<AbstractEntityInfo<UserActivityKind>> confirmedActivities;
	@JsonProperty("registered_at")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date registeredAt;
	@JsonProperty("internal")
	private boolean internal;
}
