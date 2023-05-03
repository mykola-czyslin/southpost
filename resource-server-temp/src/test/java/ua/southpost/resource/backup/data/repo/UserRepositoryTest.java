package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests methods of the {@link UserRepository}.
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repository;

	@Test
	public void testFindByLoginReturnsAbsentForNonExistent() {
		Optional<User> optUser = repository.findByLogin("non-existent");
		assertFalse(optUser.isPresent());
	}

	@Test
	public void testFindByLoginReturnsPresentForExistingLogin() throws Exception {
		Optional<User> optUser = repository.findByLogin("Test Supervisor");
		assertTrue(optUser.isPresent());
		User user = optUser.orElseThrow(Exception::new);
		assertEquals(Long.valueOf(10001L), user.getId());
		assertEquals("John", user.getFirstName());
		assertEquals("Lennon", user.getLastName());
	}

	@Test
	public void testFindByEmailReturnsAbsentForNonExistent() {
		Optional<User> optUser = repository.findByEmail("non-existent@mail.domain.com");
		assertFalse(optUser.isPresent());
	}

	@Test
	public void testFindByEmailReturnsPresentForExistent() throws Exception {
		Optional<User> optUser = repository.findByEmail("test.viewer.02@mail.net");
		assertTrue(optUser.isPresent());
		User user = optUser.orElseThrow(Exception::new);
		assertEquals(Long.valueOf(10003L), user.getId());
		assertEquals("George", user.getFirstName());
		assertEquals("Harrison", user.getLastName());
	}


	@Test
	public void testCountReturnsExpectedNumberForValidCriteria() {
		assertEquals(
				15L,
				repository.count(
						"Test %", null, null, null,
						new Date(0L), new Date(),
						false, Sets.newSet(UserRole.VIEWER, UserRole.OPERATOR, UserRole.ADMINISTRATOR),
						true, Sets.newSet(UserActivityKind.VISITOR),
						true, Sets.newSet(UserActivityKind.VISITOR)
				)
		);

	}

	@Test
	public void testListAdministrators() {
		List<User> users = repository.list(
				"Test %", null, null, null,
				new Date(0L), new Date(),
				false, Sets.newSet(UserRole.VIEWER, UserRole.OPERATOR, UserRole.ADMINISTRATOR),
				true, Sets.newSet(UserActivityKind.VISITOR),
				true, Sets.newSet(UserActivityKind.VISITOR),
				PageRequest.of(0, 100, UserRepository.DEFAULT_SORT)
		);
		assertFalse(users.isEmpty());
		assertTrue(users.stream().map(User::getId).anyMatch(id -> id == 10001L));
		assertTrue(users.stream().map(User::getId).anyMatch(id -> id == 10008L));
	}

	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void testListUsersHavingRolesInSet() {
		final Set<UserRole> adminOperatorSet = Sets.newSet(UserRole.ADMINISTRATOR, UserRole.OPERATOR);

		List<User> users = repository.listWithAnyOfRoles(adminOperatorSet);

		assertFalse(users.isEmpty());
		assertTrue(users.stream().map(User::getRoles).anyMatch(u -> u.contains(UserRole.ADMINISTRATOR)));
		assertTrue(users.stream().map(User::getRoles).anyMatch(u -> u.contains(UserRole.OPERATOR)));
		assertTrue(users.stream().map(User::getRoles).anyMatch(u -> u.contains(UserRole.OPERATOR) && !u.contains(UserRole.ADMINISTRATOR)));
		assertFalse(users.stream().map(User::getRoles).anyMatch(u -> !u.contains(UserRole.OPERATOR) && !u.contains(UserRole.ADMINISTRATOR)));
	}

}