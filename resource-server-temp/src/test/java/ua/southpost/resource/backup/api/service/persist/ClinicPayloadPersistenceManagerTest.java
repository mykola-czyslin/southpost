package ua.southpost.resource.backup.api.service.persist;

import com.google.common.collect.Lists;
import ua.southpost.resource.backup.api.model.submit.ClinicPayload;
import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.backup.api.service.persist.ClinicPayloadPersistenceManager;
import ua.southpost.resource.backup.api.service.persist.ContactPayloadPersistenceManager;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.ClinicRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClinicPayloadPersistenceManagerTest {

	private static final long CLINIC_ID = 1000L;
	private static final String CLINIC_NAME = "Central City Clinic";
	private static final String CLINIC_DESCRIPTION = "Central City Clinic Description";
	private static final ClinicType CLINIC_TYPE = ClinicType.MIXED;
	private static final EnumSet<MedicalService> MEDICAL_SERVICES_UPDATE = EnumSet.of(MedicalService.GENERAL_THERAPY, MedicalService.ADDICTION_THERAPY, MedicalService.SURGERY);
	private static final List<MedicalService> MEDICAL_SERVICES_UPDATE_LIST = Lists.newArrayList(MEDICAL_SERVICES_UPDATE);
	private static final String OLD_CLINIC_DESCRIPTION = "Old Clinic Description";
	private static final EnumSet<MedicalService> OLD_MEDICAL_SERVICES = EnumSet.of(MedicalService.ENDOCRINOLOGY, MedicalService.GASTROENTEROLOGY, MedicalService.GENERAL_THERAPY);
	private static final ClinicType OLD_CLINIC_TYPE = ClinicType.AMBULATORY;
	@InjectMocks
	private ClinicPayloadPersistenceManager testObj = new ClinicPayloadPersistenceManager();

	@Mock
	private ClinicRepository repositoryMock;
	@Mock
	private ContactPayloadPersistenceManager contactPayloadPersistenceManager;

	@Mock
	private User userMock;
	@Mock
	private User otherUserMock;
	private Clinic persistent;

	private ClinicPayload payload;

	@Before
	public void setUp() {
		doNothing().when(contactPayloadPersistenceManager).populateEntity(any(ContactPayload.class), any(ContactEntity.class), anyBoolean(), any(User.class));

		payload = new ClinicPayload();
		payload.setId(CLINIC_ID);
		payload.setClinicName(CLINIC_NAME);
		payload.setDescription(CLINIC_DESCRIPTION);
		payload.setClinicType(CLINIC_TYPE);
		payload.setServices(MEDICAL_SERVICES_UPDATE_LIST);
		payload.setContact(new ContactPayload());
		when(repositoryMock.save(any(Clinic.class)))
				.then((Answer<Clinic>) this::mockSaveClinicInvocation);
		persistent = new Clinic();
		persistent.setId(CLINIC_ID);
		persistent.setDisplayName(CLINIC_NAME);
		persistent.setSearchName(CLINIC_NAME);
		persistent.setDescription(OLD_CLINIC_DESCRIPTION);
		persistent.setServices(OLD_MEDICAL_SERVICES);
		persistent.setClinicType(OLD_CLINIC_TYPE);
		persistent.setModifiedBy(otherUserMock);
		persistent.setModificationTime(new Date());
	}

	private Clinic mockSaveClinicInvocation(InvocationOnMock invocation) {
		Clinic clinic = invocation.getArgument(0, Clinic.class);
		clinic.setId(CLINIC_ID);
		return clinic;
	}

	@Test(expected = NotFoundException.class)
	public void testPersistWhenWrongIdSpecified() {
		//given
		when(repositoryMock.findById(CLINIC_ID)).thenReturn(Optional.empty());
		//when
		testObj.persist(payload, false, userMock);
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndNoClinicByUniqueKeyFound() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repositoryMock.findByName(CLINIC_NAME)).thenReturn(Optional.empty());
		//when
		final Clinic saved = testObj.persist(payload, false, userMock);
		//then
		assertNotNull(saved);
		assertEquals((Long)CLINIC_ID, saved.getId());
		assertEquals(CLINIC_NAME, saved.getDisplayName());
		assertEquals(upperCase(CLINIC_NAME), saved.getSearchName());
		assertEquals(CLINIC_DESCRIPTION, saved.getDescription());
		assertEquals(CLINIC_TYPE, saved.getClinicType());
		assertEquals(MEDICAL_SERVICES_UPDATE, saved.getServices());
		assertEquals(userMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeClinicByUniqueKeyFoundAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repositoryMock.findByName(CLINIC_NAME)).thenReturn(Optional.of(persistent));
		//when
		final Clinic saved = testObj.persist(payload, false, userMock);
		//then
		assertNotNull(saved);
		assertEquals((Long)CLINIC_ID, saved.getId());
		assertEquals(CLINIC_NAME, saved.getDisplayName());
		assertEquals(upperCase(CLINIC_NAME), saved.getSearchName());
		assertEquals(OLD_CLINIC_DESCRIPTION, saved.getDescription());
		assertEquals(OLD_CLINIC_TYPE, saved.getClinicType());
		assertEquals(OLD_MEDICAL_SERVICES, saved.getServices());
		assertEquals(otherUserMock, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenNoIdSpecifiedAndSomeClinicByUniqueKeyFoundAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		payload.setId(null);
		when(repositoryMock.findByName(CLINIC_NAME)).thenReturn(Optional.of(persistent));
		//when
		final Clinic saved = testObj.persist(payload, true, userMock);
		//then
		assertNotNull(saved);
		assertEquals((Long)CLINIC_ID, saved.getId());
		assertEquals(CLINIC_NAME, saved.getDisplayName());
		assertEquals(upperCase(CLINIC_NAME), saved.getSearchName());
		assertEquals(CLINIC_DESCRIPTION, saved.getDescription());
		assertEquals(CLINIC_TYPE, saved.getClinicType());
		assertEquals(MEDICAL_SERVICES_UPDATE, saved.getServices());
		assertEquals(userMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndClinicIsFoundByIdAndMergeIsOff() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repositoryMock.findById(CLINIC_ID)).thenReturn(Optional.of(persistent));
		//when
		final Clinic saved = testObj.persist(payload, false, userMock);
		//then
		assertNotNull(saved);
		assertEquals((Long)CLINIC_ID, saved.getId());
		assertEquals(CLINIC_NAME, saved.getDisplayName());
		assertEquals(upperCase(CLINIC_NAME), saved.getSearchName());
		assertEquals(OLD_CLINIC_DESCRIPTION, saved.getDescription());
		assertEquals(OLD_CLINIC_TYPE, saved.getClinicType());
		assertEquals(OLD_MEDICAL_SERVICES, saved.getServices());
		assertEquals(otherUserMock, saved.getModifiedBy());
		assertFalse(before.before(saved.getModificationTime()));
	}

	@Test
	public void testPersistWhenIdSpecifiedAndClinicIsFoundByIdAndMergeIsOn() throws Exception {
		//given
		Date before = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		when(repositoryMock.findById(CLINIC_ID)).thenReturn(Optional.of(persistent));
		//when
		final Clinic saved = testObj.persist(payload, true, userMock);
		//then
		assertNotNull(saved);
		assertEquals((Long)CLINIC_ID, saved.getId());
		assertEquals(CLINIC_NAME, saved.getDisplayName());
		assertEquals(upperCase(CLINIC_NAME), saved.getSearchName());
		assertEquals(CLINIC_DESCRIPTION, saved.getDescription());
		assertEquals(CLINIC_TYPE, saved.getClinicType());
		assertEquals(MEDICAL_SERVICES_UPDATE, saved.getServices());
		assertEquals(userMock, saved.getModifiedBy());
		assertTrue(String.format("%s should be before %s", before, saved.getModificationTime()), before.before(saved.getModificationTime()));
	}
}