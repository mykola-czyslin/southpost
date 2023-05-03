package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactPayloadPersistenceManagerTest {

	@InjectMocks
	private ContactPayloadPersistenceManager testObj = new ContactPayloadPersistenceManager();
	@Mock
	private LocationPayloadPersistenceManager locationPayloadPersistenceManager;
	@Mock
	private PhonePayloadPersistenceManager phonePayloadPersistenceManager;
	@Mock
	private EmailPayloadPersistenceManager emailPayloadPersistenceManager;
	@Mock
	private User currentUser;
	@Mock
	private ContactEntity contactEntityMock;
	@Mock
	private ContactEntity fromContactEntityMock;
	@Mock
	private ContactEntity toContactEntityMock;

	private ContactPayload payload;

	@Before
	public void setUp() {
		payload = EntityPayloadPersistenceManagerTestUtil.createContactPayload();
		when(locationPayloadPersistenceManager.persist(any(LocationPayload.class), anyBoolean(), any(User.class)))
				.then((Answer<Location>) this::invokeLocationPersistenceManager);
		when(phonePayloadPersistenceManager.persist(any(PhonePayload.class), anyBoolean(), any(User.class)))
				.then((Answer<Phone>) this::invokePhonePersistenceManager);
		when(emailPayloadPersistenceManager.persist(any(EmailPayload.class), anyBoolean(), any(User.class)))
				.then((Answer<EmailAddress>) this::invokeEmailPersistenceManager);
	}

	@Test
	public void testPopulateEntity() {
		//given
		System.out.printf("Payload: %s\n", payload);
		//when
		testObj.populateEntity(payload, contactEntityMock, false, currentUser);
		//then
		verify(contactEntityMock).setLocationAddress(argThat(EntityPayloadPersistenceManagerTestUtil::locationMeetsExpectations));
		verify(contactEntityMock).setPhones(argThat(EntityPayloadPersistenceManagerTestUtil::phoneListMeetsExpectations));
		verify(contactEntityMock).setEmails(argThat(EntityPayloadPersistenceManagerTestUtil::emailListMeetsExpectations));
	}

	@Test
	public void testMergeContactWithoutLocationToOneWithLocation() {
		//given
		final Location location = EntityPayloadPersistenceManagerTestUtil.locationFromPayload(payload.getLocation());
		when(toContactEntityMock.getLocationAddress()).thenReturn(location);
		when(fromContactEntityMock.getPhones()).thenReturn(null);
		final Phone fromPhone = EntityPayloadPersistenceManagerTestUtil.phoneFromPayload(payload.getPhones().get(0));
		when(toContactEntityMock.getPhones()).thenReturn(Collections.singletonList(fromPhone));
		when(fromContactEntityMock.getEmails()).thenReturn(null);
		when(toContactEntityMock.getEmails()).thenReturn(Collections.singletonList(EntityPayloadPersistenceManagerTestUtil.emailFromPayload(payload.getEmails().get(0))));
		//when
		testObj.merge(fromContactEntityMock, toContactEntityMock);
		//then
		verify(toContactEntityMock).setLocationAddress(isNull());
		verify(toContactEntityMock).setPhones(isNull());
		verify(toContactEntityMock).setEmails(isNull());
	}

	@Test
	public void testMergeContactWithLocationPhonesAndEmailsToEmptyOne() {
		//given
		final Location location = EntityPayloadPersistenceManagerTestUtil.locationFromPayload(payload.getLocation());
		when(fromContactEntityMock.getLocationAddress()).thenReturn(location);
		when(toContactEntityMock.getPhones()).thenReturn(null);
		final List<Phone> phoneList = Collections.singletonList(EntityPayloadPersistenceManagerTestUtil.phoneFromPayload(payload.getPhones().get(0)));
		when(fromContactEntityMock.getPhones()).thenReturn(phoneList);
		when(toContactEntityMock.getEmails()).thenReturn(null);
		final List<EmailAddress> emailList = Collections.singletonList(EntityPayloadPersistenceManagerTestUtil.emailFromPayload(payload.getEmails().get(0)));
		when(fromContactEntityMock.getEmails()).thenReturn(emailList);
		//when
		testObj.merge(fromContactEntityMock, toContactEntityMock);
		//then
		verify(toContactEntityMock).setLocationAddress(location);
		verify(toContactEntityMock).setPhones(phoneList);
		verify(toContactEntityMock).setEmails(emailList);
	}

	@Test
	public void testMergeOfTwoFullyPopulatedObjects() {
		//given
		final Location fromLocation = EntityPayloadPersistenceManagerTestUtil.locationFromPayload(payload.getLocation());
		final Location toLocation = EntityPayloadPersistenceManagerTestUtil.locationFromPayload(payload.getLocation());
		fromLocation.setRoomNumber("2B");
		toLocation.setRoomNumber("1A");
		when(fromContactEntityMock.getLocationAddress()).thenReturn(fromLocation);
		when(toContactEntityMock.getLocationAddress()).thenReturn(toLocation);
		final Phone toPhone = EntityPayloadPersistenceManagerTestUtil.phoneFromPayload(payload.getPhones().get(0));
		toPhone.setDescription("=====" + EntityPayloadPersistenceManagerTestUtil.PHONE_DESCRIPTION + "=====");
		final List<Phone> toPhones = Collections.singletonList(toPhone);
		when(toContactEntityMock.getPhones()).thenReturn(toPhones);
		final Phone fromPhone = EntityPayloadPersistenceManagerTestUtil.phoneFromPayload(payload.getPhones().get(0));
		final List<Phone> fromPhones = Collections.singletonList(fromPhone);
		when(fromContactEntityMock.getPhones()).thenReturn(fromPhones);
		final EmailAddress toAddress = EntityPayloadPersistenceManagerTestUtil.emailFromPayload(payload.getEmails().get(0));
		toAddress.setDescription("======" + EntityPayloadPersistenceManagerTestUtil.EMAIL_DESCRIPTION + "======");
		List<EmailAddress> toEmails = Collections.singletonList(toAddress);
		when(toContactEntityMock.getEmails()).thenReturn(toEmails);
		final EmailAddress fromAddress = EntityPayloadPersistenceManagerTestUtil.emailFromPayload(payload.getEmails().get(0));
		final List<EmailAddress> fromEmails = Collections.singletonList(fromAddress);
		when(fromContactEntityMock.getEmails()).thenReturn(fromEmails);
		when(locationPayloadPersistenceManager.merge(any(Location.class), any(Location.class))).then((Answer<Location>) this::invokeMergeLocations);
		when(phonePayloadPersistenceManager.merge(any(Phone.class), any(Phone.class))).thenAnswer((Answer<Phone>) this::invokeMergePhones);
		when(emailPayloadPersistenceManager.merge(any(EmailAddress.class), any(EmailAddress.class))).then((Answer<EmailAddress>) this::invokeMergeEmails);
		//when
		testObj.merge(fromContactEntityMock, toContactEntityMock);
		//then
		verify(locationPayloadPersistenceManager).merge(fromLocation, toLocation);
		verify(phonePayloadPersistenceManager).merge(fromPhone, toPhone);
		verify(emailPayloadPersistenceManager).merge(fromAddress, toAddress);
		verify(toContactEntityMock).setLocationAddress(eq(fromLocation));
		verify(toContactEntityMock).setPhones(eq(fromPhones));
		verify(toContactEntityMock).setEmails(eq(toEmails));
	}


	private Location invokeMergeLocations(InvocationOnMock invocation) {
		final Location result = invocation.getArgument(1, Location.class);
		result.setRoomNumber(invocation.getArgument(0, Location.class).getRoomNumber());
		return result;
	}

	private Phone invokeMergePhones(InvocationOnMock invocation) {
		final Phone result = invocation.getArgument(1, Phone.class);
		result.setDescription(invocation.getArgument(0, Phone.class).getDescription());
		return result;
	}

	private EmailAddress invokeMergeEmails(InvocationOnMock invocation) {
		final EmailAddress result = invocation.getArgument(1, EmailAddress.class);
		result.setDescription(invocation.getArgument(0, EmailAddress.class).getDescription());
		return result;
	}

	private Location invokeLocationPersistenceManager(InvocationOnMock invocation) {
		final LocationPayload locationPayload = invocation.getArgument(0, LocationPayload.class);
		return EntityPayloadPersistenceManagerTestUtil.locationFromPayload(locationPayload);
	}


	private Phone invokePhonePersistenceManager(InvocationOnMock invocation) {
		final PhonePayload phonePayload = invocation.getArgument(0, PhonePayload.class);
		return EntityPayloadPersistenceManagerTestUtil.phoneFromPayload(phonePayload);
	}

	private EmailAddress invokeEmailPersistenceManager(InvocationOnMock invocation) {
		final EmailPayload payload = invocation.getArgument(0, EmailPayload.class);
		return EntityPayloadPersistenceManagerTestUtil.emailFromPayload(payload);
	}

}