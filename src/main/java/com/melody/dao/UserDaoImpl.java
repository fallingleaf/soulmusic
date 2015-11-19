package com.melody.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.melody.model.User;
import com.melody.model.UserRole;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class UserDaoImpl implements UserDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {
		List<User> users = sessionFactory.getCurrentSession()
				.createQuery("from User where username = :name")
				.setParameter("name", username).list();
		System.out.println("Username is :" + username);
		if(users.size() > 0) {
			return users.get(0);
		}
		return null;
	}



	@Override
	public void save(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}



	@Override
	public void saveRole(UserRole r) {
		sessionFactory.getCurrentSession().persist(r);
	}

}
