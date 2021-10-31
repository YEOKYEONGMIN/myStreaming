package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.BookmarkVO;
import com.example.mapper.BookmarkMapper;

@Service
public class BookmarkService {
	
	@Autowired
	private BookmarkMapper bookmarkMapper;
	
	//전체삭제
	public void deleteAll() {
		bookmarkMapper.deleteAll();
	
	}//deleteAll 

	//북마크 넘버로 삭제하기
	public void deleteBoardByNum(int num) {
		bookmarkMapper.deleteBoardByNum(num);
		
	}//deleteBoardByNum
	
	//북마크 수정하기(순서,아이디는 제외)
	public void updateBookmark(BookmarkVO bookmarkVO) {
		bookmarkMapper.updateBookmark(bookmarkVO);
	
	}//updateBookmark
	
	//북마크 아이디로 삭제하기
	public int deleteBookmarkById(String mid) {
		return bookmarkMapper.deleteBookmarkById(mid);
	
	}//deleteBookmarkById
	
	//글번호 만들어서 가져오기
	public int getNextNum() {
		return bookmarkMapper.getNextNum();
	
	}//getNextNum
	
	//모든북마크 가져오기
	public List<BookmarkVO>getBookmarksAll(){
		return bookmarkMapper.getBookmarksAll();
	
	}//getBookmarksAll
	
	//북마크 추가하기
	public void addBookmark(BookmarkVO bookmarkVO) {
		 bookmarkMapper.addBookmark(bookmarkVO);
	
	}//addBookmark
	
	//북마크 아이디로 조회하기
	public BookmarkVO getBookmarkById(String mid) {
		return bookmarkMapper.getBookmarkById(mid);
	
	}//getBookmarkById
	

}
