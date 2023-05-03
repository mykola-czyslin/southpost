package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.SortOptionsPayload;
import org.springframework.data.domain.Sort;

import javax.annotation.Nonnull;

public interface SortService<P extends SortOptionsPayload> {
	@Nonnull
	Sort sort(P sortPayload);
}
