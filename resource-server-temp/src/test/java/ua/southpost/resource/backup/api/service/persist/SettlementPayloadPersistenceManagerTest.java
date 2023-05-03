package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.DistrictRepository;
import ua.southpost.resource.backup.data.repo.SettlementRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettlementPayloadPersistenceManagerTest {

	@InjectMocks
	private SettlementPayloadPersistenceManager testObj = new SettlementPayloadPersistenceManager();
	@Mock
	private SettlementRepository repository;
	@Mock
	private DistrictRepository districtRepository;

	@Mock
	private District districtMock;
	@Mock
	private Region regionMock;

	@Mock
	private User modifiedByMock;

	private SettlementPayload payload;
	private Settlement persistent;

	@Before
	public void setUp() {
		payload = EntityPayloadPersistenceManagerTestUtil.createSettlementPayload();
		when(districtRepository.findById(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID)).thenReturn(Optional.of(districtMock));
		when(districtMock.getId()).thenReturn(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID);
		when(districtMock.getDisplayName()).thenReturn(EntityPayloadPersistenceManagerTestUtil.DISTRICT_NAME);
		when(districtMock.getSearchValue()).thenReturn(upperCase(EntityPayloadPersistenceManagerTestUtil.DISTRICT_NAME));
		when(districtMock.getRegion()).thenReturn(regionMock);
		when(regionMock.getId()).thenReturn(EntityPayloadPersistenceManagerTestUtil.REGION_ID);
		when(regionMock.getDisplayName()).thenReturn(EntityPayloadPersistenceManagerTestUtil.REGION_NAME);
		when(regionMock.getSearchValue()).thenReturn(upperCase(EntityPayloadPersistenceManagerTestUtil.REGION_NAME));
		when(repository.save(any(Settlement.class)))
				.then((Answer<Settlement>) this::invokeSave);
		persistent = new Settlement();
		persistent.setId(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID);
		persistent.setDisplayName(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME);
		persistent.setSearchValue(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME);
		persistent.setKind(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_KIND);
		persistent.setDistrict(districtMock);
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoSettlementByUniqueKeyFound() {
		//given
		payload.setId(null);
		when(repository.findUnique(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_KIND, upperCase(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME))).thenReturn(Optional.empty());
		//when
		Settlement saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findUnique(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_KIND, upperCase(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME));
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.settlementMeetsExpectations(saved));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeSettlementByUniqueKeyFoundAndMergeIsOff() {
		//given
		payload.setId(null);
		when(repository.findUnique(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_KIND, upperCase(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME))).thenReturn(Optional.of(persistent));
		//when
		Settlement saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findUnique(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_KIND, upperCase(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME));
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.settlementMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeSettlementByUniqueKeyFoundAndMergeIsOn() {
		//given
		payload.setId(null);
		when(repository.findUnique(EntityPayloadPersistenceManagerTestUtil.DISTRICT_ID, EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_KIND, upperCase(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_NAME))).thenReturn(Optional.of(persistent));
		//when
		Settlement saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.settlementMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndSettlementIsFoundByIdAndMergeIsOff() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID)).thenReturn(Optional.of(persistent));
		//when
		Settlement saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.settlementMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndSettlementIsFoundByIdAndMergeIsOn() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID)).thenReturn(Optional.of(persistent));
		//when
		Settlement saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.settlementMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	private Settlement invokeSave(InvocationOnMock invocation) {
		final Settlement settlement = invocation.getArgument(0, Settlement.class);
		settlement.setId(EntityPayloadPersistenceManagerTestUtil.SETTLEMENT_ID);
		return settlement;
	}
}