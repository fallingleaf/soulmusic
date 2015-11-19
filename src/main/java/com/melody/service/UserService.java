package com.melody.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.melody.dao.UserDAO;
import com.melody.model.User;
import com.melody.model.UserRole;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDAO userDao;
	
	private final String ROLE_USER = "ROLE_USER";
	private final String ROLE_ADMIN = "ROLE_ADMIN";
	
	
	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}


	public void createUserAndRole(User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		user.setEnabled(true);
		UserRole r = new UserRole(ROLE_USER, user);
		userDao.saveRole(r);
		userDao.save(user);
	}

}
