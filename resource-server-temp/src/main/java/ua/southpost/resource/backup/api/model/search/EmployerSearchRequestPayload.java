package ua.southpost.resource.backup.api.model.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployerSearchRequestPayload extends SettlementSearchRequestPayload {
	private String employerNamePattern;
	private boolean webSiteSignificant;
	private String webSiteAddressPattern;
}
