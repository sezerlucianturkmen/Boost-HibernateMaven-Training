package com.bilgeadam.hibernate.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

	private String firstName;
	private String middleName;
	private String lastName;

}
