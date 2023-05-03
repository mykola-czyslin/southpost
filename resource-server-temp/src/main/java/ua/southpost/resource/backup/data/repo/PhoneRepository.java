package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.Phone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The interface definition for the {@link Phone} entity.
 * Created by mchys on 25.12.2018.
 */
public interface PhoneRepository extends JpaRepository<Phone, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "description");

	@Query("select count(ph) from Phone ph\n" +
			"where (:numberPattern is null or ph.searchNumber like upper(:numberPattern)  escape '\\\\')\n " +
			"  and (:descriptionPattern is null or upper(ph.description) like upper(:descriptionPattern)  escape '\\\\')")
	long count(
			@Param("numberPattern") String phoneNumberPattern,
			@Param("descriptionPattern") String descriptionPattern
	);

	@Query("select ph from Phone ph\n" +
			"where (:numberPattern is null or ph.searchNumber like upper(:numberPattern)  escape '\\\\')\n " +
			"  and (:descriptionPattern is null or upper(ph.description) like upper(:descriptionPattern)  escape '\\\\')")
	List<Phone> list(
			@Param("numberPattern") String phoneNumberPattern,
			@Param("descriptionPattern") String descriptionPattern,
			Pageable pageable
	);

	@Query("select ph from Phone as ph\n" +
			"where ph.searchNumber = :searchNumber")
	Optional<Phone> findByNumber(@Param("searchNumber") String phoneNumber);
}
