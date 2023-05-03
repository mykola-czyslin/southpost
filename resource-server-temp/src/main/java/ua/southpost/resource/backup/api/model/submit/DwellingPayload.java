package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.DwellingKind;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@ToString
public class DwellingPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("settlement")
	@Valid
	@NotNull(message = "err.settlement.required")
	private SettlementPayload settlement;
	@JsonProperty("settlement_area")
	private String settlementArea;
	@NotNull(message = "err.dwelling.kind.required")
	@JsonProperty("dwelling_kind")
	private DwellingKind dwellingKind;
	@JsonProperty("number_of_rooms")
	private int numberOfRooms;
	@JsonProperty("total_area")
	private BigDecimal totalArea;
	@JsonProperty("living_area")
	private BigDecimal livingArea;
	@JsonProperty("contact")
	@Valid
	@NotNull(message = "err.contact.required")
	private ContactPayload contact;
	@JsonProperty("price")
	@NotNull(message = "err.price.required")
	private BigDecimal price;
	@JsonProperty("billing_period")
	@NotNull(message = "err.billing.period.required")
	private BillingPeriod billingPeriod;
	@JsonProperty("description")
	private String description;
}
