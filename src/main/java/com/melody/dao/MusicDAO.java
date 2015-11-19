package com.melody.dao;

import com.melody.model.*;

public interface MusicDAO {
	
	public void createAlbum(String name, User user);
	public void savePlaylist(Playlist pl);
	public void changeMusicAlbum(Music music, Album album);
	public void addMusic(Album album, String name, String url);
	public Music load(long id);
	public void delete(Music music);
}
