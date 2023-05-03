/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.service.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.EntityHistory;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.EntityHistoryRepository;
import ua.southpost.resource.backup.service.history.domain.HistoryRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * prerforms unit tests over {@link EntityHistoryServiceImpl}.
 * Created by mchys on 08.12.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityHistoryServiceImplTest {

	private static final String TEST_REGION_ID = "TEST_REGION";
	private static final String REGION_NAME = "Region Name";
	private static final long DISTRICT_ID = -1L;
	private static final String DISTRICT_NAME = "District";
	private static final long SETTLEMENT_ID = -2L;
	private static final SettlementKind SETTLEMENT_KIND = SettlementKind.city;
	private static final String SETTLEMENT_NAME = "Settlement";
	private static final long STREET_ID = -3L;
	private static final StreetKind STREET_KIND = StreetKind.street;
	private static final String STREET_NAME = "Street";
	private static final long LOCATION_ID = -5L;
	private static final String POSTAL_CODE = "00000";
	private static final String STREET_NUMBER = "99S";
	private static final String BLOCK_NUMBER = "Block";
	private static final String ROOM_NUMBER = "1R";
	private static final long HISTORY_ID = -1000L;

	@InjectMocks
	private EntityHistoryServiceImpl testObj = new EntityHistoryServiceImpl();
	@Mock
	private EntityHistoryRepository repositoryMock;
	private final Region region = new Region();
	private final District district = new District();
	private final Settlement settlement = new Settlement();
	private final Street street = new Street();
	private final Location location = new Location();
	@Mock
	private User userMock;

	@Before
	public void setUp() {
		doAnswer(invocation -> {
			final EntityHistory entityHistory = (EntityHistory) invocation.getArguments()[0];
			entityHistory.setId(System.nanoTime());
			return entityHistory;
		}).when(repositoryMock).save(any(EntityHistory.class));
		region.setId(TEST_REGION_ID);
		region.setDisplayName(REGION_NAME);
		region.setSearchValue(REGION_NAME.toUpperCase());
		district.setId(DISTRICT_ID);
		district.setRegion(region);
		district.setDisplayName(DISTRICT_NAME);
		district.setSearchValue(DISTRICT_NAME.toUpperCase());
		district.setMockDistrict(NoYes.NO);
		settlement.setId(SETTLEMENT_ID);
		settlement.setDistrict(district);
		settlement.setKind(SETTLEMENT_KIND);
		settlement.setDisplayName(SETTLEMENT_NAME);
		settlement.setSearchValue(SETTLEMENT_NAME.toUpperCase());
		street.setId(STREET_ID);
		street.setSettlement(settlement);
		street.setKind(STREET_KIND);
		street.setDisplayName(STREET_NAME);
		street.setSearchValue(STREET_NAME.toUpperCase());
		location.setId(LOCATION_ID);
		location.setStreet(street);
		location.setPostalCode(POSTAL_CODE);
		location.setStreetNumber(STREET_NUMBER);
		location.setSearchStreetNumber(STREET_NUMBER.toUpperCase());
		location.setBlockNumber(BLOCK_NUMBER);
		location.setSearchBlockNumber(BLOCK_NUMBER.toUpperCase());
		location.setRoomNumber(ROOM_NUMBER);
		location.setSearchRoomNumber(ROOM_NUMBER.toUpperCase());
	}

	@Test
	public void testAddHistory() throws Exception {
		final String locationJson = new ObjectMapper().writeValueAsString(location);
		testObj.add(location, ChangeType.UPDATE, userMock);

		verify(repositoryMock).save(argThat(argument -> StringUtils.equals(locationJson, argument.getEntityValue())));
	}

	@Test
	public void testListParticularLocationHistory() throws Exception {
		final EntityHistory entityHistory = new EntityHistory();
		entityHistory.setId(HISTORY_ID);
		final Date changeTime = new Date();
		entityHistory.setChangeTime(changeTime);
		entityHistory.setChangeType(ChangeType.CREATE);
		entityHistory.setChangedBy(userMock);
		final String entityType = Location.class.getName();
		entityHistory.setEntityType(entityType);
		final String entityId = String.format("%d", LOCATION_ID);
		entityHistory.setEntityId(entityId);
		final String locationJson = new ObjectMapper().writeValueAsString(location);
		entityHistory.setEntityValue(locationJson);

		when(repositoryMock.list(entityType, entityId, null, null, PageRequest.of(0, 1)))
				.thenReturn(singletonList(entityHistory));

		final List<HistoryRecord<Long, Location>> historyRecords = testObj.historyOf(location, null, null, PageRequest.of(0, 1));

		assertNotNull(historyRecords);
		assertEquals(1, historyRecords.size());
		HistoryRecord<Long, Location> record = historyRecords.get(0);
		assertEquals(HISTORY_ID, record.getId());
		assertEquals(changeTime, record.getChangeDateTime());
		assertEquals(ChangeType.CREATE, record.getChangeType());
		assertEquals(userMock, record.getChangedBy());
		assertEquals(location, record.getEntity());
	}

	@Test
	public void testListLocationEntityHistory() throws Exception {
		final EntityHistory entityHistory = new EntityHistory();
		entityHistory.setId(HISTORY_ID);
		final Date changeTime = new Date();
		entityHistory.setChangeTime(changeTime);
		entityHistory.setChangeType(ChangeType.CREATE);
		entityHistory.setChangedBy(userMock);
		final String entityType = Location.class.getName();
		entityHistory.setEntityType(entityType);
		final String entityId = String.format("%d", LOCATION_ID);
		entityHistory.setEntityId(entityId);
		final String locationJson = new ObjectMapper().writeValueAsString(location);
		entityHistory.setEntityValue(locationJson);

		when(repositoryMock.list(entityType, null, null, PageRequest.of(0, 1)))
				.thenReturn(singletonList(entityHistory));

		final List<HistoryRecord<Long, Location>> historyRecords = testObj.historyOf(Location.class, null, null, PageRequest.of(0, 1));

		assertNotNull(historyRecords);
		assertEquals(1, historyRecords.size());
		HistoryRecord<Long, Location> record = historyRecords.get(0);
		assertEquals(HISTORY_ID, record.getId());
		assertEquals(changeTime, record.getChangeDateTime());
		assertEquals(ChangeType.CREATE, record.getChangeType());
		assertEquals(userMock, record.getChangedBy());
		assertEquals(location, record.getEntity());
	}

}