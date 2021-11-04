package com.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.SearchVO;
import com.example.service.SearchService;

@RestController
@RequestMapping("/search/*")
public class SearchRestController {
	
	@Autowired
	private SearchService searchService;
	
	
	// 인기 검색어 가져오기
	@GetMapping(value = "/popular",
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<SearchVO>> getPopularKeyword() {
		System.out.println("인기 검색어 접근");
		List<SearchVO> searchList = searchService.getPopularSearch();
		
		return new ResponseEntity<List<SearchVO>> (searchList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{keyword}", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, Object>> addKeyword(@PathVariable("keyword") String keyword) {
		System.out.println("키워드 접근 ");
		int count = searchService.existKeyword(keyword);
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(count == 0) {
			SearchVO searchVO = new SearchVO();
			searchVO.setKeyword(keyword);
			searchVO.setCount(1);
			searchVO.setRegDate(new Date());
			searchService.addKeyword(searchVO);
			msg = "키워드등록";
		}else {
			SearchVO searchVO = searchService.getSeachVO(keyword);
			searchVO.setRegDate(new Date());
			searchService.updateCount(searchVO);
			msg = "카운트 1 증가";
		}
		List<SearchVO> searchList = searchService.getPopularSearch();
		
		map.put("msg", msg);
		map.put("searchList", searchList);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
	}
	
	
}
