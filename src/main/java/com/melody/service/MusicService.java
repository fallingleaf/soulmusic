package com.melody.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melody.dao.*;
import com.melody.model.*;

@Service("musicService")
@Transactional
public class MusicService {
	@Autowired
	private UserDAO userDao;
	@Autowired
	private AlbumDAO albumDao;
	@Autowired
	private MusicDAO musicDao;
	
	
	public AlbumDAO getAlbumDao() {
		return albumDao;
	}

	public void setAlbumDao(AlbumDAO albumDao) {
		this.albumDao = albumDao;
	}
	
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public MusicDAO getMusicDao() {
		return musicDao;
	}

	public void setMusicDao(MusicDAO musicDao) {
		this.musicDao = musicDao;
	}

	public Album addUserAlbum(String username, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getCurrentUser(String name) {
		return userDao.findByUserName(name);
	}

	public Album loadAlbumById(long id) {
		return albumDao.loadAlbum(id);
	}

	public void addAlbumMusic(Album album, String name, String url) {
		musicDao.addMusic(album, name, url);
	}

	public long createNewAlbum(String username, Album album) {
		User user = userDao.findByUserName(username);
		album.setCreator(user);
		return albumDao.saveAlbum(album);	
	}

	public Music loadMusic(long id) {
		return musicDao.load(id);
	}

	public void deleteMusic(Music music) {
		musicDao.delete(music);
	}
	

}
