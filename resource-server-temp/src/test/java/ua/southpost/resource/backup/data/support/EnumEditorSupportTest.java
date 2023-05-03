/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

/**
 * Tests {@link EnumEditorSupport} functionality.
 * Created by mchys on 21.04.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnumEditorSupportTest {
	private enum TestEnum {
		ITEM1, ITeM2, Item3
	}

	@Spy
	private EnumEditorSupport<TestEnum> testObj = new EnumEditorSupport<>(TestEnum.class);

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionThrownWhenNullIsPassedAsArgument() {
		EnumEditorSupport<?> support = null;
		try {
			support = new EnumEditorSupport<>(null);
		} finally {
			assertNull(support);
		}
	}

	@Test
	public void test_setAsText_exactly_ITEM1() {
		testObj.setAsText("ITEM1");
		verify(testObj).setValue(TestEnum.ITEM1);
	}

	@Test
	public void test_setAsText_exactly_ITeM2() {
		testObj.setAsText("ITeM2");
		verify(testObj).setValue(TestEnum.ITeM2);
	}

	@Test
	public void test_setAsText_lower_ITeM2() {
		testObj.setAsText("item2");
		verify(testObj).setValue(TestEnum.ITeM2);
	}

	@Test
	public void test_setAsText_upper_ITeM2() {
		testObj.setAsText("ITEM2");
		verify(testObj).setValue(TestEnum.ITeM2);
	}


	@Test
	public void test_setAsText_exactly_Item3() {
		testObj.setAsText("Item3");
		verify(testObj).setValue(TestEnum.Item3);
	}

	@Test
	public void test_setAsText_lower_Item3() {
		testObj.setAsText("item3");
		verify(testObj).setValue(TestEnum.Item3);
	}

	@Test
	public void test_setAsText_upper_Item3() {
		testObj.setAsText("ITEM3");
		verify(testObj).setValue(TestEnum.Item3);
	}
	@Test
	public void test_setAsText_lowecase_ITEM1() {
		testObj.setAsText("item1");
		verify(testObj).setValue(TestEnum.ITEM1);
	}

	@Test
	public void test_setAsText_withWrongValue() {
		testObj.setAsText("some wrong value");
		verify(testObj).setValue(null);
	}

	@Test
	public void test_setAsText_with_null() {
		testObj.setAsText(null);
		verify(testObj).setValue(null);
	}
}