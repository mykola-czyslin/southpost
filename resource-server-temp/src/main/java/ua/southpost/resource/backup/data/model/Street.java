/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

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
import javax.persistence.Table;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * The street entity.
 * Created by mchys on 23.02.2018.
 */
@Entity
@Table(name = "address_street")
@NamedQueries({
		@NamedQuery(
				name = "street.all_of_settlement",
				query = "select s from Street as s\n" +
						"where s.settlement.id = :settlementId\n" +
						"order by s.searchValue"
		),
		@NamedQuery(
				name = "street.count_all_of_settlement",
				query = "select count(s) from Street as s\n" +
						"where s.settlement.id = :settlementId"
		),
		@NamedQuery(
				name = "street.all_of_settlement_like_pattern",
				query = "select s from Street as s\n" +
						"where s.settlement.id = :settlementId\n" +
						"  and s.searchValue like upper(:pattern) escape '\\\\' \n" +
						"order by s.searchValue"
		),
		@NamedQuery(
				name = "street.count_all_of_settlement_like_pattern",
				query = "select count(s) from Street as s\n" +
						"where s.settlement.id = :settlementId\n" +
						"  and s.searchValue like upper(:pattern) escape '\\\\' "
		),
		@NamedQuery(
				name = "street.all_of_settlement_like_pattern_of_kind",
				query = "select s from Street as s\n" +
						"where s.settlement.id = :settlementId\n" +
						"  and s.searchValue like upper(:pattern) escape '\\\\' \n" +
						"  and s.kind = :kind\n" +
						"order by s.searchValue"
		),
		@NamedQuery(
				name = "street.count_all_of_settlement_like_pattern_of_kind",
				query = "select count(s) from Street as s\n" +
						"where s.settlement.id = :settlementId\n" +
						"  and s.searchValue like upper(:pattern) escape '\\\\' \n" +
						"  and s.kind = :kind"
		),
		@NamedQuery(
				name = "street.findUnique",
				query = "select s from Street as s\n " +
						"where s.searchValue = upper(:name)\n " +
						"  and s.kind = :kind\n " +
						"  and s.settlement.id = :settlementId"
		),
		@NamedQuery(
				name = "street.totalCount",
				query = "select count(s) from Street as s\n " +
						"where (:regionId is null or s.settlement.district.region.id = :regionId)\n" +
						"  and (:districtId is null or s.settlement.district.id = :districtId)\n" +
						"  and (:settlementKind is null or s.settlement.kind = :settlementKind)\n" +
						"  and (:settlementPattern is null or s.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
						"  and (:kind is null or s.kind = :kind)\n " +
						"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')"
		),
		@NamedQuery(
				name = "street.totalList",
				query = "select s from Street as s\n " +
						"where (:regionId is null or s.settlement.district.region.id = :regionId)\n" +
						"  and (:districtId is null or s.settlement.district.id = :districtId)\n" +
						"  and (:settlementKind is null or s.settlement.kind = :settlementKind)\n" +
						"  and (:settlementPattern is null or s.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
						"  and (:kind is null or s.kind = :kind)\n" +
						"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')\n " +
						"order by s.searchValue"
		)
})
public class Street implements Identity<Long> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField(label = "label.settlement.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "SETTLEMENT_ID", referencedColumnName = "ID", nullable = false)
	private Settlement settlement;
	@SortField("label.common.kind")
	@Column(name = "STREET_KIND", nullable = false)
	@Enumerated(EnumType.STRING)
	private StreetKind kind;
	@Column(name = "DISPLAY_NAME", nullable = false, length = 80)
	private String displayName;
	@SortField("label.common.name")
	@Column(name = "SEARCH_VALUE", nullable = false, length = 80)
	private String searchValue;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "settlement", required = true)
	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@JsonProperty(value = "kind", required = true)
	public StreetKind getKind() {
		return kind;
	}

	public void setKind(StreetKind kind) {
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("settlement", settlement)
				.append("kind", kind)
				.append("displayName", displayName)
				.append("searchValue", searchValue)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Street)) return false;

		Street street = (Street) o;

		return new EqualsBuilder()
				.append(id, street.id)
				.append(settlement, street.settlement)
				.append(kind, street.kind)
				.append(searchValue, street.searchValue)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(settlement)
				.append(kind)
				.append(searchValue)
				.toHashCode();
	}
}
