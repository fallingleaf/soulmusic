package com.melody.dao;

import com.melody.model.User;
import com.melody.model.UserRole;

public interface UserDAO {
	
	User findByUserName(String username);

	void save(User user);

	void saveRole(UserRole r);
}
