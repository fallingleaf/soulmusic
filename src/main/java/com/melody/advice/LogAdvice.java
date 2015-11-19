package com.melody.advice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.melody.logger.MelodyLogger;
import com.melody.model.Music;

@Component
@Aspect
public class LogAdvice {
	
	@Before("execution(* com.melody.service.MusicService.deleteMusic(..)) && args(music)")
	public void logCall(JoinPoint point, Music music) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String now = dateFormat.format(date);
		System.out.println("call logger");
		MelodyLogger logger = new MelodyLogger();
		logger.log("music delete: " + music.getTitle() + " on " + now);
	}
	
}
