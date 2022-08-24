package com.bilgeadam.hibernate.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.hibernate.entity.User;
import com.bilgeadam.hibernate.utility.HibernatesUtils;

public class UserDao implements ICrud<User> {

	public void save(User t) {
		Session session = null;

		try {
			session = databaseConnectionHibernate();
			session.getTransaction().begin();
			session.save(t);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Baglanti Hatasi");
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

	}

	public void update(User t, long id) {
		Session session = HibernatesUtils.getSessionFactory().openSession();

		try {
			User user = findById(id).get();
			if (user != null) {

				user.setUsername(t.getUsername());
				user.setGender(t.getGender());
				user.setPassword(t.getPassword());
				session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.merge(user);
				session.getTransaction().commit();
				System.out.println("User has updated ..: " + user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

	}

	public void delete(long id) {
		Session session = HibernatesUtils.getSessionFactory().openSession();

		try {
			Optional<User> user = findById(id);

			if (user != null) {
				session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.remove(user);
				session.getTransaction().commit();
				System.out.println("User has deleted ..: " + user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();

		} finally {
			session.close();
		}
	}

	public Optional<User> findById(long id) {
		Session session = HibernatesUtils.getSessionFactory().openSession();
		User user;
		try {
			user = session.find(User.class, id);

			if (user != null) {
				System.out.println("User has found ..: " + user);
				return Optional.ofNullable(user);
			} else {
				System.out.println("User has not found ..: " + user);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public List<User> findAll() {

		Session session = null;
		try {
			session = databaseConnectionHibernate();
			String query = "select users from User as users ";
			TypedQuery<User> typedQuery = session.createQuery(query, User.class);
			List<User> userList = typedQuery.getResultList();
			for (User user : userList) {
				System.out.println(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.close();
		}
		return null;
	}

}
