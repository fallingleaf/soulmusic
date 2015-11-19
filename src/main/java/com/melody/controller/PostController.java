package com.melody.controller;

import java.util.List;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.melody.service.*;
import com.melody.model.Album;
import com.melody.model.Music;
import com.melody.model.Post;

@Controller
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private MusicService musicService;

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping(value = "/post/music/{id}", method = RequestMethod.GET) 
	public String addNewPost(@PathVariable long id, @ModelAttribute("post") Post post, Model model) {
		Music music = musicService.loadMusic(id);
		if(music == null) {
			return "redirect:/hotmusic";
		}
		model.addAttribute("music", music);
		return "post";
	}
	
	@RequestMapping(value = "/post/music/{id}", method = RequestMethod.POST) 
	public String submitPost(@PathVariable long id, @Valid Post post, BindingResult result, RedirectAttributes ra) {
		//Music music = musicService.loadMusic(id);
		//Album album = music.getAlbum();
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
		if(!result.hasErrors()) {
			postService.addNewPost(username, id, post);
			ra.addFlashAttribute("message", "Post created.");
			return "redirect:/profile";
		}
		return "post";
	}
	
	@RequestMapping(value = "/hotmusic", method = RequestMethod.GET)
	public String listMusic(Model model) {
		List<Post> posts = postService.listNewPosts();
		model.addAttribute("posts", posts);
		return "hot";
	}
	
	@RequestMapping(value = "/addComment/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> newComment(@PathVariable long id, @RequestParam String note) {
		JSONObject responseBody = new JSONObject();
		HttpStatus httpStatus = HttpStatus.OK;
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = user.getUsername();
	    long commentId = postService.addNewComment(id, username, note);
	    responseBody.put("success", true);
	    responseBody.put("id", commentId);
	    responseBody.put("msg", note);
	    responseBody.put("username", username);
		return new ResponseEntity<String>(responseBody.toString(), httpStatus);
		
	}
	
	
	
}
