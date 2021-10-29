package com.example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.MemberVO;
import com.example.service.MemberService;
import com.example.util.JScript;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	// ========================= GET 요청 모음 =========================
	
	// 회원가입 화면
	@GetMapping("/register")
	public void regiterForm() {
		// 호출 확인용
		System.out.println("회원가입 화면 호출 확인....");
		
	} // regiterForm
	
	
	@GetMapping("/login")
	public void loginForm() {
		System.out.println("로그인 화면 호출 확인....");
		
	} // loginForm
	
	
	@GetMapping("/logout")
	public String logoutForm(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		session.invalidate(); // 세션 비우기 (로그인 정보 비우기)
		
		// 로그인 유지용 쿠키값 가져오기
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) {
			for (Cookie c : cookies) {
				// 쿠키이름은 userId로
				if (c.getName().equals("userId")) {
					c.setMaxAge(0);
					c.setPath("/"); // 모든 경로에서 접근가능 하도록 설정
					response.addCookie(c); // 응답객체에 설정한 쿠키 유효시간 넣기
				}
			} // for
		} // if
		
		return "redirect:/member/login";
	} // logoutForm
	
	
	@GetMapping("/modify")
	public void modifyForm(HttpSession session, Model model) {
		System.out.println("회원정보 변경 화면 호출 확인...");
		
		// 회원수정 화면에 필요시 정보 넘겨줄 용도
		String id = (String) session.getAttribute("id");
		MemberVO member = memberService.getMemberById(id);
		
		model.addAttribute("member", member);
		
	} // modifyForm
	
	
	@GetMapping("/changePasswd")
	public void changePasswdForm() {
		System.out.println("비밀번호 변경 화면 호출 확인...");
		
	} // changePasswdForm
	
	
	@GetMapping("/remove")
	public void removeForm() {
		
		System.out.println("회원탈퇴 화면 호출 확인...");
	} // removeForm
	
	
	// ========================== GET 요청 끝 ==========================
	
	
	// ========================= POST 요청 모음 =========================
	// 회원가입 처리
	@PostMapping("/register")
	public ResponseEntity<String> register(MemberVO memberVO, String passwd2) { // 비밀번호 확인란 name명은 임의로 passwd2로 처리
		
		int memberCount = memberService.getMemberCount(memberVO.getId());
		String msg = "회원가입을 완료하였습니다."; // 보낼 메세지
		
		// 필수정보 입력 확인 (일치하지 않을시)
		// 임의로 아이디, 비밀번호, 비밀번호 확인란만 필수정보로 입력했습니다. 필수정보 추가시 수정예정
		if (memberVO.getId() == "" || memberVO.getPasswd() == "" || passwd2 == "") {
			msg = "필수 회원정보를 입력해주세요.";
			return pageBack(msg);
		}
		
		// 비밀번호란과 비밀번호 확인란 일치 여부 확인 (일치하지 않을시)
		if (!memberVO.getPasswd().equals(passwd2)) {
			msg = "입력하신 두개의 비밀번호가 일치하지 않습니다.";
			return pageBack(msg);
		}
		
		// 아이디 중복 여부 확인 (회원정보 있을시)
		if (memberCount == 1) {
			msg = "이미 존재하는 아이디입니다.";
			return pageBack(msg);
		}
		
		// 회원가입 처리
		// 비밀번호 암호화 후 객체에 넣기
		String passwd = memberVO.getPasswd();
		String pwHash = BCrypt.hashpw(passwd, BCrypt.gensalt());
		memberVO.setPasswd(pwHash);
		
		memberService.register(memberVO);
		
		return pageRedirect(msg, "/login");
		
	} // register
	
	// 로그인은 MemberRestController에서 처리
	
	
	// ========================== POST 요청 끝 ==========================
	
	
	
	
	
	// ========================= 메소드 모음 =========================
	
	// 페이지 뒤로가기 처리 메소드
	private ResponseEntity<String> pageBack(String msg) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		String code = JScript.back(msg);
		return new ResponseEntity<String>(code, headers, HttpStatus.OK);
	} // pageBack
	
	// 페이지 리다이렉트 처리 메소드
	private ResponseEntity<String> pageRedirect(String msg, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		String code = JScript.href(msg, url);
		return new ResponseEntity<String>(code, headers, HttpStatus.OK);
	} // pageRedirect
	
	
	// 생년월일 
	
	
	
}
