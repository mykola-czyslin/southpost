package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.LocationRepository;
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

import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.LOCATION_ID;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.STREET_ID;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.STREET_NUMBER;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.createLocationPayload;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.createStreetPayload;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.locationFromPayload;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.locationMeetsExpectations;
import static ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManagerTestUtil.streetFromPayload;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationPayloadPersistenceManagerTest {

	@InjectMocks
	private LocationPayloadPersistenceManager testObj = new LocationPayloadPersistenceManager();
	
	@Mock
	private LocationRepository repository;
	@Mock
	private StreetPayloadPersistenceManager streetPersistenceManager;

	@Mock
	private User modifiedByMock;

	private LocationPayload payload;
	private Location persistent;
	
	@Before
	public void setUp() {
		payload = createLocationPayload();
		persistent = locationFromPayload(createLocationPayload());
		Street street = streetFromPayload(createStreetPayload());
		
		when(repository.save(any(Location.class))).then((Answer<Location>) this::invokeSave);
		when(streetPersistenceManager.persist(any(StreetPayload.class), anyBoolean(), any(User.class)))
				.thenReturn(street);
		when(streetPersistenceManager.merge(any(Street.class), any(Street.class)))
				.then((Answer<Street>) invocation -> invocation.getArgument(1, Street.class));
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(LOCATION_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoLocationByUniqueKeyFound() {
		//given
		payload.setId(null);
		when(repository.findByIdentity(STREET_ID, STREET_NUMBER, null, null)).thenReturn(Optional.empty());
		//when
		Location saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findByIdentity(STREET_ID, STREET_NUMBER, null, null);
		assertNotNull(saved);
		assertTrue(locationMeetsExpectations(saved));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeLocationByUniqueKeyFoundAndMergeIsOff() {
		//given
		payload.setId(null);
		when(repository.findByIdentity(STREET_ID, STREET_NUMBER, null, null)).thenReturn(Optional.of(persistent));
		//when
		Location saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findByIdentity(STREET_ID, STREET_NUMBER, null, null);
		assertNotNull(saved);
		assertTrue(locationMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeLocationByUniqueKeyFoundAndMergeIsOn() {
		//given
		payload.setId(null);
		when(repository.findByIdentity(STREET_ID, STREET_NUMBER, null, null)).thenReturn(Optional.of(persistent));
		//when
		Location saved = testObj.persist(payload, true, modifiedByMock);
		//then
		verify(repository).findByIdentity(STREET_ID, STREET_NUMBER, null, null);
		assertNotNull(saved);
		assertTrue(locationMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndLocationIsFoundByIdAndMergeIsOff() {
		//given
		when(repository.findById(LOCATION_ID)).thenReturn(Optional.of(persistent));
		//when
		Location saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(locationMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndLocationIsFoundByIdAndMergeIsOn() {
		//given
		when(repository.findById(LOCATION_ID)).thenReturn(Optional.of(persistent));
		//when
		Location saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(locationMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}
	
	private Location invokeSave(InvocationOnMock invocation) {
		final Location location = invocation.getArgument(0, Location.class);
		location.setId(LOCATION_ID);
		return location;
	}
}