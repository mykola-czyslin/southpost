package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.model.search.PhoneSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhoneManagementEndpointTest {
	private static final Locale LOCALE = Locale.getDefault();
	private static final long PHONE_ID = 1000L;
	
	@InjectMocks
	private PhoneManagementEndpoint testObj = new PhoneManagementEndpoint();

	@Mock
	private EntityLookupService<PhoneSearchRequestPayload, PhoneInfo, Long> lookupService;
	@Mock
	private EntityManagementRequestHandler<Long, PhoneInfo, PhonePayload> entityManagementRequestHandler;


	@Mock
	private PhoneSearchRequestPayload searchPayloadMock;


	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private AbstractSubmitRequest<PhonePayload, Long> submitRequestMock;
	@Mock
	private BindingResult bindingResultMock;


	@Test
	public void lookup() {
		//when
		testObj.lookup(searchPayloadMock, LOCALE);
		//then
		verify(lookupService).lookup(searchPayloadMock, LOCALE);
	}

	@Test
	public void save() {
		//when
		testObj.save(httpServletRequestMock, submitRequestMock, bindingResultMock);
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
		testObj.remove(httpServletRequestMock, PHONE_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, PHONE_ID);
	}
}