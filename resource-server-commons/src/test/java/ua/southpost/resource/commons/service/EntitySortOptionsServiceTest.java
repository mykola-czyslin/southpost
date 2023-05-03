package ua.southpost.resource.commons.service;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.entity.Identity;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;
import java.util.Map;

import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static ua.southpost.resource.commons.service.EntitySortOptionsServiceImpl.EMPTY_ARGS;
import static ua.southpost.resource.commons.service.EntitySortOptionsServiceImpl.MSG_KEY_OPTION_SELECT_ONE;

@RunWith(MockitoJUnitRunner.class)
public class EntitySortOptionsServiceTest {

	private static final Locale NATIVE_LOCALE = new Locale("uk", "UA");
	public static final String TEST_CONTENT_KEY = "test.content";
	public static final String TEST_CONTENT = "Content";
	public static final String TEST_RATE_KEY = "test.rate";
	public static final String TEST_RATE = "Rate";
	public static final String TEST_DESCRIPTION_KEY = "test.description";
	public static final String TEST_DESCRIPTION = "Description";
	public static final String TEST_CHILD_DATA_KEY = "test.child.data";
	public static final String TEST_CHILD_CONTENT = "Child Content";
	public static final String TEST_CHILD_RATE = "Child Rate";
	private EntitySortOptionsService testObj;
	@Mock
	private MessageSource messageSourceMock;
	@Mock EntityInfoTypeToIdentityTypeResolver entityInfoTypeToIdentityTypeResolverMock;

	@Before
	public void setUp() throws Exception {
		doReturn(SimpleIdentity.class).when(entityInfoTypeToIdentityTypeResolverMock).resolveIdentityType(SimpleEntityInfo.class);
		doReturn(ComplexIdentity.class).when(entityInfoTypeToIdentityTypeResolverMock).resolveIdentityType(ComplexEntityInfo.class);
		doThrow(new IllegalArgumentException()).when(entityInfoTypeToIdentityTypeResolverMock).resolveIdentityType(LongEntityInfo.class);
		when(messageSourceMock.getMessage(MSG_KEY_OPTION_SELECT_ONE, EMPTY_ARGS, NATIVE_LOCALE)).thenReturn("select something");
		when(messageSourceMock.getMessage(TEST_CONTENT_KEY, EMPTY_ARGS, NATIVE_LOCALE)).thenReturn(TEST_CONTENT);
		when(messageSourceMock.getMessage(TEST_RATE_KEY, EMPTY_ARGS, NATIVE_LOCALE)).thenReturn(TEST_RATE);
		when(messageSourceMock.getMessage(TEST_DESCRIPTION_KEY, EMPTY_ARGS, NATIVE_LOCALE)).thenReturn(TEST_DESCRIPTION);
		when(messageSourceMock.getMessage(TEST_CHILD_DATA_KEY, new String[]{ TEST_CONTENT }, NATIVE_LOCALE)).thenReturn(TEST_CHILD_CONTENT);
		when(messageSourceMock.getMessage(TEST_CHILD_DATA_KEY, new String[] { TEST_RATE }, NATIVE_LOCALE)).thenReturn(TEST_CHILD_RATE);
		testObj = new EntitySortOptionsServiceImpl(entityInfoTypeToIdentityTypeResolverMock, messageSourceMock);
	}

	@Test
	public void testSimpleEntityInfoSortOptions() {
		final EntitySortOptions sortOptions = testObj.getSortOptions(SimpleEntityInfo.class, NATIVE_LOCALE);
		System.out.println("sort options: " + sortOptions);
		assertNotNull(sortOptions);
		final Map<String, String> options = sortOptions.asMap();
		assertTrue(isNotEmpty(options));
	}

	@Test
	public void testComplextEntityInfoSortOptions() {
		final EntitySortOptions sortOptions = testObj.getSortOptions(ComplexEntityInfo.class, NATIVE_LOCALE);
		System.out.println("sort options: " + sortOptions);
		assertNotNull(sortOptions);
		final Map<String, String> options = sortOptions.asMap();
		assertTrue(isNotEmpty(options));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttemptToResolveSortOptionsForAbstractEntityInfo() {
		testObj.getSortOptions(LongEntityInfo.class, NATIVE_LOCALE);
	}
	private static class SimpleEntityInfo implements EntityInfo<String> {

		private String id;
		private String content;
		private int rate;

		@Override
		public String getId() {
			return id;
		}

		@Override
		public void setId(String id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getRate() {
			return rate;
		}

		public void setRate(int rate) {
			this.rate = rate;
		}

		@Override
		public String getTextValue() {
			return String.format("id: %1$s; content: %2$s; rate: %3$d", this.id, this.content, this.rate);
		}

		@Override
		public void setTextValue(String textValue) {
			// do nothing
		}
	}

	private static class SimpleIdentity implements Identity<String> {

		private String id;
		@SortField(TEST_CONTENT_KEY)
		private String content;
		@SortField(TEST_RATE_KEY)
		private int rate;

		@Override
		public String getId() {
			return null;
		}

		@Override
		public void setId(String id) {

		}
	}

	private static class ComplexEntityInfo implements EntityInfo<Long> {
		private Long id;
		private String description;
		private SimpleEntityInfo childData;

		@Override
		public Long getId() {
			return id;
		}

		@Override
		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public String getTextValue() {
			return String.format("id: %d", this.id, this.description, this.childData != null ? this.childData.getTextValue() : "<null>");
		}

		@Override
		public void setTextValue(String textValue) {
			// do nothing
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public SimpleEntityInfo getChildData() {
			return childData;
		}

		public void setChildData(SimpleEntityInfo childData) {
			this.childData = childData;
		}
	}

	private static class ComplexIdentity implements Identity<Long> {
		private Long id;
		@SortField(TEST_DESCRIPTION_KEY)
		private String description;
		@SortField(label = TEST_CHILD_DATA_KEY, complex = true)
		private SimpleIdentity childData;

		@Override
		public Long getId() {
			return id;
		}

		@Override
		public void setId(Long id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public SimpleIdentity getChildData() {
			return childData;
		}

		public void setChildData(SimpleIdentity childData) {
			this.childData = childData;
		}
	}

	private static class LongEntityInfo extends AbstractEntityInfo<Long> {}
}