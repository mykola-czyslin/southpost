package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.commons.model.ChangeType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests methods of the {@link EntityHistoryRepository}.
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class EntityHistoryRepositoryTest {
	@Autowired
	private EntityHistoryRepository repository;

	@Test
	public void testCountNonExistentEntity() {
		assertEquals(0, repository.count("non.existent.Class", "-1000L", -1L, ChangeType.UPDATE));
	}

	@Test
	public void testListNonExistentEntityHistory() {
		assertTrue(repository.list("non.existent.Class", "-1000L", -1L, ChangeType.UPDATE, PageRequest.of(0, 100, EntityHistoryRepository.DEFAULT_SORT)).isEmpty());
	}

	@Test
	public void testCountNonExistent() {
		assertEquals(0, repository.count("non.existent.Class", -1L, ChangeType.DELETE));
	}

	@Test
	public void testListNonExistentHistory() {
		assertTrue(repository.list("non.existent.Class", -1L, ChangeType.UPDATE, PageRequest.of(0, 100, EntityHistoryRepository.DEFAULT_SORT)).isEmpty());
	}

}