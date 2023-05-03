package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.backup.data.model.LawCase;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class LawyerAgencyPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("settlement")
	@Valid
	@NotNull(message = "err.settlement.required")
	private SettlementPayload settlement;
	@JsonProperty("agency_name")
	@NotEmpty(message = "err.lawyer.agency.name.missed")
	private String agencyName;
	@JsonProperty("web_site")
	@NotEmpty(message = "err.lawyer.agency.web.site.missed")
	private String webSite;
	@JsonProperty("contact")
	@Valid
	private ContactPayload contact;
	@JsonProperty("cases")
	@NotEmpty(message = "err.lawyer.at.least.one.case.required")
	private List<LawCase> cases;

}
