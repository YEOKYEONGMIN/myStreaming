package com.example.controller;

import com.example.domain.MemberVO;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String adminModifyForm(HttpSession session, Model model) {
        System.out.println("관리자 변경 화면 호출확인~");


        //선택한 고객의 정보를 DB에서 조회해온 후
        //수정 화면에 출력할 수있도록 Model에 담는다
        String id =(String)session.getAttribute("id");
        List<MemberVO> member = memberService.getMembers();
        model.addAttribute("member",member);

        return "admin/modify";

    }//adminModifyForm



    @GetMapping("/adminDetail")//고객 상세 화면 요청
    public String detail(Model model) { //int id,
        //선택한 고객 정보를 DB에 조회해와서
        List<MemberVO> memberVO = memberService.getMembers();
        //화면에 출력할 수 있도록 Model에 담는다.
        //원래는 string타입으로 담겨야하지만 스프링에서는 자동으로 형변환이 되서 int타입으로 담긴다.

        model.addAttribute("memberVO", memberVO);
        return "/member/adminDetail";
    }

}
