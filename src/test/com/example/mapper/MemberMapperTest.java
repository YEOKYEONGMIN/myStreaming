package com.example.mapper;

import com.example.domain.MemberVO;
import com.example.mapper.MemberMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Before
    public void setup(){
        memberMapper.deleteMembers();
    }


    @Test
    public void testInsert() {

        MemberVO memberVO = new MemberVO();
        memberVO.setId("admin");

        String hashedPw = BCrypt.hashpw("test12", BCrypt.gensalt());
        memberVO.setPasswd(hashedPw);

        memberVO.setName("관리자");
        memberVO.setNickname("관리자");
        memberVO.setGender("N");
        memberVO.setEmail("ubs4939@naver.com");
        memberVO.setRegDate(new Date());

        memberMapper.addMember(memberVO);

        for(int i=0;i<200;i++){
            memberVO = new MemberVO();
            memberVO.setId("test"+i);

            hashedPw = BCrypt.hashpw("test12", BCrypt.gensalt());
            memberVO.setPasswd(hashedPw);

            memberVO.setName(i+"번째 테스트 이름");
            memberVO.setNickname(i+"번째 테스트 닉네임");
            memberVO.setGender("N");
            memberVO.setEmail("test"+i+"@naver.com");
            memberVO.setRegDate(new Date());

            memberMapper.addMember(memberVO);
        }

        int count = memberMapper.getCountAllMembers();
        Assert.assertEquals(201,count);

    }
}
