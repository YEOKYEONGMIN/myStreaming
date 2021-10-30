package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.example.domain.AttachVO;

public interface AttachMapper {

	// 여러 첨부파일을 넣을때 첨부파일 한 게시글에 5개 까지 가능하다,
	@Insert("INSERT INTO attach (uuid, uploadpath, filename, filetype, bno)"
			+ "VALUES (#{ uuid }, #{ uploadpath }, #{ filename }, #{ filetype }, #{ bno }) ")
	void addAttach(AttachVO attachVO);

	// 여러 첨부파일을 넣을때 첨부파일 한 게시글에 5개 까지 가능하다,
	void addAttaches(List<AttachVO> attachList);

	// 첨부파일 한개 정보 가져오기(#{}변수이름)
	@Select("SELECT * FROM attach WHERE uuid = #{uuid}")
	AttachVO getAttachByUuid(String uuid);

	// 여러개 한번에 묶어서 가져옴(2개이상)
	List<AttachVO> getAttachesByUuids(List<String> uuidList);

	// 특정 게시글에 포함된 첨부파일 정보 가져오기
	List<AttachVO> getAttachesByBno(int bno);

	// 업로드 경로가 일치하는 첨부파일 정보 가져오기 --> 간단한 sql 문은 애노테이션 형식으로 하고 위의 특정게시글~ 처럼 sql이 긴거는
	// xml 문으로 옮겨서 작업하면 효율 상승한다.
	@Select("SELECT *  FROM attach WHERE uploadpath = #{uploadpath} ")
	List<AttachVO> getAttachesByUploadpath(String uploadpath);

	// 특정 게시글번호에 해당하는 첨부파일들 삭제하기
	@Delete("DELETE FROM attach WHERE bno = #{bno} ")
	void deleteAttachesByBno(int bno);

	// uuid에 해당하는 첨부파일 한개 삭제하기
	@Delete("DELETE FROM attach WHERE uuid = #{uuid} ")
	void deleteAttachByUuid(String uuid);
	
	//여러개의 uuid에 해당하는 첨부파일 삭제
	int deleteAttachesByUuids(List<String> uuidList);

}