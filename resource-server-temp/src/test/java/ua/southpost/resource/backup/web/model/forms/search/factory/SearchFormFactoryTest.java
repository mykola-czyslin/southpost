package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.PagedSearchForm;
import ua.southpost.resource.commons.model.dto.SortOption;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class SearchFormFactoryTest<FF extends SearchFormFactory<F>, F extends PagedSearchForm> {

	protected FF factory;

	@SuppressWarnings("UnusedLabel")
	@Test
	public void test_create_when_regular_grid_settings_passed() {
		EntityGridSettings gridSettings;
		given:
		gridSettings = new EntityGridSettings(10, Collections.singletonList(new SortOption("field1", Sort.Direction.ASC)));
		final F form;
		when:
		form = factory.create(gridSettings);

		thenAssertFormHasExpectedParameters(gridSettings, form);
	}

	@SuppressWarnings({"UnusedLabel", "ConstantConditions"})
	@Test(expected = NullPointerException.class)
	public void test_create_fails_when_null_passed_as_grid_settings() {
		when:
		factory.create(null);
	}

	private void thenAssertFormHasExpectedParameters(EntityGridSettings gridSettings, F form) {
		assertNotNull(form);
		assertEquals(1, form.getPageNum());
		assertEquals(gridSettings.getPageSize(), form.getLinesPerPage());
		assertEquals(gridSettings.getSortOptions(), form.getSortOptions());
	}
}