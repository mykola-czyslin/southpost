package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.StreetKind;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.upperCase;

public class EntityPayloadPersistenceManagerTestUtil {
	static final long LOCATION_ID = 20001L;
	static final String STREET_NUMBER = "34a";
	static final String POSTAL_CODE = "67881";
	static final long STREET_ID = 30001L;
	static final StreetKind STREET_KIND = StreetKind.street;
	static final String STREET_NAME = "Street Name";
	static final long SETTLEMENT_ID = 40001L;
	static final SettlementKind SETTLEMENT_KIND = SettlementKind.city;
	static final String SETTLEMENT_NAME = "City";
	static final long DISTRICT_ID = 50003L;
	static final String REGION_ID = "000-REGID";
	static final String PHONE_NUMBER = "+380998765432";
	static final String PHONE_DESCRIPTION = "Phone Description";
	static final long PHONE_ID = 80001L;
	static final long EMAIL_ID = 90012L;
	static final String EMAIL_ADDRESS = "email@address.com";
	static final String EMAIL_DESCRIPTION = "Email Description";
	static final String DISTRICT_NAME = "District Name";
	static final String REGION_NAME = "Region Name";

	static SettlementPayload createSettlementPayload() {
		final SettlementPayload settlementPayload = new SettlementPayload();
		settlementPayload.setId(SETTLEMENT_ID);
		settlementPayload.setKind(SETTLEMENT_KIND);
		settlementPayload.setName(SETTLEMENT_NAME);
		settlementPayload.setDistrictId(DISTRICT_ID);
		settlementPayload.setRegionId(REGION_ID);
		return settlementPayload;
	}

	static StreetPayload createStreetPayload() {
		final StreetPayload streetPayload = new StreetPayload();
		streetPayload.setId(STREET_ID);
		streetPayload.setKind(STREET_KIND);
		streetPayload.setName(STREET_NAME);
		final SettlementPayload settlementPayload = createSettlementPayload();
		streetPayload.setSettlement(settlementPayload);
		return streetPayload;
	}

	static LocationPayload createLocationPayload() {
		final LocationPayload locationPayload = new LocationPayload();
		locationPayload.setId(LOCATION_ID);
		locationPayload.setStreetNumber(STREET_NUMBER);
		locationPayload.setPostalCode(POSTAL_CODE);
		final StreetPayload streetPayload = createStreetPayload();
		locationPayload.setStreet(streetPayload);
		return locationPayload;
	}

	static ContactPayload createContactPayload() {

		final ContactPayload contactPayload = new ContactPayload();

		final LocationPayload locationPayload = createLocationPayload();
		contactPayload.setLocation(locationPayload);
		final PhonePayload phonePayload = createPhonePayload();
		contactPayload.setPhones(Collections.singletonList(phonePayload));
		final EmailPayload emailPayload = createEmailPayload();
		contactPayload.setEmails(Collections.singletonList(emailPayload));
		return contactPayload;
	}

	static PhonePayload createPhonePayload() {
		final PhonePayload phonePayload = new PhonePayload();
		phonePayload.setId(PHONE_ID);
		phonePayload.setPhoneNumber(PHONE_NUMBER);
		phonePayload.setDescription(PHONE_DESCRIPTION);
		return phonePayload;
	}

	static EmailPayload createEmailPayload() {
		final EmailPayload emailPayload = new EmailPayload();
		emailPayload.setId(EMAIL_ID);
		emailPayload.setEmailAddress(EMAIL_ADDRESS);
		emailPayload.setDescription(EMAIL_DESCRIPTION);
		return emailPayload;
	}

	static boolean phoneListMeetsExpectations(List<Phone> phoneList) {
		return phoneList.size() == 1
				&& phoneList.stream().anyMatch(EntityPayloadPersistenceManagerTestUtil::phoneMeetsExpectations);
	}

	static boolean phoneMeetsExpectations(Phone phone) {
		return PHONE_ID == phone.getId()
				&& PHONE_NUMBER.equals(phone.getSearchNumber())
				&& PHONE_DESCRIPTION.equals(phone.getDescription());
	}

	static boolean emailListMeetsExpectations(List<EmailAddress> emailList) {
		return emailList.size() == 1
				&& emailList.stream()
				.anyMatch(EntityPayloadPersistenceManagerTestUtil::emailMeetsExpectations);
	}

	static boolean emailMeetsExpectations(EmailAddress ma) {
		return EMAIL_ID == ma.getId()
				&& EMAIL_ADDRESS.equals(ma.getEmailAddress())
				&& EMAIL_DESCRIPTION.equals(ma.getDescription());
	}

	static boolean locationMeetsExpectations(Location location) {
		boolean result = streetMeetsExpectations(location.getStreet())
				&& LOCATION_ID == location.getId()
				&& POSTAL_CODE.equals(location.getPostalCode())
				&& STREET_NUMBER.equals(location.getStreetNumber())
				&& upperCase(STREET_NUMBER).equals(location.getSearchStreetNumber());
		if (result) {
			return true;
		}

		System.err.printf(
				"Location does not meet expectations:\n" +
						"\tExpected: id =  %1$d, postalCode = '%2$s', street number = '%3$s\n" +
						"\tActual: %4$s\n",
				LOCATION_ID,
				POSTAL_CODE,
				STREET_NUMBER,
				location
		);
		return false;
	}

	static boolean streetMeetsExpectations(Street street) {
		boolean result = settlementMeetsExpectations(street.getSettlement())
				&& STREET_ID == street.getId()
				&& STREET_KIND == street.getKind()
				&& STREET_NAME.equals(street.getDisplayName())
				&& upperCase(STREET_NAME).equals(street.getSearchValue());
		if (result) {
			return true;
		}
		System.err.printf(
				"Street does not meet expectations:\n\tExpected: id = %1$d, kind = %2$s, displayName = '%3$s', searchValue = '%4$s'\n\tActual: %5$s\n",
				STREET_ID, SETTLEMENT_KIND, STREET_NAME, upperCase(STREET_NAME), street
		);
		return false;
	}

	static boolean settlementMeetsExpectations(Settlement settlement) {
		boolean result = districtMeetsExpectations(settlement.getDistrict())
				&& SETTLEMENT_ID == settlement.getId()
				&& SETTLEMENT_KIND == settlement.getKind()
				&& SETTLEMENT_NAME.equals(settlement.getDisplayName())
				&& upperCase(SETTLEMENT_NAME).equals(settlement.getSearchValue());
		if (result) {
			return true;
		}
		System.err.printf(
				"Settlement does not meet expectations:\n\tExpected: id = %1$d, kind = %2$s, displayName = '%3$s', searchName = '%4$s'\n\tActual %5$s:\n",
				SETTLEMENT_ID, SETTLEMENT_KIND, SETTLEMENT_NAME, upperCase(SETTLEMENT_NAME), settlement
		);
		return false;
	}

	static boolean districtMeetsExpectations(District district) {
		boolean result = regionMeetsExpectations(district.getRegion())
				&& DISTRICT_ID == district.getId()
				&& DISTRICT_NAME.equals(district.getDisplayName())
				&& upperCase(DISTRICT_NAME).equals(district.getSearchValue());
		if (result) {
			return true;
		}
		System.err.printf(
				"District does not meet expectations:\n\tExpected: id = %1$d, displayName = '%2$s', searchValue = '%3$s'\n\tActual: %4$s",
				DISTRICT_ID, DISTRICT_NAME, upperCase(DISTRICT_NAME), district
		);
		return false;
	}

	static boolean regionMeetsExpectations(Region region) {
		boolean result = REGION_ID.equals(region.getId())
				&& REGION_NAME.equals(region.getDisplayName())
				&& upperCase(REGION_NAME).equals(region.getSearchValue());
		if (result) {
			return true;
		}
		System.err.printf(
				"Region does not meet expectations:\n\tExpected: regionId = '%1$s', displayName = '%2$s', searchName = %3$s\n\tActual: %4$s\n",
				REGION_ID, REGION_NAME, upperCase(REGION_NAME), region
		);
		return false;
	}

	static Location locationFromPayload(LocationPayload locationPayload) {
		final Location location = new Location();
		location.setId(LOCATION_ID);
		location.setStreetNumber(locationPayload.getStreetNumber());
		location.setSearchStreetNumber(locationPayload.getStreetNumber());
		location.setPostalCode(locationPayload.getPostalCode());
		location.setBlockNumber(locationPayload.getBlockNumber());
		location.setSearchBlockNumber(locationPayload.getBlockNumber());
		location.setRoomNumber(locationPayload.getRoomNumber());
		location.setSearchRoomNumber(locationPayload.getRoomNumber());
		final Street street = streetFromPayload(locationPayload.getStreet());
		location.setStreet(street);
		return location;
	}

	static Street streetFromPayload(StreetPayload streetPayload) {
		final Street street = new Street();
		street.setId(STREET_ID);
		street.setKind(streetPayload.getKind());
		street.setDisplayName(streetPayload.getName());
		street.setSearchValue(streetPayload.getName());
		street.setSettlement(settlementFromPayload(streetPayload.getSettlement()));
		return street;
	}

	static Settlement settlementFromPayload(SettlementPayload settlementPayload) {
		final Settlement settlement = new Settlement();
		settlement.setId(SETTLEMENT_ID);
		settlement.setDistrict(new District());
		settlement.getDistrict().setId(settlementPayload.getDistrictId());
		settlement.getDistrict().setDisplayName(DISTRICT_NAME);
		settlement.getDistrict().setSearchValue(DISTRICT_NAME);
		settlement.getDistrict().setRegion(new Region());
		settlement.getDistrict().getRegion().setId(settlementPayload.getRegionId());
		settlement.getDistrict().getRegion().setDisplayName(REGION_NAME);
		settlement.getDistrict().getRegion().setSearchValue(REGION_NAME);
		settlement.setKind(settlementPayload.getKind());
		settlement.setDisplayName(settlementPayload.getName());
		settlement.setSearchValue(settlementPayload.getName());
		return settlement;
	}

	static Phone phoneFromPayload(PhonePayload phonePayload) {
		final Phone phone = new Phone();
		phone.setId(PHONE_ID);
		phone.setDisplayNumber(phonePayload.getPhoneNumber());
		phone.setSearchNumber(phonePayload.getPhoneNumber());
		phone.setDescription(phonePayload.getDescription());
		return phone;
	}

	static EmailAddress emailFromPayload(EmailPayload payload) {
		final EmailAddress address = new EmailAddress();
		address.setId(EMAIL_ID);
		address.setEmailAddress(payload.getEmailAddress());
		address.setDescription(payload.getDescription());
		return address;
	}

	static EmployerPayload createEmployerPayload() {
		final EmployerPayload payload = new EmployerPayload();
		payload.setId(TestDataUtil.EMPLOYER_ID);
		payload.setName(TestDataUtil.EMPLOYER_NAME);
		payload.setWebsite(TestDataUtil.WEBSITE);
		payload.setSettlement(createSettlementPayload());
		payload.setContact(createContactPayload());
		return payload;
	}
}
