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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * Defines an address location entity.
 * Created by mchys on 23.02.2018.
 */
@Entity
@Table(name = "address_location")
@NamedQueries({
		@NamedQuery(
				name = "location.street_count",
				query = "select count(l) from Location as l" +
						"\n where l.street is not null" +
						"\n   and l.street.id = :streetId"
		),
		@NamedQuery(
				name = "location.street_list",
				query = "select l from Location as l" +
						"\n where l.street.id = :streetId" +
						"\n order by l.streetNumber, l.blockNumber"
		),
		@NamedQuery(
				name = "location.find_location",
				query = "select l from Location as l" +
						"\n where l.street.id = :streetId" +
						"\n   and l.searchStreetNumber = upper(:streetNumber)" +
						"\n   and ((:blockNumber is null and l.blockNumber is null) or (:blockNumber is not null and l.searchBlockNumber = upper(:blockNumber)))" +
						"\n   and ((:roomNumber is null and l.roomNumber is null) or (:roomNumber is not null and l.searchRoomNumber = upper(:roomNumber)))"
		),
		@NamedQuery(
				name = "location.totalCount",
				query = "select count(l) from Location as l\n " +
						"where (:regionId is null or l.street.settlement.district.region.id = :regionId)\n" +
						"  and (:districtId is null or l.street.settlement.district.id = :districtId)\n" +
						"  and (:settlementKind is null or l.street.settlement.kind = :settlementKind)\n" +
						"  and (:settlementPattern is null or l.street.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
						"  and (:streetKind is null or l.street.kind = :streetKind)\n " +
						"  and (:streetPattern is null or l.street.searchValue like upper(:streetPattern) escape '\\\\')\n " +
						"  and (:postal is null or l.postalCode like :postal escape '\\\\')\n " +
						"  and (:streetNumber is null or l.searchStreetNumber like upper(:streetNumber) escape '\\\\') \n " +
						"  and (:blockNumber is null or l.searchBlockNumber like upper(:blockNumber) escape '\\\\')\n " +
						"  and (:roomNumber is null or l.searchRoomNumber like upper(:roomNumber) escape '\\\\')"
		),
		@NamedQuery(
				name = "location.totalList",
				query = "select l from Location as l\n " +
						"where (:regionId is null or l.street.settlement.district.region.id = :regionId)\n " +
						"  and (:districtId is null or l.street.settlement.district.id = :districtId)\n " +
						"  and (:settlementKind is null or l.street.settlement.kind = :settlementKind)\n " +
						"  and (:settlementPattern is null or l.street.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
						"  and (:streetKind is null or l.street.kind = :streetKind)\n " +
						"  and (:streetPattern is null or l.street.searchValue like upper(:streetPattern) escape '\\\\')\n " +
						"  and (:postal is null or l.postalCode like upper(:postal) escape '\\\\')\n " +
						"  and (:streetNumber is null or l.searchStreetNumber like upper(:streetNumber) escape '\\\\')\n " +
						"  and (:blockNumber is null or l.searchBlockNumber like upper(:blockNumber) escape '\\\\')\n " +
						"  and (:roomNumber is null or l.searchRoomNumber like upper(:roomNumber) escape '\\\\')\n " +
						"order by l.postalCode, l.street.searchValue, l.searchStreetNumber, l.searchRoomNumber, l.roomNumber"
		)
})
public class Location implements Identity<Long> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField(label = "label.location.street", complex = true)
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "STREET_ID", referencedColumnName = "ID")
	})
	private Street street;
	@SortField("label.street.postal")
	@Column(name = "POSTAL_CODE", nullable = false, length = 5)
	private String postalCode;
	@Column(name = "STREET_NUMBER", nullable = false, length = 10)
	private String streetNumber;
	@SortField("label.street.number")
	@Column(name = "SEARCH_STREET_NUMBER", nullable = false, length = 10)
	private String searchStreetNumber;
	@Column(name = "BLOCK_NUMBER", length = 10)
	private String blockNumber;
	@SortField("label.block.number")
	@Column(name = "SEARCH_BLOCK_NUMBER", length = 10)
	private String searchBlockNumber;
	@Column(name = "ROOM_NUMBER", length = 10)
	private String roomNumber;
	@SortField("label.room.number")
	@Column(name = "SEARCH_ROOM_NUMBER", length = 10)
	private String searchRoomNumber;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "street", required = true)
	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	@JsonProperty("postal_code")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@JsonProperty(value = "street_number", required = true)
	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String mainIdentifier) {
		this.streetNumber = mainIdentifier;
	}

	@JsonProperty(value = "search_street_number", required = true)
	public String getSearchStreetNumber() {
		return searchStreetNumber;
	}

	public void setSearchStreetNumber(String searchStreetNumber) {
		this.searchStreetNumber = upperCase(searchStreetNumber);
	}

	@JsonProperty("block_number")
	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	@JsonProperty("search_block_number")
	public String getSearchBlockNumber() {
		return searchBlockNumber;
	}

	public void setSearchBlockNumber(String searchBlockNumber) {
		this.searchBlockNumber = searchBlockNumber;
	}

	@JsonProperty("room_number")
	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	@JsonProperty("search_room_number")
	public String getSearchRoomNumber() {
		return searchRoomNumber;
	}

	public void setSearchRoomNumber(String searchRoomNumber) {
		this.searchRoomNumber = searchRoomNumber;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("street", street)
				.append("postalCode", postalCode)
				.append("streetNumber", streetNumber)
				.append("searchStreetNumber", searchStreetNumber)
				.append("blockNumber", blockNumber)
				.append("searchBlockNumber", searchBlockNumber)
				.append("roomNumber", roomNumber)
				.append("searchRoomNumber", searchRoomNumber)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Location)) return false;

		Location location = (Location) o;

		return new EqualsBuilder()
				.append(id, location.id)
				.append(street, location.street)
				.append(postalCode, location.postalCode)
				.append(searchStreetNumber, location.searchStreetNumber)
				.append(searchBlockNumber, location.searchBlockNumber)
				.append(searchRoomNumber, location.searchRoomNumber)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(street)
				.append(postalCode)
				.append(searchStreetNumber)
				.append(searchBlockNumber)
				.append(searchRoomNumber)
				.toHashCode();
	}
}
