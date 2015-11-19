package com.melody.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "playlist")
public class Playlist {
	private long id;
	private String name;
	private User user;
	private List<Music> musics = new ArrayList<Music>();
	
	public Playlist() {
	}

	public Playlist(String name, User user) {
		this.name = name;
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
	
	@NotEmpty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "playlist_music")
	public List<Music> getMusics() {
		return musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}
	
	
}
