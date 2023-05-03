package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Definition of the {@link User} entity repository.
 * Created by mchys on 23.12.2018.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "lastName", "firstName");

	@Query("select u from User u where upper(u.login) = upper(:login)")
	Optional<User> findByLogin(@Param("login") String login);

	@Query("select u from User u where upper(u.email) = upper(:email)")
	Optional<User> findByEmail(@Param("email") String email);

	@Query("select count(u) from User as u where :role member of u.roles")
	long countWithRole(@Param("role") UserRole role);

	@Query("select u from User as u where :role member of u.roles")
	List<User> listWithRole(@Param("role") UserRole role, Pageable pageable);

	@Query("select u from User as u where exists(select r from u.roles as r where r in :setOfRoles)")
	List<User> listWithAnyOfRoles(@Param("setOfRoles") Set<UserRole> roles);

	@Query("select count(u) from User as u\n" +
			       " where (:loginPattern is null or upper(u.login) like upper(:loginPattern) escape '\\\\')\n" +
			       "   and (:emailPattern is null or upper(u.email) like upper(:emailPattern) escape '\\\\')\n" +
			       "   and (:firstNamePattern is null or upper(u.firstName) like upper(:firstNamePattern) escape '\\\\')\n" +
			       "   and (:lastNamePattern is null or upper(u.lastName) like upper(:lastNamePattern) escape '\\\\')\n" +
			       "   and (:registeredAtFrom is null or u.registeredAt >= :registeredAtFrom)\n" +
			       "   and (:registeredAtTo is null or u.registeredAt <= :registeredAtTo)\n" +
			       "   and (:anyRole = true or exists(select r from u.roles as r where r in :roles))\n" +
			       "   and (:anyDeclaredActivity = true or exists(select da from u.declaredActivities as da where da in :declaredActivities))\n" +
			       "   and (:anyConfirmedActivity = true or exists (select ca from u.confirmedActivities as ca where ca in :confirmedActivities))"
	)
	long count(
			@Param("loginPattern") String loginPattern,
			@Param("emailPattern") String emailPattern,
			@Param("firstNamePattern") String firstNamePattern,
			@Param("lastNamePattern") String lastNamePattern,
			@Param("registeredAtFrom") Date registeredAtFrom,
			@Param("registeredAtTo") Date registeredAtTo,
			@Param("anyRole") boolean anyRole,
			@Param("roles") Set<UserRole> roles,
			@Param("anyDeclaredActivity") boolean anyDeclaredActivity,
			@Param("declaredActivities") Set<UserActivityKind> declaredActivities,
			@Param("anyConfirmedActivity") boolean anyConfirmedActivity,
			@Param("confirmedActivities") Set<UserActivityKind> confirmedActivities
	);

	@Query("select u from User as u\n" +
			       " where (:loginPattern is null or upper(u.login) like upper(:loginPattern) escape '\\\\')\n" +
			       "   and (:emailPattern is null or upper(u.email) like upper(:emailPattern) escape '\\\\')\n" +
			       "   and (:firstNamePattern is null or upper(u.firstName) like upper(:firstNamePattern) escape '\\\\')\n" +
			       "   and (:lastNamePattern is null or upper(u.lastName) like upper(:lastNamePattern) escape '\\\\')\n" +
			       "   and (:registeredAtFrom is null or u.registeredAt >= :registeredAtFrom)\n" +
			       "   and (:registeredAtTo is null or u.registeredAt <= :registeredAtTo)\n" +
			       "   and (:anyRole = true or exists(select r from u.roles as r where r in :roles))\n" +
			       "   and (:anyDeclaredActivity = true or exists(select da from u.declaredActivities as da where da in :declaredActivities))\n" +
			       "   and (:anyConfirmedActivity = true or exists (select ca from u.confirmedActivities as ca where ca in :confirmedActivities))"
	)
	List<User> list(
			@Param("loginPattern") String loginPattern,
			@Param("emailPattern") String emailPattern,
			@Param("firstNamePattern") String firstNamePattern,
			@Param("lastNamePattern") String lastNamePattern,
			@Param("registeredAtFrom") Date registeredAtFrom,
			@Param("registeredAtTo") Date registeredAtTo,
			@Param("anyRole") boolean anyRole,
			@Param("roles") Set<UserRole> roles,
			@Param("anyDeclaredActivity") boolean anyDeclaredActivity,
			@Param("declaredActivities") Set<UserActivityKind> declaredActivities,
			@Param("anyConfirmedActivity") boolean anyConfirmedActivity,
			@Param("confirmedActivities") Set<UserActivityKind> confirmedActivities,
			Pageable pageable
	);

}
