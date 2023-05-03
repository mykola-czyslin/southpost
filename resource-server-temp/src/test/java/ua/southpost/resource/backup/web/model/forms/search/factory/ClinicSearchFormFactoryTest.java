package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.ClinicSearchForm;
import org.junit.Before;

public class ClinicSearchFormFactoryTest extends SearchFormFactoryTest<ClinicSearchFormFactory, ClinicSearchForm> {
	@Before
	public void setUp() {
		factory = new ClinicSearchFormFactory();
	}
}