package com.example.controller;

import com.example.domain.BookmarkVO;
import com.example.domain.MemberVO;
import com.example.domain.ProfilepicVO;
import com.example.domain.SearchVO;
import com.example.service.BookmarkService;
import com.example.service.MemberService;
import com.example.service.ProfilepicService;
import com.example.service.SearchService;
import com.example.util.JScript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class MemberRestController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private BookmarkService bookmarkService;
	@Autowired
	private ProfilepicService profilepicService;
	
	
	// 모든 멤버 가져오기
	@GetMapping(value = "/members", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<MemberVO>> getMembers() {
		
		List<MemberVO> memberList = memberService.getMembers();
		
		return new ResponseEntity<List<MemberVO>>(memberList, HttpStatus.OK);
		
	} // getMembers
	
	// 아이디와 일치하는 회원정보 가져오기
	@GetMapping(value = "/member/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> getMember(@PathVariable("id") String id) {
		
		MemberVO member = memberService.getMemberById(id);
		int count = memberService.getMemberCount(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("member", member);
		map.put("count", count);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // getMember
	
	
	@PostMapping(value = "/member/login", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberVO memberVO,
			@RequestParam(required = false, defaultValue = "false") boolean rememberId,
			HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = memberVO.getId();
		String passwd = memberVO.getPasswd();
		String msg="";
		System.out.println("id: " + id +" passwd : "+ passwd  );
		// 비밀번호 비교를 위해 들고오기
		MemberVO member = memberService.getMemberById(id);

		int memberCount = memberService.getMemberCount(id);

		boolean checkPasswd = false;
		
		// 일치하는 회원정보가 있으면
		if (memberCount == 1) {
			checkPasswd = BCrypt.checkpw(passwd, member.getPasswd());
			
			// 비밀번호 일치하지 않을시
			if (!checkPasswd) {
				msg = "비밀번호가 일치하지 않습니다.";
			} 
		} else { // 일치하는 회원정보가 없으면
			msg = "존재하지 않는 아이디입니다.";
		}
		
		// 회원정보가 없거니 비밀번호가 일치하지 않을시
		if (memberCount == 0 || !checkPasswd) {
			map.put("msg", msg);
			return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		}
		
		// 아이디가 존재하고 비밀번호 일치시
		session.setAttribute("id", id);

//		ProfilepicVO profilepicVO = profilepicService.getProfilepicById(id);
//		String profile = profilepicVO.getUploadpath() + profilepicVO.getFilename();
//		
//		session.setAttribute("profile",profile);

		List<BookmarkVO> bookmarkList = bookmarkService.getBookmarkById(id);
		System.out.println(bookmarkList);
		if(bookmarkList != null) {
			session.setAttribute("bookmarkList", bookmarkList);
		}
		
		
		if (rememberId) {
			Cookie cookie = new Cookie("userId", id);
			cookie.setMaxAge(60 * 60 * 12);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		msg = "로그인 성공.";
		map.put("msg", msg);
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // login
}
