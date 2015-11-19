package com.melody.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melody.dao.UserDAO;
import com.melody.model.UserRole;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserDAO userDao;
	
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		com.melody.model.User user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
		return buildUserForAuthentication(user, authorities);
	}
	
	// Build authentication user
	private User buildUserForAuthentication(com.melody.model.User user, 
			List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}
	
	// Build granted authority list
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> roles) {
		
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		
		for(UserRole role: roles) {
			setAuths.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
		return result;
	}

}
