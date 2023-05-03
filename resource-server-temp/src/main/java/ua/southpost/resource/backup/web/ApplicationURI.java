/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web;

/**
 * The registry of application URI.
 * Created by mchys on 06.03.2018.
 */
public interface ApplicationURI {
	String AUTHENTICATION_REGISTER = "/user/authentication/register";
	String AUTHENTICATION_LOGIN = "/user/authentication/login";
	String AUTHENTICATION_LOGOUT = "/perform-logout";
	String USER_PROFILE_EDIT = "/user/profile/edit";
	String USER_ADMIN_LIST = "/user/admin/list";
	String VACANCY_LIST = "/vacancy/list";
	String VACANCY_ADMIN_CREATE = "/vacancy/manage/create";
	String UNDER_CONSTRUCTION = "/under-construction";
	String DWELLING_LIST = "/dwelling/list";
	String DWELLING_CREATE_FORM = "/dwelling/manage/create";
	String SETTLEMENT_LIST = "/settlement/list";
	String STREET_LIST = "/street/list";
	String LOCATION_LIST = "/location/list";
	String CONTACT_LIST = "/contact/list";
	String EMPLOYER_LIST = "/employer/list";
	String PHONE_LIST = "/contact/phone/list";
	String EMAIL_LIST = "/contact/email/list";
	String CLINIC_LIST = "/clinic/list";
	String CLINIC_CREATE_FORM = "/clinic/manage/create";
	String LAWYER_LIST = "/lawyer/list";
	String LAWYER_CREATE_FORM = "/lawyer/manage/create";
}
