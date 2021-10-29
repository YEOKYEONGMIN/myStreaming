package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.example.domain.BoardVO;
import com.example.domain.Criteria;

public interface BoardMapper {// 실행의 기준은 interface이다(xml과 interface)
								// mapper인터페이스가 자동으로 구현될떄 자신이 사용할 sql문을 어디서 찾는가
	@Delete("DELETE FROM board")
	void deleteAll();

	// 보드넘버로 삭제하기(특정글 삭제)
	@Delete("DELETE FROM board WHERE num = #{num}")
	void deleteBoardByNum(int num);

	// 전체글 개수 가져오기
	// @Select("SELECT COUNT(*)AS cnt FROM board") ->xml에 더 적어놓음
	int getCountAll();

	// 그다음 글번호 만들어서 가져오기
	int getNextnum();

	// 주글 쓰기
	void addBoard(BoardVO boardVO);

	// 페이징 없이 다가져오기(criteria 적용 안됨)
	List<BoardVO>getBoardsAll();

	// 페이징으로 글 가져오기
	List<BoardVO> getBoardsWithPaging(Criteria cri);

	// 조회수 1증가 시키는 메소드
	void updateReadcount(int num);

	// 게시글 수정하기
	void updateBoard(BoardVO boardVO);

	// 답글쓰기에서 답글을 다는 대상글과 같은 글그룹내에서
	// 답글을 다는 대상글의 그룹내 순번보다 큰 글들의 순번을 1씩 증가시키기
	void updateReSeqPlusOne(//BoardVo로 받아도 되나 따로 받는 sql 작성함.
			@Param("reRef") int reRef,
			@Param("reSeq") int reSeq);

	// 검색어가 적용된 전체 글 개수 가져오기 :조건(where 절)에 따라서 문장이 바뀐다 -> 동적 sql문
	int getCountBySearch(Criteria cri);

	BoardVO getBoard(int num);

	// join쿼리문으로 글번호에 해당하는 게시글한개과 첨부파일 리스트 모두 가져오기
	BoardVO getBoardAndAttaches(int num);

}
