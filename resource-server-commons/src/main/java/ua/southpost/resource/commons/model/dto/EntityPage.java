package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.EntityInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EntityPage<T extends EntityInfo<I>, I> {
	@JsonProperty("page_count")
	private int pageCount;
	@JsonProperty("page_size")
	private int pageSize;
	@JsonProperty("current_page")
	private int currentPage;
	@JsonProperty("entity_list")
	private List<T> entityList;
}
