package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.search.ClinicSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.ClinicPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClinicManagementEndpointTest {
	private static final Locale LOCALE =  Locale.getDefault();

	@InjectMocks
	private ClinicManagementEndpoint testObj = new ClinicManagementEndpoint();

	@Mock
	private EntityManagementRequestHandler<Long, ClinicInfo, ClinicPayload> entityManagementRequestHandler;
	@Mock
	private EntityLookupService<ClinicSearchRequestPayload, ClinicInfo, Long> lookupService;


	@Mock
	private ClinicSearchRequestPayload searchPayloadMock;
	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private AbstractSubmitRequest<ClinicPayload, Long> submitRequest;
	@Mock
	private BindingResult bindingResult;


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
		testObj.save(httpServletRequestMock, submitRequest, bindingResult);
		//then
		verify(entityManagementRequestHandler).handleSubmitRequest(
				eq(httpServletRequestMock),
				eq(submitRequest),
				eq(bindingResult)
		);
	}

	@Test
	public void remove() {
		//when
		testObj.remove(httpServletRequestMock, TestDataUtil.CLINIC_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, TestDataUtil.CLINIC_ID);
	}
}