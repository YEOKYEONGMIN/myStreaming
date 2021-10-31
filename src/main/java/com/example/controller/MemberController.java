package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
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
	public void regiterForm() {
		// 호출 확인용
		System.out.println("회원가입 화면 호출 확인....");
		
	} // regiterForm
	
	
	@GetMapping("/login")
	public void loginForm() {
		System.out.println("로그인 화면 호출 확인....");
		
	} // loginForm
	
	
	@GetMapping("/logout")
	public String logoutForm(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		session.invalidate();
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) {
			for (Cookie c : cookies) {
				// 쿠키이름은 userId로
				if (c.getName().equals("userId")) {
					c.setMaxAge(0);
					c.setPath("/");
					response.addCookie(c);
				}
			} // for
		} // if
		
		return "redirect:/member/login";
	} // logoutForm
	
	
	@GetMapping("/modify")
	public void modifyForm(HttpSession session, Model model) {
		System.out.println("회원정보 변경 화면 호출 확인...");
		
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
		
		int memberCount = memberService.getMemberCount(memberVO.getId());
		String msg = "회원가입을 완료하였습니다.";
		
		// 필수정보 입력 확인 (일치하지 않을시)
		// 임의로 아이디, 비밀번호, 비밀번호 확인란만 필수정보로 입력했습니다. 필수정보 추가시 수정예정
		if (memberVO.getId() == "" || memberVO.getPasswd() == "" || passwd2 == "") {
			msg = "필수 회원정보를 입력해주세요.";
			return pageBack(msg);
		}
		
		// 비밀번호란과 비밀번호 확인란 일치 여부 확인 (일치하지 않을시)
		if (!memberVO.getPasswd().equals(passwd2)) {
			msg = "입력하신 두개의 비밀번호가 일치하지 않습니다.";
			return pageBack(msg);
		}
		
		// 아이디 중복 여부 확인 (회원정보 있을시)
		if (memberCount == 1) {
			msg = "이미 존재하는 아이디입니다.";
			return pageBack(msg);
		}
		
		// 회원가입 처리
		String passwd = memberVO.getPasswd();
		String pwHash = BCrypt.hashpw(passwd, BCrypt.gensalt());
		memberVO.setPasswd(pwHash);
		
		memberService.register(memberVO);
		
		return pageRedirect(msg, "/");
		
	} // register
	
	// 로그인은 MemberRestController에서 처리
	
	@PostMapping("/modify")
	public ResponseEntity<String> modify(MultipartFile file, MemberVO memberVO) throws IOException {
		MemberVO member = memberService.getMemberAndProfilepic(memberVO.getId());
		ProfilepicVO profilepic = member.getProfilepicVO();
		
		String msg = "회원정보를 수정하였습니다.";
		
		if (memberVO.getPasswd().length() == 0) {
			msg = "비밀번호를 입력해주세요.";
			return pageBack(msg);
		}
		
		if (!BCrypt.checkpw(memberVO.getPasswd(), member.getPasswd())) {
			msg = "비밀번호가 틀립니다.";
			return pageBack(msg);
		}
		
		if (!file.isEmpty()) {
			Map<String, Object> map = uploadProfilepicAndGetProfilepic(file, member.getId());
			
			if (map.get("result").toString().equals("failed")) {
				msg = "프로필 사진은 이미지 파일로 업로드 해주세요.";
				return pageBack(msg);
			}
			
			ProfilepicVO newProfilepic = (ProfilepicVO) map.get("profilepicVO");
			
			if (profilepic == null) {
				profilepicService.insertProfilepic(newProfilepic);
			} else {
				profilepicService.updateProfilepic(newProfilepic);
			}
		} // if (!file.isEmpty())
		
		memberService.updateById(memberVO);
		
		return pageRedirect(msg, "/");
		
	} // modify
	
	
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
	
	
	// 이미지 파일 확인 메소드
	private boolean checkImageFile(File file) throws IOException {
		
		String fileType = Files.probeContentType(file.toPath());
		
		return fileType.startsWith("image");
	} // checkImageFile
	
	
	// 프로필 사진 파일 업로드 및 정보 가져오는 메소드
	private Map<String, Object> uploadProfilepicAndGetProfilepic(MultipartFile pic, String id) throws IOException {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		String path = "C:/project_mystreaming/profilepic_" + id;
		
		File uploadPath = new File(path);
		
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		String orginalFilename = pic.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String uploadFilename = uuid.toString() + "_" + orginalFilename;
		
		File uploadFile = new File(uploadPath, uploadFilename);
		
		if (!checkImageFile(uploadFile)) {
			resultMap.put("result", "failed");
			return resultMap;
		}
		
		pic.transferTo(uploadFile);
		
		File thumbPath = new File(uploadPath, "s_" + uploadFilename);
		Thumbnailator.createThumbnail(uploadFile, thumbPath, 200, 300);
		
		ProfilepicVO profilepicVO = new ProfilepicVO();
		profilepicVO.setUuid(uuid.toString());
		profilepicVO.setUploadpath("profilepic_" + id);
		profilepicVO.setFilename(orginalFilename);
		profilepicVO.setMid(id);
		
		resultMap.put("result", "succes");
		resultMap.put("profilepicVO", profilepicVO);
		
		return resultMap;
	} // uploadProfilepic
	
	
	
}
