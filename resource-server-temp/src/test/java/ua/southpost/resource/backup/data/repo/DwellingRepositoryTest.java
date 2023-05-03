package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.DwellingKind;
import ua.southpost.resource.backup.data.model.SettlementKind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * performs testing of the methods of {@link DwellingRepository}.
 * Created by mchys on 30.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class DwellingRepositoryTest {
	private static final String NON_EXISTENT = "Non-existent";
	private static final DwellingSearchCriteria NON_EXISTENT_CRITERIA = new DwellingSearchCriteria(
			NON_EXISTENT,
			DwellingKind.HOSTEL,
			-1,
			-10,
			new BigDecimal("-345.05"),
			new BigDecimal("-450.58"),
			new BigDecimal("-100.154"),
			new BigDecimal("-125.184"),
			BillingPeriod.DAY,
			new BigDecimal("-999.99"),
			new BigDecimal("-121.11"),
			NON_EXISTENT,
			-10L,
			SettlementKind.farmstead,
			NON_EXISTENT,
			NON_EXISTENT,
			NON_EXISTENT,
			NON_EXISTENT
	);

	@Autowired
	private DwellingRepository dwellingRepository;

	@Test
	public void testCountZero() {
		long countValue = dwellingRepository.count(
				NON_EXISTENT_CRITERIA.settlementAreaPattern,
				NON_EXISTENT_CRITERIA.dwellingKind,
				NON_EXISTENT_CRITERIA.numberOfRoomsFrom,
				NON_EXISTENT_CRITERIA.numberOfRoomsTo,
				NON_EXISTENT_CRITERIA.totalAreaFrom,
				NON_EXISTENT_CRITERIA.totalAreaTo,
				NON_EXISTENT_CRITERIA.livingAreaFrom,
				NON_EXISTENT_CRITERIA.livingAreaTo,
				NON_EXISTENT_CRITERIA.billingPeriod,
				NON_EXISTENT_CRITERIA.priceFrom,
				NON_EXISTENT_CRITERIA.priceTo,
				NON_EXISTENT_CRITERIA.regionId,
				NON_EXISTENT_CRITERIA.districtId,
				NON_EXISTENT_CRITERIA.settlementKind,
				NON_EXISTENT_CRITERIA.settlementNamePattern,
				NON_EXISTENT_CRITERIA.phonePattern,
				NON_EXISTENT_CRITERIA.emailPattern
		);
		assertEquals(0L, countValue);
	}

	@Test
	public void testList() {
		List<Dwelling> dwellingList = dwellingRepository.list(
				NON_EXISTENT_CRITERIA.settlementAreaPattern,
				NON_EXISTENT_CRITERIA.dwellingKind,
				NON_EXISTENT_CRITERIA.numberOfRoomsFrom,
				NON_EXISTENT_CRITERIA.numberOfRoomsTo,
				NON_EXISTENT_CRITERIA.totalAreaFrom,
				NON_EXISTENT_CRITERIA.totalAreaTo,
				NON_EXISTENT_CRITERIA.livingAreaFrom,
				NON_EXISTENT_CRITERIA.livingAreaTo,
				NON_EXISTENT_CRITERIA.billingPeriod,
				NON_EXISTENT_CRITERIA.priceFrom,
				NON_EXISTENT_CRITERIA.priceTo,
				NON_EXISTENT_CRITERIA.regionId,
				NON_EXISTENT_CRITERIA.districtId,
				NON_EXISTENT_CRITERIA.settlementKind,
				NON_EXISTENT_CRITERIA.settlementNamePattern,
				NON_EXISTENT_CRITERIA.phonePattern,
				NON_EXISTENT_CRITERIA.emailPattern,
				Pageable.unpaged()
		);
		assertTrue(dwellingList.isEmpty());
	}

	@Test
	public void testListSorted() {
		List<Dwelling> dwellingList = dwellingRepository.list(
				NON_EXISTENT_CRITERIA.settlementAreaPattern,
				NON_EXISTENT_CRITERIA.dwellingKind,
				NON_EXISTENT_CRITERIA.numberOfRoomsFrom,
				NON_EXISTENT_CRITERIA.numberOfRoomsTo,
				NON_EXISTENT_CRITERIA.totalAreaFrom,
				NON_EXISTENT_CRITERIA.totalAreaTo,
				NON_EXISTENT_CRITERIA.livingAreaFrom,
				NON_EXISTENT_CRITERIA.livingAreaTo,
				NON_EXISTENT_CRITERIA.billingPeriod,
				NON_EXISTENT_CRITERIA.priceFrom,
				NON_EXISTENT_CRITERIA.priceTo,
				NON_EXISTENT_CRITERIA.regionId,
				NON_EXISTENT_CRITERIA.districtId,
				NON_EXISTENT_CRITERIA.settlementKind,
				NON_EXISTENT_CRITERIA.settlementNamePattern,
				NON_EXISTENT_CRITERIA.phonePattern,
				NON_EXISTENT_CRITERIA.emailPattern,
				PageRequest.of(0, 100, DwellingRepository.DEFAULT_SORT)
		);
		assertTrue(dwellingList.isEmpty());
	}

	private static class DwellingSearchCriteria {
		final String settlementAreaPattern;
		final DwellingKind dwellingKind;
		final Integer numberOfRoomsFrom;
		final Integer numberOfRoomsTo;
		final BigDecimal totalAreaFrom;
		final BigDecimal totalAreaTo;
		final BigDecimal livingAreaFrom;
		final BigDecimal livingAreaTo;
		final BillingPeriod billingPeriod;
		final BigDecimal priceFrom;
		final BigDecimal priceTo;
		final String regionId;
		final Long districtId;
		final SettlementKind settlementKind;
		final String settlementNamePattern;
		final String contactNamePattern;
		final String phonePattern;
		final String emailPattern;

		@SuppressWarnings("SameParameterValue")
		private DwellingSearchCriteria(String settlementAreaPattern, DwellingKind dwellingKind, Integer numberOfRoomsFrom, Integer numberOfRoomsTo, BigDecimal totalAreaFrom, BigDecimal totalAreaTo, BigDecimal livingAreaFrom, BigDecimal livingAreaTo, BillingPeriod billingPeriod, BigDecimal priceFrom, BigDecimal priceTo, String regionId, Long districtId, SettlementKind settlementKind, String settlementNamePattern, String contactNamePattern, String phonePattern, String emailPattern) {
			this.settlementAreaPattern = settlementAreaPattern;
			this.dwellingKind = dwellingKind;
			this.numberOfRoomsFrom = numberOfRoomsFrom;
			this.numberOfRoomsTo = numberOfRoomsTo;
			this.totalAreaFrom = totalAreaFrom;
			this.totalAreaTo = totalAreaTo;
			this.livingAreaFrom = livingAreaFrom;
			this.livingAreaTo = livingAreaTo;
			this.billingPeriod = billingPeriod;
			this.priceFrom = priceFrom;
			this.priceTo = priceTo;
			this.regionId = regionId;
			this.districtId = districtId;
			this.settlementKind = settlementKind;
			this.settlementNamePattern = settlementNamePattern;
			this.contactNamePattern = contactNamePattern;
			this.phonePattern = phonePattern;
			this.emailPattern = emailPattern;
		}
	}
}