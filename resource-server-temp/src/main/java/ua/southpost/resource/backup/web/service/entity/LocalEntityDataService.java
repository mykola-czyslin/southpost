package ua.southpost.resource.backup.web.service.entity;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Optional;

class LocalEntityDataService<T extends EntityInfo<I>, S extends Identity<I>, I> implements EntityDataService<T, I> {
	@Nonnull
	private final JpaRepository<S, I> repository;
	@Nonnull
	private final LocalizedEntityMapper<S, T, I> entityInfoMapper;

	LocalEntityDataService(@Nonnull JpaRepository<S, I> repository, @Nonnull LocalizedEntityMapper<S, T, I> entityInfoMapper) {
		this.repository = repository;
		this.entityInfoMapper = entityInfoMapper;
	}

	@Override
	public Optional<T> byId(@Nonnull I id, Locale locale) {
		return repository.findById(id).map(identity -> entityInfoMapper.map(identity, locale));
	}
}
