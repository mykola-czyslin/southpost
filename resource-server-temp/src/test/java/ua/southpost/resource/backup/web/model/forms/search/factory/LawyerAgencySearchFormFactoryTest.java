package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.LawyerAgencySearchForm;
import org.junit.Before;

public class LawyerAgencySearchFormFactoryTest extends SearchFormFactoryTest<LawyerAgencySearchFormFactory, LawyerAgencySearchForm> {
	@Before
	public void setUp() {
		factory = new LawyerAgencySearchFormFactory();
	}
}
