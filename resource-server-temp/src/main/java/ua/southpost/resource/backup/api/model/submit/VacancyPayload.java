package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.VacancyEntity;
import ua.southpost.resource.commons.model.dto.EntityPayload;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@ToString
@VacancyEntity
public class VacancyPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("employer")
	@Valid
	private EmployerPayload employer;
	@JsonProperty("summary")
	@NotEmpty(message = "err.vacancy.summary")
	private String summary;
	@JsonProperty("description")
	private String description;
	@JsonProperty("hosting")
	private boolean hosting;
	@JsonProperty("salary_low")
	private BigDecimal salaryLow;
	@JsonProperty("salary_high")
	private BigDecimal salaryHigh;

}
