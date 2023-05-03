package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.EmailSearchForm;
import org.junit.Before;

public class EmailSearchFormFactoryTest extends SearchFormFactoryTest<EmailSearchFormFactory, EmailSearchForm> {
	@Before
	public void setUp() {
		factory = new EmailSearchFormFactory();
	}
}