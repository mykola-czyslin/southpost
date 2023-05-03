package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm;
import org.junit.Before;

public class VacancySearchFormFactoryTest extends SearchFormFactoryTest<VacancySearchFormFactory, VacancySearchForm> {

	@Before
	public void setUp() {
		factory = new VacancySearchFormFactory();
	}
}