package com.bilgeadam.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;

import com.bilgeadam.hibernate.utility.HibernatesUtils;

public interface ICrud<T> {

	void save(T t);

	void update(T t, long id);

	void delete(long id);

	Optional<T> findById(long id);

	List<T> findAll();

	default Session databaseConnectionHibernate() {
		return HibernatesUtils.getSessionFactory().openSession();
	}

}
