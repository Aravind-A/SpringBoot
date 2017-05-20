package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestApiScheduledTask {
	
	private static final Logger log = LoggerFactory.getLogger(TestApiScheduledTask.class);
	int ctr = 0;
	
	//@Value("${example.scheduledJob.enabled:false}")
	private boolean scheduledJobEnabled = true;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 2000)
	public void pullRandomData(){ 
		if(!scheduledJobEnabled) return;
		
		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		
		log.info(dateFormat.format(new Date()) + " " +  quote.toString());
		ctr++;
		if(ctr == 4) scheduledJobEnabled = false;
	}
}
