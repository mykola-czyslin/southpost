package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntityLookupServiceImplTest {

	static final Locale ONLY_PROPER_LOCALE = new Locale("uk", "UA");
	private static final long TOTAL_COUNT = 1000;
	private static final int LINES_PER_PAGE = 10;
	private static final int PAGE_NUM = 20;
	private static final Sort SORT_REQUESTED = Sort.by(Sort.Direction.ASC, "field#1", "field#2");
	private static final Pageable PAGEABLE = PageRequest.of(PAGE_NUM - 1, LINES_PER_PAGE, SORT_REQUESTED);

	@Mock
	private EntitySearchService<TestSearchRequestPayload, TestIdentity, Long> entitySearchServiceMock;
	@Mock
	private SortService<TestSearchRequestPayload> sortServiceMock;
	private final LocalizedEntityMapper<TestIdentity, TestEntityInfo, Long> entityMapperMock = new LocalizedEntityMapper<TestIdentity, TestEntityInfo, Long>() {
		@Nonnull
		@Override
		public TestEntityInfo map(@Nonnull TestIdentity entity, @Nonnull Locale locale) {
			final TestEntityInfo testEntityInfo = new TestEntityInfo();
			testEntityInfo.setId(entity.getId());
			testEntityInfo.setTextValue("text#" + entity.getId());
			return testEntityInfo;
		}
	};

	private final EntitySearchAndLookupUtil entitySearchUtilMock = new EntitySearchAndLookupUtil();

	private EntityLookupServiceImpl<TestSearchRequestPayload, TestIdentity, TestEntityInfo, Long> service;

	private final TestSearchRequestPayload searchPayload = new TestSearchRequestPayload();

	@Before
	public void setUp() {
		searchPayload.setLinesPerPage(LINES_PER_PAGE);
		searchPayload.setPageNum(PAGE_NUM);
		when(entitySearchServiceMock.count(searchPayload)).thenReturn(TOTAL_COUNT);
		final long startId = (PAGE_NUM - 1)*LINES_PER_PAGE + 1;
		final long endId = PAGE_NUM * LINES_PER_PAGE + 1;
		when(entitySearchServiceMock.list(searchPayload, PAGEABLE)).thenReturn(LongStream.range(startId, endId).mapToObj(this::createTestIdentity).collect(Collectors.toList()));
		when(sortServiceMock.sort(searchPayload)).thenReturn(SORT_REQUESTED);
		service = new EntityLookupServiceImpl<>(entitySearchUtilMock, entitySearchServiceMock, sortServiceMock, entityMapperMock);
	}

	private TestIdentity createTestIdentity(long id) {
		final TestIdentity testIdentity = new TestIdentity();
		testIdentity.setId(id);
		return testIdentity;
	}

	@Test
	public void testSearchHappyPath() {
		final EntityPage<TestEntityInfo, Long> entityPage = service.search(searchPayload, ONLY_PROPER_LOCALE);
		thenReturnSatisfyExpectations(entityPage);
	}

	@Test
	public void testLookupHappyPath() {
		final LookupResponse<TestEntityInfo> lookupResponse = service.lookup(searchPayload, ONLY_PROPER_LOCALE);
		thenReturnSatisfyExpectations(lookupResponse);
	}

	void thenReturnSatisfyExpectations(EntityPage<TestEntityInfo, Long> entityPage) {
		assertNotNull(entityPage);
		assertEquals(LINES_PER_PAGE, entityPage.getPageSize());
		assertEquals(PAGE_NUM, entityPage.getCurrentPage());
		assertEquals(100, entityPage.getPageCount());
		assertNotNull(entityPage.getEntityList());
		assertEquals(LINES_PER_PAGE, entityPage.getEntityList().size());
	}

	void thenReturnSatisfyExpectations(LookupResponse<TestEntityInfo> response) {
		assertNotNull(response);
		assertEquals(LINES_PER_PAGE, response.getOptions().size());
		assertNotNull(response.getPrevPage());
		assertEquals(PAGE_NUM - 1, response.getPrevPage().intValue());
		assertNotNull(response.getNextPage());
		assertEquals(PAGE_NUM + 1, response.getNextPage().intValue());
	}
}