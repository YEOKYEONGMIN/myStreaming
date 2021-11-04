package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.SearchVO;
import com.example.mapper.SearchMapper;

@Service
public class SearchService {
	
	@Autowired
	private SearchMapper searchMapper;
	
	public void addKeyword(SearchVO searchVO) {
		searchMapper.addKeyword(searchVO);
	}
	
	public void updateCount(SearchVO searchVO) {
		searchMapper.updateCount(searchVO);
	}
	
	public List<SearchVO> getPopularSearch(){
		return searchMapper.getPopularSearch();
	}
	
	public void countReset() {
		searchMapper.countReset();
	}
	
	public int existKeyword(String keyword) {
		return searchMapper.existKeyword(keyword);
	}
	
	public SearchVO getSeachVO(String keyword) {
		return searchMapper.getSeachVO(keyword);
	}
}
