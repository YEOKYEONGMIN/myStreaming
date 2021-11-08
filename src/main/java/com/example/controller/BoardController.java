
package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.domain.MemberVO;
import com.example.domain.PageDTO;
import com.example.service.AttachService;
import com.example.service.BoardService;

import net.coobird.thumbnailator.Thumbnailator;

//?는 향후 jsp명을 보고 고칠예정입니다!!!
//업로드 파일경로는 어떻게 해야될지...

@Controller // 프론트 컨트롤러 -> 호출할 대상 servlet -context에 있음
@RequestMapping("/board/*") // 공통경로
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private AttachService attachService;

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		
		// board 테이블에서 전체글 리스트로 가져오기 
		List<BoardVO> boardList = boardService.getBoards(cri);

		// 검색유형, 검색어가 있으면 적용하여 글개수 가져오기
		int totalCount = boardService.getCountBySearch(cri); 
		
		// 페이지블록 정보 객체준비. 필요한 정보를 생성자로 전달.
		PageDTO pageDTO = new PageDTO(cri, totalCount);
		
		// 뷰에서 사용할 데이터를 Model 객체에 저장 -> 스프링이 requestScope로 옮겨줌
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageMaker", pageDTO);
		
		return "board/boardList";
		
	} // list

	// 상세보기
	@GetMapping("/content")
	public String content (int num, @ModelAttribute("pageNum") String pageNum, Model model,HttpServletRequest request,BoardVO boardVO,HttpSession session) {
		
		
		
		if(boardVO.getSecret() == 1) {//비밀글일
			if(boardVO.getMid() == "admin" || session.getId() == boardVO.getMid()) {
				// 조회수 1 증가시키기
				boardService.updateReadcount(num);

				// join 쿼리문으로 게시판 글과 첨부파일 리스트 정보 가져오기
				boardVO = boardService.getBoard(num);
				System.out.println(boardVO);

				model.addAttribute("board", boardVO);

				model.addAttribute("attachList", boardVO.getAttachList());// 첨부파일 리스트
				model.addAttribute("attachCount", boardVO.getAttachList().size());
				model.addAttribute("pageNum", pageNum); // 컨트롤러에서 쓰이지 않음, 받은 값을 jsp로 전달

				return "board/boardContent";
			}else if(boardVO.getMid() != session.getId() || session.getId() == null) {
				
				return "board/boardList";
			}
			
		
		}
		
		
		
			// 조회수 1 증가시키기
			boardService.updateReadcount(num);

			// join 쿼리문으로 게시판 글과 첨부파일 리스트 정보 가져오기
			boardVO = boardService.getBoardAndAttaches(num);
			System.out.println(boardVO);

			model.addAttribute("board", boardVO);

			model.addAttribute("attachList", boardVO.getAttachList());// 첨부파일 리스트
			model.addAttribute("attachCount", boardVO.getAttachList().size());
			model.addAttribute("pageNum", pageNum); // 컨트롤러에서 쓰이지 않음, 받은 값을 jsp로 전달

			return "board/boardContent";
		
				
		
		
	} // content

//=======================글쓰기 메소드 관련=================================================	

	// 새로운 주글(qna)쓰기 폼 화면 요청
	@GetMapping("/write")
	public String write(@ModelAttribute("pageNum") String pageNum) {

		System.out.println("write()form호출");

		return "board/boardWrite";
	} // write

	// "년/월/일" 형식의 폴더명을 리턴하는 메소드
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String str = sdf.format(new Date());
		return str;
	}

	// 이미지 파일여부 리턴하는 메소드
	private boolean checkImageType(File file) throws IOException {
		boolean isImage = false;

		// contentType 증명
		String contentType = Files.probeContentType(file.toPath()); // 이미지일 경우, "image/jpg" "image/gif" "image/png"
		System.out.println("contentType : " + contentType);

		isImage = contentType.startsWith("image");// 특정 문자열로 시작할경우
		return isImage;
	} // checkImageType

	// 첨부파일 업로드(썸네일 이미지 생성함) -> attachList리턴
	private List<AttachVO> uploadFilesAndGetAttachList(List<MultipartFile> files, int bno)
			throws IllegalStateException, IOException {

		List<AttachVO> attachList = new ArrayList<AttachVO>();// 첨부파일을 attachLsit에 담음

		// 업로드 처리로 생성 해야할 정보가 없을 경우 메소드 종료
		if (files == null || files.size() == 0) {
			System.out.println("첨부파일 없음!");
			return attachList;
		}

		System.out.println("첨부파일 갯수 :" + files.size());

		String uploadFolder = "C:/project_myStreaming/upload";// 업로드 기준경로

		// 파일업로드 생성경로
		File uploadPath = new File(uploadFolder, getFolder());// C://(폴더명)/upload/2021/10/29
		System.out.println("uploadPath : " + uploadPath.getPath());

		// 업로드 파일 존재 확인(파일 시스템상에 존재 해야 파일을 확인 할수 있음)
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();// 복수형으로 할시 하위폴더까지 생성

		}

		// 첨부파일 확인
		for (MultipartFile multipartFile : files) {

			if (multipartFile.isEmpty() == true) {
				continue;
			}

			// 원본 파일 이름
			String originalFilename = multipartFile.getOriginalFilename();
			System.out.println("originalFilename : " + originalFilename);

			// 파일중복 피하기(실제 파일명)
			UUID uuid = UUID.randomUUID();// 정보식별을 위해 쓰임
			String uploadFileName = uuid.toString() + "_" + originalFilename;

			File file = new File(uploadPath, uploadFileName);// 생성할 파일경로 파일명 정보

			// 파일1개 업로드(파일 생성)완료
			multipartFile.transferTo(file);

			// 현재 업로드한 파일이 이미지 파일이면 썸네일 이미지를 추가로 생성하기
			boolean isImage = checkImageType(file);// 이미지 파일 여부 true/false로 리턴

			if (isImage == true) {
				// 생성할 파일
				File outFile = new File(uploadPath, "s_" + uploadFileName);// s_가 붙을경우 썸네일 이미지
				Thumbnailator.createThumbnail(file, outFile, 100, 100);// 썸네일 이미지파일 생성하기

			} // if

			// insert할 주글 AttachVO 객체 준비 및 데이터 저장
			AttachVO attachVO = new AttachVO();
			attachVO.setUuid(uuid.toString());
			attachVO.setUploadpath(getFolder());
			attachVO.setFilename(originalFilename);// 원본파일명
			attachVO.setFiletype((isImage == true) ? "I" : "O");
			attachVO.setBno(bno);// 글번호

			attachList.add(attachVO);// 리스트에 추가

		} // for

		return attachList;

	}// uploadFilesAndGetAttachList

	// 첨부파일 업로드와 함께 주글쓰기 처리
	@PostMapping("/write")
	public String write(List<MultipartFile> files, BoardVO boardVO, HttpServletRequest request, RedirectAttributes rttr)
			throws IllegalStateException, IOException { // 배열로 받아도 된다.(MultipartFile[] multipartFile)
		
		System.out.println("post write call...");
		// 스프링(mvc 모듈) 웹에서는 클라이언트(사용자)로부터 넘어오는 file 타입 input 요소의 갯수만큼
		// MultipartFile 타입의 객체로 전달받게 됨.

		// insert할 새 글번호 가져오기
		int num = boardService.getNextNum();

		// 첨부파일 업로드(썸네일 이미지 생성) 후 attachList 리턴하는 메소드
		List<AttachVO> attachList = uploadFilesAndGetAttachList(files, num);

		// insert 할 BoardVO 객체의 데이터 설정 -- 스프링은 vo의 setter이름과 name 속성의
		// 이름이 같으면 자동으로 준비되므로 그 외에 준비되지 않은 것들만 적어주기 --> write에 아이디, 비밀번호, content는 있기
		// 때문에 제외!
		boardVO.setNum(num); // 글 번호, 위에 int num으로 설정해놨기 때문에 그냥 그거 받으면 된다!
		boardVO.setReadcount(0);// 새글은 0
		boardVO.setSecret(boardVO.getSecret());//booelan은 get이 아니라 is로 받을
		boardVO.setRegDate(new Date());
		boardVO.setReRef(num); // 주글일 경우 글그룹 번호는 글번호와 동일함
		boardVO.setReLev(0); // 주글일 경우 들여쓰기 레벨은 0
		boardVO.setReSeq(0); // 주글일 경우 글그룹 안에서의 순번은 0

		boardVO.setAttachList(attachList);// attachList 세팅

		// 주글 한개(boardVO)와 첨부파일 여러개(attachList)를 한개의 트랜잭션으로 insert 처리함
		boardService.addBoardAndAttaches(boardVO);

		// 리다이렉트 시 쿼리스트링으로 서버에 전달할 데이터를
		// RedirectAttributes 타입의 객체에 저장하면,
		// 스프링이 자동으로 쿼리스트링으로 변환해서 처리해줌 -> return "redirect:/board/content?num=" +
		// boardVO.getNum() + "&pageNum=1"; 이렇게 적을 거를 return "redirect:/board/content";
		// 이렇게만 적으면 된다.
		rttr.addAttribute("num", boardVO.getNum());// key = num, value= baordVO.getNum()
		rttr.addAttribute("pageNum", 1);// 새글 쓰는것이기 때문에 1
		rttr.addAttribute("secret", boardVO.getSecret());//비밀글설정
		

		return "redirect:/board/content";
	} // write

//=====================================글쓰기 관련 메소드 끝======================================	

//  첨부파일 삭제 하는 메소드
	private void deleteAttachFiles(List<AttachVO> attachList) {

		// 삭제할 파일이 없을 경우 종료
		if (attachList == null || attachList.size() == 0) {
			System.out.println("삭제할 첨부파일 정보 없음");
			return;
		}

		String basePath = "C:/project_myStreaming/upload";// ?? 업로드 경로 정해질 경우 수정 사항!

		for (AttachVO attachVO : attachList) {

			String uploadpath = basePath + "/" + attachVO.getUploadpath();// ex C:/??/upload/2021/10/29
			String filename = attachVO.getUuid() + "_" + attachVO.getFilename();// ex) 파일명.jpg(etc..)

			File file = new File(uploadpath, filename);// ex) C:/??/upload/2021/10/29/uuid_파일명(etc..)

			// 첨부파일이 이미지일 경우 썸네일 이미지 파일도 삭제
			if (attachVO.getFiletype().equals("I")) {
				// 썸네일 이미지 파일 여부 확인후 삭제
				File thumbnailFile = new File(uploadpath, "s_" + filename);
				if (thumbnailFile.exists() == true) {
					thumbnailFile.delete();
				} // if
			} // if
		} // for
	}// deleteAttachFiles

	@GetMapping("/remove") // 글삭제
	public String remove(int num, @ModelAttribute("pageNum") String pageNum) {// requestParameter 시도

		// 첨부파일 삭제
		List<AttachVO> attachList = attachService.getAttachesByBno(num);
		deleteAttachFiles(attachList);// 집에 가서 살펴보기

		System.out.println("첨부파일 삭제 완료");

		// attach와 board테이블 내용 삭제 - 한개의 트랜잭션 단위로 처리
		boardService.deleteBoardAndAttaches(num);

		// 글목록으로 이동
		return "redirect:/board/list?pageNum =" + pageNum;

	}// remove

	@GetMapping("/modify")
	public String modifyFrom(int num, String pageNum, Model model) {

		// 글과 첨부파일 가져오기
		BoardVO boardVO = boardService.getBoardAndAttaches(num);// JOIN으로 모두 가져옴

		// view에 보내지는것
		model.addAttribute("board", boardVO);// 게시글
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("attachList", boardVO.getAttachList());// 첨부파일

		return "board/boardModify";

	}// modifyForm()

	@PostMapping("/modify")
	public String modify(List<MultipartFile> files, BoardVO boardVO, String pageNum,
			@RequestParam(name = "delfile", required = false) List<String> delUuidList,
			@RequestParam(name = "secret1", required = false) boolean secret1,
			HttpServletRequest request, RedirectAttributes rttr ) throws IllegalStateException, IOException {
		
		//비밀
		if(secret1) {
			
			boardVO.setSecret(1);
			
		}else {
			boardVO.setSecret(0);
		}
		
		
		
		
		
		// 1) 신규 첨부파일 업로드하기. 신규파일정보 신규리스트에 추가.
		List<AttachVO> newAttachList = uploadFilesAndGetAttachList(files, boardVO.getNum());
		System.out.println("================ POST modify - 첨부파일 업로드 완료 ================");
		
		System.out.println("pageNum=" +pageNum);
		// 2) 삭제할 첨부파일 삭제하기(썸네일 이미지도 삭제). 삭제파일정보 삭제리스트에 추가
		// ================== 첨부파일 삭제 ==================
		// 삭제할 첨부파일정보 리스트 가져오기
		List<AttachVO> delAttachList = null;
		
		if (delUuidList != null) {
			delAttachList = attachService.getAttachesByUuids(delUuidList);
			
			deleteAttachFiles(delAttachList); // 첨부파일(썸네일도 삭제) 삭제하기
		}
		System.out.println("================ POST modify - 첨부파일 삭제 완료 ================");
		
		
		// 3) boardVO 준비해서  첨부파일 신규리스트, 삭제리스트와 함께
		//    테이블 글 수정(update)을 트랜잭션 단위로 처리
		
		// ===== update할 BoardVO 객체 데이터 설정 ======
		boardVO.setRegDate(new Date());
		
		
		// 글번호에 해당하는 글정보 수정. 첨부파일정보 수정(insert, delete) - 트랜잭션 단위 처리
		boardService.updateBoardAndInsertAttachesAndDeleteAttaches(boardVO, newAttachList, delUuidList);
		System.out.println("================ POST modify - 테이블 수정 완료 ================");
		
		// 리다이렉트 쿼리스트링 정보 설정
		rttr.addAttribute("num", boardVO.getNum());
		rttr.addAttribute("pageNum", pageNum);
		
		// 상세보기 화면으로 리다이렉트 이동
		return "redirect:/board/content";
	} // modify
	
	
	
	@GetMapping("/reply")
	public String replyForm(@ModelAttribute("reRef") String reRef, 
			@ModelAttribute("reLev") String reLev, 
			@ModelAttribute("reSeq") String reSeq, 
			@ModelAttribute("pageNum") String pageNum, 
			Model model) {
		
		System.out.println("replayForm()호출!");
	
		return "board/replyWrite";
	} // replyForm
	
	
	// 답글쓰기
	@PostMapping("/reply")
	public String reply(List<MultipartFile> files, BoardVO boardVO, String pageNum,
			HttpServletRequest request, RedirectAttributes rttr) 
			throws IllegalStateException, IOException {
		
		// insert할 새 글번호 가져오기
		int num = boardService.getNextNum();
		
		// 첨부파일 업로드(썸네일 생성) 후 attachList 리턴
		List<AttachVO> attachList = uploadFilesAndGetAttachList(files, num);
		
		// insert 할 답글 BoardVO 객체 데이터 설정
		
		boardVO.setNum(num);
		boardVO.setReadcount(0);
		boardVO.setSecret(0);
		boardVO.setRegDate(new Date());
		boardVO.setAttachList(attachList); // 첨부파일 정보 리스트 저장
		
		// ※ 현재 boardVO에 들어있는 reRef, reLev, reSeq는 바로 insert될 답글 정보가 아님에 주의!
		//   답글을 남길 대상글에 대한 reRef, reLev, reSeq 정보임에 주의!
		
		
		//boardService.addReplyAndAttaches(boardVO, attachList);
		boardService.addReplyAndAttaches(boardVO);
		//=========================================================
		
		// 리다이렉트시 서버에 전달할 데이터를 rttr에 저장하면 스프링이 자동으로 쿼리스트링으로 변환하여 처리해준다.
		rttr.addAttribute("num", boardVO.getNum());
		rttr.addAttribute("pageNum", pageNum);
		
		return "redirect:/board/content";
	} // reply
	
	@GetMapping("/display")
	@ResponseBody  // 컨트롤러 메소드가 리턴하는 데이터 자체를 바로 응답으로 주고자 할 경우 사용함
	public ResponseEntity<byte[]> getImageFile(String fileName) throws IOException {
		System.out.println("fileName : " + fileName);
		
		File file = new File("C:/project_myStreaming/upload", fileName);
		System.out.println("실제 이미지 파일 경로 : " + file.getPath());
		
		HttpHeaders headers = new HttpHeaders();
		
		String contentType = Files.probeContentType(file.toPath());
		headers.add("Content-Type", contentType); // "image/jpeg"  "image/png"  "image/gif"
		
		byte[] imageData = FileCopyUtils.copyToByteArray(file);
		System.out.println("imageDate"+ imageData );
		
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
		return responseEntity;
	} // getImageFile
	
	
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) throws UnsupportedEncodingException {
		System.out.println("fileName : " + fileName);
		
		File file = new File("C:/project_myStreaming/upload", fileName);
		
		Resource resource = new FileSystemResource(file);
		System.out.println("resource : " + resource);
		
		if (resource.exists() == false) { // 다운로드할 파일이 존재하지 않으면
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 404 코드. 자원없음 응답코드 보내고 종료.
		}
		
		// 다운로드할 파일이 존재하면
		String resourceName = resource.getFilename();
		System.out.println("resourceName : " + resourceName);
		// resourceName : 7f34243e-d680-451e-b935-02ad55345ece_학습안내서.pdf
		
		int beginIndex = resourceName.indexOf("_") + 1;
		String originFilename = resourceName.substring(beginIndex);  // 순수 파일명 구하기
		System.out.println("originFilename : " + originFilename);  // "학습안내서.pdf"
		
		// 다운로드 파일명의 문자셋을 "utf-8"에서 "iso-8859-1"로 변환하기
		String downloadName = new String(originFilename.getBytes("utf-8"), "iso-8859-1");
		System.out.println("downloadName : " + downloadName);
		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("Content-Type", "application/octet-stream"); // 애노테이션의 produces 속성으로 대체함
		headers.add("Content-Disposition", "attachment; filename=" + downloadName); // 다운로드 파일명을 헤더에 설정하기
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // downloadFile
	
	
	



}