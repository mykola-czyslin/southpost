/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Search form for employers.
 * Created by mchys on 26.08.2018.
 */
public class EmployerSearchForm extends SettlementSearchForm {
	private String employerNamePattern;
	private boolean webSiteSignificant;
	private String webSiteAddressPattern;

	public EmployerSearchForm() {
	}

	public EmployerSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getEmployerNamePattern() {
		return employerNamePattern;
	}

	public void setEmployerNamePattern(String employerNamePattern) {
		this.employerNamePattern = employerNamePattern;
	}

	public boolean isWebSiteSignificant() {
		return webSiteSignificant;
	}

	public void setWebSiteSignificant(boolean webSiteSignificant) {
		this.webSiteSignificant = webSiteSignificant;
	}

	public String getWebSiteAddressPattern() {
		return webSiteAddressPattern;
	}

	public void setWebSiteAddressPattern(String webSiteAddressPattern) {
		this.webSiteAddressPattern = webSiteAddressPattern;
	}
}
