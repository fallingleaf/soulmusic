package com.melody.model;
import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
	
	private long id;
	private String role;
	
	private User user;
	
	
	public UserRole() {
	}

	public UserRole(String role, User user) {
		this.role = role;
		this.user = user;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "username", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
