package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.backup.api.model.search.LocationSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.AbstractSubmitRequest;
import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.commons.service.EntityLookupService;
import ua.southpost.resource.commons.service.EntityManagementRequestHandler;
import ua.southpost.resource.backup.data.repo.LocationRepository;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocationManagementEndpointTest {
	private static final Locale LOCALE = Locale.getDefault();
	private static final int PAGE_NUM = 1;
	private static final int PAGE_SIZE = 5;

	@InjectMocks
	private LocationManagementEndpoint testObj = new LocationManagementEndpoint();

	@Mock
	private EntityLookupService<LocationSearchRequestPayload, LocationInfo, Long> lookupService;
	@Mock
	private EntityManagementRequestHandler<Long, LocationInfo, LocationPayload> entityManagementRequestHandler;


	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private BindingResult bindingResultMock;
	@Mock
	private LocationSearchRequestPayload searchPayloadMock;
	@Mock
	private AbstractSubmitRequest<LocationPayload, Long> submitRequestMock;


	@Test
	public void lookup() {
		//given
		final PageRequest pageable = PageRequest.of(PAGE_NUM - 1, PAGE_SIZE, LocationRepository.DEFAULT_SORT);
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
		testObj.remove(httpServletRequestMock, TestDataUtil.LOCATION_ID);
		//then
		verify(entityManagementRequestHandler).handleRemoveRequest(httpServletRequestMock, TestDataUtil.LOCATION_ID);
	}
}