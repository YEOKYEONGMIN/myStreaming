package com.example.domain;

import lombok.Data;

@Data
public class BookmarkVO {
	private String mid; // 북마크 작성자 아이디
	private String streamerId; // 스트리머 ID값
	private String streamerName; // 스트리머 이름
	private String profile_image_url; // 스트리머 프로필 이미지
	
}