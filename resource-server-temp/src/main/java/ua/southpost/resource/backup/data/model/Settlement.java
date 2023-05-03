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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * The settlement entity.
 * Created by mchys on 23.02.2018.
 */
@Entity
@Table(name = "address_settlement")
@NamedQueries({
		@NamedQuery(
				name = "settlement.list_by_region",
				query = "select s from Settlement as s\n" +
						"where s.district.region.id = :regionId\n" +
						"order by s.searchValue"
		),
		@NamedQuery(
				name = "settlement.count_by_region",
				query = "select count(s) from Settlement as s\n" +
						"where s.district.region.id = :regionId"
		),
		@NamedQuery(
				name = "settlement.list_by_district",
				query = "select s from Settlement as s\n" +
						"where s.district.id = :districtId\n" +
						"order by s.searchValue"
		),
		@NamedQuery(
				name = "settlement.count_by_district",
				query = "select count(s) from Settlement as s\n" +
						"where s.district.id = :districtId"
		),
		@NamedQuery(
				name = "settlement.like_pattern_in_region",
				query = "select s from Settlement as s\n" +
						"where s.district.region.id = :regionId\n" +
						"  and s.searchValue like upper(:pattern) escape '\\\\' \n" +
						" order by s.searchValue"
		),
		@NamedQuery(
				name = "settlement.fully_qualified_search",
				query = "select s from Settlement as s \n" +
						" where (:regionId is null or s.district.region.id = :regionId) \n " +
						"   and (:districtId is null or s.district.id = :districtId)\n" +
						"   and (:kind is null or s.kind = :kind) \n" +
						"   and s.searchValue like upper(:pattern)\n" +
						" order by s.searchValue asc "

		),
		@NamedQuery(
				name = "settlement.findUnique",
				query = "select s from Settlement as s \n" +
						" where s.district.id = :districtId\n" +
						"   and s.kind = :kind \n" +
						"   and s.searchValue = upper(:name)"

		),
		@NamedQuery(
				name = "settlement.totalCount",
				query = "select count(s) from Settlement as s\n " +
						"where (:regionId is null or s.district.region.id = :regionId)\n" +
						"  and (:districtId is null or s.district.id = :districtId)\n" +
						"  and (:kind is null or s.kind = :kind)\n" +
						"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')"
		),
		@NamedQuery(
				name = "settlement.totalList",
				query = "select s from Settlement as s\n " +
						"where (:regionId is null or s.district.region.id = :regionId)\n" +
						"  and (:districtId is null or s.district.id = :districtId)\n" +
						"  and (:kind is null or s.kind = :kind)\n" +
						"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')\n " +
						"order by s.searchValue"
		)
})
public class Settlement implements Identity<Long> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField(label = "label.settlement.district", complex = true)
	@ManyToOne(optional = false)
	@JoinColumns({
			@JoinColumn(name = "DISTRICT_ID", referencedColumnName = "ID")
	})
	private District district;
	@SortField("label.common.kind")
	@Column(name = "SETTLEMENT_KIND", nullable = false)
	@Enumerated(EnumType.STRING)
	private SettlementKind kind;
	@Column(name = "DISPLAY_NAME", nullable = false, length = 80)
	private String displayName;
	@SortField("label.common.name")
	@Column(name = "SEARCH_VALUE", nullable = false, length = 80)
	private String searchValue;
	@OneToMany(mappedBy = "settlement")
	private List<Street> streets;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Region getRegion() {
		return Optional.ofNullable(district).map(District::getRegion).orElse(null);
	}

	@JsonProperty(value = "district", required = true)
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@JsonProperty(value = "kind", required = true)
	public SettlementKind getKind() {
		return kind;
	}

	public void setKind(SettlementKind kind) {
		this.kind = kind;
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
	public List<Street> getStreets() {
		return streets;
	}

	@JsonIgnore
	public String getQualifiedDisplayName() {
		return getDisplayName() + ", " +
				district.getQualifiedDisplayName();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("district", district)
				.append("kind", kind)
				.append("displayName", displayName)
				.append("searchValue", searchValue)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Settlement)) return false;

		Settlement that = (Settlement) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(district, that.district)
				.append(kind, that.kind)
				.append(searchValue, that.searchValue)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(district)
				.append(kind)
				.append(searchValue)
				.toHashCode();
	}
}
