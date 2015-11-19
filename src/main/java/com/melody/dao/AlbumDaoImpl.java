package com.melody.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.melody.model.Album;

@Repository
public class AlbumDaoImpl implements AlbumDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public Album loadAlbum(long id) {
		Album a = sessionFactory.getCurrentSession().load(Album.class, id);
		return a;
	}

	@Override
	public long saveAlbum(Album album) {
		sessionFactory.getCurrentSession().persist(album);
		return album.getId();
	}
}
