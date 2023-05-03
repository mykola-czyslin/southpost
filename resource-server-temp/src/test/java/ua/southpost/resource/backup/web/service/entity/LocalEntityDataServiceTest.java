package ua.southpost.resource.backup.web.service.entity;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.data.repo.RegionRepository;
import ua.southpost.resource.backup.web.model.dto.address.RegionInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalEntityDataServiceTest {
	private static final String WRONG_REGION_ID = "WRONG-REGION";
	private static final String REGION_ID = "00-TEST-REGION";
	private static final Locale LOCALE = Locale.getDefault();


	private LocalEntityDataService<RegionInfo, Region, String> testObj;

	@Mock
	private RegionRepository repositoryMock;
	@Mock
	private LocalizedEntityMapper<Region, RegionInfo, String> entityInfoMapperMock;
	@Mock
	private Region regionMock;
	@Mock
	private RegionInfo regionInfoMock;

	@Before
	public void setUp() {
		 testObj = new LocalEntityDataService<>(repositoryMock, entityInfoMapperMock);
		 when(entityInfoMapperMock.map(regionMock, LOCALE)).thenReturn(regionInfoMock);
		 when(repositoryMock.findById(REGION_ID)).thenReturn(Optional.of(regionMock));
		 when(repositoryMock.findById(WRONG_REGION_ID)).thenReturn(Optional.empty());
	}

	@Test
	public void testWrongRegionIdScenario() {
		Optional<RegionInfo> result = testObj.byId(WRONG_REGION_ID, LOCALE);

		assertFalse(result.isPresent());

		verify(entityInfoMapperMock, never()).map(any(Region.class), any(Locale.class));
	}

	@Test
	public void testValidRegionIdScenario() {
		Optional<RegionInfo> result = testObj.byId(REGION_ID, LOCALE);

		assertTrue(result.isPresent());
		assertEquals(regionInfoMock, result.get());
		verify(entityInfoMapperMock).map(regionMock, LOCALE);
	}
}