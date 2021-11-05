package com.example.mapper;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.domain.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class) // @Component 계열 애노테이션에 해당함
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {
	
	@Autowired
	private BoardMapper boardMapper;

	
	@Test
	public void testInsert() {
		// 주글 127개 추가하기 테스트
		
		// ================================ 게시글 추가 부분 =========================================
		 게시글 없으면 100개 넣기 (테스트 진행을 위해서 임시로 추가)
		if (boardList.isEmpty()) { // 게시글 하나도 없을 경우
			Random random = new Random();
			for (int i=1; i<=100; i++) {
			// insert할 새 글번호 가져오기
				int num = boardService.getNextNum();
				
				BoardVO boardVO = new BoardVO();
				boardVO.setNum(i);
				boardVO.setMid("테스트");
				boardVO.setSubject(i + "번글");
				boardVO.setContent("테스트 내용 " + i);
				boardVO.setReadcount(random.nextInt(200)); // 조회수 0~199 임의의 값
				boardVO.setSecret(0);
				boardVO.setRegDate(new Date(System.currentTimeMillis()));
				boardVO.setReRef(num);
				boardVO.setReLev(0);
				boardVO.setReSeq(0);
				boardService.addBoard(boardVO);
			}
			
		} // if
		
		
		
		
	
	
}







