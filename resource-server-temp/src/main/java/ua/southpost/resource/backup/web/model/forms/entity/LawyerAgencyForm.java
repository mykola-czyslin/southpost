package ua.southpost.resource.backup.web.model.forms.entity;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class LawyerAgencyForm implements RegionalEntityForm<Long>{
	private Long id;
	private SettlementForm settlementForm;
	private String agencyName;
	private String webSite;
	private ContactForm contact;
	private List<String> cases;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public ContactForm getContact() {
		return Optional.ofNullable(contact).orElseGet(() -> (contact = new ContactForm()));
	}

	public void setContact(ContactForm contact) {
		this.contact = contact;
	}

	public List<String> getCases() {
		return cases;
	}

	public void setCases(List<String> cases) {
		this.cases = cases;
	}

	public SettlementForm getSettlementForm() {
		return ofNullable(settlementForm).orElseGet(() -> settlementForm = new SettlementForm());
	}

	public void setSettlementForm(SettlementForm settlementForm) {
		this.settlementForm = settlementForm;
	}


	@Override
	public String regionId() {
		return ofNullable(getSettlementForm()).map(SettlementForm::regionId).orElse(MOCK_REGION_ID);
	}
}
