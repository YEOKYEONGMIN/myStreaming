package com.example.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
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
import org.springframework.web.servlet.function.ServerRequest.Headers;

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
	public void registerForm() {
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
	
	
	@GetMapping("/adminModify")//관리자 변경 화면 
	public String adminModifyForm(HttpSession session, Model model) {
		System.out.println("관리자 변경 화면 호출확인~");
		
		
		//선택한 고객의 정보를 DB에서 조회해온 후
		//수정 화면에 출력할 수있도록 Model에 담는다
		String id =(String)session.getAttribute("id");
		List<MemberVO> member = memberService.getMembers();
		model.addAttribute("member",member);
		
		return "/member/adminModify";
		
	}//adminModifyForm
	
	
	
	@GetMapping("/adminDetail")//고객 상세 화면 요청
	public String detail(int id, Model model) {
		//선택한 고객 정보를 DB에 조회해와서
		List<MemberVO> memberVO = memberService.getMembers();
		//화면에 출력할 수 있도록 Model에 담는다.
		//원래는 string타입으로 담겨야하지만 스프링에서는 자동으로 형변환이 되서 int타입으로 담긴다.
		
		model.addAttribute("memberVO", memberVO);
		return "member/adminDetail";
	}
	
	
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
	
	//관리자 기능
	@PostMapping("/login")
	public ResponseEntity<String> login(String id, String passwd, String rememberMe, 
			HttpSession session, HttpServletResponse response) {
		
		MemberVO memberVO = memberService.getMemberById(id);
		
		boolean isPasswdSame = false;
		String message = "";
		
		
		if (memberVO.getId() == "admin") {
			isPasswdSame = BCrypt.checkpw(passwd, memberVO.getPasswd());
			
			if (isPasswdSame == false) { // 비밀번호 일치하지 않음
				message = "비밀번호가 일치하지 않습니다.";
			}//if
		} //if
		
		// 로그인 실패시 비밀번호 틀렸을때
		if ( isPasswdSame == false) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			
			String str = JScript.back(message);
			
			return new ResponseEntity<String>(str, headers, HttpStatus.OK);
		}
		
		// 로그인 성공시, 로그인 인증하기
		session.setAttribute("id", id);
		
	
		// 로그인 상태유지가 체크되었으면
		if (rememberMe != null) {
			Cookie cookie = new Cookie("id", id); // 로그인 아이디로 쿠키정보 생성
			cookie.setPath("/");
			cookie.setMaxAge(60 * 10); // 초단위. 60초 * 10 -> 10분
			
			response.addCookie(cookie); // 응답객체에 쿠키를 추가해놓으면 최종응답시 쿠키를 클라이언트에게 전송해줌
		}
		
		
		
		//관리자가 썸네일 필요 한가?
//		// 썸네일
//		ProfilePicVO profilePicVO =  profilePicService.getProfilePic(id);
//		// 로그인 성공시 썸네일
//		session.setAttribute("profilePicVO", profilePicVO);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		String str = JScript.href("관리자 입장!", "/member/adminlist");
		
		return new ResponseEntity<String>(str, headers, HttpStatus.OK);
	} // login
	
	
		@PostMapping("/adminModify")//고객 정보 수정 저장 처리 요청
		public String adminModify(MemberVO memberVO) {
			//화면에서 수정 입력한 정보를 DB에 저장한 후
			memberService. updateById(memberVO);
			
			//화면으로 연결
			return "redirect:/member/adminDetail?id="+memberVO.getId();
			
		}
		
		
		
		@PostMapping("/adminRemove")//고객 정보 삭제 처리 요청
		public String delete(String id) {
			//선택한 고객 정보를 DB에서 삭제한 후
			memberService.deleteById(id);
			//목록 화면으로 연결
			return "redirect:list.cu";
		}
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
