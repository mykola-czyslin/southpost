/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.model.entity.Identity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * Describes the region entity
 * Created by mchys on 23.02.2018.
 */
@Entity
@Table(name = "address_region")
@NamedQueries(
		{
				@NamedQuery(
						name = "region.all.orderByName",
						query = "select r from Region r order by r.searchValue"
				),
				@NamedQuery(
						name = "region.likeName",
						query = "select r from Region r where r.searchValue like upper(:namePattern)  escape '\\\\' order by r.searchValue"
				)
		}
)
public class Region implements Identity<String> {
	@Id
	@Column(name = "id", nullable = false)
	private String id;
	@Column(name = "display_name", nullable = false, length = 40)
	private String displayName;
	@SortField("label.common.name")
	@Column(name = "search_value", nullable = false, length = 40)
	private String searchValue;
	@OneToMany(mappedBy = "region")
	private List<District> districts;

	@JsonProperty(value = "id", required = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty(value = "display_name", required = true)
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@JsonProperty(value = "search_value", required = true)
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = upperCase(searchValue);
	}

	@JsonIgnore
	public List<District> getDistricts() {
		return districts;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("displayName", displayName)
				.append("searchValue", searchValue)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Region)) return false;

		Region region = (Region) o;

		return new EqualsBuilder()
				.append(id, region.id)
				.append(searchValue, region.searchValue)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(searchValue)
				.toHashCode();
	}
}
