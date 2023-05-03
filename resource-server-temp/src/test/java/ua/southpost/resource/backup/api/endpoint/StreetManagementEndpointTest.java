package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.search.StreetSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
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
public class StreetManagementEndpointTest {
	private static final Locale LOCALE = Locale.getDefault();

	@InjectMocks
	private StreetManagementEndpoint testObj = new StreetManagementEndpoint();
	@Mock
	private EntityLookupService<StreetSearchRequestPayload, StreetInfo, Long> lookupService;
	@Mock
	private EntityManagementRequestHandler<Long, StreetInfo, StreetPayload> entityManagementRequestHandler;

	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private AbstractSubmitRequest<StreetPayload, Long> submitRequestMock;
	@Mock
	private BindingResult bindingResultMock;
	@Mock
	private StreetSearchRequestPayload searchPayloadMock;

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
		testObj.remove(httpServletRequestMock, TestDataUtil.STREET_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, TestDataUtil.STREET_ID);
	}

}