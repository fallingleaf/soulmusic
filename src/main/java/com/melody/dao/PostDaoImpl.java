package com.melody.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.melody.model.Comment;
import com.melody.model.Music;
import com.melody.model.Post;
import com.melody.model.User;


@Repository
public class PostDaoImpl implements PostDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Override
	public long save(Music music, User user, Post post) {
		post = (Post)sessionFactory.getCurrentSession().merge(post);
		post.setUser(user);
		post.setMusic(music);
		sessionFactory.getCurrentSession().persist(post);
		//sessionFactory.getCurrentSession().saveOrUpdate(music);
		//sessionFactory.getCurrentSession().saveOrUpdate(user);
		return post.getId();
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Post> findTopPosts() {
		Query q = sessionFactory.getCurrentSession().createQuery("from Post p order by p.id DESC");
		q.setMaxResults(20);
		return q.list();
	}



	@Override
	public Post load(long id) {
		return sessionFactory.getCurrentSession().load(Post.class, id);
	}



	@Override
	public Comment createNewComment(Post post, User user, String note) {
		Comment comment = new Comment(note, user, post);
		sessionFactory.getCurrentSession().persist(comment);
		return comment;
	}

}
