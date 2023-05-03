package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.search.DwellingSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.DwellingPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DwellingManagementEndpointTest {
	@InjectMocks
	private DwellingManagementEndpoint testObj = new DwellingManagementEndpoint();

	@Mock
	private EntityManagementRequestHandler<Long, DwellingInfo, DwellingPayload> entityManagementRequestHandler;
	@Mock
	private EntityLookupService<DwellingSearchRequestPayload, DwellingInfo, Long> lookupService;

	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private BindingResult bindingResultMock;
	@Mock
	private AbstractSubmitRequest<DwellingPayload, Long> submitRequestMock;


	@Test
	public void submit() {
		//when
		testObj.submit(httpServletRequestMock, submitRequestMock, bindingResultMock);
		//then
		verify(entityManagementRequestHandler)
				.handleSubmitRequest(
						httpServletRequestMock,
						submitRequestMock,
						bindingResultMock
				);

	}

	@Test
	public void remove() {
		//when
		testObj.remove(httpServletRequestMock, TestDataUtil.DWELLING_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, TestDataUtil.DWELLING_ID);
	}
}