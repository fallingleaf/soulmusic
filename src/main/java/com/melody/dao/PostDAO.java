package com.melody.dao;

import java.util.List;

import com.melody.model.Comment;
import com.melody.model.Music;
import com.melody.model.Post;
import com.melody.model.User;

public interface PostDAO {

	public long save(Music music, User user, Post post);

	public List<Post> findTopPosts();

	public Post load(long id);

	public Comment createNewComment(Post post, User user, String note);

}
