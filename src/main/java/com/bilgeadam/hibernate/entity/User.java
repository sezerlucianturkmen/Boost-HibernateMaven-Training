package com.bilgeadam.hibernate.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluser")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(length = 32)
	private String password;
	@Enumerated(EnumType.STRING)
	private EGender gender;
	@Embedded
	private Name name;

	@ElementCollection
	Map<EAddressType, Address> addressMap;

	public User(String username, String password, EGender gender) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
	}

	public User(long id, String username, String password, EGender gender) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.gender = gender;
	}

	public User(String username, String password, EGender gender, Name name) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EGender getGender() {
		return gender;
	}

	public void setGender(EGender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender + ", name="
				+ name + "]";
	}

}
