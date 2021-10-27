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
        member.setId("ubs4939");
        member.setPasswd("ubs3541");
        member.setName("유병수");
        member.setEmail("ubs4939@naver.com");

        return member;
    }
}
