package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.commons.model.ChangeType;
import ua.southpost.resource.backup.data.model.EntityHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface definition for the {@link EntityHistory} entity.
 * Created by mchys on 25.12.2018.
 */
public interface EntityHistoryRepository extends JpaRepository<EntityHistory, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "changeTime");

	@Query(name = "entityHistory.particularEntity.count")
	long count(
			@Param("entityType") String entityClass,
			@Param("entityId") String entityId,
			@Param("userId") Long changeUserId,
			@Param("changeType") ChangeType changeType
	);

	@Query("select h from EntityHistory as h\n" +
			"where h.entityType = :entityType\n" +
			"  and h.entityId = :entityId\n " +
			"  and (:userId is null or h.changedBy.id = :userId)\n" +
			"  and (:changeType is null or h.changeType = :changeType)")
	List<EntityHistory> list(
			@Param("entityType") String entityType,
			@Param("entityId") String entityId,
			@Param("userId") Long userId,
			@Param("changeType") ChangeType changeType,
			Pageable pageable
	);

	@Query(name = "entityHistory.entityType.count")
	long count(
			@Param("entityType") String entityType,
			@Param("userId") Long userId,
			@Param("changeType") ChangeType changeType
	);

	@Query("select h from EntityHistory as h\n" +
			"where h.entityType = :entityType\n" +
			"  and (:userId is null or h.changedBy.id = :userId)\n" +
			"  and (:changeType is null or h.changeType = :changeType)")
	List<EntityHistory> list(
			@Param("entityType") String entityType,
			@Param("userId") Long userId,
			@Param("changeType") ChangeType changeType,
			Pageable pageable
	);
}
