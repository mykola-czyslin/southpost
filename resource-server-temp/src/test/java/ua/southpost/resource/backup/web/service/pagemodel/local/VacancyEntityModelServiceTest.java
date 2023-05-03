package ua.southpost.resource.backup.web.service.pagemodel.local;

import com.google.common.collect.ImmutableMap;
import ua.southpost.resource.backup.web.controller.util.RegionDistrictOptionsUtil;
import ua.southpost.resource.backup.web.model.SubmitType;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import ua.southpost.resource.backup.web.model.forms.entity.VacancyForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.page.VacancyEntityViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.southpost.resource.backup.web.service.RegionAndDistrictOptionsContainer;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VacancyEntityModelServiceTest {

	private static final Locale NATIVE_LOCALE = new Locale("uk", "UA");
	private static final long VACANCY_ID = 1000L;
	private static final String REGION_ID = "TEST REGION";
	private static final String REGION_VALUE = "Test region";
	private static final String OTHER_REGION_ID = "OTHER";
	private static final String OTHER_REGION_VALUE = "Other region";
	private static final ImmutableMap<String, String> REGION_OPTIONS = ImmutableMap.of(REGION_ID, REGION_VALUE, OTHER_REGION_ID, OTHER_REGION_VALUE);
	private static final long MOCK_VACANCY_ID =  -1L;
	private static final ImmutableMap<String, String> REGION_DISTRICT_OPTIONS = ImmutableMap.of("-1", "region", "1", "District#1");
	private static final Map<String, String> NO_REGION_DISTRICT_OPTIONS = Collections.emptyMap();


	@InjectMocks
	private VacancyEntityModelService testObj = new VacancyEntityModelService();
	@Mock
	private EntityFormFactory<Long, VacancyInfo, VacancyForm> formFactoryMock;
	@Mock
	private RegionDistrictOptionsUtil regionDistrictOptionsUtilMock;
	@Mock
	private VacancyForm vacancyFormMock;

	@Before
	public void setUp() {
		when(formFactoryMock.createNewFormInstance()).thenReturn(new VacancyForm());
		when(formFactoryMock.formByEntityId(VACANCY_ID, NATIVE_LOCALE)).thenReturn(vacancyFormMock);
		when(vacancyFormMock.regionId()).thenReturn(REGION_ID);
		when(regionDistrictOptionsUtilMock.regionOptions()).thenReturn(REGION_OPTIONS);
		when(regionDistrictOptionsUtilMock.districtOptions(REGION_ID)).thenReturn(REGION_DISTRICT_OPTIONS);
		when(regionDistrictOptionsUtilMock.districtOptions(RegionAndDistrictOptionsContainer.MOCK_REGION_ID)).thenReturn(NO_REGION_DISTRICT_OPTIONS);
	}

	@Test
	public void testPrepareModelForCreateWillReturnModelObjectWithSubmitTypeSetWithCreateAndNewClinicForm() {
		final VacancyEntityViewModel viewModel = testObj.prepareModel(SubmitType.CREATE, MOCK_VACANCY_ID, NATIVE_LOCALE);

		assertNotNull(viewModel);
		assertNotNull(viewModel.getEntityForm());
		assertNotSame(vacancyFormMock, viewModel.getEntityForm());
		assertEquals(REGION_OPTIONS, viewModel.getRegionOptions());
		assertNotNull(viewModel.getDistrictOptions());
		assertTrue(viewModel.getDistrictOptions().isEmpty());
	}

	@Test
	public void testPrepareModeForUpdateWillReturnModelObjectWithSubmitTypeSetWithUpdateAndFormReturnedByTransformer() {
		final VacancyEntityViewModel viewModel = testObj.prepareModel(SubmitType.UPDATE, VACANCY_ID, NATIVE_LOCALE);

		assertNotNull(viewModel);
		assertNotNull(viewModel.getEntityForm());
		assertSame(vacancyFormMock, viewModel.getEntityForm());
		assertEquals(REGION_OPTIONS, viewModel.getRegionOptions());
		assertNotNull(viewModel.getDistrictOptions());
		assertEquals(REGION_DISTRICT_OPTIONS, viewModel.getDistrictOptions());
	}

	@Test
	public void testPrepareModeForUpdateWillReturnModelObjectWithSubmitTypeSetWithViewAndFormReturnedByTransformer() {
		final VacancyEntityViewModel viewModel = testObj.prepareModel(SubmitType.VIEW, VACANCY_ID, NATIVE_LOCALE);

		assertNotNull(viewModel);
		assertNotNull(viewModel.getEntityForm());
		assertSame(vacancyFormMock, viewModel.getEntityForm());
		assertEquals(REGION_OPTIONS, viewModel.getRegionOptions());
		assertNotNull(viewModel.getDistrictOptions());
		assertEquals(REGION_DISTRICT_OPTIONS, viewModel.getDistrictOptions());
	}

}