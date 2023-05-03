package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.search.EmployerLookupRequest;
import ua.southpost.resource.backup.api.model.search.EmployerSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployerManagementEndpointTest {
	private static final Locale LOCALE = Locale.getDefault();

	@InjectMocks
	private EmployerManagementEndpoint testObj = new EmployerManagementEndpoint();
	@Mock
	private EntityLookupService<EmployerSearchRequestPayload, EmployerInfo, Long> lookupService;
	@Mock
	private EntityManagementRequestHandler<Long, EmployerInfo, EmployerPayload> entityManagementRequestHandler;

	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private BindingResult bindingResultMock;
	@Mock
	private EmployerLookupRequest lookupRequestMock;
	@Mock
	private AbstractSubmitRequest<EmployerPayload, Long> submitRequestMock;


	@Test
	public void lookup() {
		//given
		EmployerSearchRequestPayload payload = new EmployerSearchRequestPayload();
		when(lookupRequestMock.toSearchPayload()).thenReturn(payload);
		//when
		testObj.lookup(lookupRequestMock, LOCALE);
		//then
		verify(lookupRequestMock).toSearchPayload();
		verify(lookupService).lookup(payload, LOCALE);
	}

	@Test
	public void submit() {
		//when
		testObj.submit(httpServletRequestMock, submitRequestMock, bindingResultMock);
		//then
		verify(entityManagementRequestHandler).handleSubmitRequest(
				httpServletRequestMock,
				submitRequestMock,
				bindingResultMock
		);
	}

	@Test
	public void remove() {
		//when
		testObj.remove(httpServletRequestMock, TestDataUtil.EMPLOYER_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, TestDataUtil.EMPLOYER_ID);
	}
}