package ua.southpost.resource.backup.web.utils;

import org.junit.Test;

import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;
import static org.junit.Assert.*;

/**
 * Perform tests over {@link DataUtils} library methods.
 * Created by mchys on 28.12.2018.
 */
public class DataUtilsTest {
	@Test
	public void testPrepareOptionalPatternOrNullWhenEmpty() {
		assertEquals("Харк%", prepareOptionalPatternOrNullWhenEmpty("Харк*"));
	}

	@Test
	public void testSqlSpecialCharsAreEscaped() {
		assertEquals("100\\% GO\\_TO C:\\\\\\\\DATA", prepareOptionalPatternOrNullWhenEmpty("100% GO_TO C:\\\\DATA"));

	}
}