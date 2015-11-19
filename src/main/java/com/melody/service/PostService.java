package com.melody.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melody.dao.MusicDAO;
import com.melody.dao.PostDAO;
import com.melody.dao.UserDAO;
import com.melody.model.Comment;
import com.melody.model.Music;
import com.melody.model.Post;
import com.melody.model.User;

@Service("postService")
@Transactional
public class PostService {
	@Autowired
	private UserDAO userDao;
	@Autowired
	private PostDAO postDao;
	@Autowired
	private MusicDAO musicDao;
	
	public MusicDAO getMusicDao() {
		return musicDao;
	}

	public void setMusicDao(MusicDAO musicDao) {
		this.musicDao = musicDao;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public PostDAO getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDAO postDao) {
		this.postDao = postDao;
	}

	public long addNewPost(String username, long musicId, Post post) {
		User user = userDao.findByUserName(username);
		Music music = musicDao.load(musicId);
		//post.setMusic(music);
		//post.setUser(user);
		return postDao.save(music, user, post);
	}


	public List<Post> listNewPosts() {
		return postDao.findTopPosts();
	}

	public Post load(long id) {
		return postDao.load(id);
	}

	public long addNewComment(long postid, String username, String note) {
		Post post = postDao.load(postid);
		User user = userDao.findByUserName(username);
		Comment comment = postDao.createNewComment(post, user, note);
		return comment.getId();
	}

}
