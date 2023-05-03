package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.UserSearchForm;
import org.junit.Before;

public class UserSearchFormFactoryTest extends SearchFormFactoryTest<UserSearchFormFactory, UserSearchForm> {
	@Before
	public void setUp() {
		factory = new UserSearchFormFactory();
	}
}