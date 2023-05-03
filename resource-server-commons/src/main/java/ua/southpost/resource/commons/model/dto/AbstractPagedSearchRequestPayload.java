package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class AbstractPagedSearchRequestPayload implements PagedSearchRequestPayload {
	@JsonProperty("page_num")
	private int pageNum;
	@JsonProperty("lines_per_page")
	private int linesPerPage;
	@JsonProperty("sort_options")
	private List<SortOption> sortOptions;
}
