package ua.southpost.resource.backup.web.service.pagemodel;

import org.junit.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ModelMappingUtilTest {
	private static final String MODEL_ATTRIBUTE_1 = "testField#1";
	private static final String MODEL_ATTRIBUTE_2 = "testField#2";
	private static final String MODEL_ATTRIBUTE_3 = "testField#3";
	private static final String MODEL_ATTRIBUTE_ALIAS_1 = "alias#1";
	private static final String MODEL_ATTRIBUTE_ALIAS_2 = "alias#2";
	private static final String MODEL_ATTRIBUTE_ALIAS_3 = "alias#3";

	private static final String VALUE_1 = "value#1";
	private static final String VALUE_2 = "value#2";
	private static final String VALUE_3 = "value#3";
	private static final String VALUE_1_1 = "value#1.1";

	@Test
	public void transferDataToModel() {
		final TestA testA = new TestA(VALUE_1, VALUE_2);
		final ConcurrentModel model = new ConcurrentModel();

		ModelMappingUtil.transferDataToModel(testA, model);

		final Map<String, Object> map = model.asMap();

		assertEquals(2, map.size());
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_2));
	}

	@Test
	public void transferDataToModelOfDescendantInstance() {
		final TestB testB = new TestB(VALUE_1, VALUE_2, VALUE_3);
		final ConcurrentModel model = new ConcurrentModel();

		ModelMappingUtil.transferDataToModel(testB, model);

		final Map<String, Object> map = model.asMap();

		assertEquals(3, map.size());
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_2));
		assertEquals(VALUE_3, map.get(MODEL_ATTRIBUTE_3));
	}

	@Test
	public void transferDataToModelAndView() {
		final TestA testA = new TestA(VALUE_1, VALUE_2);
		final ModelAndView modelAndView = new ModelAndView();

		ModelMappingUtil.transferDataToModelAndView(testA, modelAndView);

		final Map<String, Object> map = modelAndView.getModel();

		assertEquals(2, map.size());
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_2));
	}

	@Test
	public void transferDataToModelAndViewOfDescendantClass() {
		final TestB testB = new TestB(VALUE_1, VALUE_2, VALUE_3);
		final ModelAndView modelAndView = new ModelAndView();

		ModelMappingUtil.transferDataToModelAndView(testB, modelAndView);

		final Map<String, Object> map = modelAndView.getModel();

		assertEquals(3, map.size());
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_2));
		assertEquals(VALUE_3, map.get(MODEL_ATTRIBUTE_3));
	}


	@Test
	public void transferDataToModelAndViewOfContainingClass() {
		final TestC testC = new TestC(VALUE_1, VALUE_2, VALUE_3);
		final ModelAndView modelAndView = new ModelAndView();

		ModelMappingUtil.transferDataToModelAndView(testC, modelAndView);

		final Map<String, Object> map = modelAndView.getModel();

		assertEquals(3, map.size());
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_2));
		assertEquals(VALUE_3, map.get(MODEL_ATTRIBUTE_3));
	}

	@Test
	public void transferDataToModelAndViewOfDescendantClassWithAliasing() {
		final TestAA testB = new TestAA(VALUE_1, VALUE_2, VALUE_3);
		final ModelAndView modelAndView = new ModelAndView();

		ModelMappingUtil.transferDataToModelAndView(testB, modelAndView);

		final Map<String, Object> map = modelAndView.getModel();

		assertEquals(3, map.size());
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_ALIAS_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_ALIAS_2));
		assertEquals(VALUE_3, map.get(MODEL_ATTRIBUTE_3));
	}

	@Test
	public void transferDataWithNestedAliasedContainers() {
		final TestD testD = new TestD(VALUE_1, VALUE_2, VALUE_3, VALUE_1_1);

		final ModelAndView modelAndView = new ModelAndView();

		ModelMappingUtil.transferDataToModelAndView(testD, modelAndView);
		final Map<String, Object> map = modelAndView.getModel();

		assertEquals(4, map.size());
		assertEquals(VALUE_1_1, map.get(MODEL_ATTRIBUTE_1));
		assertEquals(VALUE_1, map.get(MODEL_ATTRIBUTE_ALIAS_1));
		assertEquals(VALUE_2, map.get(MODEL_ATTRIBUTE_ALIAS_2));
		assertEquals(VALUE_3, map.get(MODEL_ATTRIBUTE_ALIAS_3));
	}

	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	@ModelAttributesContainer
	private static class TestA {
		@ModelAttributeMapping(modelAttributeName = MODEL_ATTRIBUTE_1)
		private final String field1;
		@ModelAttributeMapping(modelAttributeName = MODEL_ATTRIBUTE_2)
		private final String field2;
		private final String field0 = "outOfInterest";

		private TestA(String field1, String field2) {
			this.field1 = field1;
			this.field2 = field2;
		}
	}

	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	@ModelAttributesContainer
	private static class TestB extends TestA {
		@ModelAttributeMapping(modelAttributeName = MODEL_ATTRIBUTE_3)
		private final String field3;

		private final String fieldB0 = "outOfInterest";


		private TestB(String field1, String field2, String field3) {
			super(field1, field2);
			this.field3 = field3;
		}
	}

	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	@ModelAttributesContainer
	private static class TestC {
		@ModelAttributeMapping(modelAttributeName = MODEL_ATTRIBUTE_3)
		private final String field3;
		@ModelAttributesContainer
		private final TestA testA;
		private final String fieldB0 = "outOfInterest";


		private TestC(String field1, String field2, String field3) {
			testA = new TestA(field1, field2);
			this.field3 = field3;
		}
	}

	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	@ModelAttributesContainer
	@ModelAttributeAliases({
			@ModelAttributeAlias(aliasFor = MODEL_ATTRIBUTE_1, value = MODEL_ATTRIBUTE_ALIAS_1),
			@ModelAttributeAlias(aliasFor = MODEL_ATTRIBUTE_2, value = MODEL_ATTRIBUTE_ALIAS_2)
	})
	private static class TestAA extends TestA {
		@ModelAttributeMapping(modelAttributeName = MODEL_ATTRIBUTE_3)
		private final String field3;
		private final String fieldB0 = "outOfInterest";

		TestAA(String field1, String field2, String field3) {
			super(field1, field2);
			this.field3 = field3;
		}
	}

	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	@ModelAttributesContainer
	@ModelAttributeAliases({
			@ModelAttributeAlias(aliasFor = MODEL_ATTRIBUTE_3, value = MODEL_ATTRIBUTE_ALIAS_3),
			@ModelAttributeAlias(aliasFor = MODEL_ATTRIBUTE_2, value = MODEL_ATTRIBUTE_ALIAS_2)
	})
	private static class TestD {
		@ModelAttributeMapping(modelAttributeName = MODEL_ATTRIBUTE_1)
		private final String field11;
		@ModelAttributesContainer
		private final TestAA testA;
		private final String fieldB0 = "outOfInterest";


		TestD(String field1, String field2, String field3, String field11) {
			testA = new TestAA(field1, field2, field3);
			this.field11 = field11;
		}
	}

}