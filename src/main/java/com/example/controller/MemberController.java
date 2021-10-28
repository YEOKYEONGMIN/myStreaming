package com.example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.MemberVO;
import com.example.service.MemberService;

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

}
