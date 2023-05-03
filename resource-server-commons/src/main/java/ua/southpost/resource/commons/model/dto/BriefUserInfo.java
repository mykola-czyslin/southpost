package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BriefUserInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("login")
	private String login;
	@JsonProperty("email")
	private String email;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
}
