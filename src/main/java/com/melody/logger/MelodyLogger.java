package com.melody.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MelodyLogger implements ILogger{
	private static final Logger logger = LoggerFactory.getLogger("Melody");
	@Override
	public void log(String logstring) {
		logger.info(logstring);
	}

}
