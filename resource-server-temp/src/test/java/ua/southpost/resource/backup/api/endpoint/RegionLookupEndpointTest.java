package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.endpoint.RegionLookupEndpoint;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.repo.DistrictRepository;
import ua.southpost.resource.backup.web.model.dto.address.SelectOptionInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegionLookupEndpointTest {
	private static final String REGION_ID = "000-REG00";
	private static final int DISTRICT_COUNT = 5;
	private static final String DISPLAY_NAME_FORMAT = "District #%d";
	private static long ID_BASE = 1000L;

	@InjectMocks
	private RegionLookupEndpoint testObj = new RegionLookupEndpoint();
	@Mock
	private DistrictRepository repository;

	@Before
	public void setUp() {
		List<District> districts = IntStream.range(1, 1 + DISTRICT_COUNT)
			.mapToObj(this::buildDistrict)
			.collect(Collectors.toList());
		when(repository.list(REGION_ID)).thenReturn(districts);
	}

	@Test
	public void regionDistricts() {
		//when
		final List<SelectOptionInfo<Long>> selectOptionInfos = testObj.regionDistricts(REGION_ID);
		//then
		assertNotNull(selectOptionInfos);
		assertEquals(DISTRICT_COUNT, selectOptionInfos.size());
		selectOptionInfos.forEach(oi -> assertEquals(String.format(DISPLAY_NAME_FORMAT, oi.getId() - ID_BASE), oi.getName()));
	}

	private District buildDistrict(int index) {
		District district = new District();
		district.setId(ID_BASE + index);
		final String displayName = String.format(DISPLAY_NAME_FORMAT, index);
		district.setDisplayName(displayName);
		district.setSearchValue(displayName);
		return district;
	}
}