package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.SettlementKind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests methods of the {@link SettlementRepository}.
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class SettlementRepositoryTest {
	@Autowired
	private SettlementRepository repository;

	@Test
	public void testCount() {
		long count = repository.count("TEST", 1000102L, SettlementKind.city, null);
		assertEquals(2L, count);
	}

	@Test
	public void testList() {
		List<Settlement> settlements = repository.list(
				"TEST", 1000102L, SettlementKind.city, null,
				PageRequest.of(0, 100, SettlementRepository.DEFAULT_SORT)
		);
		assertFalse(settlements.isEmpty());
		assertEquals(2, settlements.size());
		assertTrue(settlements.stream().map(Settlement::getId).anyMatch(id -> id == 20010004L));
		assertTrue(settlements.stream().map(Settlement::getId).anyMatch(id -> id == 20010006L));
	}

	@Test
	public void testFindUniqueForNonExistentReturnsAbsent() {
		Optional<Settlement> optSettlement = repository.findUnique(-1L, SettlementKind.city, "non-existent");
		assertFalse(optSettlement.isPresent());
	}

	@Test
	public void testFindUniqueForExistingReturnsPresent() throws Exception {
		Optional<Settlement> optSettlement = repository.findUnique(1000102L, SettlementKind.city, "Обсолітів");
		assertTrue(optSettlement.isPresent());
		assertEquals(Long.valueOf(20010006L), optSettlement.orElseThrow(Exception::new).getId());
	}
}