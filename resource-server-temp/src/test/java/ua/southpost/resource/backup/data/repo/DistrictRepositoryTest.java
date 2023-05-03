package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test methods of the {@link DistrictRepository}.
 * Created by mchys on 29.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class DistrictRepositoryTest {
	@Autowired
	private DistrictRepository districtRepository;

	@Test
	public void testListReturnsExpectedValuesForTestRegion() {
		List<District> list = districtRepository.list("TEST");
		assertEquals(8, list.size());
		assertTrue(list.stream().anyMatch(d -> "Тестіївський".equals(d.getDisplayName())));
		assertTrue(list.stream().anyMatch(d -> "Новотестіївський".equals(d.getDisplayName())));
		assertTrue(list.stream().anyMatch(d -> "Старотестіївський".equals(d.getDisplayName())));
		assertTrue(list.stream().anyMatch(d -> "Верхньотестіївський".equals(d.getDisplayName())));
		assertTrue(list.stream().anyMatch(d -> "Нижньотестіївський".equals(d.getDisplayName())));
		assertTrue(list.stream().anyMatch(d -> "Великотестівський".equals(d.getDisplayName())));
		assertTrue(list.stream().anyMatch(d -> "Нетестанівський".equals(d.getDisplayName())));
	}

	@Test
	public void testListReturnsEmptyListForNonExistingRegionId() {
		List<District> list = districtRepository.list("NON-EXISTING");
		assertTrue(list.isEmpty());
	}
}