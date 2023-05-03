package ua.southpost.resource.backup.web.model.dto.dwelling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.DwellingKind;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.commons.model.dto.AbstractModificationTrackingEntityInfo;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.dto.contact.ContactInfo;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DwellingInfo extends AbstractModificationTrackingEntityInfo<Long> implements ContactInfo {
	@JsonProperty("settlement")
	private SettlementInfo settlement;
	@JsonProperty("settlement_area")
	private String settlementArea;
	@JsonProperty("kind")
	private AbstractEntityInfo<DwellingKind> kind;
	@JsonProperty("number_of_rooms")
	private int numberOfRooms;
	@JsonProperty("total_area")
	private BigDecimal totalArea;
	@JsonProperty("living_area")
	private BigDecimal livingArea;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("billing_period")
	private AbstractEntityInfo<BillingPeriod> billingPeriod;
	@JsonProperty("address")
	private LocationInfo address;
	@JsonProperty("description")
	private String description;
	@JsonProperty("phones")
	private List<PhoneInfo> phones;
	@JsonProperty("emails")
	private List<EmailInfo> emails;


	@Override
	public LocationInfo getLocation() {
		return getAddress();
	}

	@Override
	public void setLocation(LocationInfo location) {
		setAddress(location);
	}
}
