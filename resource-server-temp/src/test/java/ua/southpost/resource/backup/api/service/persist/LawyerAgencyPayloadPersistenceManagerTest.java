package ua.southpost.resource.backup.api.service.persist;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.backup.api.model.submit.LawyerAgencyPayload;
import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.LawyerAgencyRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LawyerAgencyPayloadPersistenceManagerTest {

	private static final Long AGENCY_ID = 22000L;
	private static final String AGENCY_NAME = "Lawyer Agency Name";
	private static final String WEB_SITE = "https://lawyer.agency.com/new";
	private static final String OLD_WEB_SITE = "https://lawyer.agency.com/old";
	private static final Set<LawCase> LAW_CASES = ImmutableSet.of(LawCase.ADMINISTRATIVE, LawCase.PROPERTY, LawCase.CRIMINAL, LawCase.CIVIL);
	private static final Set<LawCase> OLD_LAW_CASES = ImmutableSet.of(LawCase.CIVIL, LawCase.FAMILY);

	@InjectMocks
	private final LawyerAgencyPayloadPersistenceManager testObj = new LawyerAgencyPayloadPersistenceManager();
	@Mock
	private LawyerAgencyRepository repository;
	@Mock
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Mock
	private ContactPayloadPersistenceManager contactPersistenceManager;

	@Mock
	private User userMock;
	@Mock
	private User otherUserMock;

	private LawyerAgency persistent;
	private LawyerAgencyPayload payload;
	private User oldModifiedBy;

	@Before
	public void setUp() {
		payload = new LawyerAgencyPayload();
		payload.setId(AGENCY_ID);
		payload.setSettlement(EntityPayloadPersistenceManagerTestUtil.createSettlementPayload());
		payload.setAgencyName(AGENCY_NAME);
		payload.setWebSite(WEB_SITE);
		payload.setContact(EntityPayloadPersistenceManagerTestUtil.createContactPayload());
		payload.setCases(Lists.newArrayList(LAW_CASES));
		when(repository.save(any(LawyerAgency.class)))
				.then((Answer<LawyerAgency>) this::invokeSave);
		when(settlementPersistenceManager.persist(any(SettlementPayload.class), anyBoolean(), any(User.class)))
				.then((Answer<Settlement>) this::invokePersistSettlement);
		when(settlementPersistenceManager.merge(any(Settlement.class), any(Settlement.class)))
				.then((Answer<Settlement>) this::invokeMergeSettlement);
		doAnswer(this::invokeContactPopulateEntity).when(contactPersistenceManager)
				.populateEntity(any(ContactPayload.class), any(ContactEntity.class), anyBoolean(), any(User.class));
		when(contactPersistenceManager.merge(any(ContactEntity.class), any(ContactEntity.class)))
				.then((Answer<ContactEntity>) this::invokeMergeContact);
		persistent = TestDataUtil.createLawyerAgencyWithAddress();
		persistent.setId(AGENCY_ID);
		persistent.setDisplayName(AGENCY_NAME);
		persistent.setSearchValue(AGENCY_NAME);
		persistent.setWebSite(OLD_WEB_SITE);
		persistent.setCases(OLD_LAW_CASES);
		persistent.setModifiedBy(otherUserMock);
		oldModifiedBy = persistent.getModifiedBy();
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(AGENCY_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, userMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoDwellingByUniqueKeyFound() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findByName(upperCase(AGENCY_NAME))).thenReturn(Optional.empty());
		//when
		final LawyerAgency saved = testObj.persist(payload, false, userMock);
		//then
		assertNotNull(saved);
		verify(repository).findByName(upperCase(AGENCY_NAME));
		assertEquals(AGENCY_ID, saved.getId());
		assertEquals(AGENCY_NAME, saved.getDisplayName());
		assertEquals(upperCase(AGENCY_NAME), saved.getSearchValue());
		assertEquals(WEB_SITE, saved.getWebSite());
		assertEquals(LAW_CASES, saved.getCases());
		assertEquals(userMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeDwellingByUniqueKeyFoundAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findByName(upperCase(AGENCY_NAME))).thenReturn(Optional.of(persistent));
		//when
		final LawyerAgency saved = testObj.persist(payload, false, userMock);
		//then
		assertNotNull(saved);
		verify(repository).findByName(upperCase(AGENCY_NAME));
		assertEquals(AGENCY_ID, saved.getId());
		assertEquals(AGENCY_NAME, saved.getDisplayName());
		assertEquals(upperCase(AGENCY_NAME), saved.getSearchValue());
		assertEquals(OLD_WEB_SITE, saved.getWebSite());
		assertEquals(OLD_LAW_CASES, saved.getCases());
		assertEquals(oldModifiedBy, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeDwellingByUniqueKeyFoundAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findByName(upperCase(AGENCY_NAME))).thenReturn(Optional.of(persistent));
		//when
		final LawyerAgency saved = testObj.persist(payload, true, userMock);
		//then
		assertNotNull(saved);
		verify(repository).findByName(upperCase(AGENCY_NAME));
		assertEquals(AGENCY_ID, saved.getId());
		assertEquals(AGENCY_NAME, saved.getDisplayName());
		assertEquals(upperCase(AGENCY_NAME), saved.getSearchValue());
		assertEquals(WEB_SITE, saved.getWebSite());
		assertEquals(LAW_CASES, saved.getCases());
		assertEquals(userMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndDwellingIsFoundByIdAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(AGENCY_ID)).thenReturn(Optional.of(persistent));
		//when
		final LawyerAgency saved = testObj.persist(payload, false, userMock);
		//then
		assertNotNull(saved);
		assertEquals(AGENCY_ID, saved.getId());
		assertEquals(AGENCY_NAME, saved.getDisplayName());
		assertEquals(upperCase(AGENCY_NAME), saved.getSearchValue());
		assertEquals(OLD_WEB_SITE, saved.getWebSite());
		assertEquals(OLD_LAW_CASES, saved.getCases());
		assertEquals(oldModifiedBy, saved.getModifiedBy());
		assertFalse(String.format("%s should not be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndDwellingIsFoundByIdAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(AGENCY_ID)).thenReturn(Optional.of(persistent));
		//when
		final LawyerAgency saved = testObj.persist(payload, true, userMock);
		//then
		assertNotNull(saved);
		assertEquals(AGENCY_NAME, saved.getDisplayName());
		assertEquals(upperCase(AGENCY_NAME), saved.getSearchValue());
		assertEquals(WEB_SITE, saved.getWebSite());
		assertEquals(LAW_CASES, saved.getCases());
		assertEquals(userMock, saved.getModifiedBy());
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

	private LawyerAgency invokeSave(InvocationOnMock invocation) {
		final LawyerAgency dwelling = invocation.getArgument(0, LawyerAgency.class);
		dwelling.setId(AGENCY_ID);
		return dwelling;
	}

	private Object invokeContactPopulateEntity(InvocationOnMock invocation) {
		System.err.println("Invoking contact populate entity");
		final ContactPayload payload = invocation.getArgument(0, ContactPayload.class);
		final ContactEntity entity = invocation.getArgument(1, ContactEntity.class);
		ofNullable(payload.getLocation()).ifPresent(l -> entity.setLocationAddress( EntityPayloadPersistenceManagerTestUtil.locationFromPayload(l)));
		final List<Phone> phones = ofNullable(payload.getPhones()).orElseGet(Collections::emptyList).stream()
				.map(EntityPayloadPersistenceManagerTestUtil::phoneFromPayload)
				.collect(Collectors.toList());
		entity.setPhones(phones);
		final List<EmailAddress> emails = ofNullable(payload.getEmails()).orElseGet(Collections::emptyList)
				.stream()
				.map(EntityPayloadPersistenceManagerTestUtil::emailFromPayload)
				.collect(Collectors.toList());
		entity.setEmails(emails);
		return null;
	}

	private ContactEntity invokeMergeContact(InvocationOnMock invocation) {
		ContactEntity from = invocation.getArgument(0, ContactEntity.class);
		ContactEntity to = invocation.getArgument(1, ContactEntity.class);
		System.err.printf("Merging %1$s to %2$s\n", from, to);
		to.setLocationAddress(from.getLocationAddress());
		to.setPhones(from.getPhones());
		to.setEmails(from.getEmails());
		return to;
	}
}