package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.TwitchStreamerVO;
import com.example.mapper.TwitchStreamerMapper;

@Service
public class TwitchStreamerService {
	
	@Autowired
	private TwitchStreamerMapper twitchStreamerMapper;
	
	public void addStramer(TwitchStreamerVO twitchStreamerVO) {
		twitchStreamerMapper.addStramer(twitchStreamerVO);
	}
	 
	public List<String> searchStreamer(String name){
		return twitchStreamerMapper.searchStreamer(name);
	}
	
	public int getCountbyName(String name) {
		return twitchStreamerMapper.getCountbyName(name);
	}
	
}
