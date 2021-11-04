package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import com.example.domain.SearchVO;

public interface SearchMapper {
	
	@Insert("INSERT INTO search (keyword, count, regDate) "
			+"VALUES (#{keyword}, #{count}, #{regDate})" )
	void addKeyword(SearchVO searchVO);
	
	void updateCount(SearchVO searchVO);
	
	List<SearchVO> getPopularSearch();
	
	void countReset();
	
	int existKeyword(String keyword);
	
	SearchVO getSeachVO(String keyword);
	
}
