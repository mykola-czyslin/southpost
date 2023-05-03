package ua.southpost.resource.backup.api.model.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.NoYes;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VacancySearchRequestPayload extends SettlementSearchRequestPayload {
	private String summaryPattern;
	private String descriptionPattern;
	private BigDecimal salaryLow;
	private NoYes hosting;
	private String employerNamePattern;
	private String phonePattern;
	private String mailPattern;
}
