package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.MemberVO;
import com.example.domain.ProfilepicVO;
import com.example.service.MemberService;
import com.example.service.ProfilepicService;
import com.example.util.JScript;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private ProfilepicService profilepicService;

	// ========================= GET 요청 모음 =========================

	// 회원가입 화면
	@GetMapping("/register")
	public void registerForm() {
		// 호출 확인용
		System.out.println("회원가입 화면 호출 확인....");

	} // regiterForm

	@GetMapping("/login")
	public void loginForm() {
		System.out.println("로그인 화면 호출 확인....");

	} // loginForm

	@PostMapping("/logout")
	public String logoutForm(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		session.invalidate(); // 세션 비우기 (로그인 정보 비우기)

		// 로그인 유지용 쿠키값 가져오기
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				// 쿠키이름은 userId로
				if (c.getName().equals("userId")) {
					c.setMaxAge(0);
					c.setPath("/"); // 모든 경로에서 접근가능 하도록 설정
					response.addCookie(c); // 응답객체에 설정한 쿠키 유효시간 넣기
				}
			} // for
		} // if

		return "redirect:/";
	} // logoutForm

	@GetMapping("/modify")
	public void modifyForm(HttpSession session, Model model) {
		System.out.println("회원정보 변경 화면 호출 확인...");

		// 회원수정 화면에 필요시 정보 넘겨줄 용도
		String id = (String) session.getAttribute("id");
		MemberVO member = memberService.getMemberAndProfilepic(id);
		ProfilepicVO profilepic = member.getProfilepicVO();

		model.addAttribute("member", member);
		model.addAttribute("profilepic", profilepic);

	} // modifyForm

	@GetMapping("/changePasswd")
	public void changePasswdForm() {
		System.out.println("비밀번호 변경 화면 호출 확인...");

	} // changePasswdForm

	@GetMapping("/remove")
	public void removeForm() {

		System.out.println("회원탈퇴 화면 호출 확인...");
	} // removeForm

	// ========================== GET 요청 끝 ==========================

	// ========================= POST 요청 모음 =========================
	// 회원가입 처리
	@PostMapping("/register")
	public ResponseEntity<String> register(MemberVO memberVO, String passwd2) { // 비밀번호 확인란 name명은 임의로 passwd2로 처리

		String msg = "회원가입을 완료하였습니다."; // 보낼 메세지

		// 회원가입 처리
		// 비밀번호 암호화 후 객체에 넣기
		String passwd = memberVO.getPasswd();
		String pwHash = BCrypt.hashpw(passwd, BCrypt.gensalt());
		memberVO.setPasswd(pwHash);

		memberService.register(memberVO);

		return pageRedirect(msg, "/");

	} // register
	
	// 로그인은 MemberRestController에서 처리
	
	@PostMapping(value = "/modify")
	public ResponseEntity<String> modify(MultipartFile file, MemberVO memberVO) throws IOException {
		MemberVO member = memberService.getMemberAndProfilepic(memberVO.getId());
		ProfilepicVO profilepic = member.getProfilepicVO();

		String msg = "회원정보를 수정하였습니다.";
		System.out.println("modify memberVO: " + memberVO);

		if (memberVO.getPasswd().length() == 0) {
			msg = "비밀번호를 입력해주세요.";
			return pageBack(msg);
		}

		if (!BCrypt.checkpw(memberVO.getPasswd(), member.getPasswd())) {
			msg = "비밀번호가 틀립니다.";
			return pageBack(msg);
		}

		if (file != null && !file.isEmpty()) {
			Map<String, Object> map = uploadProfilepicAndGetProfilepic(file, member.getId());

			if (map.get("result").toString().equals("failed")) {
				msg = "프로필 사진은 이미지 파일로 업로드 해주세요.";
				return pageBack(msg);
			}

			ProfilepicVO newProfilepic = (ProfilepicVO) map.get("profilepic");

			if (profilepic == null) {
				profilepicService.insertProfilepic(newProfilepic);
			} else {
				deleteProfilepic(profilepic);
				profilepicService.updateProfilepic(newProfilepic);
			}
		} // if (!file.isEmpty())

		memberService.updateById(memberVO);

		return pageRedirect(msg, "/");

	} // modify
	
	

	// 관리자 기능
	@PostMapping("/login")
	public ResponseEntity<String> login(String id, String passwd, String rememberMe, HttpSession session,
			HttpServletResponse response) {

		MemberVO memberVO = memberService.getMemberById(id);

		boolean isPasswdSame = false;
		String message = "";

		if (memberVO.getId() == "admin") {
			isPasswdSame = BCrypt.checkpw(passwd, memberVO.getPasswd());

			if (isPasswdSame == false) { // 비밀번호 일치하지 않음
				message = "비밀번호가 일치하지 않습니다.";
			} // if
		} // if

		// 로그인 실패시 비밀번호 틀렸을때
		if (isPasswdSame == false) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");

			String str = JScript.back(message);

			return new ResponseEntity<String>(str, headers, HttpStatus.OK);
		}

		// 로그인 성공시, 로그인 인증하기
		session.setAttribute("id", id);

		// 로그인 상태유지가 체크되었으면
		if (rememberMe != null) {
			Cookie cookie = new Cookie("id", id); // 로그인 아이디로 쿠키정보 생성
			cookie.setPath("/");
			cookie.setMaxAge(60 * 10); // 초단위. 60초 * 10 -> 10분

			response.addCookie(cookie); // 응답객체에 쿠키를 추가해놓으면 최종응답시 쿠키를 클라이언트에게 전송해줌
		}

		// 관리자가 썸네일 필요 한가?
//		// 썸네일
//		ProfilePicVO profilePicVO =  profilePicService.getProfilePic(id);
//		// 로그인 성공시 썸네일
//		session.setAttribute("profilePicVO", profilePicVO);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");

		String str = JScript.href("관리자 입장!", "/member/adminlist");

		return new ResponseEntity<String>(str, headers, HttpStatus.OK);
	} // login

	@PostMapping("/adminModify") // 고객 정보 수정 저장 처리 요청
	public String adminModify(MemberVO memberVO) {
		// 화면에서 수정 입력한 정보를 DB에 저장한 후
		memberService.updateById(memberVO);

		// 화면으로 연결
		return "redirect:/member/adminDetail?id=" + memberVO.getId();

	}

	@PostMapping("/adminRemove") // 고객 정보 삭제 처리 요청
	public String delete(String id) {
		// 선택한 고객 정보를 DB에서 삭제한 후
		memberService.deleteById(id);
		// 목록 화면으로 연결
		return "redirect:list.cu";
	}
	// ========================== POST 요청 끝 ==========================

	// ========================= 메소드 모음 =========================

	// 페이지 뒤로가기 처리 메소드
	private ResponseEntity<String> pageBack(String msg) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");

		String code = JScript.back(msg);
		return new ResponseEntity<String>(code, headers, HttpStatus.OK);
	} // pageBack

	// 페이지 리다이렉트 처리 메소드
	private ResponseEntity<String> pageRedirect(String msg, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");

		String code = JScript.href(msg, url);
		return new ResponseEntity<String>(code, headers, HttpStatus.OK);
	} // pageRedirect
	
	// 이미지 파일여부, 리턴하는 메서드
	private boolean checkFileType(File file) throws IOException {
		boolean isImage = false;
		
		String contentType = Files.probeContentType(file.toPath());
		
		isImage = contentType.startsWith("image");
		
		return isImage;
	} // end of checkImageType
	
	// 파일업로드와 프로필사진 정보 가져오는 메소드
	private Map<String, Object> uploadProfilepicAndGetProfilepic(MultipartFile pic, String id) throws IllegalStateException, IOException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// C:/upload/profilepic/[유저 아이디]
		String path = "C:/project_myStreaming/upload/profilepic";
		String picPath = id + "_pic";
		
		File uploadPath = new File(path, picPath);
		
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		UUID uuid = UUID.randomUUID();
		String originalFilename = pic.getOriginalFilename();
		String uploadFilename = uuid.toString() + "_" + originalFilename;
		
		File uploadFile = new File(uploadPath, uploadFilename);
		
		// 이미지 파일 체크
		if (!checkFileType(uploadFile)) {
			resultMap.put("result", "failed");
			return resultMap;
		}
		
		pic.transferTo(uploadFile);
		
		// 썸네일
		File thumb = new File(uploadPath, "s_" + uploadFilename);
		Thumbnailator.createThumbnail(uploadFile, thumb, 200, 300);
		
		ProfilepicVO profilepicVO = new ProfilepicVO();
		profilepicVO.setMid(id);
		profilepicVO.setUuid(uuid.toString());
		profilepicVO.setUploadpath(picPath);
		profilepicVO.setFilename(originalFilename);
		
		System.out.println("profilepicVO: " + profilepicVO);
		
		resultMap.put("result", "success");
		resultMap.put("profilepic", profilepicVO);
		
		return resultMap;
	} // uploadProfilepicAndGetProfilepic
	
	// 프로필 파일 및 썸네일 파일 삭제
	private void deleteProfilepic(ProfilepicVO profilepicVO) {
		
		String delFileUuid = profilepicVO.getUuid();
		String path = "C:/project_mystreaming/upload/profilepic/" + profilepicVO.getUploadpath();
		System.out.println("path: " + path);
		
		File delFile = new File(path, delFileUuid + "_" + profilepicVO.getFilename());
		File delThumbFile = new File (path, "s_" + delFileUuid + "_" + profilepicVO.getFilename());
		
		if (delFile.exists()) {
			delFile.delete();
			System.out.println("이미지파일 삭제 성공");
		}
		if (delThumbFile.exists()) {
			delThumbFile.delete();
			System.out.println("썸네일 이미지파일 삭제 성공");
		}
		
	} // deleteProfilepic

}
