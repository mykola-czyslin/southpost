package ua.southpost.resource.backup.api.service;

import com.google.common.collect.Lists;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.commons.model.dto.AbstractSubmitResponse;
import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.commons.model.SubmitActionType;
import ua.southpost.resource.backup.api.service.persist.EntityPayloadPersistenceManager;
import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.service.history.EntityHistoryService;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenericEntityManagementRequestHandlerTest {
	private static final Locale LOCALE = Locale.getDefault();
	private static final String OBJECT_NAME = "object.name";
	private static final String FIELD_NAME = "field.name";
	private static final String OBJECT_ERROR_MESSAGE_KEY = "object.error.message.key";
	private static final String FIELD_ERROR_MESSAGE_KEY = "field.error.message.key";
	private static final Object[] OBJECT_ERROR_ARGUMENTS = {1L, "kg"};
	private static final Object[] FIELD_ERROR_ARGUMENTS = {"$0197%^&dc$"};
	private static final String OBJECT_ERROR_MESSAGE = "Object error occurred! +++++ !!!! *****";
	private static final String FIELD_ERROR_MESSAGE = "Wrong field value!!! A-a-a-a!!!";
	private static final String LOGIN = "Login";
	private static final SubmitActionType SUBMIT_ACTION = SubmitActionType.UPDATE;
	private static final Long ENTITY_ID = 10001L;

	@InjectMocks
	private GenericEntityManagementRequestHandler<Object, EntityInfo<Object>, EntityPayload<Object>, Identity<Object>> testObj;

	@Mock
	private EntityPayloadPersistenceManager<Object, Identity<Object>, EntityPayload<Object>> persistenceManager;
	@Mock
	private LocalizedEntityMapper<Identity<Object>, EntityInfo<Object>, Object> entityInfoMapper;
	@Mock
	private UserRepository userRepository;
	@Mock
	private MessageSource messageSource;
	@Mock
	private EntityHistoryService entityHistoryService;

	@Mock
	private HttpServletRequest servletRequest;
	@Mock
	private Principal principalMock;
	@Mock
	private User currentUserMock;
	@Mock
	private ObjectError objectErrorMock;
	@Mock
	private FieldError fieldErrorMock;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private Identity<Object> entityMock;
	@Mock
	private EntityInfo<Object> entityInfoMock;
	@Mock
	private AbstractSubmitRequest<EntityPayload<Object>, Object> submitRequestPayload;
	@Mock
	private AbstractSubmitResponse<Object, EntityInfo<Object>> submitResponsePayload;
	@Mock
	private EntityPayload<Object> mockEntityPayload;

	@Before
	public void setUp() {
		testObj = new GenericEntityManagementRequestHandler<>(
				persistenceManager,
				entityInfoMapper,
				userRepository,
				messageSource,
				entityHistoryService
		);
		when(servletRequest.getLocale()).thenReturn(LOCALE);
		when(servletRequest.getUserPrincipal()).thenReturn(principalMock);
		when(principalMock.getName()).thenReturn(LOGIN);
		when(userRepository.findByLogin(LOGIN)).thenReturn(Optional.of(currentUserMock));
		when(objectErrorMock.getObjectName()).thenReturn(OBJECT_NAME);
		when(fieldErrorMock.getField()).thenReturn(FIELD_NAME);
		when(objectErrorMock.getDefaultMessage()).thenReturn(OBJECT_ERROR_MESSAGE_KEY);
		when(fieldErrorMock.getDefaultMessage()).thenReturn(FIELD_ERROR_MESSAGE_KEY);
		when(objectErrorMock.getArguments()).thenReturn(OBJECT_ERROR_ARGUMENTS);
		when(fieldErrorMock.getArguments()).thenReturn(FIELD_ERROR_ARGUMENTS);
		when(messageSource.getMessage(OBJECT_ERROR_MESSAGE_KEY, OBJECT_ERROR_ARGUMENTS, LOCALE))
				.thenReturn(OBJECT_ERROR_MESSAGE);
		when(messageSource.getMessage(FIELD_ERROR_MESSAGE_KEY, FIELD_ERROR_ARGUMENTS, LOCALE))
				.thenReturn(FIELD_ERROR_MESSAGE);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(persistenceManager.remove(ENTITY_ID)).thenReturn(entityMock);
	}

	@Test
	public void testHandleSubmitRequestWhenBindingResultHasErrors() {
		//given
		when(bindingResult.hasErrors()).thenReturn(true);
		when(bindingResult.getAllErrors()).thenReturn(Lists.newArrayList(objectErrorMock, fieldErrorMock));
		//when
		final AbstractSubmitResponse<Object, EntityInfo<Object>> responsePayload = testObj.handleSubmitRequest(servletRequest, submitRequestPayload, bindingResult);
		//then
		assertNotNull( responsePayload);
		assertTrue(matchErrors(responsePayload.getValidationErrors()));
	}

	@Test(expected = ResponseStatusException.class)
	public void testProcessEntitySubmitWhenThereIsNoBindingResultErrorsButThereIsNoPrincipalAvailable() {
		//given
		when(servletRequest.getUserPrincipal()).thenReturn(null);
		//when
		testObj.handleSubmitRequest(servletRequest, submitRequestPayload, bindingResult);
	}

	@Test(expected = ResponseStatusException.class)
	public void testProcessEntitySubmitWhenNoBindingErrorsAndPrincipalAvailableButLoginSearchFails() {
		//given
		when(userRepository.findByLogin(LOGIN)).thenReturn(Optional.empty());
		//when
		testObj.handleSubmitRequest(servletRequest, submitRequestPayload, bindingResult);
	}

	@Test
	public void testProcessEntitySubmitHappyPathScenario() {
		//given
		when(submitRequestPayload.getAction()).thenReturn(SUBMIT_ACTION);
		when(submitRequestPayload.getData()).thenReturn(mockEntityPayload);
		when(persistenceManager.persist(eq(mockEntityPayload), eq(SUBMIT_ACTION.isMerging()), any()))
				.thenReturn(entityMock);
		when(entityInfoMapper.map(entityMock, LOCALE)).thenReturn(entityInfoMock);
		//when
		final AbstractSubmitResponse<Object, EntityInfo<Object>> responsePayload = testObj.handleSubmitRequest(servletRequest, submitRequestPayload, bindingResult);
		//then
		assertNotNull(responsePayload);
		assertEquals(entityInfoMock, responsePayload.getEntityInfo());
		verify(entityHistoryService).add(entityMock, SUBMIT_ACTION.getChangeType(), currentUserMock);
		verify(entityInfoMapper).map(entityMock, LOCALE);
	}

	@Test(expected = ResponseStatusException.class)
	public void testRemoveEntityWhenThereIsNoPrincipalAvailable() {
		//given
		when(servletRequest.getUserPrincipal()).thenReturn(null);
		//when
		testObj.handleRemoveRequest(servletRequest, ENTITY_ID);
	}

	@Test(expected = ResponseStatusException.class)
	public void testRemoveEntityWhenPrincipalAvailableButLoginSearchFails() {
		//given
		when(userRepository.findByLogin(LOGIN)).thenReturn(Optional.empty());
		//when
		testObj.handleRemoveRequest(servletRequest, ENTITY_ID);
	}

	@Test(expected = NotFoundException.class)
	public void testRemoveEntityWhenEntityNotFound() {
		//given
		doThrow(new NotFoundException(NotFoundException.ERR_ENTITY_NOT_FOUND_BY_ID, NotFoundException.ERR_ENTITY_NOT_FOUND_BY_ID)).when(persistenceManager).remove(ENTITY_ID);
		//when
		testObj.handleRemoveRequest(servletRequest, ENTITY_ID);
	}

	@Test
	public void testRemoveEntityHappyPathScenario() {
		//when
		testObj.handleRemoveRequest(servletRequest, ENTITY_ID);
		//then
		verify(entityHistoryService).add(entityMock, ChangeType.DELETE, currentUserMock);
	}

	private boolean matchErrors(List<ValidationError> argument) {
		return argument.stream().anyMatch(e -> OBJECT_NAME.equals(e.getCausedBy()) && OBJECT_ERROR_MESSAGE.equals(e.getMessage()))
				&& argument.stream().anyMatch(e -> FIELD_NAME.equals(e.getCausedBy()) && FIELD_ERROR_MESSAGE.equals(e.getMessage()));
	}

}