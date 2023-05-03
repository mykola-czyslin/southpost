package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.StreetKind;
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
 * Tests methods of the {@link StreetRepository}.
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class StreetRepositoryTest {
	@Autowired
	private StreetRepository repository;

	@Test
	public void testCountForNonExistentReturnsZero() {
		assertEquals(0L, repository.count(
				"non-existent", -1L,
				SettlementKind.farmstead, "non-existent",
				StreetKind.block, "non-existent"
				)
		);
	}

	@Test
	public void testCountForSomeSettlementReturnsExpectedNumberOfStreets() {
		assertEquals(4L, repository.count(
				"TEST", 1000102L, SettlementKind.city, "Новий Тестіїв",
				null, null
				)
		);

	}

	@Test
	public void testListForSomeSettlementsReturnsExpectedData() {
		List<Street> streets = repository.list(
				"TEST", 1000102L, SettlementKind.city, "Новий Тестіїв",
				null, null,
				PageRequest.of(0, 100, StreetRepository.DEFAULT_SORT)
		);
		assertFalse(streets.isEmpty());
		assertTrue(streets.stream().map(Street::getDisplayName).anyMatch("Станіслава Лєма"::equalsIgnoreCase));
		assertTrue(streets.stream().map(Street::getDisplayName).anyMatch("Василя Симоненка"::equalsIgnoreCase));
		assertTrue(streets.stream().map(Street::getDisplayName).anyMatch("Леся Курбаса"::equalsIgnoreCase));
		assertTrue(streets.stream().map(Street::getDisplayName).anyMatch("Василя Кожелянка"::equalsIgnoreCase));
	}

	@Test
	public void testFindUniqueForNonExistentReturnsAbsent() {
		Optional<Street> optStreet = repository.findUnique(
				"Non-existent",
				StreetKind.block,
				-1L
		);
		assertFalse(optStreet.isPresent());
	}

	@Test
	public void testFindUniqueForExistentReturnsPresent() throws Exception {
		Optional<Street> optStreet = repository.findUnique(
				"Станіслава Лєма",
				StreetKind.street,
				20010004L
		);
		assertTrue(optStreet.isPresent());
		assertEquals(Long.valueOf(30001041L), optStreet.orElseThrow(Exception::new).getId());
	}
}