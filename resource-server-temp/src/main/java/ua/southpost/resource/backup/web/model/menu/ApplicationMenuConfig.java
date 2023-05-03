/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static ua.southpost.resource.backup.web.ApplicationURI.AUTHENTICATION_LOGIN;
import static ua.southpost.resource.backup.web.ApplicationURI.AUTHENTICATION_LOGOUT;
import static ua.southpost.resource.backup.web.ApplicationURI.AUTHENTICATION_REGISTER;
import static ua.southpost.resource.backup.web.ApplicationURI.CLINIC_LIST;
import static ua.southpost.resource.backup.web.ApplicationURI.DWELLING_LIST;
import static ua.southpost.resource.backup.web.ApplicationURI.LAWYER_LIST;
import static ua.southpost.resource.backup.web.ApplicationURI.USER_ADMIN_LIST;
import static ua.southpost.resource.backup.web.ApplicationURI.USER_PROFILE_EDIT;
import static ua.southpost.resource.backup.web.ApplicationURI.VACANCY_LIST;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_CLINIC;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_DWELLING;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_HOME;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_LAWYER_AGENCY;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_ADDRESS;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_CONTACT;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_EMAILS;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_EMPLOYERS;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_LOCATIONS;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_PHONES;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_SETTLEMENT;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MASTER_DATA_STREETS;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_MY_ACCOUNT;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_USERS;
import static ua.southpost.resource.backup.web.model.menu.MenuConstants.MENU_VACANCY;

/**
 * Configures application menu.
 * Created by mchys on 06.03.2018.
 */
@Configuration
public class ApplicationMenuConfig {

	@Bean
	@Scope("prototype")
	AbstractMenu mainMenu() {
		CatalogMenu menu = new CatalogMenu("", "menu_main");
		menu
				.addSubMenu(new ActionMenu(MENU_HOME, "/"))
				.addSubMenu(
						new CatalogMenu(MenuConstants.MENU_INFO, "menu_useful_info")
								.addSubMenu(new ActionMenu(MENU_MASTER_DATA_EMPLOYERS, "/employers/list"))
								.addSubMenu(new ActionMenu(MENU_VACANCY, VACANCY_LIST))
								.addSubMenu(new ActionMenu(MENU_DWELLING, DWELLING_LIST))
								.addSubMenu(new ActionMenu(MENU_CLINIC, CLINIC_LIST))
								.addSubMenu(new ActionMenu(MENU_LAWYER_AGENCY, LAWYER_LIST))
				)
				.addSubMenu(
						new CatalogMenu(MENU_MASTER_DATA, "menu_master_data")
								.addSubMenu(
										new CatalogMenu(MENU_MASTER_DATA_ADDRESS, "menu_master_data_address")
												.addSubMenu(new ActionMenu(MENU_MASTER_DATA_SETTLEMENT, "/master/settlement/list"))
												.addSubMenu(new ActionMenu(MENU_MASTER_DATA_STREETS, "/master/streets/list"))
												.addSubMenu(new ActionMenu(MENU_MASTER_DATA_LOCATIONS, "/master/locations/list"))
								)
								.addSubMenu(
										new CatalogMenu(MENU_MASTER_DATA_CONTACT, "menu_master_data_contacts")
												.addSubMenu(new ActionMenu(MENU_MASTER_DATA_PHONES, "/master/phones/list"))
												.addSubMenu(new ActionMenu(MENU_MASTER_DATA_EMAILS, "/master/emails/list"))
								)
				)
				.addSubMenu(new ActionMenu(MENU_USERS, USER_ADMIN_LIST))
				.addSubMenu(
						new CatalogMenu(MENU_MY_ACCOUNT, "menu_my_account")
								.addSubMenu(new ActionMenu("menu.user.register", AUTHENTICATION_REGISTER))
								.addSubMenu(new ActionMenu("menu.user.login", AUTHENTICATION_LOGIN))
								.addSubMenu(new ActionMenu("menu.profile", USER_PROFILE_EDIT))
								.addSubMenu(new ActionMenu("menu.user.logout", AUTHENTICATION_LOGOUT))
				)
		;
		return menu;

	}

}
