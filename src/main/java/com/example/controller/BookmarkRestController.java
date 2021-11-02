package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.BookmarkService;

@RestController
@RequestMapping("/api/*")
public class BookmarkRestController {
	
	@Autowired
	private BookmarkService bookmarkService;
	
	
	@PostMapping(value = "/bookmark/{bookmarkValue}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> addOrDeleteBookmark(@PathVariable("bookmarkValue") String bookmarkValue, 
			HttpSession session) {
		System.out.println(bookmarkValue);
		String id = (String) session.getAttribute("id");
		System.out.println(id);
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // 
}
