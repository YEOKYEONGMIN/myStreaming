package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.mapper.AttachMapper;
import com.example.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	
	//테스트용 임시로 구현
	public void addBoard(BoardVO boardVO) {
		boardMapper.addBoard(boardVO);
	}
	
	// 페이징, 검색어 적용하여 글 목록 가져오기
	public List<BoardVO> getBoards(Criteria cri) {
			// 시작 행번호 (MySQL의 LIMIT절의 시작행번호)

			// 한 페이지당 글개수(amount)가 10개씩일때
			// 1 페이지 -> 0
			// 2 페이지 -> 10
			// 3 페이지 -> 20
			// 4 페이지 -> 30
			int startRow = (cri.getPageNum() - 1) * cri.getAmount();
			cri.setStartRow(startRow);
			
			List<BoardVO> boardList = boardMapper.getBoardsWithPaging(cri);
			return boardList;
		} // getBoards
		
		// 페이징, 검색어 적용하여 글 개수 가져오기
		public int getCountBySearch(Criteria cri) {
			int count = boardMapper.getCountBySearch(cri);
			return count;
		}
		
		
		public void updateReadcount(int num) {
			boardMapper.updateReadcount(num);
		}
		
		public BoardVO getBoard(int num) {
			return boardMapper.getBoard(num);
		}
		
		//임시로 글만 지우기
		public void deleteBoardByNum(int num){
			boardMapper.deleteBoardByNum(num);
		}
		
		
		//getNexNum
		public int getNextNum() {
			return boardMapper.getNextNum();
			
		}
		
		
	
		
		
		
		
		
		
		

}
