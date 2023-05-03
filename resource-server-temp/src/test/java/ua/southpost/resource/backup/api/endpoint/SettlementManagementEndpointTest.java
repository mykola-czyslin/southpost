package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
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
public class SettlementManagementEndpointTest {
	private static final Locale LOCALE = Locale.getDefault();

	@InjectMocks
	private SettlementManagementEndpoint testObj = new SettlementManagementEndpoint();
	@Mock
	private EntityLookupService<SettlementSearchRequestPayload, SettlementInfo, Long> lookupService;
	@Mock
	private EntityManagementRequestHandler<Long, SettlementInfo, SettlementPayload> entityManagementRequestHandler;

	@Mock
	private SettlementSearchRequestPayload searchPayloadMock;


	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private AbstractSubmitRequest<SettlementPayload, Long> submitRequestMock;
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
		testObj.remove(httpServletRequestMock, TestDataUtil.SETTLEMENT_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, TestDataUtil.SETTLEMENT_ID);
	}
}