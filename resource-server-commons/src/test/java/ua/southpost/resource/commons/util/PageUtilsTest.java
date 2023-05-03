package ua.southpost.resource.commons.util;

import org.junit.Test;
import ua.southpost.resource.commons.util.PageUtils;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("UnusedLabel")
public class PageUtilsTest {
	@Test
	public void testPageCountForNegativeWidthWithCurrentPageInsideInterval()  {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(81, 15, -10);
		then:
		assertEquals(3, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 15, 81}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testPageCountForNegativeWidthWithCurrentPageStarting() {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(81, 1, -10);
		then:
		assertEquals(3, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 2, 81}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testTwoPagesCase()  {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(2, 1, 0);
		then:
		assertEquals(2, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 2}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testSinglePageCase()  {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(1, 1, 1);
		then:
		assertEquals(0, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testPageCountForPositiveWidthExceedingBoundsWithCurrentPageInsideInterval() {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(11, 15, 16);
		then:
		assertEquals(11, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testPageCountForPositiveWidthExceedingBoundsWithCurrentPageStarting() {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(11, 1, 16);
		then:
		assertEquals(11, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testPageCountForPositiveWidthFitIntoBoundsWithCurrentPageInsideInterval() {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(81, 15, 8);
		then:
		assertEquals(8, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 12, 13, 14, 15, 16, 17, 81}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

	@Test
	public void testPageCountForWidthFitIntoBoundsWithCurrentPageStarting() {
		final int[] pages;
		when:
		pages = PageUtils.generatePageOptions(81, 1, 8);
		then:
		assertEquals(8, pages.length);
		System.out.println("Pages: " + Arrays.toString(pages));
		assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 81}, pages);
		Arrays.stream(pages).forEach(i -> assertTrue(i > 0));
	}

}