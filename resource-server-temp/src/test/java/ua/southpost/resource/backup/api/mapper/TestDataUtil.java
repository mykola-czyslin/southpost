package ua.southpost.resource.backup.api.mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.DwellingKind;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.address.DistrictInfo;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.dto.address.RegionInfo;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo;
import ua.southpost.resource.commons.model.dto.BriefUserInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestDataUtil {
	private static final Date MODIFICATION_TIME;

	public static final int MODIFICATION_YEAR = 2020;

	public static final int MODIFICATION_MONTH = Calendar.FEBRUARY;

	public static final int MODIFICATION_DATE = 27;

	public static final int MODIFICATION_HOUR = 16;

	public static final int MODIFICATION_MINUTE = 0;
	public static final ImmutableSet<UserRole> USER_ROLES = ImmutableSet.of(UserRole.VIEWER, UserRole.OPERATOR);
	public static final ImmutableSet<UserActivityKind> USER_DECLARED_ACTIVITIES = ImmutableSet.of(UserActivityKind.EMPLOYER, UserActivityKind.CLINIC_ADVISOR, UserActivityKind.PROPERTY_AGENT);
	public static final ImmutableSet<UserActivityKind> USER_CONFIRMED_ACTIVITIES = ImmutableSet.of(UserActivityKind.EMPLOYER, UserActivityKind.PROPERTY_AGENT);
	public static final ImmutableSet<LawCase> LAW_CASES = ImmutableSet.of(LawCase.CRIMINAL, LawCase.CIVIL, LawCase.FAMILY);
	public static final String DWELLING_DESCRIPTION = "Dwelling Description";

	static {
		Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(Calendar.YEAR, MODIFICATION_YEAR);
		calendar.set(Calendar.MONTH, MODIFICATION_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, MODIFICATION_DATE);
		calendar.set(Calendar.HOUR_OF_DAY, MODIFICATION_HOUR);
		calendar.set(Calendar.MINUTE, MODIFICATION_MINUTE);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		MODIFICATION_TIME = calendar.getTime();
	}

	public static final long LAWYER_AGENCY_ID = 888888L;
	public static final String LAWYER_AGENCY_NAME = "Lawyer Agency";
	public static final String LAWYER_AGENCY_SITE = "https://lowyer.agency.com.ua/";
	public static final long CLINIC_ID = 9999L;
	public static final String CLINIC_NAME = "Clinic Name";
	public static final String CLINIC_DESCRIPTION = "Clinic Description";
	public static final Locale NATIVE_LOCALE = new Locale("uk", "UA");
	public static final String CONTACT_NAME = "Contact Name";
	public static final String E_MAIL_DESCRIPTION = CONTACT_NAME + " e-mail";
	public static final String PHONE_DESCRIPTION = CONTACT_NAME + " PHONE";
	public static final ClinicType CLINIC_TYPE = ClinicType.MIXED;
	public static final long USER_ID = 999L;
	public static final String LOGIN = "Login";
	public static final String EMAIL = "user@mail.com";
	public static final String FIRST_NAME = "First";
	public static final String LAST_NAME = "Last";
	public static final String SEARCH_NUMBER = "+380678995544";
	public static final String DISPLAY_NUMBER = "+38(067)899-55-44";
	public static final ImmutableSet<MedicalService> MEDICAL_SERVICES = ImmutableSet.of(MedicalService.GENERAL_THERAPY, MedicalService.OTOLARYNGOLOGY, MedicalService.SURGERY, MedicalService.UROLOGY);
	public static final long LOCATION_ID = 22222L;
	public static final String POSTAL_CODE = "00009";
	public static final String STREET_NUMBER = "88A";
	public static final String BLOCK_NUMBER = "Block #A";
	public static final String ROOM_NUMBER = "99A";
	public static final long STREET_ID = 33333L;
	public static final String STREET_NAME = "Test Street";
	public static final long SETTLEMENT_ID = 44444L;
	public static final String SETTLEMENT_NAME = "Test City";
	public static final SettlementKind SETTLEMENT_KIND = SettlementKind.city;
	public static final long DISTRICT_ID = 55555L;
	public static final String DISTRICT_NAME = "District Name";
	public static final NoYes MOCK_DISTRICT = NoYes.NO;
	public static final String REGION_ID = "TEST-REGION";
	public static final String REGION_NAME = "Region Name";
	public static final String EMAIL_ADDRESS = "test@email.address.com";
	public static final StreetKind STREET_KIND = StreetKind.street;
	public static final long EMPLOYER_ID = 6666L;
	public static final String EMPLOYER_NAME = "Employer Name";
	public static final String WEBSITE = "https://employer.website.com/";
	public static final long DWELLING_ID = 7777L;
	public static final String SETTLEMENT_AREA = "Area";
	public static final DwellingKind DWELLING_KIND = DwellingKind.FLAT;
	public static final BigDecimal DWELLING_PRICE = BigDecimal.TEN;
	public static final int NUMBER_OF_ROOMS = 3;
	public static final BigDecimal TOTAL_AREA = BigDecimal.valueOf(5685, 2);
	public static final BigDecimal LIVING_AREA = BigDecimal.valueOf(3765, 2);
	public static final BillingPeriod BILLING_PERIOD = BillingPeriod.MONTH;
	public static final long VACANCY_ID = 8888L;
	public static final String VACANCY_SUMMARY = "Vacancy Summary";
	public static final String VACANCY_DESCRIPTION = "Vacancy Description";
	public static final BigDecimal SALARY_LOW = BigDecimal.valueOf(1800000, 2);
	public static final BigDecimal SALARY_HIGH = BigDecimal.valueOf(2200000, 2);
	public static final NoYes HOSTING_AVAILABLE = NoYes.YES;
	public static final String PASSWORD = "**********";


	public static Vacancy createVacancy() {
		Vacancy vacancy = new Vacancy();
		vacancy.setId(VACANCY_ID);
		vacancy.setEmployer(createEmployerCommon());
		vacancy.setSummary(VACANCY_SUMMARY);
		vacancy.setSearchValue(VACANCY_SUMMARY);
		vacancy.setDescription(VACANCY_DESCRIPTION);
		vacancy.setSalaryLow(SALARY_LOW);
		vacancy.setSalaryHigh(SALARY_HIGH);
		vacancy.setHostingAvailable(HOSTING_AVAILABLE);
		vacancy.setModificationTime(MODIFICATION_TIME);
		vacancy.setModifiedBy(createUser());
		return vacancy;
	}

	public static void assertVacancyInfo(VacancyInfo vacancyInfo) {
		assertNotNull(vacancyInfo);
		assertEquals((Long) VACANCY_ID, vacancyInfo.getId());
		assertEquals(VACANCY_SUMMARY, vacancyInfo.getSummary());
		assertEquals(VACANCY_DESCRIPTION, vacancyInfo.getDescription());
		assertEquals(SALARY_LOW, vacancyInfo.getSalaryLow());
		assertEquals(SALARY_HIGH, vacancyInfo.getSalaryHigh());
		assertEquals(HOSTING_AVAILABLE, vacancyInfo.getHostingAvailable().getId());
		assertEmployerInfoCommon(vacancyInfo.getEmployer());
		assertNotNull(vacancyInfo.getModificationTime());
		assertBriefUserInfo(vacancyInfo.getModifiedBy());
	}

	public static Dwelling createDwelling() {
		Dwelling dwelling = new Dwelling();
		dwelling.setId(DWELLING_ID);
		dwelling.setSettlement(createSettlement());
		dwelling.setSettlementArea(SETTLEMENT_AREA);
		dwelling.setSettlementAreaSearch(SETTLEMENT_AREA);
		dwelling.setKind(DWELLING_KIND);
		dwelling.setLocationAddress(createLocation());
		dwelling.setPhones(createPhones());
		dwelling.setEmails(createEmails());
		dwelling.setPrice(DWELLING_PRICE);
		dwelling.setBillingPeriod(BILLING_PERIOD);
		dwelling.setNumberOfRooms(NUMBER_OF_ROOMS);
		dwelling.setTotalArea(TOTAL_AREA);
		dwelling.setLivingArea(LIVING_AREA);
		dwelling.setModificationTime(MODIFICATION_TIME);
		dwelling.setDescription(DWELLING_DESCRIPTION);
		dwelling.setModifiedBy(createUser());
		return dwelling;
	}

	public static void assertDwellingInfo(DwellingInfo dwellingInfo) {
		assertNotNull(dwellingInfo);
		assertEquals((Long) DWELLING_ID, dwellingInfo.getId());
		assertSettlementInfo(dwellingInfo.getSettlement());
		assertEquals(SETTLEMENT_AREA, dwellingInfo.getSettlementArea());
		assertEquals(DWELLING_KIND, dwellingInfo.getKind().getId());
		assertEquals(NUMBER_OF_ROOMS, dwellingInfo.getNumberOfRooms());
		assertEquals(TOTAL_AREA, dwellingInfo.getTotalArea());
		assertEquals(LIVING_AREA, dwellingInfo.getLivingArea());
		assertEquals(DWELLING_PRICE, dwellingInfo.getPrice());
		assertEquals(BILLING_PERIOD, dwellingInfo.getBillingPeriod().getId());
		assertLocationInfo(dwellingInfo.getAddress());
		assertPhones(dwellingInfo.getPhones());
		assertEmails(dwellingInfo.getEmails());
		assertNotNull(dwellingInfo.getModificationTime());
		assertEquals(MODIFICATION_TIME, dwellingInfo.getModificationTime());
		assertBriefUserInfo(dwellingInfo.getModifiedBy());
	}

	static Employer createEmployerCommon() {
		Employer employer = new Employer();
		employer.setId(EMPLOYER_ID);
		employer.setDisplayName(EMPLOYER_NAME);
		employer.setSearchValue(EMPLOYER_NAME);
		employer.setWebsite(WEBSITE);
		employer.setSettlement(createSettlement());
		employer.setModificationTime(MODIFICATION_TIME);
		employer.setModifiedBy(createUser());
		return employer;
	}

	static Employer createEmployerWithContactWithoutAddress() {
		Employer employer = createEmployerCommon();
		employer.setPhones(createPhones());
		employer.setEmails(createEmails());
		return employer;
	}

	public static Employer createEmployerWithFullContactInfo() {
		Employer employer = createEmployerWithContactWithoutAddress();
		employer.setLocationAddress(createLocation());
		return employer;
	}

	public static void assertEmployerInfoCommon(EmployerInfo employerInfo) {
		assertNotNull(employerInfo);
		assertEquals((Long)EMPLOYER_ID, employerInfo.getId());
		assertEquals(EMPLOYER_NAME, employerInfo.getName());
		assertEquals(WEBSITE, employerInfo.getWebsite());
		assertSettlementInfo(employerInfo.getSettlement());
		assertEquals(MODIFICATION_TIME, employerInfo.getModificationTime());
		assertBriefUserInfo(employerInfo.getModifiedBy());
	}

	public static void assertEmployerInfoWithContactWithoutAddress(EmployerInfo employerInfo) {
		assertEmployerInfoCommon(employerInfo);
		assertPhones(employerInfo.getPhones());
		assertEmails(employerInfo.getEmails());
	}

	public static LawyerAgency createLawyerAgencyWithAddress() {
		LawyerAgency agency = createLawyerWithoutAddress();
		agency.setLocationAddress(createLocation());
		return agency;
	}

	static LawyerAgency createLawyerWithoutAddress() {
		LawyerAgency agency = new LawyerAgency();
		agency.setId(LAWYER_AGENCY_ID);
		agency.setSettlement(createSettlement());
		agency.setDisplayName(LAWYER_AGENCY_NAME);
		agency.setSearchValue(LAWYER_AGENCY_NAME);
		agency.setWebSite(LAWYER_AGENCY_SITE);
		agency.setCases(LAW_CASES);
		agency.setPhones(createPhones());
		agency.setEmails(createEmails());
		agency.setModificationTime(MODIFICATION_TIME);
		agency.setModifiedBy(createUser());
		return agency;
	}

	static Clinic createClinicWithoutAddress() {
		return createClinicCommon();
	}

	static Clinic createClinicWithAddress() {
		Clinic clinic = createClinicCommon();
		clinic.setLocationAddress(createLocation());
		return clinic;
	}

	static Clinic createClinicCommon() {
		Clinic clinic = new Clinic();
		clinic.setId(CLINIC_ID);
		clinic.setSearchName(CLINIC_NAME);
		clinic.setDisplayName(CLINIC_NAME);
		clinic.setDescription(CLINIC_DESCRIPTION);
		clinic.setClinicType(CLINIC_TYPE);
		clinic.setServices(MEDICAL_SERVICES);
		clinic.setModificationTime(MODIFICATION_TIME);
		clinic.setModifiedBy(createUser());
		clinic.setPhones(createPhones());
		clinic.setEmails(createEmails());
		return clinic;
	}

	static User createUser() {
		final User user = createUserWithoutPermissionDetails();
		user.setRoles(USER_ROLES);
		user.setDeclaredActivities(USER_DECLARED_ACTIVITIES);
		user.setConfirmedActivities(USER_CONFIRMED_ACTIVITIES);
		return user;
	}

	static User createUserWithoutPermissionDetails() {
		final User user = new User();
		user.setId(USER_ID);
		user.setLogin(LOGIN);
		user.setEmail(EMAIL);
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		user.setPassword(PASSWORD);
		user.setInternalUser(false);
		user.setRegisteredAt(MODIFICATION_TIME);
		return user;
	}


	public static void assertBriefUserInfo(BriefUserInfo userInfo) {
		assertNotNull(userInfo);
		assertEquals((Long)USER_ID,  userInfo.getId());
		assertEquals(LOGIN, userInfo.getLogin());
		assertEquals(FIRST_NAME, userInfo.getFirstName());
		assertEquals(LAST_NAME, userInfo.getLastName());
		assertEquals(EMAIL, userInfo.getEmail());
	}

	public static void assertDetailedUserInfo(DetailedUserInfo userInfo) {
		assertBriefUserInfo(userInfo);
		assertEquals(PASSWORD, userInfo.getPassword());
		assertEquals(MODIFICATION_TIME, userInfo.getRegisteredAt());
		assertNotNull(userInfo.getRoles());
		assertTrue(userInfo.getRoles().stream().map(AbstractEntityInfo::getId).allMatch(USER_ROLES::contains));
		assertTrue(USER_ROLES.stream().allMatch(r -> userInfo.getRoles().stream().anyMatch(ur -> r == ur.getId())));
		assertNotNull(userInfo.getDeclaredActivities());
		assertTrue(userInfo.getDeclaredActivities().stream().map(AbstractEntityInfo::getId).allMatch(USER_DECLARED_ACTIVITIES::contains));
		assertTrue(USER_DECLARED_ACTIVITIES.stream().allMatch(a -> userInfo.getDeclaredActivities().stream().anyMatch(ua -> ua.getId() == a)));
		assertNotNull(userInfo.getConfirmedActivities());
		assertTrue(userInfo.getConfirmedActivities().stream().map(AbstractEntityInfo::getId).allMatch(USER_CONFIRMED_ACTIVITIES::contains));
		assertTrue(USER_CONFIRMED_ACTIVITIES.stream().allMatch(a -> userInfo.getConfirmedActivities().stream().anyMatch(ua -> ua.getId() == a)));
	}

	public static void assertDetailedUserInfoWithoutPermissionDetails(DetailedUserInfo userInfo) {
		assertBriefUserInfo(userInfo);
		assertEquals(PASSWORD, userInfo.getPassword());
		assertEquals(MODIFICATION_TIME, userInfo.getRegisteredAt());
		assertNotNull(userInfo.getRoles());
		assertTrue(userInfo.getRoles().isEmpty());
		assertNotNull(userInfo.getDeclaredActivities());
		assertTrue(userInfo.getDeclaredActivities().isEmpty());
		assertNotNull(userInfo.getConfirmedActivities());
		assertTrue(userInfo.getConfirmedActivities().isEmpty());
	}

	private static ImmutableList<EmailAddress> createEmails() {
		final EmailAddress email = new EmailAddress();
		email.setEmailAddress(EMAIL_ADDRESS);
		email.setDescription(E_MAIL_DESCRIPTION);
		return ImmutableList.of(email);
	}

	private static ImmutableList<Phone> createPhones() {
		final Phone phone = new Phone();
		phone.setSearchNumber(SEARCH_NUMBER);
		phone.setDisplayNumber(DISPLAY_NUMBER);
		phone.setDescription(PHONE_DESCRIPTION);
		return ImmutableList.of(phone);
	}

	static Location createLocation() {
		final Location location = new Location();
		location.setId(LOCATION_ID);
		location.setStreet(createStreet());
		location.setPostalCode(POSTAL_CODE);
		location.setStreetNumber(STREET_NUMBER);
		location.setSearchStreetNumber(STREET_NUMBER);
		location.setBlockNumber(BLOCK_NUMBER);
		location.setSearchBlockNumber(BLOCK_NUMBER);
		location.setRoomNumber(ROOM_NUMBER);
		location.setSearchRoomNumber(ROOM_NUMBER);
		return location;
	}

	static Street createStreet() {
		Street street = new Street();
		street.setId(STREET_ID);
		street.setSettlement(createSettlement());
		street.setKind(STREET_KIND);
		street.setDisplayName(STREET_NAME);
		street.setSearchValue(STREET_NAME);
		return street;
	}

	static Settlement createSettlement() {
		Settlement settlement = new Settlement();
		settlement.setId(SETTLEMENT_ID);
		settlement.setKind(SETTLEMENT_KIND);
		settlement.setDisplayName(SETTLEMENT_NAME);
		settlement.setDistrict(createDistrict());
		return settlement;
	}

	static District createDistrict() {
		District district = new District();
		district.setId(DISTRICT_ID);
		district.setDisplayName(DISTRICT_NAME);
		district.setSearchValue(DISTRICT_NAME);
		district.setMockDistrict(MOCK_DISTRICT);
		district.setRegion(createRegion());
		return district;
	}

	static Region createRegion() {
		final Region region = new Region();
		region.setId(REGION_ID);
		region.setDisplayName(REGION_NAME);
		region.setDisplayName(REGION_NAME);
		return region;
	}



	private TestDataUtil() {
	}

	public static void assertLawyerAgencyInfoCommon(LawyerAgencyInfo agencyInfo) {
		assertNotNull(agencyInfo);
		assertSettlementInfo(agencyInfo.getSettlement());
		assertEquals((Long) LAWYER_AGENCY_ID, agencyInfo.getId());
		assertEquals(LAWYER_AGENCY_NAME, agencyInfo.getAgencyName());
		assertEquals(LAWYER_AGENCY_SITE, agencyInfo.getWebSite());
		assertNotNull(agencyInfo.getCases());
		assertTrue(agencyInfo.getCases().stream().map(AbstractEntityInfo::getId).allMatch(LAW_CASES::contains));
		assertTrue(LAW_CASES.stream().allMatch(cs -> agencyInfo.getCases().stream().map(AbstractEntityInfo::getId).anyMatch(ecs -> ecs.equals(cs))));
		assertPhones(agencyInfo.getPhones());
		assertEmails(agencyInfo.getEmails());
		assertNotNull(agencyInfo.getModificationTime());
		assertEquals(MODIFICATION_TIME, agencyInfo.getModificationTime());
		assertBriefUserInfo(agencyInfo.getModifiedBy());
	}

	public static void assertClinicInfoCommon(ClinicInfo clinicInfo) {
		assertNotNull(clinicInfo);
		assertEquals((Long)CLINIC_ID, clinicInfo.getId());
		assertEquals(CLINIC_NAME, clinicInfo.getClinicName());
		assertNotNull(clinicInfo.getClinicType());
		assertEquals(CLINIC_TYPE, clinicInfo.getClinicType().getId());
		assertNotNull(clinicInfo.getServices());
		assertTrue(clinicInfo.getServices().stream().map(AbstractEntityInfo::getId).allMatch(MEDICAL_SERVICES::contains));
		assertTrue(MEDICAL_SERVICES.stream().allMatch(s -> clinicInfo.getServices().stream().map(AbstractEntityInfo::getId).anyMatch(es -> es.equals(s))));
		assertPhones(clinicInfo.getPhones());
		assertEmails(clinicInfo.getEmails());
		assertNotNull(clinicInfo.getModificationTime());
		assertEquals(MODIFICATION_TIME, clinicInfo.getModificationTime());
		assertBriefUserInfo(clinicInfo.getModifiedBy());
	}

	static void assertPhones(List<PhoneInfo> phones) {
		assertNotNull(phones);
		final Optional<PhoneInfo> phoneOpt = phones.stream()
				.filter(p -> DISPLAY_NUMBER.equalsIgnoreCase(p.getPhoneNumber()))
				.findFirst();
		assertTrue(phoneOpt.isPresent());
		PhoneInfo phone = phoneOpt.get();
		assertEquals(DISPLAY_NUMBER, phone.getPhoneNumber());
		assertEquals(PHONE_DESCRIPTION, phone.getDescription());
	}

	static void assertEmails(List<EmailInfo> emails) {
		assertNotNull(emails);
		Optional<EmailInfo> emailOpt = emails.stream()
				.filter(e -> EMAIL_ADDRESS.equalsIgnoreCase(e.getEmailAddress()))
				.findFirst();
		assertTrue(emailOpt.isPresent());
		EmailInfo emailInfo = emailOpt.get();
		assertEquals(EMAIL_ADDRESS, emailInfo.getEmailAddress());
		assertEquals(E_MAIL_DESCRIPTION, emailInfo.getDescription());
	}

	public static void assertLocationInfo(LocationInfo locationInfo) {
		assertNotNull(locationInfo);
		assertEquals((Long)LOCATION_ID, locationInfo.getId());
		assertEquals(POSTAL_CODE, locationInfo.getPostalCode());
		assertEquals(STREET_NUMBER, locationInfo.getStreetNumber());
		assertEquals(BLOCK_NUMBER, locationInfo.getBlockNumber());
		assertEquals(ROOM_NUMBER, locationInfo.getRoomNumber());
		StreetInfo streetInfo = locationInfo.getStreet();
		assertStreetInfo(streetInfo);
	}

	static void assertStreetInfo(StreetInfo streetInfo) {
		assertNotNull(streetInfo);
		assertEquals((Long) STREET_ID, streetInfo.getId());
		assertEquals(STREET_NAME, streetInfo.getName());
		assertNotNull(streetInfo.getKind());
		assertEquals(STREET_KIND, streetInfo.getKind().getId());
		assertSettlementInfo(streetInfo.getSettlement());
	}

	static void assertSettlementInfo(SettlementInfo settlementInfo) {
		assertNotNull(settlementInfo);
		assertEquals((Long)SETTLEMENT_ID, settlementInfo.getId());
		assertNotNull(settlementInfo.getKind());
		assertEquals(SETTLEMENT_KIND, settlementInfo.getKind().getId());
		assertEquals(SETTLEMENT_NAME, settlementInfo.getName());
		assertDistrictInfo(settlementInfo.getDistrict());
	}

	static void assertDistrictInfo(DistrictInfo districtInfo) {
		assertNotNull(districtInfo);
		assertEquals((Long)DISTRICT_ID, districtInfo.getId());
		assertEquals(DISTRICT_NAME, districtInfo.getName());
		assertNotNull(districtInfo.getMock());
		assertEquals(MOCK_DISTRICT, districtInfo.getMock().getId());
		assertRegionInfo(districtInfo.getRegion());
	}

	static void assertRegionInfo(RegionInfo regionInfo) {
		assertNotNull(regionInfo);
		assertEquals(REGION_ID, regionInfo.getId());
		assertEquals(REGION_NAME, regionInfo.getName());
	}
}
