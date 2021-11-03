package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.mapper.AttachMapper;
import com.example.mapper.BoardMapper;

@Service
public class BoardService {//스프링이 관리하는 객체는 싱글톤 설계(공유해서 사용함)
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	
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
			
			// 가져올 글의 시작 행번호
			int startRow = (cri.getPageNum() - 1) * cri.getAmount();//getPageNum , getAmount(10개로 고정)는 사용자로 부터 받음
			cri.setStartRow(startRow);
			
			//boardMapper db처리
			List<BoardVO> boardList = boardMapper.getBoardsWithPaging(cri);
			return boardList;
			
		} // getBoards
		
		// 페이징, 검색을(검색유형 검색어) 적용하여 글 개수 가져오기
		public int getCountBySearch(Criteria cri) {
			int count = boardMapper.getCountBySearch(cri);
			return count;
		}
		
		
		//조회수 1증가 시키기
		public void updateReadcount(int num) {
			boardMapper.updateReadcount(num);
		}
		
		//글 한개  가져오기
		public BoardVO getBoard(int num) {
			return boardMapper.getBoard(num);
		}
		
		//임시로 글만 지우기
		public void deleteBoardByNum(int num){
			boardMapper.deleteBoardByNum(num);
		}
		
		
		//새로운 insert글번호
		public int getNextNum() {
			return boardMapper.getNextNum();
			
		}
		
		
		
		
		
		//게시글 한개와 첨부파일(여러개)가져오기
		public BoardVO getBoardAndAttaches(int num) {
			BoardVO boardVO = boardMapper.getBoardAndAttaches(num);//join쿼리로 데이터 가져옴
			return boardVO;
		}
		
		
		// 주글쓰기 메소드(게시글 정보와 첨부파일정보를 한개의 트랜잭션으로 처리)
		@Transactional
		public void addBoardAndAttaches(BoardVO boardVO) {
			
			// attach 테이블의 bno 컬럼이 외래키로서
			// board 테이블의 num 컬럼을 참조하므로
			// board 레코드가 먼저 insert된 이후에 attach 레코드가 insert 가능함.
			boardMapper.addBoard(boardVO);
			
			//게시판에 첨부된 파일을 꺼내기
			List<AttachVO> attachList = boardVO.getAttachList();
			
			//첨부파일 1개이상일 경우 테이블 인서트
			if (attachList.size() > 0) {//attachList 체크
				
				attachMapper.addAttaches(attachList);//insert한 첨부파일 리스트
				
			}
		} // addBoardAndAttaches
		
		@Transactional	// attach와 board테이블 내용 삭제 - 한개의 트랜잭션 단위로 처리
		public void deleteBoardAndAttaches(int num) {
			// 외래키 관계가 있다면 삭제 순서는 외래키로 참조하는 테이블부터 삭제함에 유의!
			attachMapper.deleteAttachesByBno(num);
			boardMapper.deleteBoardByNum(num);
		} // deleteBoardAndAttaches
		
		
		@Transactional//글번호에 해당하는 게시글 수정, 첨부파일 정보 수정(insert,delete ) - 트랜잭션처리
		public void updateBoardAndInsertAttachesAndDeleteAttaches(BoardVO boardVO, List<AttachVO> newAttachList, List<String> deletUuidList) {
			if (newAttachList != null && newAttachList.size() > 0) {
				attachMapper.addAttaches(newAttachList);
			}
			if (deletUuidList != null && deletUuidList.size() > 0) {
				attachMapper.deleteAttachesByUuids(deletUuidList);
			}
			boardMapper.updateBoard(boardVO);
		} // updateBoardAndInsertAttachesAndDeleteAttaches
		
		
		
		// 답글쓰기 메소드(게시글 정보와 첨부파일정보를 한개의 트랜잭션으로 처리)
		
		@Transactional
		public void addReplyAndAttaches(BoardVO boardVO) {
			// 답글을 남길 대상글과 같은 글 (reRef) 그룹 안에서
			// 대상들의 순번보다 큰 글들의 순번을 1씩 증가(UPDATE)
			boardMapper.updateReSeqPlusOne(boardVO.getReRef(), boardVO.getReSeq());
			
			// insert 할 답글 re 값으로 수정
			boardVO.setReLev(boardVO.getReLev() + 1);
			boardVO.setReSeq(boardVO.getReSeq() + 1);
			
			// 답글 insert 하기
			boardMapper.addBoard(boardVO);
			
			// 첨부파일 정보 insert 하기
			List<AttachVO> attachList = boardVO.getAttachList();
			if( attachList != null && attachList.size() > 0 ) {
				attachMapper.addAttaches(attachList);
			}
		} // addReplyAndAttaches
		
}