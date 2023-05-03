package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm;
import org.junit.Before;

public class SettlementSearchFormFactoryTest extends SearchFormFactoryTest<SettlementSearchFormFactory, SettlementSearchForm> {
	@Before
	public void setUp() {
		factory = new SettlementSearchFormFactory();
	}
}