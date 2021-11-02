package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import com.example.domain.BookmarkVO;


public interface BookmarkMapper {
	

	//전체 삭제
	@Delete("DELETE FROM bookmark")
	void deleteAll();
	
	//북마크 삭제하기
	@Delete("DELETE FROM board WHERE mid = #{mid} and streamerId = #{streamerId} ")
	void deleteBoardByMidAndStramerId(String mid, String streamerId);
	
	//북마크 수정하기(순서,아이디는 제외)
	@Update("UPDATE bookmark SET name=#{name}, type=#{type}, url=#{url} WHERE mid = #{mid}" )
	void updateBookmark(BookmarkVO bookmarkVO);
	
	//북마크 아이디로 조회하기
	List<BookmarkVO>getBookmarkById(String mid);
	
	//북마크 추가하기
	void addBookmark(BookmarkVO bookmarkVO);
	
	int getCountbyIdAndStreamerId(String mid, String streamerId);
	
}