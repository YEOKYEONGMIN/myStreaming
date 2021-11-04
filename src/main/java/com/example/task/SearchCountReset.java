package com.example.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.service.SearchService;

@Component
public class SearchCountReset {
	
	@Autowired
	private SearchService searchService;
	
	@Scheduled(cron = "0 0 0/1 * * ?") // 1시간마다 카운트 리셋
	public void countReset() {
		System.out.println("===============================");
		System.out.println("countReset() task run........");
		System.out.println("===============================");
		
		
		searchService.countReset();
		
	}
}
