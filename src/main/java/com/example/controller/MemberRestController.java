package com.example.controller;

import com.example.domain.MemberVO;
import com.example.service.MemberService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
public class MemberRestController {
	
	// 테스트용
    @GetMapping(value = "/one", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public MemberVO returnMember(){
        MemberVO member = new MemberVO();
        member.setId("testing");
        member.setPasswd("testpass");
        member.setName("테스트");
        member.setEmail("test@naver.com");

        return member;
    }
	
	@Autowired
	private MemberService memberService;
	
	
	// 모든 멤버 가져오기
	@GetMapping(value = "/members", 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<MemberVO>> getMembers() {
		
		List<MemberVO> memberList = memberService.getMembers();
		
		return new ResponseEntity<List<MemberVO>>(memberList, HttpStatus.OK);
		
	} // getMembers
	
	// 아이디와 일치하는 회원정보 가져오기
	@GetMapping(value = "/member/{id}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, Object>> getMember(@PathVariable("id") String id) {
		
		MemberVO member = memberService.getMemberById(id);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("member", member);
		
		return new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
		
	} // getMember
	
	
	@PostMapping(value = "/member/login")
	public ResponseEntity<String> login(MemberVO memberVO,
			@RequestParam(required = false, defaultValue = "false") boolean rememberId,
			HttpSession session, HttpServletResponse response) {
		String id = memberVO.getId();
		String passwd = memberVO.getPasswd();
		String msg="";
		
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
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			
			String code = JScript.back(msg);
			return new ResponseEntity<String>(code, headers, HttpStatus.OK);
		}
		
		// 아이디가 존재하고 비밀번호 일치시
		session.setAttribute("id", id); 
		
		if (rememberId) {
			Cookie cookie = new Cookie("userId", id);
			cookie.setMaxAge(60 * 60 * 12);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/"); // 홈으로 리다이렉트
		// 리다이렉트로 보낼때는 HttpStatus.FOUND
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);
		
	} // login
}
