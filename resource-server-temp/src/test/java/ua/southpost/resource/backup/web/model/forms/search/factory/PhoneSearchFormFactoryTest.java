package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.PhoneSearchForm;
import org.junit.Before;

public class PhoneSearchFormFactoryTest extends SearchFormFactoryTest<PhoneSearchFormFactory, PhoneSearchForm> {
	@Before
	public void setUp() {
		factory = new PhoneSearchFormFactory();
	}
}