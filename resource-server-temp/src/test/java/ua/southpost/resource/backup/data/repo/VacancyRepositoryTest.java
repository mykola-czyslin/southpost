package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.WhatAndWhereApplication;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Vacancy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests methods of {@link VacancyRepository}
 * Created by mchys on 31.12.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class)
@Transactional
public class VacancyRepositoryTest {
	@Autowired
	private VacancyRepository repository;

	@Test
	public void testCountReturnsZeroForNonExistingCriteria() {
		assertEquals(
				0L,
				repository.count(
						"non-existing",
						"non-existing",
						new BigDecimal("-5000.34"),
						NoYes.NO,
						"non-existent",
						"non-existent",
						SettlementKind.farmstead,
						-1L,
						"NON-EXISTENT",
						"non-existent",
						"non-existent"
				)
		);
	}

	@Test
	public void testCountReturnsOneForExistingVacancyCriteria() {
		assertEquals(
				1L,
				repository.count(
						"ПАРХВУМЕР-ТЕХНОЛОГ",
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null
				)
		);
	}

	@Test
	public void testListVacancies() {
		List<Vacancy> vacancies = repository.list(
				null,
				"Англійська/німецька не нижче %",
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				PageRequest.of(0, 25, VacancyRepository.DEFAULT_SORT)
		);
		assertFalse(vacancies.isEmpty());
		assertEquals(2, vacancies.size());
		assertTrue(vacancies.stream().map(Vacancy::getId).anyMatch(id -> 201L == id));
		assertTrue(vacancies.stream().map(Vacancy::getId).anyMatch(id -> 202L == id));
		vacancies.forEach(System.out::println);
		vacancies.stream().map(Vacancy::getId).forEach(System.out::println);
	}

	@Test
	public void customSort() {
		List<Vacancy> vacancies = repository.list(
				null,
				"%Відпустка згідно КЗпП%",
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				PageRequest.of(0, 25, Sort.by(Sort.Direction.DESC, "employer.locationAddress.street.settlement.searchValue"))
		);
		assertFalse(vacancies.isEmpty());
		assertEquals(12, vacancies.size());
		assertTrue(vacancies.stream().map(Vacancy::getId).anyMatch(id -> 402L == id));
		assertTrue(vacancies.stream().map(Vacancy::getId).anyMatch(id -> 403L == id));
		vacancies.stream().map(Vacancy::getId).forEach(System.out::println);
	}
}