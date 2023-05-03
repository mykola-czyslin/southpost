package ua.southpost.resource.backup.web.controller.service;

import com.google.common.collect.Lists;
import ua.southpost.resource.commons.model.dto.EntityGridPage;
import ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo;
import ua.southpost.resource.commons.model.dto.SortOption;
import ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class PageSearchServiceTest {
	private static final Locale NATIVE_LOCALE = new Locale("uk", "UA");

	@Autowired
	private PageListService<VacancySearchForm, VacancyInfo, Long> vacancyPageListService;

	@Test
	public void testPreparePageDataWithoutSearchForm() {
		final ModelAndView modelAndView = vacancyPageListService.prepareListModelAndView(null, NATIVE_LOCALE);
		assertNotNull(modelAndView);
		final VacancySearchForm form = modelAndView.getModel().values().stream().filter(o -> o instanceof VacancySearchForm)
				.findFirst().map(VacancySearchForm.class::cast)
				.orElse(null);
		assertNotNull(form);
		@SuppressWarnings("unchecked")
		final EntityGridPage<VacancyInfo, Long> page = modelAndView.getModel().values()
				.stream().filter(o -> o instanceof EntityGridPage)
				.map(EntityGridPage.class::cast)
				.findFirst().orElse(null);
		assertNotNull(page);

	}

	@Test
	public void testPreparePageDataWithSearchForm() {
		final SortOption sortOption = new SortOption();
		sortOption.setDirection(Sort.Direction.DESC);
		sortOption.setFieldName("summary");
		final VacancySearchForm searchForm = new VacancySearchForm(1, 10, Lists.newArrayList(sortOption));
		searchForm.setSalaryLow(BigDecimal.ZERO);
		final ModelAndView modelAndView = vacancyPageListService.prepareListModelAndView(searchForm, NATIVE_LOCALE);
		assertNotNull(modelAndView);
		final VacancySearchForm form = modelAndView.getModel().values().stream().filter(o -> o instanceof VacancySearchForm)
				.findFirst().map(VacancySearchForm.class::cast)
				.orElse(null);
		assertNotNull(form);
		@SuppressWarnings("unchecked")
		final EntityGridPage<VacancyInfo, Long> page = modelAndView.getModel().values()
				.stream().filter(o -> o instanceof EntityGridPage)
				.map(EntityGridPage.class::cast)
				.findFirst().orElse(null);
		assertNotNull(page);

	}

}