package com.bilgeadam.hibernate.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bilgeadam.hibernate.entity.Post;
import com.bilgeadam.hibernate.utility.HibernatesUtils;

public class PostRepository implements ICrud<Post> {

	private Session session;
	private EntityManager entityManager;
	private CriteriaBuilder criteriaBuilder;
	private Transaction transaction;

	public PostRepository() {
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
	public void save(Post t) {
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
	public void update(Post t, long id) {

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
		Optional<Post> post = findById(id);

		if (post.isPresent()) {
			try {
				openSession();
				session.delete(post);
				successClose();
			} catch (Exception e) {
				e.printStackTrace();
				errorClose();
			}
		} else {
			System.err.println("This Post is not exist..");
		}
	}

	@Override
	public Optional<Post> findById(long id) {
		Post tempPost = null;
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

		try {
			tempPost = entityManager.createQuery(criteriaQuery).getSingleResult();
			return Optional.ofNullable(tempPost);
		} catch (Exception e) {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<Post> findAll() {
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		criteriaQuery.select(root);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
