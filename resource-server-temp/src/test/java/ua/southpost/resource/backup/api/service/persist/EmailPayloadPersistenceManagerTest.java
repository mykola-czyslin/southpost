package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.EmailAddressRepository;
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
public class EmailPayloadPersistenceManagerTest {

	@InjectMocks
	private EmailPayloadPersistenceManager testObj = new EmailPayloadPersistenceManager();
	@Mock
	private EmailAddressRepository repository;
	@Mock
	private User modifiedByMock;
	
	private EmailPayload payload;
	private EmailAddress persistent;

	@Before
	public void setUp() {
		payload = EntityPayloadPersistenceManagerTestUtil.createEmailPayload();
		persistent = EntityPayloadPersistenceManagerTestUtil.emailFromPayload(EntityPayloadPersistenceManagerTestUtil.createEmailPayload());
		when(repository.save(any(EmailAddress.class))).then((Answer<EmailAddress>)this::invokeSave);
	}


	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.EMAIL_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, modifiedByMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoPhoneByUniqueKeyFound() {
		//given
		payload.setId(null);
		when(repository.findByAddress(EntityPayloadPersistenceManagerTestUtil.EMAIL_ADDRESS)).thenReturn(Optional.empty());
		//when
		EmailAddress saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findByAddress(EntityPayloadPersistenceManagerTestUtil.EMAIL_ADDRESS);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.emailMeetsExpectations(saved));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomePhoneByUniqueKeyFoundAndMergeIsOff() {
		//given
		payload.setId(null);
		when(repository.findByAddress(EntityPayloadPersistenceManagerTestUtil.EMAIL_ADDRESS)).thenReturn(Optional.of(persistent));
		//when
		EmailAddress saved = testObj.persist(payload, false, modifiedByMock);
		//then
		verify(repository).findByAddress(EntityPayloadPersistenceManagerTestUtil.EMAIL_ADDRESS);
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.emailMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomePhoneByUniqueKeyFoundAndMergeIsOn() {
		//given
		payload.setId(null);
		when(repository.findByAddress(EntityPayloadPersistenceManagerTestUtil.EMAIL_ADDRESS)).thenReturn(Optional.of(persistent));
		//when
		EmailAddress saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.emailMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndPhoneIsFoundByIdAndMergeIsOff() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.EMAIL_ID)).thenReturn(Optional.of(persistent));
		//when
		EmailAddress saved = testObj.persist(payload, false, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.emailMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}

	@Test
	public void testPersistWhenIdSpecifiedAndPhoneIsFoundByIdAndMergeIsOn() {
		//given
		when(repository.findById(EntityPayloadPersistenceManagerTestUtil.EMAIL_ID)).thenReturn(Optional.of(persistent));
		//when
		EmailAddress saved = testObj.persist(payload, true, modifiedByMock);
		//then
		assertNotNull(saved);
		assertTrue(EntityPayloadPersistenceManagerTestUtil.emailMeetsExpectations(saved));
		assertEquals(persistent, saved);
	}
	
	private EmailAddress invokeSave(InvocationOnMock invocation) {
		final EmailAddress address = invocation.getArgument(0, EmailAddress.class);
		address.setId(EntityPayloadPersistenceManagerTestUtil.EMAIL_ID);
		return address;
	}
}