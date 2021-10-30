package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.AttachVO;
import com.example.domain.Criteria;
import com.example.mapper.AttachMapper;

@Service
public class AttachService {
	
	private static final int List = 0;
	@Autowired
	private AttachMapper attachMapper;

	// 특정 게시글에 포함된 첨부파일 정보 가져오기
	public List<AttachVO> getAttachesByBno(int bno) {
	return attachMapper.getAttachesByBno(bno);
	
	}
	
	//여러개 묶어서 파일 가져오기
	public List<AttachVO> getAttachesByUuids(List<String> uuidList) {
		return attachMapper.getAttachesByUuids(uuidList);
	}

	//여러개 묶어서 첨부파일 업로드
	public List<AttachVO> getAttachesByUploadpath(String uploadpath) {
		return attachMapper.getAttachesByUploadpath(uploadpath);
	}
	
	//첨부파일 삭제
	public int deleteAttachesByUuids(List<String> uuidList) {
		return attachMapper.deleteAttachesByUuids(uuidList);
	}
	
	
	
}