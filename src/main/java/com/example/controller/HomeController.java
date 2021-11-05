package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.SearchVO;
import com.example.service.SearchService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping(value = {"/","/index"})
	public String home(HttpSession session) {
		System.out.println("home() 호출됨...");
		List<SearchVO> searchList = searchService.getPopularSearch();
		
		if(session.getAttribute("searchList") == null) {
			if(searchList != null) {
				session.setAttribute("searchList", searchList);
			}
		}
		
		return "index";
//		return "redirect:index";
	}
	
	@GetMapping("/imgView")
	@ResponseBody
	public ResponseEntity<byte[]> getImageFile(String fileName) throws IOException {
		
		File source = new File("C:/project_mystreaming/upload", fileName);
		
		HttpHeaders headers = new HttpHeaders();
		
		String contentType = Files.probeContentType(source.toPath());
		headers.add("Content-Type", contentType);
		
		byte[] image = FileCopyUtils.copyToByteArray(source);
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
		
		return entity;
	} // getImageFile
	
}
