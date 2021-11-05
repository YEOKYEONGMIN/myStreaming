package com.example.controller;

import com.example.domain.MemberVO;
import com.example.domain.ProfilepicVO;
import com.example.service.MemberService;
import com.example.util.JScript;

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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired
    private MemberService memberService;

    @GetMapping("main")
    public String adminMainForm(){

        return "/admin/main";
    }

    @GetMapping("list")
    public String adminListForm(Model model){

        List<MemberVO> memberList = memberService.getMembers();
        model.addAttribute("memberList", memberList);


        return "/admin/list";
    }



    @GetMapping("/modify")//관리자 변경 화면
    public String adminModifyForm(MemberVO memberVO, Model model) throws Exception{
        System.out.println("관리자 변경 화면 호출확인~");

		// 회원수정 화면에 필요시 정보 넘겨줄 용도
		
		MemberVO member = memberService.getMemberById(memberVO.getId());

		model.addAttribute("member", member);

        return "admin/modify";

    }//adminModify
    
	@PostMapping("/modify")
	public ResponseEntity<String> modify(MemberVO memberVO){
		//MemberVO dbMemberVO = memberService.getMemberById(memberVO.getId());
		memberService.updateById(memberVO);
		String msg = "회원정보를 수정하였습니다.";
		//System.out.println("modify memberVO: " + dbMemberVO);
		
		

		

		return pageRedirect(msg, "/admin/list");

	} // modify



    @GetMapping("/adminDetail")//고객 상세 화면 요청
    public String detail(Model model) { //int id,
        //선택한 고객 정보를 DB에 조회해와서
        List<MemberVO> memberVO = memberService.getMembers();
        //화면에 출력할 수 있도록 Model에 담는다.
        //원래는 string타입으로 담겨야하지만 스프링에서는 자동으로 형변환이 되서 int타입으로 담긴다.

        model.addAttribute("memberVO", memberVO);
        return "/member/adminDetail";
    }
    
    @PostMapping("/remove")
	public ResponseEntity<String> remove(String id, String passwd, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		MemberVO memberVO = memberService.getMemberById(id);
		//ProfilepicVO profilepicVO = profilepicService.getProfilepicByMid(id);

		// 세션비우기
		session.invalidate();

		// 로그인 상태유지용 쿠키가 있으면 삭제처리하기
		// 쿠키값 가져오기
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					cookie.setMaxAge(0); // 쿠키 유효기간 0초 설정(삭제 의도)
					cookie.setPath("/");
					response.addCookie(cookie); // 응답객체에 추가하기
				}
			}
		}
		//deleteProfilepic(profilepicVO);
		//profilepicService.deleteprofilepicByMid(id); // 프로필 이미지 삭제하기
		memberService.deleteById(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");

		String str = JScript.href("회원탈퇴 처리되었습니다.!", "/");

		return new ResponseEntity<String>(str, headers, HttpStatus.OK);

	}
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
}
