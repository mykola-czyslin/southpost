package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.backup.api.model.submit.VacancyPayload;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.data.repo.VacancyRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VacancyPayloadPersistenceManagerTest {
	@InjectMocks
	private VacancyPayloadPersistenceManager testObj = new VacancyPayloadPersistenceManager();
	@Mock
	private VacancyRepository repository;
	@Mock
	private EmployerPayloadPersistenceManager employerPersistenceManager;

	@Mock
	private User modifiedByMock;
	private User oldModifiedBy;
	
	
	private VacancyPayload payload;
	private Vacancy persistent;

	@Before
	public void setUp() {
		payload = new VacancyPayload();
		payload.setId(TestDataUtil.VACANCY_ID);
		payload.setEmployer(EntityPayloadPersistenceManagerTestUtil.createEmployerPayload());
		payload.setSummary(TestDataUtil.VACANCY_SUMMARY);
		payload.setDescription(TestDataUtil.VACANCY_DESCRIPTION);
		payload.setHosting(TestDataUtil.HOSTING_AVAILABLE.isValue());
		payload.setSalaryLow(TestDataUtil.SALARY_LOW);
		payload.setSalaryHigh(TestDataUtil.SALARY_HIGH);
		persistent = TestDataUtil.createVacancy();
		oldModifiedBy = persistent.getModifiedBy();
		when(repository.save(any(Vacancy.class))).then((Answer<Vacancy>) this::invokeSave);
		Employer employer = TestDataUtil.createEmployerWithFullContactInfo();
		when(employerPersistenceManager.persist(any(EmployerPayload.class), anyBoolean(), any(User.class)))
				.thenReturn(employer);
		when(employerPersistenceManager.merge(any(Employer.class), any(Employer.class)))
				.then((Answer<Employer>) invocation -> invocation.getArgument(1, Employer.class));
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(TestDataUtil.VACANCY_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoVacancyByUniqueKeyFound() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findUnique(TestDataUtil.EMPLOYER_ID, upperCase(TestDataUtil.VACANCY_SUMMARY))).thenReturn(Optional.empty());
		//when
		final Vacancy saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findUnique(TestDataUtil.EMPLOYER_ID, upperCase(TestDataUtil.VACANCY_SUMMARY));
		assertEquals((Long) TestDataUtil.VACANCY_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.VACANCY_SUMMARY, saved.getSummary());
		assertEquals(upperCase(TestDataUtil.VACANCY_SUMMARY), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.VACANCY_DESCRIPTION, saved.getDescription());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeVacancyByUniqueKeyFoundAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findUnique(TestDataUtil.EMPLOYER_ID, upperCase(TestDataUtil.VACANCY_SUMMARY))).thenReturn(Optional.of(persistent));
		//when
		final Vacancy saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findUnique(TestDataUtil.EMPLOYER_ID, upperCase(TestDataUtil.VACANCY_SUMMARY));
		assertEquals((Long) TestDataUtil.VACANCY_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.VACANCY_SUMMARY, saved.getSummary());
		assertEquals(upperCase(TestDataUtil.VACANCY_SUMMARY), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.VACANCY_DESCRIPTION, saved.getDescription());
		assertEquals(oldModifiedBy, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeVacancyByUniqueKeyFoundAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findUnique(TestDataUtil.EMPLOYER_ID, upperCase(TestDataUtil.VACANCY_SUMMARY))).thenReturn(Optional.of(persistent));
		//when
		final Vacancy saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findUnique(TestDataUtil.EMPLOYER_ID, upperCase(TestDataUtil.VACANCY_SUMMARY));
		assertEquals((Long) TestDataUtil.VACANCY_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.VACANCY_SUMMARY, saved.getSummary());
		assertEquals(upperCase(TestDataUtil.VACANCY_SUMMARY), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.VACANCY_DESCRIPTION, saved.getDescription());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndVacancyIsFoundByIdAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(TestDataUtil.VACANCY_ID)).thenReturn(Optional.of(persistent));
		//when
		final Vacancy saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertEquals((Long) TestDataUtil.VACANCY_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.VACANCY_SUMMARY, saved.getSummary());
		assertEquals(upperCase(TestDataUtil.VACANCY_SUMMARY), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.VACANCY_DESCRIPTION, saved.getDescription());
		assertEquals(oldModifiedBy, saved.getModifiedBy());
		assertFalse(String.format("%s should not be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndVacancyIsFoundByIdAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(TestDataUtil.VACANCY_ID)).thenReturn(Optional.of(persistent));
		//when
		final Vacancy saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		Assert.assertEquals(TestDataUtil.VACANCY_SUMMARY, saved.getSummary());
		assertEquals(upperCase(TestDataUtil.VACANCY_SUMMARY), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.VACANCY_DESCRIPTION, saved.getDescription());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	private Vacancy invokeSave(InvocationOnMock invocation) {
		final Vacancy vacancy = invocation.getArgument(0, Vacancy.class);
		vacancy.setId(TestDataUtil.VACANCY_ID);
		return vacancy;
	}
	

}