/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.model.entity.Identity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

/**
 * Describes user object.
 * Created by mchys on 16.02.2018.
 */
@Entity
@Table(name = "user")
@NamedQueries(value = {
		@NamedQuery(name = "user.byLogin", query = "select u from User u where upper(u.login) = upper(:login)"),
		@NamedQuery(name = "user.byEMail", query = "select u from User u where upper(u.email) = upper(:email)"),
		@NamedQuery(
				name = "user.count",
				query = "select count(u) from User as u \n" +
						        " where (:loginPattern is null or upper(u.login) like upper(:loginPattern) escape '\\\\')\n" +
						        "   and (:emailPattern is null or upper(u.email) like upper(:emailPattern) escape '\\\\')\n" +
						        "   and (:firstNamePattern is null or upper(u.firstName) like upper(:firstNamePattern) escape '\\\\')\n" +
						        "   and (:lastNamePattern is null or upper(u.lastName) like upper(:lastNamePattern) escape '\\\\')\n" +
						        "   and (:registeredAtFrom is null or u.registeredAt >= :registeredAtFrom)\n" +
						        "   and (:registeredAtTo is null or u.registeredAt <= :registeredAtTo)\n" +
						        "   and (:viewer is null or :viewer member of u.roles)\n" +
						        "   and (:operator is null or :operator member of u.roles)\n" +
						        "   and (:administrator is null or :administrator member of u.roles)\n" +
						        "   and (:supervisor is null or :supervisor member of u.roles)\n"
		),
		@NamedQuery(
				name = "user.list",
				query = "select u from User as u\n" +
						        " where (:loginPattern is null or upper(u.login) like upper(:loginPattern) escape '\\\\')\n" +
						        "   and (:emailPattern is null or upper(u.email) like upper(:emailPattern) escape '\\\\')\n" +
						        "   and (:firstNamePattern is null or upper(u.firstName) like upper(:firstNamePattern) escape '\\\\')\n" +
						        "   and (:lastNamePattern is null or upper(u.lastName) like upper(:lastNamePattern) escape '\\\\')\n" +
						        "   and (:registeredAtFrom is null or u.registeredAt >= :registeredAtFrom)\n" +
						        "   and (:registeredAtTo is null or u.registeredAt <= :registeredAtTo)\n" +
						        "   and (:viewer is null or :viewer member of u.roles)\n" +
						        "   and (:operator is null or :operator member of u.roles)\n" +
						        "   and (:administrator is null or :administrator member of u.roles)\n" +
						        "   and (:supervisor is null or :supervisor member of u.roles)\n"
		)
})
public class User implements Identity<Long> {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField("label.user.login")
	@Column(name = "LOGIN", nullable = false, unique = true, length = 20)
	private String login;
	@SortField("label.user.e-mail")
	@Column(name = "EMAIL", nullable = false, unique = true, length = 64)
	private String email;
	@Column(name = "PASSWORD", nullable = false, length = 256)
	private String password;
	@SortField("label.user.registered.at")
	@Column(name = "REGISTERED_AT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredAt;
	@SortField("label.user.firstName")
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	@SortField("label.user.lastName")
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	@Column(name = "INTERNAL_USER", nullable = false)
	private boolean internalUser;
	@ElementCollection
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "USER_ID"))
	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	private Set<UserRole> roles;
	@ElementCollection
	@CollectionTable(name = "user_declared_activity", joinColumns = @JoinColumn(name = "USER_ID"))
	@Column(name = "ACTIVITY_KIND")
	@Enumerated(EnumType.STRING)
	private Set<UserActivityKind> declaredActivities;
	@ElementCollection
	@CollectionTable(name = "user_confirmed_activity", joinColumns = @JoinColumn(name = "USER_ID"))
	@Column(name = "ACTIVITY_KIND")
	@Enumerated(EnumType.STRING)
	private Set<UserActivityKind> confirmedActivities;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "login", required = true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonProperty(value = "e_mail", required = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty(value = "first_name", required = true)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty(value = "last_name", required = true)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty(value = "registered_at", required = true)
	public Date getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(Date registeredAt) {
		this.registeredAt = registeredAt;
	}

	@JsonProperty(value = "internal", required = true)
	public boolean isInternalUser() {
		return internalUser;
	}

	public void setInternalUser(boolean internalUser) {
		this.internalUser = internalUser;
	}

	@JsonProperty(value = "roles", required = true)
	public Set<UserRole> getRoles() {
		return roles;
	}


	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	@JsonProperty("declared_activities")
	public Set<UserActivityKind> getDeclaredActivities() {
		return declaredActivities;
	}

	public void setDeclaredActivities(Set<UserActivityKind> kinds) {
		this.declaredActivities = kinds;
	}

	public Set<UserActivityKind> getConfirmedActivities() {
		return confirmedActivities;
	}

	@JsonProperty("confirmed_activities")
	public void setConfirmedActivities(Set<UserActivityKind> confirmedActivities) {
		this.confirmedActivities = confirmedActivities;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				       .append("id", id)
				       .append("login", login)
				       .append("email", email)
				       .append("password", password)
				       .append("registeredAt", registeredAt)
				       .append("firstName", firstName)
				       .append("lastName", lastName)
				       .append("roles", roles)
				       .append("declaredActivities", declaredActivities)
				       .append("confirmedActivities", confirmedActivities)
				       .toString();
	}

	public String displayValue() {
		return String.format("%s (%s %s; %s)", login, firstName, lastName, email);
	}
}
