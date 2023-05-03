package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.PhoneRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhonePayloadPersistenceManagerTest {

	@InjectMocks
	private PhonePayloadPersistenceManager testObj = new PhonePayloadPersistenceManager();
	@Mock
	private PhoneRepository repository;

	@Mock
	private User modifiedByMock;

	private PhonePayload payload;
	private Phone persistent;

	@Before
	public void setUp() {
		payload = EntityPayloadPersistenceManagerTestUtil.createPhonePayload();
		persistent = EntityPayloadPersistenceManagerTestUtil.phoneFromPayload(EntityPayloadPersistenceManagerTestUtil.createPhonePayload());
		when(repository.save(any(Phone.class))).then((Answer<Phone>) this::invokeSave);
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.PHONE_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoPhoneByUniqueKeyFound() {
		//given
		payload.setId(null);
		when(repository.findByNumber(EntityPayloadPersistenceManagerTestUtil.PHONE_NUMBER)).thenReturn(Optional.empty());
		//when
		Phone saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findByNumber(EntityPayloadPersistenceManagerTestUtil.PHONE_NUMBER);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.phoneMeetsExpectations(saved));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomePhoneByUniqueKeyFoundAndMergeIsOff() {
		//given
		payload.setId(null);
		when(repository.findByNumber(EntityPayloadPersistenceManagerTestUtil.PHONE_NUMBER)).thenReturn(Optional.of(persistent));
		//when
		Phone saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findByNumber(EntityPayloadPersistenceManagerTestUtil.PHONE_NUMBER);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.phoneMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomePhoneByUniqueKeyFoundAndMergeIsOn() {
		//given
		payload.setId(null);
		when(repository.findByNumber(EntityPayloadPersistenceManagerTestUtil.PHONE_NUMBER)).thenReturn(Optional.of(persistent));
		//when
		Phone saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.phoneMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndPhoneIsFoundByIdAndMergeIsOff() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.PHONE_ID)).thenReturn(Optional.of(persistent));
		//when
		Phone saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.phoneMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndPhoneIsFoundByIdAndMergeIsOn() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.PHONE_ID)).thenReturn(Optional.of(persistent));
		//when
		Phone saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.phoneMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	private Phone invokeSave(InvocationOnMock invocation) {
		final Phone phone = invocation.getArgument(0, Phone.class);
		phone.setId(EntityPayloadPersistenceManagerTestUtil.PHONE_ID);
		return phone;
	}
}