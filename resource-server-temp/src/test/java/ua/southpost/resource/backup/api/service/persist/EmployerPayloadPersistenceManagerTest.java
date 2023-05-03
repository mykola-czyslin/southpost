package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.EmployerRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployerPayloadPersistenceManagerTest {

	private static final String OLD_WEBSITE = "https://old.web.site.com/";
	@InjectMocks
	private EmployerPayloadPersistenceManager testObj = new EmployerPayloadPersistenceManager();
	@Mock
	private EmployerRepository repository;
	@Mock
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Mock
	private ContactPayloadPersistenceManager contactPersistenceManager;
	
	@Mock
	private User modifiedByMock;
	@Mock
	private User otherModifiedByMock;
	
	private EmployerPayload payload;
	private Employer persistent;

	@Before
	public void setUp() {
		payload = EntityPayloadPersistenceManagerTestUtil.createEmployerPayload();
		persistent = TestDataUtil.createEmployerWithFullContactInfo();
		persistent.setWebsite(OLD_WEBSITE);
		persistent.setModifiedBy(otherModifiedByMock);
		when(repository.save(any(Employer.class))).then((Answer<Employer>) this::invokeSaveEmployer);
		when(settlementPersistenceManager.persist(any(SettlementPayload.class), anyBoolean(), any(User.class)))
				.then((Answer<Settlement>) this::invokePersistSettlement);
		when(settlementPersistenceManager.merge(any(Settlement.class), any(Settlement.class)))
				.then((Answer<Settlement>) this::invokeMergeSettlement);
		doNothing().when(contactPersistenceManager)
				.populateEntity(any(ContactPayload.class), any(ContactEntity.class), anyBoolean(), any(User.class));

	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(TestDataUtil.EMPLOYER_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoEmployerByUniqueKeyFound() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findByName(upperCase(TestDataUtil.EMPLOYER_NAME))).thenReturn(Optional.empty());
		//when
		final Employer saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findByName(upperCase(TestDataUtil.EMPLOYER_NAME));
		assertEquals((Long) TestDataUtil.EMPLOYER_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.EMPLOYER_NAME, saved.getDisplayName());
		assertEquals(upperCase(TestDataUtil.EMPLOYER_NAME), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.WEBSITE, saved.getWebsite());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeEmployerByUniqueKeyFoundAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findByName(upperCase(TestDataUtil.EMPLOYER_NAME))).thenReturn(Optional.of(persistent));
		//when
		final Employer saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findByName(upperCase(TestDataUtil.EMPLOYER_NAME));
		assertEquals((Long) TestDataUtil.EMPLOYER_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.EMPLOYER_NAME, saved.getDisplayName());
		assertEquals(upperCase(TestDataUtil.EMPLOYER_NAME), saved.getSearchValue());
		assertEquals(OLD_WEBSITE, saved.getWebsite());
		assertEquals(otherModifiedByMock, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeEmployerByUniqueKeyFoundAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findByName(upperCase(TestDataUtil.EMPLOYER_NAME))).thenReturn(Optional.of(persistent));
		//when
		final Employer saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findByName(upperCase(TestDataUtil.EMPLOYER_NAME));
		assertEquals((Long) TestDataUtil.EMPLOYER_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.EMPLOYER_NAME, saved.getDisplayName());
		assertEquals(upperCase(TestDataUtil.EMPLOYER_NAME), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.WEBSITE, saved.getWebsite());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndEmployerIsFoundByIdAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(TestDataUtil.EMPLOYER_ID)).thenReturn(Optional.of(persistent));
		//when
		final Employer saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertEquals((Long) TestDataUtil.EMPLOYER_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.EMPLOYER_NAME, saved.getDisplayName());
		assertEquals(upperCase(TestDataUtil.EMPLOYER_NAME), saved.getSearchValue());
		assertEquals(OLD_WEBSITE, saved.getWebsite());
		assertEquals(otherModifiedByMock, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndEmployerIsFoundByIdAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(TestDataUtil.EMPLOYER_ID)).thenReturn(Optional.of(persistent));
		//when
		final Employer saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertEquals((Long) TestDataUtil.EMPLOYER_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.EMPLOYER_NAME, saved.getDisplayName());
		assertEquals(upperCase(TestDataUtil.EMPLOYER_NAME), saved.getSearchValue());
		Assert.assertEquals(TestDataUtil.WEBSITE, saved.getWebsite());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}
	

	private Settlement invokePersistSettlement(InvocationOnMock invocation) {
		final SettlementPayload settlementPayload = invocation.getArgument(0, SettlementPayload.class);
		Settlement settlement = EntityPayloadPersistenceManagerTestUtil.settlementFromPayload(settlementPayload);
		settlement.setId(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID);
		return settlement;
	}
	
	private Settlement invokeMergeSettlement(InvocationOnMock invocation) {
		final Settlement fromSettlement = invocation.getArgument(0, Settlement.class);
		final Settlement toSettlement = invocation.getArgument(1, Settlement.class);
		toSettlement.setKind(fromSettlement.getKind());
		toSettlement.setDisplayName(fromSettlement.getDisplayName());
		toSettlement.setSearchValue(fromSettlement.getDisplayName());
		return toSettlement;
	}
	
	private Employer invokeSaveEmployer(InvocationOnMock invocation) {
		final Employer employer = invocation.getArgument(0, Employer.class);
		employer.setId(TestDataUtil.EMPLOYER_ID);
		return employer;
	}
}