package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.SortOptionsPayload;
import org.springframework.data.domain.Sort;

import javax.annotation.Nonnull;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

class SortServiceImpl<P extends SortOptionsPayload> implements SortService<P> {
	@Nonnull
	private final Class<P> payloadType;
	@Nonnull
	private final Sort defaultSort;

	SortServiceImpl(@Nonnull Class<P> payloadType, @Nonnull Sort defaultSort) {
		this.payloadType = payloadType;
		this.defaultSort = defaultSort;
	}

	@Nonnull
	@Override
	public Sort sort(P sortPayload) {
		return ofNullable(sortPayload).map(this::parameterizedSort).orElse(this.defaultSort);
	}

	@Nonnull
	private Sort parameterizedSort(@Nonnull P sortPayload) {
		assert payloadType.isInstance(sortPayload) : "Never should occur: sortPayload should be instance of " + payloadType;
		return isEmpty(sortPayload.getSortOptions()) ? defaultSort : buildSort(sortPayload);
	}

	private Sort buildSort(@Nonnull P sortPayload) {
		return sortPayload.getSortOptions()
				       .stream()
				       .map(o -> Sort.by(o.getDirection(), o.getFieldName()))
				       .reduce(Sort::and)
				       .orElse(defaultSort);
	}
}
