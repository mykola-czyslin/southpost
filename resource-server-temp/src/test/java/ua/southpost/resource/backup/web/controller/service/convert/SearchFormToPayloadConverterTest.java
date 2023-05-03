package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.UserSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.ClinicSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.DwellingSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.EmailSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.EmployerSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.LocationSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.PhoneSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.StreetSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.VacancySearchRequestPayload;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.DwellingKind;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.web.model.forms.search.ClinicSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.DwellingSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.EmailSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.EmployerSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.LocationSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.PhoneSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.StreetSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.UserSearchFormImpl;
import ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class SearchFormToPayloadConverterTest {

	private static final String REGION_ID = "REGION ID";
	private static final long DISTRICT_ID = -100L;
	private static final SettlementKind SETTLEMENT_KIND = SettlementKind.city;
	private static final String SETTLEMENT_NAME_PATTERN = "Settlement*Name*";
	private static final StreetKind STREET_KIND = StreetKind.street;
	private static final String STREET_NAME_PATTERN = "Street*Name?Pattern*";
	private static final String POSTAL_CODE_PATTERN = "0000*";
	private static final String STREET_NUMBER_PATTERN = "2*7?";
	private static final String BLOCK_NUMBER_PATTERN = "B?";
	private static final String ROOM_NUMBER_PATTERN = "20?";
	private static final String PHONE_NUMBER_PATTERN = "+380*00*02";
	private static final String DESCRIPTION_PATTERN = "Phone*Descr*Patt?rn";
	private static final String EMAIL_ADDRESS_PATTERN = "john?lennon@*beatles*";
	private static final String EMPLOYER_NAME_PATTERN = "Emplo*Name*Pat*rn";
	private static final String WEB_SITE_ADDRESS_PATTERN = "http?://com*pany.name.co?/path/*/pattern";
	private static final ClinicType CLINIC_TYPE = ClinicType.MIXED;
	private static final String CLINIC_NAME_PATTERN = "Clinic*Name*Pattern";
	private static final MedicalService MEDICAL_SERVICE = MedicalService.GENERAL_THERAPY;
	private static final String SETTLEMENT_AREA_PATTERN = "Settlement*Area?Pattern";
	private static final BillingPeriod BILLING_PERIOD = BillingPeriod.MONTH;
	private static final DwellingKind DWELLING_KIND = DwellingKind.FLAT;
	private static final int NUMBER_OF_ROOMS_FROM = 0;
	private static final int NUMBER_OF_ROOMS_TO = 10;
	private static final BigDecimal TOTAL_AREA_FROM = BigDecimal.valueOf(4055L, 2);
	private static final BigDecimal TOTAL_AREA_TO = BigDecimal.valueOf(10000L, 2);
	private static final BigDecimal LIVING_AREA_FROM = BigDecimal.valueOf(2200, 2);
	private static final BigDecimal LIVING_AREA_TO = BigDecimal.valueOf(7500, 2);
	private static final BigDecimal PRICE_FROM = BigDecimal.valueOf(80000, 2);
	private static final BigDecimal PRICE_TO = BigDecimal.valueOf(320000, 2);
	private static final BigDecimal SALARY_LOW = BigDecimal.valueOf(1200000, 2);
	private static final String LOGIN_PATTERN = "Login*Pattern";
	private static final String FIRST_NAME_PATTERN = "First*Name*Pattern";
	private static final String LAST_NAME_PATTERN = "Last*Name*Pattern";
	private static final Date REGISTERED_AT_FROM = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));
	private static final Date REGISTERED_AT_TO = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10L));
	private static final EnumSet<UserRole> ROLES = EnumSet.of(UserRole.VIEWER, UserRole.OPERATOR, UserRole.ADMINISTRATOR);
	private static final EnumSet<UserActivityKind> DECLARED_ACTIVITIES = EnumSet.of(UserActivityKind.EMPLOYER, UserActivityKind.PROPERTY_AGENT, UserActivityKind.CLINIC_ADVISOR);
	private static final EnumSet<UserActivityKind> CONFIRMED_ACTIVITIES = EnumSet.of(UserActivityKind.EMPLOYER, UserActivityKind.CLINIC_ADVISOR);
	private static final String SUMMARY_PATTERN = "Summary?Pattern";

	@Test
	public void testSettlementConverter() {
		//given
		final SettlementSearchFormToPayloadConverter converter = new SettlementSearchFormToPayloadConverter();
		final SettlementSearchForm form = settlementSearchForm();
		//when
		final SettlementSearchRequestPayload payload = converter.convert(form);
		//then
		assertSettlementSearchPayload(payload);
		System.out.println(payload);
	}

	@Test
	public void testStreetConverter() {
		//given
		final StreetSearchFormToPayloadConverter converter = new StreetSearchFormToPayloadConverter();
		final StreetSearchForm form = streetSearchForm();
		//when
		final StreetSearchRequestPayload payload = converter.convert(form);
		//then
		assertStreetSearchPayload(payload);
		System.out.println(payload);
	}

	@Test
	public void testLocationSearchConverter() {
		//given
		final LocationSearchFormToPayloadConverter converter = new LocationSearchFormToPayloadConverter();
		final LocationSearchForm form = locationSearchForm();
		//when
		final LocationSearchRequestPayload payload = converter.convert(form);
		//then
		assertLocationSearchPayload(payload);
		System.out.println(payload);
	}

	@Test
	public void testPhoneSearchConverter() {
		//given
		final PhoneSearchFormToPayloadConverter converter = new PhoneSearchFormToPayloadConverter();
		final PhoneSearchForm form = new PhoneSearchForm();
		form.setPhoneNumberPattern(PHONE_NUMBER_PATTERN);
		form.setDescriptionPattern(DESCRIPTION_PATTERN);
		System.out.println(form);
		//when
		PhoneSearchRequestPayload payload = converter.convert(form);
		//then
		assertEquals(PHONE_NUMBER_PATTERN, payload.getPhoneNumberPattern());
		assertEquals(DESCRIPTION_PATTERN, payload.getDescriptionPattern());
		System.out.println(payload);
	}

	@Test
	public void testEmailSearchFormConverter() {
		//given
		final EmailSearchFormToPayloadConverter converter = new EmailSearchFormToPayloadConverter();
		final EmailSearchForm form = new EmailSearchForm();
		form.setEmailAddressPattern(EMAIL_ADDRESS_PATTERN);
		form.setDescriptionPattern(DESCRIPTION_PATTERN);
		System.out.println(form);
		//when
		EmailSearchRequestPayload payload = converter.convert(form);
		//then
		assertEquals(EMAIL_ADDRESS_PATTERN, payload.getEmailAddressPattern());
		assertEquals(DESCRIPTION_PATTERN, payload.getDescriptionPattern());
		System.out.println(payload);
	}

	@Test
	public void testEmployerSearchFormConverter() {
		//given
		final EmployerSearchFormToPayloadConverter converter = new EmployerSearchFormToPayloadConverter();
		final EmployerSearchForm form = employerSearchForm();
		//when
		EmployerSearchRequestPayload payload = converter.convert(form);
		//then
		assertSettlementSearchPayload(payload);
		assertEquals(EMPLOYER_NAME_PATTERN, payload.getEmployerNamePattern());
		assertEquals(WEB_SITE_ADDRESS_PATTERN, payload.getWebSiteAddressPattern());
		System.out.println(payload);
	}

	@Test
	public void testClinicSearchFormConverter() {
		//given
		final ClinicSearchFormToPayloadConverter converter = new ClinicSearchFormToPayloadConverter();
		final ClinicSearchForm form = new ClinicSearchForm(1, 10, Collections.emptyList());
		populateLocationSearchForm(form);
		form.setClinicType(CLINIC_TYPE);
		form.setNamePattern(CLINIC_NAME_PATTERN);
		form.setDescriptionPattern(DESCRIPTION_PATTERN);
		form.setEmailPattern(EMAIL_ADDRESS_PATTERN);
		form.setPhonePattern(PHONE_NUMBER_PATTERN);
		form.setService(MEDICAL_SERVICE);
		System.out.println(form);
		//when
		ClinicSearchRequestPayload payload = converter.convert(form);
		//then
		assertLocationSearchPayload(payload);
		Assert.assertEquals(CLINIC_TYPE, payload.getClinicType());
		assertEquals(CLINIC_NAME_PATTERN, payload.getNamePattern());
		assertEquals(DESCRIPTION_PATTERN, payload.getDescriptionPattern());
		assertEquals(EMAIL_ADDRESS_PATTERN, payload.getEmailPattern());
		assertEquals(PHONE_NUMBER_PATTERN, payload.getPhonePattern());
		Assert.assertEquals(MEDICAL_SERVICE, payload.getService());
		System.out.println(payload);
	}

	@Test
	public void testDwellingSearchFormConverter() {
		//given
		final DwellingSearchFormToPayloadConverter converter = new DwellingSearchFormToPayloadConverter();
		final DwellingSearchForm form = new DwellingSearchForm();
		populateSettlementSearch(form);
		form.setSettlementAreaPattern(SETTLEMENT_AREA_PATTERN);
		form.setDwellingKind(DWELLING_KIND);
		form.setContactEmailPattern(EMAIL_ADDRESS_PATTERN);
		form.setContactPhonePattern(PHONE_NUMBER_PATTERN);
		form.setNumberOfRoomsFrom(NUMBER_OF_ROOMS_FROM);
		form.setNumberOfRoomsTo(NUMBER_OF_ROOMS_TO);
		form.setTotalAreaFrom(TOTAL_AREA_FROM);
		form.setTotalAreaTo(TOTAL_AREA_TO);
		form.setLivingAreaFrom(LIVING_AREA_FROM);
		form.setLivingAreaTo(LIVING_AREA_TO);
		form.setPriceFrom(PRICE_FROM);
		form.setPriceTo(PRICE_TO);
		form.setBillingPeriod(BILLING_PERIOD);
		System.out.println(form);
		//
		DwellingSearchRequestPayload payload = converter.convert(form);
		//then
		assertSettlementSearchPayload(payload);
		assertEquals(SETTLEMENT_AREA_PATTERN, payload.getSettlementAreaPattern());
		Assert.assertEquals(DWELLING_KIND, payload.getDwellingKind());
		assertEquals((Integer) NUMBER_OF_ROOMS_FROM, payload.getNumberOfRoomsFrom());
		assertEquals((Integer) NUMBER_OF_ROOMS_TO, payload.getNumberOfRoomsTo());
		assertEquals(TOTAL_AREA_FROM, payload.getTotalAreaFrom());
		assertEquals(TOTAL_AREA_TO, payload.getTotalAreaTo());
		assertEquals(LIVING_AREA_FROM, payload.getLivingAreaFrom());
		assertEquals(LIVING_AREA_TO, payload.getLivingAreaTo());
		assertEquals(PRICE_FROM, payload.getPriceFrom());
		assertEquals(PRICE_TO, payload.getPriceTo());
		Assert.assertEquals(BILLING_PERIOD, payload.getBillingPeriod());
		System.out.println(payload);
	}

	@Test
	public void testVacancySearchFormConverter() {
		//given
		final VacancySearchFormToPayloadConverter converter = new VacancySearchFormToPayloadConverter();
		final VacancySearchForm form = new VacancySearchForm();
		populateSettlementSearch(form);
		form.setSalaryLow(SALARY_LOW);
		form.setDescriptionPattern(DESCRIPTION_PATTERN);
		form.setEmployerNamePattern(EMPLOYER_NAME_PATTERN);
		form.setMailPattern(EMAIL_ADDRESS_PATTERN);
		form.setPhonePattern(PHONE_NUMBER_PATTERN);
		form.setSummaryPattern(SUMMARY_PATTERN);
		System.out.println(form);
		//when
		final VacancySearchRequestPayload payload = converter.convert(form);
		//then
		assertSettlementSearchPayload(payload);
		assertEquals(SUMMARY_PATTERN, payload.getSummaryPattern());
		assertEquals(DESCRIPTION_PATTERN, payload.getDescriptionPattern());
		assertEquals(EMPLOYER_NAME_PATTERN, payload.getEmployerNamePattern());
		assertEquals(EMAIL_ADDRESS_PATTERN, payload.getMailPattern());
		assertEquals(PHONE_NUMBER_PATTERN, payload.getPhonePattern());
		assertEquals(SALARY_LOW, payload.getSalaryLow());
		System.out.println(payload);

	}

	@Test
	public void testUserSearchFormToPayloadConverter() {
		//given
		final UserSearchFormToPayloadConverter converter = new UserSearchFormToPayloadConverter();
		final UserSearchFormImpl searchForm = new UserSearchFormImpl(1, 10, Collections.emptyList());
		searchForm.setLoginPattern(LOGIN_PATTERN);
		searchForm.setEmailPattern(EMAIL_ADDRESS_PATTERN);
		searchForm.setFirstNamePattern(FIRST_NAME_PATTERN);
		searchForm.setLastNamePattern(LAST_NAME_PATTERN);
		searchForm.setRegisteredAtFrom(REGISTERED_AT_FROM);
		searchForm.setRegisteredAtTo(REGISTERED_AT_TO);
		searchForm.setRoles(ROLES);
		searchForm.setDeclaredActivities(DECLARED_ACTIVITIES);
		searchForm.setConfirmedActivities(CONFIRMED_ACTIVITIES);
		System.out.println(searchForm);
		//when
		final UserSearchRequestPayload payload = converter.convert(searchForm);
		//then
		assertEquals(LOGIN_PATTERN, payload.getLoginPattern());
		assertEquals(EMAIL_ADDRESS_PATTERN, payload.getEmailPattern());
		assertEquals(FIRST_NAME_PATTERN, payload.getFirstNamePattern());
		assertEquals(LAST_NAME_PATTERN, payload.getLastNamePattern());
		assertEquals(REGISTERED_AT_FROM, payload.getRegisteredAtFrom());
		assertEquals(REGISTERED_AT_TO, payload.getRegisteredAtTo());
		assertEquals(ROLES, payload.userRoles());
		assertFalse(payload.anyRoleAcceptable());
		assertEquals(DECLARED_ACTIVITIES, payload.userDeclaredActivities());
		assertFalse(payload.anyDeclaredActivityAcceptable());
		assertEquals(CONFIRMED_ACTIVITIES, payload.userConfirmedActivities());
		assertFalse(payload.anyConfirmedActivityAcceptable());
		System.out.println(payload);
	}

	private static EmployerSearchForm employerSearchForm() {
		EmployerSearchForm searchForm = new EmployerSearchForm();
		populateSettlementSearch(searchForm);
		searchForm.setEmployerNamePattern(EMPLOYER_NAME_PATTERN);
		searchForm.setWebSiteAddressPattern(WEB_SITE_ADDRESS_PATTERN);
		searchForm.setWebSiteSignificant(true);
		System.out.println(searchForm);
		return searchForm;
	}

	private void assertLocationSearchPayload(LocationSearchRequestPayload result) {
		assertStreetSearchPayload(result);
		assertEquals(POSTAL_CODE_PATTERN, result.getPostalCodePattern());
		assertEquals(STREET_NUMBER_PATTERN, result.getStreetNumberPattern());
		assertEquals(BLOCK_NUMBER_PATTERN, result.getBlockNumberPattern());
		assertEquals(ROOM_NUMBER_PATTERN, result.getRoomNumberPattern());
	}

	private static LocationSearchForm locationSearchForm() {
		LocationSearchForm searchForm = new LocationSearchForm();
		populateLocationSearchForm(searchForm);
		System.out.println(searchForm);
		return searchForm;
	}

	private static void populateLocationSearchForm(LocationSearchForm searchForm) {
		populateStreetSearch(searchForm);
		searchForm.setPostalCodePattern(POSTAL_CODE_PATTERN);
		searchForm.setStreetNumberPattern(STREET_NUMBER_PATTERN);
		searchForm.setBlockNumberPattern(BLOCK_NUMBER_PATTERN);
		searchForm.setRoomNumberPattern(ROOM_NUMBER_PATTERN);
	}

	private void assertStreetSearchPayload(StreetSearchRequestPayload result) {
		assertSettlementSearchPayload(result);
		Assert.assertEquals(STREET_KIND, result.getStreetKind());
		assertEquals(STREET_NAME_PATTERN, result.getStreetNamePattern());
	}

	private void assertSettlementSearchPayload(SettlementSearchRequestPayload result) {
		assertNotNull(result);
		assertEquals(REGION_ID, result.getRegionId());
		assertEquals((Long) DISTRICT_ID, result.getDistrictId());
		Assert.assertEquals(SETTLEMENT_KIND, result.getSettlementKind());
		assertEquals(SETTLEMENT_NAME_PATTERN, result.getSettlementNamePattern());
	}

	private static StreetSearchForm streetSearchForm() {
		StreetSearchForm searchForm = new StreetSearchForm();
		populateStreetSearch(searchForm);
		System.out.println(searchForm);
		return searchForm;
	}

	private static void populateStreetSearch(StreetSearchForm searchForm) {
		populateSettlementSearch(searchForm);
		searchForm.setStreetKind(STREET_KIND);
		searchForm.setStreetNamePattern(STREET_NAME_PATTERN);
	}

	private static SettlementSearchForm settlementSearchForm() {
		SettlementSearchForm searchForm = new SettlementSearchForm();
		populateSettlementSearch(searchForm);
		System.out.println(searchForm);
		return searchForm;
	}

	private static void populateSettlementSearch(SettlementSearchForm searchForm) {
		searchForm.setRegionId(REGION_ID);
		searchForm.setDistrictId(DISTRICT_ID);
		searchForm.setSettlementKind(SETTLEMENT_KIND);
		searchForm.setSettlementNamePattern(SETTLEMENT_NAME_PATTERN);
	}
}