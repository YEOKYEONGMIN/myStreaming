package com.example.controller;

import com.example.domain.Criteria;
import com.example.domain.MemberVO;
import com.example.domain.PageDTO;
import com.example.domain.ProfilepicVO;
import com.example.service.AttachService;
import com.example.service.BoardService;
import com.example.service.MemberService;
import com.example.service.ProfilepicService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	@Autowired
	private ProfilepicService profilepicService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private AttachService attachService;

    @GetMapping("main")
    public String adminMainForm(){

        return "/admin/main";
    }

    @GetMapping("list")
    public String adminListForm(Criteria cri,Model model){

        List<MemberVO> memberList = memberService.getMembersNotadmin(cri);
        int totalCount = memberService.getCountBySearch(cri);
        

        PageDTO pageDTO = new PageDTO(cri, totalCount);
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("pageMaker", pageDTO);


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
    
    @PostMapping("/remove")//삭제
	public ResponseEntity<String> remove( HttpServletRequest request) {

		String[] idArr = request.getParameterValues("chk");

		if(idArr != null){
			for (String id : idArr) {
			profilepicService.deleteProfilepicById(id);

			List<String> boardNumList = boardService.getBoardNumById(id);

				for (String sNum : boardNumList) {
					int boardNum = Integer.parseInt(sNum);
					boardService.deleteBoardAndAttaches(boardNum);
				}
			memberService.deleteById(id);
			}
		}


		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");

		String str = JScript.href("회원탈퇴 처리되었습니다.!", "/admin/list");

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
