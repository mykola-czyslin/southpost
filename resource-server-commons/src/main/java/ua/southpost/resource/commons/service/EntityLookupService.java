package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.EntityPage;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.LookupResponse;

import javax.annotation.Nullable;
import java.util.Locale;

public interface EntityLookupService<P extends PagedSearchRequestPayload, E extends EntityInfo<I>, I> {
	EntityPage<E,I> search(@Nullable P searchPayload, Locale locale);

	LookupResponse<E> lookup(P lookupPayload, Locale locale);
}
