package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository interface definition for the {@link Region} entity.
 * Created by mchys on 25.12.2018.
 */
public interface RegionRepository extends JpaRepository<Region, String> {
}
