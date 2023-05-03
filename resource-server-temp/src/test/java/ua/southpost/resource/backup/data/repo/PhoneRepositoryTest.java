package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.Phone;
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
 * Tests methods of the {@link PhoneRepository}.
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class PhoneRepositoryTest {
	@Autowired
	private PhoneRepository repository;

	@Test
	public void testCountWithoutConditions() {
		assertTrue(0 < repository.count(null, null));
	}

	@Test
	public void testCountForNumberPattern() {
		assertEquals(5L, repository.count("+38091101%", null));
	}

	@Test
	public void testCountForDescriptionPattern() {
		assertEquals(3L, repository.count(null, "ТОВ \"Перша Приватна Мемарня\"%"));
	}

	@Test
	public void testCountForAnotherDescriptionPattern() {
		assertEquals(2L, repository.count(null, "ПрАТ \"Дика Орхідея в Багні\"%"));
	}

	@Test
	public void testCountForNumberAndDescriptionPatterns() {
		assertEquals(1L, repository.count("_380911020201", "ПрАТ \"Дика Орхідея в Багні\"%"));
	}

	@Test
	public void testList() {
		List<Phone> phones = repository.list("_3809__010201", "ТОВ \"Перша Приватна Мемарня\"%",
				PageRequest.of(0, 100, PhoneRepository.DEFAULT_SORT));
		assertFalse(phones.isEmpty());
		assertEquals(1, phones.size());
	}

	@Test
	public void testFindByNumberForNonExistingReturnsAbsent() {
		Optional<Phone> optPhone = repository.findByNumber("non-existent");
		assertFalse(optPhone.isPresent());
	}


	@Test
	public void testFindByNumberForExistingReturnsPresent() throws Exception {
		Optional<Phone> optPhone = repository.findByNumber("+380911010203");
		assertTrue(optPhone.isPresent());
		assertEquals(Long.valueOf(1010203L), optPhone.orElseThrow(Exception::new).getId());
	}
}