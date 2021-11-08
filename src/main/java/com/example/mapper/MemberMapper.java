package com.example.mapper;

import java.util.List;

import com.example.domain.BoardVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.Criteria;
import com.example.domain.MemberVO;

public interface MemberMapper {
	
	// 회원 하나 추가
	@Insert("INSERT INTO member (id, passwd, name, nickname, birthday, gender, email, reg_date) "
			+ "VALUES (#{id}, #{passwd}, #{name}, #{nickname}, #{birthday}, #{gender}, #{email}, #{regDate})")
	int addMember(MemberVO memberVO);
	
	// 회원 하나 삭제
	@Delete("DELETE FROM member WHERE id = #{id}")
	int deleteMemberById(String id);
	
	// 모든 회원 삭제 (이후에 관리자 아이디 빼고 삭제하는 것으로 바꿀 예정)
	@Delete("DELETE FROM member")
	int deleteMembers();
	
	// 멤버 수정 (아이디 비밀번호를 제외한 정보들 수정)
	@Update("UPDATE member SET name=#{name}, nickname=#{nickname}, birthday=#{birthday}, "
			+ "gender=#{gender}, email=#{email} WHERE id = #{id}")
	void updateMemberById(MemberVO memberVO);
	
	// 비밀번호만 수정
	@Update("UPDATE member SET passwd=#{passwd} WHERE id = #{id}")
	void updatePasswd(MemberVO memberVO);
	
	// 모든 회원정보 조회
	List<MemberVO> getMembers();
	
	// 회원정보 하나 조회
	MemberVO getMemberById(String id);
	
	// 아이디와 일치하는 회원 수
	int getMemberCount(String id);
	
	// 모든 회원정보와 프로필사진 정보 조인해서 가져오기
	List<MemberVO> getMembersAndProfilepics();
	
	// 아이디와 일치하는 회원정보와 프로필사진 정보 조인해서 가져오기
	MemberVO getMemberAndProfilepic(String id);

	// 회원 갯수
	@Select("SELECT count(*) AS cnt FROM member")
	int getCountAllMembers();
	
	  // 관리자 제외 모든 회원정보 조회
	   List<MemberVO> getMembersNotadmin(Criteria cri);
	   
	// 페이징으로 글 가져오기
	   List<MemberVO> getMembersWithPaging(Criteria cri);
	   
	   // 검색어가 적용된 전체 멤버 개수 가져오기 :조건(where 절)에 따라서 문장이 바뀐다 -> 동적 sql문
	   int getCountBySearch(Criteria cri);


	
}
