package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.EmailAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests methods of {@link EmailAddressRepository}.
 * Created by mchys on 30.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class EmailAddressRepositoryTest {
	@Autowired
	private EmailAddressRepository repository;

	@Test
	public void testCountOfNonExistentReturnsZero() {
		assertEquals(0L, repository.count("non-existent", "non-existent"));
	}

	@Test
	public void testCountForSearchByAddressPatternOnly() {
		assertEquals(2L, repository.count("%@werchniotestijiwska-miska-likarnia.mail.ua", null));
	}

	@Test
	public void testCountForSearchByDescriptionPatternOnly() {
		assertEquals(13L, repository.count(null, "%Відділ% кадрів%"));
	}

	@Test
	public void testContForSearchByBothAddressAndDescription() {
		assertEquals(1L, repository.count("%@werchniotestijiwska-miska-likarnia.mail.ua", "%Відділ кадрів%"));
	}

	@Test
	public void testListWithNonExistentCriteria() {
		List<EmailAddress> emails = repository.list("non-existent", "non-existent", Pageable.unpaged());
		assertTrue(emails.isEmpty());
	}

	@Test
	public void testListForSearchByAddressPatternOnlySorted() {
		List<EmailAddress> emails = repository.list("%@werchniotestijiwska-miska-likarnia.mail.ua", null, PageRequest.of(0, 100, EmailAddressRepository.DEFAULT_SORT));
		assertFalse(emails.isEmpty());
		assertEquals(2, emails.size());
		assertTrue(emails.stream().anyMatch(a -> 1010101L == a.getId()));
		assertTrue(emails.stream().anyMatch(a -> 1010102L == a.getId()));
	}

	@Test
	public void testListForSearchByAddressPatternOnly() {
		List<EmailAddress> emails = repository.list("%@werchniotestijiwska-miska-likarnia.mail.ua", null, Pageable.unpaged());
		assertFalse(emails.isEmpty());
		assertEquals(2, emails.size());
		assertTrue(emails.stream().anyMatch(a -> 1010101L == a.getId()));
		assertTrue(emails.stream().anyMatch(a -> 1010102L == a.getId()));
	}

	@Test
	public void testListForSearchByDescriptionPatternOnly() {
		List<EmailAddress> emails = repository.list(null, "%відділ% кадрів%", Pageable.unpaged());
		assertFalse(emails.isEmpty());
		assertEquals(13, emails.size());
		assertTrue(emails.stream().anyMatch(a -> 1010101L == a.getId()));
		assertTrue(emails.stream().anyMatch(a -> 1010202L == a.getId()));
	}

	@Test
	public void testListFoSearchByBothAddressAndDescriptionPatterns() {
		List<EmailAddress> emails = repository.list("%@werchniotestijiwska-miska-likarnia.mail.ua", "%відділ% кадрів%", Pageable.unpaged());
		assertFalse(emails.isEmpty());
		assertEquals(1, emails.size());
		assertTrue(emails.stream().anyMatch(a -> 1010101L == a.getId()));
	}

	@Test
	public void testFindByAddressForNonExistentReturnsAbsentValue() {
		Optional<EmailAddress> emailOpt = repository.findByAddress("non-existent");
		assertFalse(emailOpt.isPresent());
	}

	@Test
	public void testFindByAddressForExistingAddressReturnsPresentValue() throws Exception {
		Optional<EmailAddress> emailOpt = repository.findByAddress("narcis@work.mail.ua");
		assertTrue(emailOpt.isPresent());
		assertEquals(Long.valueOf(1040201L), emailOpt.orElseThrow(Exception::new).getId());
	}
}