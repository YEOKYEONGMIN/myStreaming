package com.example.mapper;

import java.util.List;

import com.example.domain.ProfilepicVO;

public interface ProfilepicMapper {
	
	
	int insertProfilepic(ProfilepicVO profilepicVO);
	
	int deleteProfilepicById(String id);
	
	void updateProfilepic(ProfilepicVO profilepicVO);
	
	ProfilepicVO getProfilepicById(String id);
	
	List<ProfilepicVO> getProfilepics();

}
