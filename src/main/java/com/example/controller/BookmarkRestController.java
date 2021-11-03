package com.example.controller;

import java.util.HashMap;
import java.util.List;
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
	
	@GetMapping(value = "/bookmark",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> getBookmark(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "Id null";
		String id = (String) session.getAttribute("id");
		System.out.println(id);
		if(id==null) {
			map.put("msg", msg);
			return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		}
		List<BookmarkVO> bookmarkList = bookmarkService.getBookmarkById(id);
		
		map.put("bookmarkList", bookmarkList);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // 
	
	@PostMapping(value = "/bookmark",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> addOrDeleteBookmark(@RequestBody BookmarkVO bookmarkVO,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "failed";
		String id = (String) session.getAttribute("id");
		System.out.println(id);
		if(id==null) {
			map.put("msg", msg);
			return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		}
		bookmarkVO.setMid(id);
		
		int count = bookmarkService.getCountbyIdAndStreamerId(bookmarkVO.getMid(), bookmarkVO.getStreamerId());
		System.out.println(count);
		if(count == 0) {
			bookmarkService.addBookmark(bookmarkVO);
			msg = "즐겨찾기 성공!";
		}else {
			bookmarkService.deleteBoardByIdAndStreamerID(bookmarkVO.getMid(), bookmarkVO.getStreamerId());
			msg = "즐겨찾기 삭제";
		}
		List<BookmarkVO> bookmarkList = bookmarkService.getBookmarkById(id);
		System.out.println(bookmarkList);
		if(bookmarkList != null) {
			session.setAttribute("bookmarkList", bookmarkList);
		}
		map.put("msg", msg);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // 
}
