package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.SettlementKind;
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
 * Tests methods of {@link LocationRepository}.
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class LocationRepositoryTest {
	@Autowired
	private LocationRepository repository;

	@Test
	public void testCount() {
		assertEquals(1, repository.count("TEST", 1000104L, SettlementKind.city, "Верхній Тестіїв", StreetKind.street, "Адама Міцкевича",
				"30101", "33", null, "102a"));
	}

	@Test
	public void testList() {
		List<Location>  locations = repository.list(
				"TEST", 1000104L, SettlementKind.city, "Верхній Тестіїв", StreetKind.street, "Адама Міцкевича",
				"30101", "33", null, "102a",
				PageRequest.of(0, 100, LocationRepository.DEFAULT_SORT)
		);
		assertFalse(locations.isEmpty());
		assertEquals(1, locations.size());
		assertEquals(Long.valueOf(10001001L), locations.get(0).getId());
	}

	@Test
	public void testFindByIdentity() throws Exception {
		Optional<Location> optLocation = repository.findByIdentity(
				30001102L, "Сонце", null, "414"
		);
		assertTrue(optLocation.isPresent());
		assertEquals(Long.valueOf(10001003L), optLocation.orElseThrow(Exception::new).getId());
	}

}