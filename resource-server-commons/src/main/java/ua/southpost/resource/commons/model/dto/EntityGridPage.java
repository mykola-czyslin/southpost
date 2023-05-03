package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.EntityInfo;

import java.util.List;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class EntityGridPage<T extends EntityInfo<I>, I> {
	@JsonProperty("page_size")
	private int pageSize;
	@JsonProperty("model")
	private Model model;
	@JsonProperty("entity_list")
	private List<T> entityList;

	@Data
	@Builder
	@ToString
	@EqualsAndHashCode
	public static class Model {
		@JsonProperty("current_page")
		private int currentPage;
		@JsonProperty("pages")
		private int[] pages;
	}
}
