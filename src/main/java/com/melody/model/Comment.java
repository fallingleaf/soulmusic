package com.melody.model;
import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
	private long id;
	private String msg;
	private User user;
	private Post post;
	
	
	public Comment() {
	}

	public Comment(String msg, User user, Post post) {
		this.msg = msg;
		this.user = user;
		this.post = post;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "post_id")
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
}
