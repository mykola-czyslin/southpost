package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.StreetSearchForm;
import org.junit.Before;

public class StreetSearchFormFactoryTest extends SearchFormFactoryTest<StreetSearchFormFactory, StreetSearchForm> {

	@Before
	public void setUp() {
		factory = new StreetSearchFormFactory();
	}
}