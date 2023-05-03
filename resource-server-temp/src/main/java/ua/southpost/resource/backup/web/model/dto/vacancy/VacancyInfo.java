package ua.southpost.resource.backup.web.model.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.commons.model.dto.AbstractModificationTrackingEntityInfo;
import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VacancyInfo extends AbstractModificationTrackingEntityInfo<Long> {
	@JsonProperty("employer")
	private EmployerInfo employer;
	@JsonProperty("summary")
	private String summary;
	@JsonProperty("description")
	private String description;
	@JsonProperty("hosting_available")
	private AbstractEntityInfo<NoYes> hostingAvailable;
	@JsonProperty("salary_low")
	private BigDecimal salaryLow;
	@JsonProperty("salary_high")
	private BigDecimal salaryHigh;
}
