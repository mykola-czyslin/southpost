package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.SortOption;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;

public class EntityGridSettings implements Serializable {
	private int pageSize;
	private List<SortOption> sortOptions;

	public EntityGridSettings(int pageSize, @Nonnull List<SortOption> sortOptions) {
		this.pageSize = pageSize;
		this.sortOptions = sortOptions;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<SortOption> getSortOptions() {
		return sortOptions;
	}

	public void setSortOptions(List<SortOption> sortOptions) {
		this.sortOptions = sortOptions;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("pageSize", pageSize)
				.append("sortOptions", sortOptions)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof EntityGridSettings)) return false;

		EntityGridSettings that = (EntityGridSettings) o;

		return new EqualsBuilder()
				.append(pageSize, that.pageSize)
				.append(sortOptions, that.sortOptions)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(pageSize)
				.append(sortOptions)
				.toHashCode();
	}
}
