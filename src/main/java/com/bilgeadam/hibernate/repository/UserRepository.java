package com.bilgeadam.hibernate.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bilgeadam.hibernate.entity.User;
import com.bilgeadam.hibernate.utility.HibernatesUtils;

public class UserRepository implements ICrud<User> {

	private Session session;
	private EntityManager entityManager;
	private CriteriaBuilder criteriaBuilder;
	private Transaction transaction;

	public UserRepository() {
		entityManager = HibernatesUtils.getSessionFactory().createEntityManager();
		criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public void openSession() {
		session = databaseConnectionHibernate();
		transaction = session.beginTransaction();
	}

	public void successClose() {
		transaction.commit();
		session.close();
	}

	public void errorClose() {
		transaction.rollback();
		session.close();
	}

	@Override
	public void save(User t) {
		try {
			openSession();
			session.save(t);
			successClose();
		} catch (Exception e) {
			e.printStackTrace();
			errorClose();
		}
	}

	@Override
	public void update(User t, long id) {

		try {
			openSession();
			t.setId(id);
			session.update(t);
			successClose();
		} catch (Exception e) {
			e.printStackTrace();
			errorClose();
		}
	}

	@Override
	public void delete(long id) {
		Optional<User> user = findById(id);

		if (user.isPresent()) {
			try {
				openSession();
				session.delete(user);
				successClose();
			} catch (Exception e) {
				e.printStackTrace();
				errorClose();
			}
		} else {
			System.err.println("This user is not exist..");
		}
	}

	@Override
	public Optional<User> findById(long id) {
		User tempUser = null;
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

		try {
			tempUser = entityManager.createQuery(criteriaQuery).getSingleResult();
			return Optional.ofNullable(tempUser);
		} catch (Exception e) {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<User> findAll() {
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
