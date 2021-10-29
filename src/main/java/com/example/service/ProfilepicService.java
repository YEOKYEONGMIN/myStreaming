package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ProfilepicVO;
import com.example.mapper.ProfilepicMapper;

@Service
public class ProfilepicService {

	@Autowired
	private ProfilepicMapper profilepicMapper;
	
	// 프로필사진 등록
	public int insertProfilepic(ProfilepicVO profilepicVO) {
		return profilepicMapper.insertProfilepic(profilepicVO);
	} // insertProfilepic
	
	// 프로필 사진 삭제
	public int deleteProfilepicById(String id) {
		return profilepicMapper.deleteProfilepicById(id);
	} // deleteProfilepicById
	
	// 프로필 사진 수정
	public void updateProfilepic(ProfilepicVO profilepicVO) {
		profilepicMapper.updateProfilepic(profilepicVO);
	} // updateProfilepic

	// 회원 하나의 프로필 사진 가져오기
	public ProfilepicVO getProfilepicById(String id) {
		return profilepicMapper.getProfilepicById(id);
	} // getProfilepicById
	
	// 회원 전체의 프로필 사진 id기준으로 오름차순 정렬하여 가져오기 (필요없으면 삭제)
	public List<ProfilepicVO> getProfilepics() {
		return profilepicMapper.getProfilepics();
	}
	
}
