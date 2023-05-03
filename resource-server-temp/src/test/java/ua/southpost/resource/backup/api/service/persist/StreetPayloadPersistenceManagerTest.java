package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.StreetRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StreetPayloadPersistenceManagerTest {

	@InjectMocks
	private StreetPayloadPersistenceManager testObj = new StreetPayloadPersistenceManager();
	@Mock
	private StreetRepository repository;
	@Mock
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Mock
	private User modifiedByMock;

	private StreetPayload payload;
	private Street persistent;

	@Before
	public void setUp() {
		payload = EntityPayloadPersistenceManagerTestUtil.createStreetPayload();
		final Settlement settlement = EntityPayloadPersistenceManagerTestUtil.settlementFromPayload(payload.getSettlement());

		when(repository.save(any(Street.class))).then((Answer<Street>) this::invokeSave);
		when(settlementPersistenceManager.persist(any(SettlementPayload.class), anyBoolean(), any(User.class)))
				.thenReturn(settlement);
		when(settlementPersistenceManager.merge(any(Settlement.class), any(Settlement.class)))
				.then((Answer<Settlement>) invocation -> invocation.getArgument(1, Settlement.class));
		persistent = EntityPayloadPersistenceManagerTestUtil.streetFromPayload(EntityPayloadPersistenceManagerTestUtil.createStreetPayload());
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.STREET_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoStreetByUniqueKeyFound() {
		//given
		payload.setId(null);
		when(repository.findUnique(upperCase(EntityPayloadPersistenceManagerTestUtil.STREET_NAME), EntityPayloadPersistenceManagerTestUtil.STREET_KIND, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID)).thenReturn(Optional.empty());
		//when
		Street saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findUnique(upperCase(EntityPayloadPersistenceManagerTestUtil.STREET_NAME), EntityPayloadPersistenceManagerTestUtil.STREET_KIND, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.streetMeetsExpectations(saved));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeStreetByUniqueKeyFoundAndMergeIsOff() {
		//given
		payload.setId(null);
		when(repository.findUnique(upperCase(EntityPayloadPersistenceManagerTestUtil.STREET_NAME), EntityPayloadPersistenceManagerTestUtil.STREET_KIND, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID)).thenReturn(Optional.of(persistent));
		//when
		Street saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findUnique(upperCase(EntityPayloadPersistenceManagerTestUtil.STREET_NAME), EntityPayloadPersistenceManagerTestUtil.STREET_KIND, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.streetMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeStreetByUniqueKeyFoundAndMergeIsOn() {
		//given
		payload.setId(null);
		when(repository.findUnique(upperCase(EntityPayloadPersistenceManagerTestUtil.STREET_NAME), EntityPayloadPersistenceManagerTestUtil.STREET_KIND, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID)).thenReturn(Optional.of(persistent));
		//when
		Street saved = testObj.persist(payload, true, modifiedByMock);
		//then
		verify(repository).findUnique(upperCase(EntityPayloadPersistenceManagerTestUtil.STREET_NAME), EntityPayloadPersistenceManagerTestUtil.STREET_KIND, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.streetMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndStreetIsFoundByIdAndMergeIsOff() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.STREET_ID)).thenReturn(Optional.of(persistent));
		//when
		Street saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.streetMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndStreetIsFoundByIdAndMergeIsOn() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.STREET_ID)).thenReturn(Optional.of(persistent));
		//when
		Street saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.streetMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}


	private Street invokeSave(InvocationOnMock invocation) {
		final Street street = invocation.getArgument(0, Street.class);
		street.setId(EntityPayloadPersistenceManagerTestUtil.STREET_ID);
		return street;
	}
}