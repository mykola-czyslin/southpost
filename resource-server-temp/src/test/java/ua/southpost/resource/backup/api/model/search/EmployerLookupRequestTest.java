package ua.southpost.resource.backup.api.model.search;

import ua.southpost.resource.backup.api.mapper.TestDataUtil;
import ua.southpost.resource.commons.model.dto.SortOption;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EmployerLookupRequestTest {

	private static final String SETTLEMENT_NAME_PATTERN = "Settlement*Name?Pattern";
	private static final String EMPLOYER_NAME_PATTERN = "Employer?Name*Pattern";
	private static final String WEB_SITE_PATTERN = "*web?site?pattern";
	private static final int PAGE_NUM = 20;
	private static final int LINES_PER_PAGE = 10;
	private static final List<SortOption> SORT_OPTIONS = Collections.singletonList(new SortOption());

	@Test
	public void toSearchPayload() {
		//given
		EmployerLookupRequest testObj = new EmployerLookupRequest();
		testObj.setRegionId(TestDataUtil.REGION_ID);
		testObj.setDistrictId(TestDataUtil.DISTRICT_ID);
		testObj.setSettlementName(SETTLEMENT_NAME_PATTERN);
		testObj.setSettlementKind(TestDataUtil.SETTLEMENT_KIND);
		testObj.setEmployerName(EMPLOYER_NAME_PATTERN);
		testObj.setWebSite(WEB_SITE_PATTERN);
		testObj.setPageNum(PAGE_NUM);
		testObj.setLinesPerPage(LINES_PER_PAGE);
		testObj.setSortOptions(SORT_OPTIONS);
		//when
		EmployerSearchRequestPayload payload = testObj.toSearchPayload();
		//then
		Assert.assertEquals(TestDataUtil.REGION_ID, payload.getRegionId());
		assertEquals((Long) TestDataUtil.DISTRICT_ID, payload.getDistrictId());
		assertEquals(SETTLEMENT_NAME_PATTERN, payload.getSettlementNamePattern());
		Assert.assertEquals(TestDataUtil.SETTLEMENT_KIND, payload.getSettlementKind());
		assertEquals(EMPLOYER_NAME_PATTERN, payload.getEmployerNamePattern());
		assertEquals(WEB_SITE_PATTERN, payload.getWebSiteAddressPattern());
		assertTrue(payload.isWebSiteSignificant());
		assertEquals(PAGE_NUM, payload.getPageNum());
		assertEquals(LINES_PER_PAGE, payload.getLinesPerPage());
		assertEquals(SORT_OPTIONS, payload.getSortOptions());
	}

	@Test
	public void toSearchPayloadWithoutWebSite() {
		//given
		EmployerLookupRequest testObj = new EmployerLookupRequest();
		testObj.setRegionId(TestDataUtil.REGION_ID);
		testObj.setDistrictId(TestDataUtil.DISTRICT_ID);
		testObj.setSettlementName(SETTLEMENT_NAME_PATTERN);
		testObj.setSettlementKind(TestDataUtil.SETTLEMENT_KIND);
		testObj.setEmployerName(EMPLOYER_NAME_PATTERN);
		testObj.setPageNum(PAGE_NUM);
		testObj.setLinesPerPage(LINES_PER_PAGE);
		testObj.setSortOptions(SORT_OPTIONS);
		//when
		EmployerSearchRequestPayload payload = testObj.toSearchPayload();
		//then
		Assert.assertEquals(TestDataUtil.REGION_ID, payload.getRegionId());
		assertEquals((Long) TestDataUtil.DISTRICT_ID, payload.getDistrictId());
		assertEquals(SETTLEMENT_NAME_PATTERN, payload.getSettlementNamePattern());
		Assert.assertEquals(TestDataUtil.SETTLEMENT_KIND, payload.getSettlementKind());
		assertEquals(EMPLOYER_NAME_PATTERN, payload.getEmployerNamePattern());
		assertNull(payload.getWebSiteAddressPattern());
		assertFalse(payload.isWebSiteSignificant());
		assertEquals(PAGE_NUM, payload.getPageNum());
		assertEquals(LINES_PER_PAGE, payload.getLinesPerPage());
		assertEquals(SORT_OPTIONS, payload.getSortOptions());
	}

}