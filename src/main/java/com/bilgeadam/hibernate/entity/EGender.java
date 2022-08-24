package com.bilgeadam.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblgender")
public enum EGender {
	MALE, FEMALE;
}
