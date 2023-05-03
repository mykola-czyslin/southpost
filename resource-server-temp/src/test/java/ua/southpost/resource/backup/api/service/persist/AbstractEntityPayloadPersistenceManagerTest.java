package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.service.persist.AbstractEntityPayloadPersistenceManager;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.service.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractEntityPayloadPersistenceManagerTest {

	private static final Object PERSISTENT_ID = "PersistentId";
	private static final Object ABSENT_ID = new Object[]{"absent"};

	@Spy
	private AbstractEntityPayloadPersistenceManager<Object, Identity<Object>, EntityPayload<Object>, CrudRepository<Identity<Object>, Object>> testObj;

	@Mock
	private CrudRepository<Identity<Object>, Object> repositoryMock;
	@Mock
	private Identity<Object> newEntityMock;
	@Mock
	private Identity<Object> persistentEntityMock;
	@Mock
	private EntityPayload<Object> payloadMock;
	@Mock
	private User modifiedBy;

	private AtomicLong currentId = new AtomicLong(0L);

	@Before
	public void setUp() {
		when(testObj.getRepository()).thenReturn(repositoryMock);
		when(testObj.createEntityInstance()).thenReturn(newEntityMock);
		//noinspection unchecked
		when(testObj.merge(any(Identity.class), any(Identity.class))).then((Answer<Identity<Object>>) invocation -> invocation.getArgument(1, Identity.class));
		//noinspection unchecked
		doNothing().when(testObj).populateEntity(any(EntityPayload.class), any(Identity.class), anyBoolean(), any(User.class));
		//noinspection unchecked
		when(repositoryMock.save(any(Identity.class))).then((Answer<Identity<Object>>) this::invokeSave);
		when(persistentEntityMock.getId()).thenReturn(PERSISTENT_ID);
		when(repositoryMock.findById(PERSISTENT_ID)).thenReturn(Optional.of(persistentEntityMock));
	}

	@Test
	public void persistWhenIdUndefinedAndNoEntityByUniqueFound() {
		when(testObj.findUnique(newEntityMock)).thenReturn(Optional.empty());
		//when
		final Identity<Object> persisted = testObj.persist(payloadMock, false, modifiedBy);
		//then
		assertNotNull(persisted);
		assertEquals(newEntityMock, persisted);
		verify(repositoryMock).save(newEntityMock);
		verify(newEntityMock).setId(currentId.get());
		//noinspection unchecked
		verify(testObj, never()).merge(any(Identity.class), any(Identity.class));
	}

	@Test
	public void persistWhenIdUndefinedAndSomeEntityByUniqueFoundAndMergeOff() {
		when(testObj.findUnique(newEntityMock)).thenReturn(Optional.of(persistentEntityMock));
		//when
		final Identity<Object> persisted = testObj.persist(payloadMock, false, modifiedBy);
		//then
		assertNotNull(persisted);
		assertEquals(PERSISTENT_ID, persisted.getId());
		assertEquals(persistentEntityMock, persisted);
		verify(repositoryMock, never()).save(newEntityMock);
		verify(repositoryMock, never()).save(persistentEntityMock);
		verify(persistentEntityMock, never()).setId(any());
		//noinspection unchecked
		verify(testObj, never()).merge(any(Identity.class), any(Identity.class));
	}

	@Test
	public void persistWhenIdUndefinedAndSomeEntityByUniqueFoundAndMergeOn() {
		when(testObj.findUnique(newEntityMock)).thenReturn(Optional.of(persistentEntityMock));
		//when
		final Identity<Object> persisted = testObj.persist(payloadMock, true, modifiedBy);
		//then
		assertNotNull(persisted);
		assertEquals(PERSISTENT_ID, persisted.getId());
		assertEquals(persistentEntityMock, persisted);
		verify(repositoryMock, never()).save(newEntityMock);
		verify(repositoryMock).save(persistentEntityMock);
		verify(persistentEntityMock, never()).setId(any());
		verify(testObj).merge(newEntityMock, persistentEntityMock);
	}

	@Test
	public void persistWhenEntityFoundByIdAndMergeIsOff() {
		//given
		when(payloadMock.getId()).thenReturn(PERSISTENT_ID);
		//when
		final Identity<Object> persisted = testObj.persist(payloadMock, false, modifiedBy);
		//then
		//noinspection unchecked
		verify(testObj, never()).findUnique(any(Identity.class));
		assertNotNull(persisted);
		assertEquals(PERSISTENT_ID, persisted.getId());
		assertEquals(persistentEntityMock, persisted);
		verify(repositoryMock, never()).save(newEntityMock);
		verify(repositoryMock, never()).save(persistentEntityMock);
		verify(persistentEntityMock, never()).setId(any());
		//noinspection unchecked
		verify(testObj, never()).merge(any(Identity.class), any(Identity.class));
	}

	@Test
	public void persistWhenEntityFoundByIdAndMergeIsOn() {
		//given
		when(payloadMock.getId()).thenReturn(PERSISTENT_ID);
		//when
		final Identity<Object> persisted = testObj.persist(payloadMock, true, modifiedBy);
		//then
		//noinspection unchecked
		verify(testObj, never()).findUnique(any(Identity.class));
		assertNotNull(persisted);
		assertEquals(PERSISTENT_ID, persisted.getId());
		assertEquals(persistentEntityMock, persisted);
		verify(repositoryMock, never()).save(newEntityMock);
		verify(repositoryMock).save(persistentEntityMock);
		verify(persistentEntityMock, never()).setId(any());
		verify(testObj).merge(newEntityMock, persistentEntityMock);
	}

	@Test(expected = NotFoundException.class)
	public void removeAbsent() {
		//given
		when(repositoryMock.findById(ABSENT_ID)).thenReturn(Optional.empty());
		//when
		testObj.remove(ABSENT_ID);
	}

	@Test
	public void removePresent() {
		//when
		Identity<Object> removed = testObj.remove(PERSISTENT_ID);
		//then
		assertEquals(persistentEntityMock, removed);
		verify(repositoryMock).delete(persistentEntityMock);
	}

	private Identity<Object> invokeSave(InvocationOnMock invocation) {
		@SuppressWarnings("unchecked") final Identity<Object> identity = invocation.getArgument(0, Identity.class);
		if (identity.getId() == null) {
			identity.setId(currentId.incrementAndGet());
		}
		return identity;
	}
}