package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.Employer;
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
 *
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class EmployerRepositoryTest {
	@Autowired
	private EmployerRepository repository;
	@Test
	public void testCountNonExistentReturnsZero() {
		long count = repository.count("non-existent", 1, "non-existent", "non-existent", SettlementKind.farmstead, -1L, "NON-EXISTENT");
		assertEquals(0, count);
	}

	@Test
	public void testListNonExistenReturnEmptyList() {
		List<Employer> employers = repository.list("non-existent", 1, "non-existent", "non-existent", SettlementKind.farmstead, -1L, "NON-EXISTENT", PageRequest.of(0, 100, EmployerRepository.DEFAULT_SORT));
		assertTrue(employers.isEmpty());
	}

	@Test
	public void testListAllCafe() {
		List<Employer> employers = repository.list("%Кафе%", 0, null, "Верхні Забрьохи", SettlementKind.village, 1000102L, "TEST", PageRequest.of(0, 100, EmployerRepository.DEFAULT_SORT));
		assertFalse(employers.isEmpty());
		assertEquals(1, employers.size());
		assertEquals(Long.valueOf(1003001L), employers.get(0).getId());
	}

	@Test
	public void testFindByWebSiteForNonExistentReturnsAbsent() {
		Optional<Employer> employerOpt = repository.findByWebSite("non-existent");
		assertFalse(employerOpt.isPresent());
	}

	@Test
	public void testFindByWebSiteForExistentValueReturnsPresent() throws Exception {
		Optional<Employer> employerOpt = repository.findByWebSite("https://www.lileya.beauty.com.ua/");
		assertTrue(employerOpt.isPresent());
		assertEquals(Long.valueOf(1004001L), employerOpt.orElseThrow(Exception::new).getId());
	}

	@Test
	public void testFindByNameForNonExistentReturnsAbsent() {
		Optional<Employer> employerOpt = repository.findByName("non-existent");
		assertFalse(employerOpt.isPresent());
	}

	@Test
	public void testFindByNameForExistentReturnsPresent() throws Exception {
		Optional<Employer> employerOpt = repository.findByName("Кафе \"Дніпровська Хвиля\"");
		assertTrue(employerOpt.isPresent());
		assertEquals(Long.valueOf(1003001L), employerOpt.orElseThrow(Exception::new).getId());
	}
}