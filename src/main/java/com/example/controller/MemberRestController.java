package com.example.controller;

import com.example.domain.MemberVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members/*")
public class MemberRestController {

    @GetMapping(value = "/one", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public MemberVO returnMember(){
        MemberVO member = new MemberVO();
        member.setId("testing");
        member.setPasswd("testpass");
        member.setName("테스트");
        member.setEmail("test@naver.com");

        return member;
    }
}
