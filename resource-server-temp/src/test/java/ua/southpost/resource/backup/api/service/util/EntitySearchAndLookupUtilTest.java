package ua.southpost.resource.backup.api.service.util;

import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchAndLookupUtil;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.LookupResponse;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntitySearchAndLookupUtilTest {
	private int PAGE_NUMBER = 1;
	private int MIDDLE_PAGE_NUMBER = 2;
	private int ROWS_PER_PAGE = 5;
	private static final long COUNT = 2L;

	private EntitySearchAndLookupUtil testObj = new EntitySearchAndLookupUtil();

	@Mock
	private PagedSearchRequestPayload payloadMock;
	@Mock
	private Function<PagedSearchRequestPayload, Long> countFunctionMock;
	@Mock
	private BiFunction<PagedSearchRequestPayload, Pageable, List<Identity<Object>>> listFunctionMock;
	@Mock
	private Sort sortMock;
	@Mock
	private Function<Identity<Object>, EntityInfo<Object>> lookupInfoMapperMock;
	@Mock
	private Identity<Object> item1;
	@Mock
	private Identity<Object> item2;
	@Mock
	private EntityInfo<Object> entityInfo1;
	@Mock
	private EntityInfo<Object> entityInfo2;

	@Before
	public void setUp() {
		when(payloadMock.getPageNum()).thenReturn(PAGE_NUMBER);
		when(payloadMock.getLinesPerPage()).thenReturn(ROWS_PER_PAGE);
		when(countFunctionMock.apply(payloadMock)).thenReturn(COUNT);
		when(listFunctionMock.apply(eq(payloadMock), any(Pageable.class)))
				.thenReturn(Lists.newArrayList(item1, item2));
		when(lookupInfoMapperMock.apply(item1)).thenReturn(entityInfo1);
		when(lookupInfoMapperMock.apply(item2)).thenReturn(entityInfo2);
	}

	@Test
	public void testLookupWithSinglePage() {
		//when
		final LookupResponse<EntityInfo<Object>> res = testObj.lookup(payloadMock, countFunctionMock, listFunctionMock, sortMock, lookupInfoMapperMock);
		//then
		verify(listFunctionMock).apply(eq(payloadMock), argThat(pageable -> sortMock.equals(pageable.getSort()) && pageable.getOffset() == (PAGE_NUMBER - 1) && pageable.getPageSize() == ROWS_PER_PAGE));
		assertNotNull(res);
		assertNotNull(res.getOptions());
		assertNull(res.getPrevPage());
		assertNull(res.getNextPage());
		assertTrue(res.getOptions().contains(entityInfo1));
		assertTrue(res.getOptions().contains(entityInfo2));
	}

	@Test
	public void testLookupWithSecondPage() {
		//given
		when(countFunctionMock.apply(payloadMock)).thenReturn(2 * ROWS_PER_PAGE + COUNT);
		final List<Identity<Object>> items = Lists.newArrayList();
		final List<EntityInfo<Object>> entities = Lists.newArrayList();
		for(int i = 0; i < ROWS_PER_PAGE; i++) {
			@SuppressWarnings("unchecked") Identity<Object> item = mock(Identity.class);
			@SuppressWarnings("unchecked") EntityInfo<Object> entityInfo = mock(EntityInfo.class);
			when(lookupInfoMapperMock.apply(item)).thenReturn(entityInfo);
			items.add(item);
			entities.add(entityInfo);
		}
		when(listFunctionMock.apply(eq(payloadMock), argThat(pageable -> sortMock.equals(pageable.getSort()) && pageable.getOffset() == (MIDDLE_PAGE_NUMBER - 1)*ROWS_PER_PAGE && pageable.getPageSize() == ROWS_PER_PAGE)))
				.thenReturn(items);
		when(payloadMock.getPageNum()).thenReturn(MIDDLE_PAGE_NUMBER);
		//when
		final LookupResponse<EntityInfo<Object>> res = testObj.lookup(payloadMock, countFunctionMock, listFunctionMock, sortMock, lookupInfoMapperMock);
		//then
		verify(listFunctionMock).apply(eq(payloadMock), argThat(pageable -> sortMock.equals(pageable.getSort()) && pageable.getOffset() == (MIDDLE_PAGE_NUMBER - 1)*ROWS_PER_PAGE && pageable.getPageSize() == ROWS_PER_PAGE));
		assertNotNull(res);
		assertNotNull(res.getOptions());
		assertEquals(entities, res.getOptions());
		assertNotNull(res.getPrevPage());
		assertNotNull(res.getNextPage());
		assertEquals(1, res.getPrevPage().intValue());
		assertEquals(3, res.getNextPage().intValue());
	}
}