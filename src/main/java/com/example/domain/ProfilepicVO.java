package com.example.domain;

import lombok.Data;

@Data
public class ProfilepicVO {
	
	String mid; // 업로드한 유저 아이디
	String uuid; // 파일명에 첨부할 uuid
	String uploadpath; // 업로드 경로
	String filename; // 업로드 파일명

}
