package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.LocationSearchForm;
import org.junit.Before;

public class LocationSearchFormFactoryTest extends SearchFormFactoryTest<LocationSearchFormFactory, LocationSearchForm> {
	@Before
	public void setUp() {
		factory = new LocationSearchFormFactory();
	}
}