package com.example.task;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.domain.SearchVO;
import com.example.service.SearchService;

@Component
public class popularKeyword {
	
	@Autowired
	private SearchService searchService;
	
	// 인기 검색어 가져오기
	@Scheduled(cron =  "0/10 0 * * * ?")
	public void getPopularKeyword() {
		System.out.println("===============================");
		System.out.println("getPopularKeyword() task run........");
		System.out.println("===============================");
		List<SearchVO> searchList = searchService.getPopularSearch();
		HttpSession session = null;
		session.setAttribute("searchList", searchList);
		
	}
}
