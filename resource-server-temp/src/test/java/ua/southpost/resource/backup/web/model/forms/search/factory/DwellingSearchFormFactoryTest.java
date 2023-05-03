package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.DwellingSearchForm;
import org.junit.Before;

public class DwellingSearchFormFactoryTest extends SearchFormFactoryTest<DwellingSearchFormFactory, DwellingSearchForm> {
	@Before
	public void setUp() {
		factory = new DwellingSearchFormFactory();
	}
}