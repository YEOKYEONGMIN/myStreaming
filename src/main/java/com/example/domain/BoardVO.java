package com.example.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	private Integer num; // 게시글 번호
	private String mid; // 작성자 아이디
	private String subject; // 게시글 제목
	private String content; // 게시글 내용
	private boolean secret = false; //비밀 글 //비밀글이면 true , 비밀글이 아니면 기본값인 false로 지정 
	private Integer readcount; // 게시글 조회수
	private Date regDate; // 게시글 작성날짜
	
	// 댓글용
	private Integer reRef;//주글이랑 답글  -> 글그룹번호
	private Integer reLev;//들여쓰기
	private Integer reSeq;//답글 구분 주글일 경우 re_seq는 0

	
	private List<AttachVO> attachList;		// SQL문 -> JOIN쿼리에서 1:N의 관계
}