package com.melody.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.melody.model.Album;
import com.melody.model.Music;
import com.melody.model.Playlist;
import com.melody.model.User;
import com.melody.model.Post;
import com.melody.model.Comment;

@Repository
public class MusicDaoImpl implements MusicDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void createAlbum(String name, User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void savePlaylist(Playlist pl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeMusicAlbum(Music music, Album album) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMusic(Album album, String name, String url) {
		Music music = new Music(name, url, album);
		sessionFactory.getCurrentSession().persist(music);
		sessionFactory.getCurrentSession().persist(album);
		
	}

	@Override
	public Music load(long id) {
		return sessionFactory.getCurrentSession().load(Music.class, id);
	}
	
	public void deletePost(Post post) {
		for(Comment comment: post.getComments()) {
			comment.setUser(null);
			sessionFactory.getCurrentSession().delete(comment);
		}
		post.setUser(null);
		sessionFactory.getCurrentSession().delete(post);
	}

	@Override
	public void delete(Music music) {
		Album album = music.getAlbum();
		album.getMusics().remove(music);
		music.setAlbum(null);
		for(Post post: music.getPosts()) {
			deletePost(post);
		}
		//sessionFactory.getCurrentSession().persist(album);
		sessionFactory.getCurrentSession().delete(music);
	}

}
