package ua.southpost.resource.backup.data.repo;

import com.google.common.collect.Sets;
import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.LawCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class LawyerAgencyRepositoryTest {

	@Autowired
	private LawyerAgencyRepository repository;

	@Test
	public void testCountNonExistent() {
		long actualCount = repository.count(
				"---Doesn't Exists",
				-1L,
				null,
				"---Doesn't exists",
				"---Doesn't exists",
				null,
				null,
				null,
				false,
				Collections.emptySet()
		);
		assertEquals(0L, actualCount);
	}

	@Test
	public void testAnyCaseSupportedCount() {
		long actualCount = repository.count(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				true,
				Collections.emptySet()
		);
		assertEquals(15L, actualCount);
	}

	@Test
	public void testFamilyAndPropertyCasesSupportedCount() {
		long actualCount = repository.count(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				false,
				Sets.newHashSet(LawCase.FAMILY, LawCase.PROPERTY)
		);
		assertEquals(11L, actualCount);
	}

	@Test
	public void testFamilyCaseSupportedCount() {
		long actualCount = repository.count(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				false,
				Sets.newHashSet(LawCase.FAMILY)
		);
		assertEquals(6L, actualCount);
	}

	@Test
	public void testPropertyCaseSupportedCount() {
		long actualCount = repository.count(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				false,
				Sets.newHashSet(LawCase.PROPERTY)
		);
		assertEquals(11L, actualCount);
	}
}