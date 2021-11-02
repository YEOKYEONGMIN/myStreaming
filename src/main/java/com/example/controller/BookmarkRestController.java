package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.domain.BookmarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.service.BookmarkService;

@RestController
@RequestMapping("/api/*")
public class BookmarkRestController {
	
	@Autowired
	private BookmarkService bookmarkService;
	
	
	@PostMapping(value = "/bookmark",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> addOrDeleteBookmark(@RequestBody BookmarkVO bookmarkVO,
			HttpSession session) {

		String id = (String) session.getAttribute("id");
		System.out.println(id);

		System.out.println(bookmarkVO.getProfile_image_url());
		System.out.println(bookmarkVO.getStreamerId());
		System.out.println(bookmarkVO.getStreamerName());


		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // 
}
