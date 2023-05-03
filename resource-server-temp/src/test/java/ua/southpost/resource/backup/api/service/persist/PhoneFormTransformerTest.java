/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test transforming phone number from display format to search one.
 * Created by mchys on 17.03.2018.
 */
public class PhoneFormTransformerTest {
	@Test
	public void validFormat() {
		assertTrue(PhoneForm.validFormat("+38(057)867-76-61"));
	}

	@Test
	public void testSearchFromDisplay() {
		assertEquals("+380784510917", PhoneForm.searchFromDisplay("+38(078)451-09-17"));
		assertEquals("+380784519517", PhoneForm.searchFromDisplay("+38(078)451-95-17"));
	}

	@Test
	public void testSearchFromDisplayAsValid() {
		assertEquals("+380784510917", PhoneForm.searchFromValid("+38(078)451-09-17"));
		assertEquals("+380784519517", PhoneForm.searchFromValid("+38(078)451-95-17"));
	}


	@Test
	public void testSearchFromValid() {
		assertEquals("+380784510917", PhoneForm.searchFromValid("+38 078 451 0917"));
		assertEquals("+380784519517", PhoneForm.searchFromValid("+38078 451 9517"));
	}

	@Test
	public void testDisplayFromValid() {
		assertEquals("+38(078)451-09-17", PhoneForm.displayFromValid("+38 078 451 0917"));
		assertEquals("+38(078)451-95-17", PhoneForm.displayFromValid("+38078 451 9517"));
		assertEquals("+38(078)451-95-18", PhoneForm.displayFromValid("+38 (078) 451 - 9518"));
	}
}