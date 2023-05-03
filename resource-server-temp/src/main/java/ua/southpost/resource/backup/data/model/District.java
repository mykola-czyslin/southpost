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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * Entity describes districts
 * Created by mchys on 23.02.2018.
 */
@Entity
@Table(name = "address_district")
@NamedQueries({
		@NamedQuery(name = "district.all_of_region",
				query = "select d from District d where d.region.id = :regionId order by d.searchValue"),
		@NamedQuery(name = "district.match_pattern_for_region",
				query = "select d from District d where d.region.id = :regionId and d.searchValue like upper(:pattern) escape '\\\\' order by d.searchValue")
})
public class District implements Identity<Long> {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField(label = "label.district.region", complex = true)
	@ManyToOne(optional = false)
	@JoinColumn(name = "REGION_ID", referencedColumnName = "id", nullable = false)
	private Region region;
	@Column(name = "DISPLAY_NAME", nullable = false, length = 60)
	private String displayName;
	@SortField("label.common.name")
	@Column(name = "SEARCH_VALUE", nullable = false, length = 60)
	private String searchValue;
	@Column(name = "MOCK_DISTRICT", nullable = false)
	@Enumerated(EnumType.STRING)
	private NoYes mockDistrict;
	@OneToMany(mappedBy = "district")
	private List<Settlement> settlements;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "region", required = true)
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
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

	@JsonProperty(value = "mock_district", required = true)
	public NoYes getMockDistrict() {
		return mockDistrict;
	}

	@SuppressWarnings("unused")
	public void setMockDistrict(NoYes mockDistrict) {
		this.mockDistrict = mockDistrict;
	}

	@JsonIgnore
	public List<Settlement> getSettlements() {
		return settlements;
	}

	@JsonIgnore
	String getQualifiedDisplayName() {
		return (getMockDistrict().isValue() ? "" : getDisplayName() + ", ") +
				getRegion().getDisplayName();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("region", region)
				.append("displayName", displayName)
				.append("searchValue", searchValue)
				.append("mockDistrict", mockDistrict)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof District)) return false;

		District district = (District) o;

		return new EqualsBuilder()
				.append(id, district.id)
				.append(region, district.region)
				.append(searchValue, district.searchValue)
				.append(mockDistrict, district.mockDistrict)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(region)
				.append(searchValue)
				.append(mockDistrict)
				.toHashCode();
	}
}
