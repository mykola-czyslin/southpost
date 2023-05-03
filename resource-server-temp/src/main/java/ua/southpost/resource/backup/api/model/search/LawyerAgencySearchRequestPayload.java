package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.LawCase;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LawyerAgencySearchRequestPayload extends SettlementSearchRequestPayload {
	@JsonProperty("agency_name_pattern")
	private String agencyNamePattern;
	@JsonProperty("web_site_pattern")
	private String webSitePattern;
	@JsonProperty("phone_num_pattern")
	private String phoneNumPattern;
	@JsonProperty("email_pattern")
	private String emailPattern;
	@JsonProperty("supported_cases")
	private Set<LawCase> cases;
}
