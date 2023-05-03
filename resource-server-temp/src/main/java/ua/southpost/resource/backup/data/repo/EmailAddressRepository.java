package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.EmailAddress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The repository interface definition for the {@link EmailAddress} entity.
 * Created by mchys on 25.12.2018.
 */
public interface EmailAddressRepository extends JpaRepository<EmailAddress, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "description");

	@Query("select count(ea) from EmailAddress as ea \n" +
			"where (:addressPattern is null or ea.emailAddress like lower(:addressPattern) escape '\\\\')\n" +
			"  and (:descriptionPattern is null or upper(ea.description) like upper(:descriptionPattern) escape '\\\\')")
	long count(@Param("addressPattern") String emailAddressPattern, @Param("descriptionPattern") String descriptionPattern);

	@Query("select ea from EmailAddress as ea \n" +
			"where (:addressPattern is null or ea.emailAddress like lower(:addressPattern) escape '\\\\')\n" +
			"  and (:descriptionPattern is null or upper(ea.description) like upper(:descriptionPattern) escape '\\\\')")
	List<EmailAddress> list(@Param("addressPattern") String emailAddressPattern, @Param("descriptionPattern") String descriptionPattern, Pageable pageable);

	@Query("select ea from EmailAddress ea \n " +
			"where ea.emailAddress = lower(:emailAddress)")
	Optional<EmailAddress> findByAddress(@Param("emailAddress") String emailAddress);
}
