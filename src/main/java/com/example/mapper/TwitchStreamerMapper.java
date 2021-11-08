package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.example.domain.TwitchStreamerVO;

public interface TwitchStreamerMapper {
	
	@Insert("INSERT INTO twitchstreamer (name, id, login) "
			+"VALUES (#{name}, #{id}, #{login})")
	void addStramer(TwitchStreamerVO twitchStreamerVO);
	
	
	@Select("SELECT * FROM twitchstreamer "
			+"WHERE name LIKE '%#{name}% ")
	List<String> searchStreamer(String name);
	
	@Select("SELECT count(*) FROM twitchstreamer "
			+"WHERE name = #{name} ")
	int getCountbyName(String name);
}
