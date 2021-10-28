package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.AttachVO;
import com.example.domain.BoardVO;
import com.example.domain.Criteria;
import com.example.domain.PageDTO;
import com.example.service.AttachService;
import com.example.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private AttachService attachService;

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {

		System.out.println("list()호출됨");

		// board테이블에서 (검색어가 있으면)검색,페이징 적용한글 리스트 가져오기
		List<BoardVO> boardList = boardService.getBoards(cri);

		// ================================ 게시글 추가 부분
		// =========================================
		// 게시글 없으면 100개 넣기 (테스트 진행을 위해서 임시로 추가)
		if (boardList.isEmpty()) { // 게시글 하나도 없을 경우
			for (int i = 1; i <= 100; i++) {
				// insert할 새 글번호 가져오기
				int num = boardService.getNextNum();

				BoardVO boardVO = new BoardVO();
				boardVO.setNum(i);
				boardVO.setMid("테스트");
				boardVO.setSubject(i + "번글");
				boardVO.setContent("테스트 내용 " + i);
				boardVO.setRegDate(new Date(System.currentTimeMillis()));
				boardVO.setReRef(num);
				boardVO.setReLev(0);
				boardVO.setReSeq(0);
				boardService.addBoard(boardVO);
			}

		} // if

		boardList = boardService.getBoards(cri); // 게시글 추가될시 새로 불러옴
		// =========================================================================================

		// 검색유형, 검색어가 있으면 적용하여 글개수 가져오기
		int totalCount = boardService.getCountBySearch(cri);

		// 페이지 블록 정보 객체 준비, 필요한 정보를 생성자로 전달
		PageDTO pageDTO = new PageDTO(cri, totalCount);

		// 뷰에서 사용할 데이터를 Model객체에 저장 - > 스프링(dispatcher servlet)이 requestScope로 옮겨줌
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageMarker", pageDTO);

		return "board/boardList";

	}

	@GetMapping("/content")
	public String content(int num, @ModelAttribute("pageNum") String pageNum, Model model) {

		// 조회수 1 증가시키기
		boardService.updateReadcount(num);

		// 상세보기할 글 한개 가져오기
		// BoardVO boardVO = boardService.getBoard(num);
		// 첨부파일 정보 리스트 가져오기
		// List<AttachVO> attachList = attachService.getAttachesByBno(num);

		// join 쿼리문으로 게시판 글과 첨부파일 리스트 정보 가져오기 (위에 두개처럼 한개씩 따로 말고 두개 같이 가져오기)
		BoardVO boardVO = boardService.getBoard(num);
		System.out.println(boardVO);

		model.addAttribute("board", boardVO);
		// 나중에 쓰임
//		model.addAttribute("attachList", boardVO.getAttachList());
//		model.addAttribute("attachCount", boardVO.getAttachList().size());
		// model.addAttribute("pageNum", pageNum); - 위에 애노테이션으로 줬기 때문에 직접적으로 쓸 필요 없다.
		// 애노테이션으로 주면 서버에서만 필요한 값이고, 직접적으로는 쓸 필요가 없다는 뜻도 함께 가지고 있다.

		return "board/boardContent";
	} // content

	// "년/월/일" 형식의 폴더명을 리턴하는 메소드
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String str = sdf.format(new Date());
		return str;
	}

	// 새로운 주글쓰기 폼 화면 요청
	@GetMapping("/write")
	public String write(@ModelAttribute("pageNum") String pageNum, HttpSession session) {

		return "board/Write";
	} // write

	@GetMapping("/remove")
	public String remove(int num, String pageNum) { // 삭제 버튼 누르면 이 /board/remove?num=${ board.num }&pageNum=${ pageNum }
													// 주소 정보가 넘어가서 int num(게시글 번호)과 pageNum 을 매개 변수로 받음

		// =========== 첨부파일 삭제 ===================

		List<AttachVO> attachList = attachService.getAttachesByBno(num);

		// 이제 반복문 돌면서 삭제하면 됨. ->이걸 메소드로 만들어놓기. 나중에 수정에도 필요할 수도 있으니까

		// 첨부파일이 삭제되었으면 남은건 테이블이니까 이제 테이블 삭제하기
		// attach와 board 테이블 내용 삭제 - 트랜잭션 단위로 처리
		boardService.deleteBoardByNum(num);

		// 글목록으로 리다이렉트 이동
		return "redirect:/board/list?pageNum=" + pageNum;
	} // remove

	@GetMapping("/modify")
	public String modifyForm(int num, @ModelAttribute("pageNum") String pageNum, Model model) {

		BoardVO boardVO = boardService.getBoard(num); // JOIN 으로 모두 가져옴

		model.addAttribute("board", boardVO);
		// model.addAttribute("attachList", boardVO.getAttachList());

		return "board/Modify";
	} // modifyForm

	@GetMapping("/reply")
	public String replyForm(@ModelAttribute("reRef") String reRef, @ModelAttribute("reLev") String reLev,
			@ModelAttribute("reSeq") String reSeq, @ModelAttribute("pageNum") String pageNum, Model model) {

		return "board/Write";
	} // replyForm

}
