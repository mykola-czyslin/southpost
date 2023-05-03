package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;

import javax.annotation.Nonnull;

public interface SearchPayloadFactory<R extends PagedSearchRequestPayload> {
    @Nonnull
    R create(@Nonnull EntityGridSettings gridSettings);
}
