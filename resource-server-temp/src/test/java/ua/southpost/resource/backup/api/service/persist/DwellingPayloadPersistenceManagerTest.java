package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.backup.api.model.submit.DwellingPayload;
import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.DwellingRepository;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
public class DwellingPayloadPersistenceManagerTest {

	@InjectMocks
	private DwellingPayloadPersistenceManager testObj = new DwellingPayloadPersistenceManager();
	@Mock
	private DwellingRepository repository;
	@Mock
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Mock
	private ContactPayloadPersistenceManager contactPersistenceManager;

	@Mock
	private User modifiedByMock;

	private DwellingPayload payload;
	private Dwelling persistent;

	private User oldModifiedBy;

	@Before
	public void setUp() {
		payload = new DwellingPayload();
		payload.setId(TestDataUtil.DWELLING_ID);
		payload.setSettlement(EntityPayloadPersistenceManagerTestUtil.createSettlementPayload());
		payload.setSettlementArea(TestDataUtil.SETTLEMENT_AREA);
		payload.setDwellingKind(TestDataUtil.DWELLING_KIND);
		payload.setNumberOfRooms(TestDataUtil.NUMBER_OF_ROOMS);
		payload.setTotalArea(TestDataUtil.TOTAL_AREA);
		payload.setLivingArea(TestDataUtil.LIVING_AREA);
		payload.setPrice(TestDataUtil.DWELLING_PRICE);
		payload.setBillingPeriod(TestDataUtil.BILLING_PERIOD);
		payload.setDescription(TestDataUtil.DWELLING_DESCRIPTION);
		payload.setContact(EntityPayloadPersistenceManagerTestUtil.createContactPayload());
		persistent = TestDataUtil.createDwelling();
		oldModifiedBy = persistent.getModifiedBy();
		when(repository.save(any(Dwelling.class)))
				.then((Answer<Dwelling>) this::invokeSave);
		when(settlementPersistenceManager.persist(any(SettlementPayload.class), anyBoolean(), any(User.class)))
				.then((Answer<Settlement>) this::invokePersistSettlement);
		when(settlementPersistenceManager.merge(any(Settlement.class), any(Settlement.class)))
				.then((Answer<Settlement>) this::invokeMergeSettlement);
		doAnswer(this::invokeContactPopulateEntity).when(contactPersistenceManager)
				.populateEntity(any(ContactPayload.class), any(ContactEntity.class), anyBoolean(), any(User.class));
		when(contactPersistenceManager.merge(any(ContactEntity.class), any(ContactEntity.class)))
				.then((Answer<ContactEntity>) this::invokeMergeContact);
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(TestDataUtil.DWELLING_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoDwellingByUniqueKeyFound() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findUnique(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID, TestDataUtil.SETTLEMENT_AREA, TestDataUtil.DWELLING_KIND, EntityPayloadPersistenceManagerTestUtil.LOCATION_ID)).thenReturn(Optional.empty());
		//when
		final Dwelling saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findUnique(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID, TestDataUtil.SETTLEMENT_AREA, TestDataUtil.DWELLING_KIND, EntityPayloadPersistenceManagerTestUtil.LOCATION_ID);
		assertEquals((Long) TestDataUtil.DWELLING_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.SETTLEMENT_AREA, saved.getSettlementArea());
		assertEquals(upperCase(TestDataUtil.SETTLEMENT_AREA), saved.getSettlementAreaSearch());
		Assert.assertEquals(TestDataUtil.DWELLING_KIND, saved.getKind());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeDwellingByUniqueKeyFoundAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findUnique(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID, TestDataUtil.SETTLEMENT_AREA, TestDataUtil.DWELLING_KIND, EntityPayloadPersistenceManagerTestUtil.LOCATION_ID)).thenReturn(Optional.of(persistent));
		//when
		final Dwelling saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findUnique(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID, TestDataUtil.SETTLEMENT_AREA, TestDataUtil.DWELLING_KIND, EntityPayloadPersistenceManagerTestUtil.LOCATION_ID);
		assertEquals((Long) TestDataUtil.DWELLING_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.SETTLEMENT_AREA, saved.getSettlementArea());
		assertEquals(upperCase(TestDataUtil.SETTLEMENT_AREA), saved.getSettlementAreaSearch());
		Assert.assertEquals(TestDataUtil.DWELLING_KIND, saved.getKind());
		assertEquals(oldModifiedBy, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeDwellingByUniqueKeyFoundAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repository.findUnique(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID, TestDataUtil.SETTLEMENT_AREA, TestDataUtil.DWELLING_KIND, EntityPayloadPersistenceManagerTestUtil.LOCATION_ID)).thenReturn(Optional.of(persistent));
		//when
		final Dwelling saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		verify(repository).findUnique(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID, TestDataUtil.SETTLEMENT_AREA, TestDataUtil.DWELLING_KIND, EntityPayloadPersistenceManagerTestUtil.LOCATION_ID);
		assertEquals((Long) TestDataUtil.DWELLING_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.SETTLEMENT_AREA, saved.getSettlementArea());
		assertEquals(upperCase(TestDataUtil.SETTLEMENT_AREA), saved.getSettlementAreaSearch());
		Assert.assertEquals(TestDataUtil.DWELLING_KIND, saved.getKind());
		assertEquals(modifiedByMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndDwellingIsFoundByIdAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(TestDataUtil.DWELLING_ID)).thenReturn(Optional.of(persistent));
		//when
		final Dwelling saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertEquals((Long) TestDataUtil.DWELLING_ID, saved.getId());
		Assert.assertEquals(TestDataUtil.SETTLEMENT_AREA, saved.getSettlementArea());
		assertEquals(upperCase(TestDataUtil.SETTLEMENT_AREA), saved.getSettlementAreaSearch());
		Assert.assertEquals(TestDataUtil.DWELLING_KIND, saved.getKind());
		assertEquals(oldModifiedBy, saved.getModifiedBy());
		assertFalse(String.format("%s should not be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndDwellingIsFoundByIdAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repository.findById(TestDataUtil.DWELLING_ID)).thenReturn(Optional.of(persistent));
		//when
		final Dwelling saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		Assert.assertEquals(TestDataUtil.SETTLEMENT_AREA, saved.getSettlementArea());
		assertEquals(upperCase(TestDataUtil.SETTLEMENT_AREA), saved.getSettlementAreaSearch());
		Assert.assertEquals(TestDataUtil.DWELLING_KIND, saved.getKind());
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

	private Dwelling invokeSave(InvocationOnMock invocation) {
		final Dwelling dwelling = invocation.getArgument(0, Dwelling.class);
		dwelling.setId(TestDataUtil.DWELLING_ID);
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