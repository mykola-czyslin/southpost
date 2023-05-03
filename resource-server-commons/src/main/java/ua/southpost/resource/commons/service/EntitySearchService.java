package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.model.entity.Identity;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import java.util.List;

public interface EntitySearchService<P extends PagedSearchRequestPayload, E extends Identity<I>, I> {

	long count(@Nonnull P searchPayload);

	List<E> list(@Nonnull P searchPayload, @Nonnull Pageable pageable);
}
