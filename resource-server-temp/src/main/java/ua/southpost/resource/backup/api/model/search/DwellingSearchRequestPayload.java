package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.DwellingKind;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DwellingSearchRequestPayload extends SettlementSearchRequestPayload {
	@JsonProperty("settlement_area_pattern")
	private String settlementAreaPattern;
	@JsonProperty("dwelling_kind")
	private DwellingKind dwellingKind;
	@JsonProperty("number_of_rooms_from")
	private Integer numberOfRoomsFrom;
	@JsonProperty("number_of_rooms_to")
	private Integer numberOfRoomsTo;
	@JsonProperty("total_area_from")
	private BigDecimal totalAreaFrom;
	@JsonProperty("total_area_to")
	private BigDecimal totalAreaTo;
	@JsonProperty("living_area_from")
	private BigDecimal livingAreaFrom;
	@JsonProperty("living_area_to")
	private BigDecimal livingAreaTo;
	@JsonProperty("contact_phone_pattern")
	private String contactPhonePattern;
	@JsonProperty("contact_email_pattern")
	private String contactEmailPattern;
	@JsonProperty("billing_period")
	private BillingPeriod billingPeriod;
	@JsonProperty("price_from")
	private BigDecimal priceFrom;
	@JsonProperty("price_to")
	private BigDecimal priceTo;
}
