package com.melody.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import com.melody.model.Album;
import com.melody.model.Music;
import com.melody.service.*;

@Controller
public class MusicController {
	@Autowired
	private MusicService musicService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(Model model, @ModelAttribute("album") Album album) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername();
	    
	    com.melody.model.User current = musicService.getCurrentUser(name);
	    List<Album> albums = current.getAlbums();
		model.addAttribute("username", name);
		model.addAttribute("albums", albums);
	    return "profile";
	}
	
	
	@RequestMapping(value = "/music/delete/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteMusic(@PathVariable long id) {
		JSONObject responseBody = new JSONObject();
		HttpStatus httpStatus = HttpStatus.OK;
		Music music = musicService.loadMusic(id);
		if(music == null) {
			responseBody.put("success", false);
			responseBody.put("message", "music not found");
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		else {
			musicService.deleteMusic(music);
			responseBody.put("success", true);
		}
		return new ResponseEntity<String>(responseBody.toString(), httpStatus);
		
	}
	
	@RequestMapping(value = "/addAlbum", method = RequestMethod.POST)
	public String newAlbum(@Valid Album album, BindingResult result, RedirectAttributes re) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String name = user.getUsername();
		if(!result.hasErrors()) {
			long id = musicService.createNewAlbum(name, album);
			re.addFlashAttribute("message", "New album added.");
			return "redirect:/albums/"+id;
		}
		return "profile";
	}
	
	@RequestMapping(value = "/albums/{id}", method = RequestMethod.GET)
	public String viewAlbum(@PathVariable long id, Model model) {
		Album album = musicService.loadAlbumById(id);
		List<Music> musics = album.getMusics();
		model.addAttribute("musics", musics);
		model.addAttribute("album", album);
		return "album";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody RedirectView handleFileUpload(@RequestParam("id") long id,
    		@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs){
		String name = "";
		String path = "";
		Album album = musicService.loadAlbumById(id);
		String url = "/albums/"+album.getId();
		RedirectView v = new RedirectView();
		v.setContextRelative(true);
		v.setUrl(url);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                name = file.getOriginalFilename();
                path = servletContext.getRealPath("/resources/upload/") + name;
                
                System.out.println("path is: "+path);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(path)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                //return "You failed to upload file => " + e.getMessage();
            	redirectAttrs.addFlashAttribute("message", e.getMessage());
            	return v;
            }
            musicService.addAlbumMusic(album, name, path);
            redirectAttrs.addFlashAttribute("message", "Upload successfully");
            return v;
        } else {
        	redirectAttrs.addFlashAttribute("message", "File is empty");
        	return v;
        }
    }
	
	
}
