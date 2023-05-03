package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode
@ToString
public class PagedSearchResponsePayload<R extends PagedSearchRequestPayload, E extends EntityInfo<I>, I> {
    @JsonProperty("request")
    private R request;
    @JsonProperty("page_data")
    private EntityGridPage<E, I> pageData;
    @JsonProperty("entity_sort_options")
    private Map<String, String> entitySortOptions;
    @JsonProperty("sort_directions")
    private Map<String, String> sortDirections;
}
