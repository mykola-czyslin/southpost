/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.DwellingKind;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;

/**
 * The search form that transferred from dwelling list page.
 * Created by mchys on 22.04.2018.
 */
public class DwellingSearchForm extends SettlementSearchForm {
	private String settlementAreaPattern;
	private DwellingKind dwellingKind;
	private Integer numberOfRoomsFrom;
	private Integer numberOfRoomsTo;
	private BigDecimal totalAreaFrom;
	private BigDecimal totalAreaTo;
	private BigDecimal livingAreaFrom;
	private BigDecimal livingAreaTo;
	private String contactPhonePattern;
	private String contactEmailPattern;
	private BillingPeriod billingPeriod;
	private BigDecimal priceFrom;
	private BigDecimal priceTo;

	public DwellingSearchForm() {
	}

	public DwellingSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getSettlementAreaPattern() {
		return settlementAreaPattern;
	}

	public void setSettlementAreaPattern(String settlementAreaPattern) {
		this.settlementAreaPattern = settlementAreaPattern;
	}

	public DwellingKind getDwellingKind() {
		return dwellingKind;
	}

	public void setDwellingKind(DwellingKind dwellingKind) {
		this.dwellingKind = dwellingKind;
	}

	public Integer getNumberOfRoomsFrom() {
		return numberOfRoomsFrom;
	}

	public void setNumberOfRoomsFrom(Integer numberOfRoomsFrom) {
		this.numberOfRoomsFrom = numberOfRoomsFrom;
	}

	public Integer getNumberOfRoomsTo() {
		return numberOfRoomsTo;
	}

	public void setNumberOfRoomsTo(Integer numberOfRoomsTo) {
		this.numberOfRoomsTo = numberOfRoomsTo;
	}

	public BigDecimal getTotalAreaFrom() {
		return totalAreaFrom;
	}

	public void setTotalAreaFrom(BigDecimal totalAreaFrom) {
		this.totalAreaFrom = totalAreaFrom;
	}

	public BigDecimal getTotalAreaTo() {
		return totalAreaTo;
	}

	public void setTotalAreaTo(BigDecimal totalAreaTo) {
		this.totalAreaTo = totalAreaTo;
	}

	public BigDecimal getLivingAreaFrom() {
		return livingAreaFrom;
	}

	public void setLivingAreaFrom(BigDecimal livingAreaFrom) {
		this.livingAreaFrom = livingAreaFrom;
	}

	public BigDecimal getLivingAreaTo() {
		return livingAreaTo;
	}

	public void setLivingAreaTo(BigDecimal livingAreaTo) {
		this.livingAreaTo = livingAreaTo;
	}

	public String getContactPhonePattern() {
		return contactPhonePattern;
	}

	public void setContactPhonePattern(String contactPhonePattern) {
		this.contactPhonePattern = contactPhonePattern;
	}

	public String getContactEmailPattern() {
		return contactEmailPattern;
	}

	public void setContactEmailPattern(String contactEmailPattern) {
		this.contactEmailPattern = contactEmailPattern;
	}

	public BillingPeriod getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(BillingPeriod billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.appendSuper(super.toString())
				.append("settlementAreaPattern", settlementAreaPattern)
				.append("dwellingKind", dwellingKind)
				.append("numberOfRoomsFrom", numberOfRoomsFrom)
				.append("numberOfRoomsTo", numberOfRoomsTo)
				.append("totalAreaFrom", totalAreaFrom)
				.append("totalAreaTo", totalAreaTo)
				.append("livingAreaFrom", livingAreaFrom)
				.append("livingAreaTo", livingAreaTo)
				.append("contactPhonePattern", contactPhonePattern)
				.append("contactEmailPattern", contactEmailPattern)
				.append("billingPeriod", billingPeriod)
				.append("priceFrom", priceFrom)
				.append("priceTo", priceTo)
				.toString();
	}
}
