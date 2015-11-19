package com.melody.dao;

import com.melody.model.Album;

public interface AlbumDAO {
	

	public Album loadAlbum(long id);

	public long saveAlbum(Album album);
}
