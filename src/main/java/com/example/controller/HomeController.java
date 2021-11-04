package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@GetMapping(value = {"/","/index"})
	public String home() {
		System.out.println("home() 호출됨...");


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
