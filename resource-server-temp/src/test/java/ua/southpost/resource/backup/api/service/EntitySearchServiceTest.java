package ua.southpost.resource.backup.api.service;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.api.model.UserSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.ClinicSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.DwellingSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.EmailSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.EmployerLookupRequest;
import ua.southpost.resource.backup.api.model.search.EmployerSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.LawyerAgencySearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.LocationSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.PhoneSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.StreetSearchRequestPayload;
import ua.southpost.resource.backup.api.model.search.UserSearchRequestPayloadImpl;
import ua.southpost.resource.backup.api.model.search.VacancySearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.data.repo.ClinicRepository;
import ua.southpost.resource.backup.data.repo.DwellingRepository;
import ua.southpost.resource.backup.data.repo.EmailAddressRepository;
import ua.southpost.resource.backup.data.repo.EmployerRepository;
import ua.southpost.resource.backup.data.repo.LawyerAgencyRepository;
import ua.southpost.resource.backup.data.repo.LocationRepository;
import ua.southpost.resource.backup.data.repo.PhoneRepository;
import ua.southpost.resource.backup.data.repo.SettlementRepository;
import ua.southpost.resource.backup.data.repo.StreetRepository;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.data.repo.VacancyRepository;
import ua.southpost.resource.commons.model.dto.SortOption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ActiveProfiles({"test"})
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
@Transactional
public class EntitySearchServiceTest {

	@Autowired
	@Qualifier("settlementSearchService")
	private EntitySearchService<SettlementSearchRequestPayload, Settlement, Long> settlementSearchService;

	@Autowired
	@Qualifier("settlementSort")
	private List<SortOption> settlementSort;

	@Autowired
	@Qualifier("streetSearchService")
	private EntitySearchService<StreetSearchRequestPayload, Street, Long> streetSearchService;

	@Autowired
	@Qualifier("streetSort")
	private List<SortOption> streetSort;

	@Autowired
	@Qualifier("locationSearchService")
	private EntitySearchService<LocationSearchRequestPayload, Location, Long> locationSearchService;
	@Autowired
	@Qualifier("locationSort")
	private List<SortOption> locationSort;

	@Autowired
	@Qualifier("phoneSearchService")
	private EntitySearchService<PhoneSearchRequestPayload, Phone, Long> phoneSearchService;
	@Autowired
	@Qualifier("phoneSort")
	private List<SortOption> phoneSort;

	@Autowired
	@Qualifier("emailSearchService")
	private EntitySearchService<EmailSearchRequestPayload, EmailAddress, Long> emailSearchService;
	@Autowired
	@Qualifier("emailSort")
	private List<SortOption> emailSort;
	@Autowired
	@Qualifier("employerSearchService")
	private EntitySearchService<EmployerSearchRequestPayload, Employer, Long> employerSearchService;
	@Autowired
	@Qualifier("employerSort")
	private List<SortOption> employerSort;
	@Autowired
	@Qualifier("employerLookupSearchService")
	private EntitySearchService<EmployerLookupRequest, Employer, Long> employerLookupService;

	@Autowired
	@Qualifier("userSearchService")
	private EntitySearchService<UserSearchRequestPayload, User, Long> userSearchService;
	@Autowired
	@Qualifier("userSort")
	private List<SortOption> userSort;

	@Autowired
	@Qualifier("vacancySearchService")
	private EntitySearchService<VacancySearchRequestPayload, Vacancy, Long> vacancySearchService;
	@Autowired
	@Qualifier("vacancySort")
	private List<SortOption> vacancySort;

	@Autowired
	@Qualifier("dwellingSearchService")
	private EntitySearchService<DwellingSearchRequestPayload, Dwelling, Long> dwellingSearchService;
	@Autowired
	@Qualifier("dwellingSort")
	private List<SortOption> dwellingSort;


	@Autowired
	@Qualifier("clinicSearchService")
	private EntitySearchService<ClinicSearchRequestPayload, Clinic, Long> clinicSearchService;
	@Autowired
	@Qualifier("clinicSort")
	private List<SortOption> clinicSort;

	@Autowired
	@Qualifier("lawyerAgencySearchService")
	private EntitySearchService<LawyerAgencySearchRequestPayload, LawyerAgency, Long> lawyerSearchService;
	@Autowired
	@Qualifier("lawyerAgencySort")
	private List<SortOption> lawyerSort;

	@Test
	public void testSettlementCount() {
		genericCountTest(settlementSearchService, new SettlementSearchRequestPayload(), 669L);
	}

	@Test
	public void testSettlementList() {
		final SettlementSearchRequestPayload payload = new SettlementSearchRequestPayload();
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		payload.setSortOptions(settlementSort);
		genericListTest(settlementSearchService, payload, SettlementRepository.DEFAULT_SORT);
	}

	@Test
	public void testStreetCount() {
		genericCountTest(streetSearchService, new StreetSearchRequestPayload(), 64L);
	}

	@Test
	public void testStreetList() {
		final StreetSearchRequestPayload payload = new StreetSearchRequestPayload();
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		payload.setSortOptions(streetSort);
		genericListTest(streetSearchService, payload, StreetRepository.DEFAULT_SORT);
	}

	@Test
	public void testLocationCount() {
		genericCountTest(locationSearchService, new LocationSearchRequestPayload(), 142L);
	}

	@Test
	public void testLocationList() {
		final LocationSearchRequestPayload payload = new LocationSearchRequestPayload();
		payload.setSortOptions(locationSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(locationSearchService, payload, LocationRepository.DEFAULT_SORT);
	}

	@Test
	public void tetPhonesCount() {
		genericCountTest(phoneSearchService, new PhoneSearchRequestPayload(), 67L);
	}

	@Test
	public void testPhonesList() {
		final PhoneSearchRequestPayload payload = new PhoneSearchRequestPayload();
		payload.setSortOptions(phoneSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(phoneSearchService, payload, PhoneRepository.DEFAULT_SORT);
	}

	@Test
	public void testEmailCount() {
		genericCountTest(emailSearchService, new EmailSearchRequestPayload(), 62L);
	}

	@Test
	public void testEmailList() {
		final EmailSearchRequestPayload payload = new EmailSearchRequestPayload();
		payload.setSortOptions(emailSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(emailSearchService, payload, EmailAddressRepository.DEFAULT_SORT);
	}

	@Test
	public void testEmployerCount() {
		genericCountTest(employerSearchService, new EmployerSearchRequestPayload(), 12L);
	}

	@Test
	public void testEmployerList() {
		final EmployerSearchRequestPayload payload = new EmployerSearchRequestPayload();
		payload.setSortOptions(employerSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(employerSearchService, payload, EmployerRepository.DEFAULT_SORT);
	}

	@Test
	public void testEmployerLookupCount() {
		genericCountTest(employerLookupService, new EmployerLookupRequest(), 12L);
	}

	@Test
	public void testEmployerLookupList() {
		final EmployerLookupRequest payload = new EmployerLookupRequest();
		payload.setSortOptions(employerSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(employerLookupService, payload, EmployerRepository.DEFAULT_SORT);
	}



	@Test
	public void testUserCount() {
		genericCountTest(userSearchService, new UserSearchRequestPayloadImpl(), 17L);
	}

	@Test
	public void testUserList() {
		final UserSearchRequestPayloadImpl payload = new UserSearchRequestPayloadImpl();
		payload.setSortOptions(userSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(userSearchService, payload, UserRepository.DEFAULT_SORT);
	}

	@Test
	public void testVacancyCount() {
		genericCountTest(vacancySearchService, new VacancySearchRequestPayload(), 36L);
	}

	@Test
	public void testVacancyList() {
		final VacancySearchRequestPayload payload = new VacancySearchRequestPayload();
		payload.setSortOptions(vacancySort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(vacancySearchService, payload, VacancyRepository.DEFAULT_SORT);
	}

	@Test
	public void testDwellingCount() {
		genericCountTest(dwellingSearchService, new DwellingSearchRequestPayload(), 37L);
	}

	@Test
	public void testDwellingList() {
		final DwellingSearchRequestPayload payload = new DwellingSearchRequestPayload();
		payload.setSortOptions(dwellingSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(dwellingSearchService, payload, DwellingRepository.DEFAULT_SORT);
	}

	@Test
	public void testClinicCount() {
		genericCountTest(clinicSearchService, new ClinicSearchRequestPayload(), 13L);
	}

	@Test
	public void testClinicList() {
		final ClinicSearchRequestPayload payload = new ClinicSearchRequestPayload();
		payload.setSortOptions(clinicSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(10);
		genericListTest(clinicSearchService, payload, ClinicRepository.DEFAULT_SORT);
	}

	@Test
	public void testLayerAgencyCount() {
		genericCountTest(lawyerSearchService, new LawyerAgencySearchRequestPayload(), 15L);
	}

	@Test
	public void testLawyerAgencyList() {
		final LawyerAgencySearchRequestPayload payload = new LawyerAgencySearchRequestPayload();
		payload.setSortOptions(lawyerSort);
		payload.setPageNum(1);
		payload.setLinesPerPage(5);
		genericListTest(lawyerSearchService, payload, LawyerAgencyRepository.DEFAULT_SORT);
	}

	private <P extends PagedSearchRequestPayload, E extends Identity<I>, I> void genericCountTest(@Nonnull EntitySearchService<P, E, I> searchService, P payload, final long expectedCount) {
		final long count = searchService.count(payload);
		assertEquals(expectedCount, count);
	}

	private <P extends PagedSearchRequestPayload, E extends Identity<I>, I> void genericListTest(@Nonnull EntitySearchService<P, E, I> searchService, @Nonnull P payload, @Nonnull Sort defaultSort) {
		final Sort sort = ofNullable(payload.getSortOptions()).flatMap(
				opts -> opts.stream().map(so -> Sort.by(so.getDirection(), so.getFieldName())).reduce(Sort::and)
		)
				.orElse(defaultSort);
		final Pageable pageable = PageRequest.of(payload.getPageNum() - 1, payload.getLinesPerPage(), sort);

		final List<E> list = searchService.list(payload, pageable);
		assertNotNull(list);
		assertTrue(list.size() <= payload.getLinesPerPage());
	}
}