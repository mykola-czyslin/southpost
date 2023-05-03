package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class SortOption implements Serializable {
	@JsonProperty("field_name")
	private String fieldName;
	@JsonProperty("direction")
	private Sort.Direction direction;

	public SortOption(String fieldName, Sort.Direction direction) {
		this.fieldName = fieldName;
		this.direction = direction;
	}

	public SortOption() {
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Sort.Direction getDirection() {
		return direction;
	}

	public void setDirection(Sort.Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("fieldName", fieldName)
				.append("direction", direction)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof SortOption)) return false;

		SortOption that = (SortOption) o;

		return new EqualsBuilder()
				.append(fieldName, that.fieldName)
				.append(direction, that.direction)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(fieldName)
				.append(direction)
				.toHashCode();
	}
}
