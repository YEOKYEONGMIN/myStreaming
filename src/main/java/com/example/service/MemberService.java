package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.MemberVO;
import com.example.mapper.MemberMapper;

@Service // @Component 계열 애노테이션. 트랜잭션 처리 기능 가짐
//@Transactional  // 클래스에 선언하면 이 클래스에 있는 모든 메서드가 트랜잭션으로 동작함
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;

	// 회원 하나 추가
	public int register(MemberVO memberVO) {
		return memberMapper.addMember(memberVO);
	} // register

	public int deleteById(String id) {
		return memberMapper.deleteMemberById(id);
	} // deleteById
	
	// 모든 회원 삭제 (이후에 관리자 아이디 빼고 삭제하는 것으로 바꿀 예정)
	public int deleteMembers() {
		return memberMapper.deleteMembers();
	} // deleteMembers
	
	// 멤버 수정 (아이디 비밀번호를 제외한 정보들 수정)
	public void updateById(MemberVO memberVO) {
		memberMapper.updateMemberById(memberVO);
	} // updateById
	
	// 비밀번호만 수정
	public void changePasswd(MemberVO memberVO) {
		memberMapper.updatePasswd(memberVO);
	} // updatePasswd
	
	// 모든 회원정보 조회
	public List<MemberVO> getMembers() {
		return memberMapper.getMembers();
	} // getMembers

	// 회원정보 하나 조회
	public MemberVO getMemberById(String id) {
		return memberMapper.getMemberById(id);
	} // getMemberById
	
	// 아이디와 일치하는 회원 수
	public int getMemberCount(String id) {
		return memberMapper.getMemberCount(id);
	} // getMemberCount
	
	// 모든 회원정보와 프로필사진 정보 조인해서 가져오기
	public List<MemberVO> getMembersAndProfilepics() {
		return memberMapper.getMembersAndProfilepics();
	} // getMembersAndProfilepics
	
	// 아이디와 일치하는 회원정보와 프로필사진 정보 조인해서 가져오기
	public MemberVO getMemberAndProfilepic(String id) {
		return memberMapper.getMemberAndProfilepic(id);
	} // getMemberAndProfilepic
	
}
