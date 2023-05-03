package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ActiveProfiles({"integration-test", "test"})
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class EntityInfoMapperTest {

	@Autowired
	private LawyerAgencyInfoMapper lawyerAgencyInfoMapper;
	@Autowired
	private ClinicInfoMapper clinicInfoMapper;
	@Autowired
	private EmployerInfoMapper employerInfoMapper;
	@Autowired
	private DwellingInfoMapper dwellingInfoMapper;
	@Autowired
	private VacancyInfoMapper vacancyInfoMapper;
	@Autowired
	private DetailedUserInfoMapper detailedUserInfoMapper;


	@Test
	public void testMapLawyerAgencyWithoutAddress() {
		LawyerAgency agency = TestDataUtil.createLawyerWithoutAddress();

		LawyerAgencyInfo info = lawyerAgencyInfoMapper.map(agency, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertLawyerAgencyInfoCommon(info);

		assertNull(info.getLocation());
	}

	@Test
	public void testMapLawyerAgencyWithAddress() {
		LawyerAgency agency = TestDataUtil.createLawyerAgencyWithAddress();

		LawyerAgencyInfo info = lawyerAgencyInfoMapper.map(agency, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertLawyerAgencyInfoCommon(info);

		assertNotNull(info.getLocation());

		TestDataUtil.assertLocationInfo(info.getLocation());
	}

	@Test
	public void testMapClinicWithoutAddress() {
		Clinic clinicWithoutAddress = TestDataUtil.createClinicWithoutAddress();

		ClinicInfo clinicInfo = clinicInfoMapper.map(clinicWithoutAddress, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertClinicInfoCommon(clinicInfo);

		assertNull(clinicInfo.getLocation());
	}

	@Test
	public void testMapClinicWithAddress() {
		Clinic clinic = TestDataUtil.createClinicWithAddress();

		ClinicInfo clinicInfo = clinicInfoMapper.map(clinic, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertClinicInfoCommon(clinicInfo);

		TestDataUtil.assertLocationInfo(clinicInfo.getLocation());
	}

	@Test
	public void testMapEmployerWithoutContacts() {
		Employer employer = TestDataUtil.createEmployerCommon();

		EmployerInfo employerInfo = employerInfoMapper.map(employer, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertEmployerInfoCommon(employerInfo);

		assertNull(employerInfo.getLocation());
	}

	@Test
	public void testMapEmployerWithContactWithoutAddress() {
		Employer employer = TestDataUtil.createEmployerWithContactWithoutAddress();

		EmployerInfo employerInfo = employerInfoMapper.map(employer, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertEmployerInfoWithContactWithoutAddress(employerInfo);
	}

	@Test
	public void testMapDwelling() {
		Dwelling dwelling = TestDataUtil.createDwelling();

		DwellingInfo dwellingInfo = dwellingInfoMapper.map(dwelling, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertDwellingInfo(dwellingInfo);
	}

	@Test
	public void testMapVacancy() {
		Vacancy vacancy = TestDataUtil.createVacancy();

		VacancyInfo vacancyInfo = vacancyInfoMapper.map(vacancy, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertVacancyInfo(vacancyInfo);
	}

	@Test
	public void testMapDetailedUserInfo() {
		User user = TestDataUtil.createUser();

		DetailedUserInfo userInfo = detailedUserInfoMapper.map(user, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertDetailedUserInfo(userInfo);
	}

	@Test
	public void testMapDetailedUserInfoForUserWithoutPermissionDetails() {
		User user = TestDataUtil.createUserWithoutPermissionDetails();

		DetailedUserInfo userInfo = detailedUserInfoMapper.map(user, TestDataUtil.NATIVE_LOCALE);

		TestDataUtil.assertDetailedUserInfoWithoutPermissionDetails(userInfo);
	}
}