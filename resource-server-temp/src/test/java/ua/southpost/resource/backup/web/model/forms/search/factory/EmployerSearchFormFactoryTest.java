package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.EmployerSearchForm;
import org.junit.Before;

public class EmployerSearchFormFactoryTest extends SearchFormFactoryTest<EmployerSearchFormFactory, EmployerSearchForm> {
	@Before
	public void setUp() {
		factory = new EmployerSearchFormFactory();
	}
}