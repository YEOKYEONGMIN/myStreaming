package com.example.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
//콘트롤러 이전에 체크함(로그인전에 확인해야함)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {// request, response는 톰캣이 주는 객체 , object(최종적으로 호출할 컨트롤러? 관리)

		System.out.println("LoginCheckInterceptor()호출");

		// 요청 객체로부터 세션 참조 가져오기
		HttpSession session = request.getSession();
		// 로그인 검증하기, 세션값 가져오기
		String id = (String) session.getAttribute("id");
		// 로그인 세션값 없으면, 로그인 페이지로 이동시키기
		if (id == null) {
			
			response.sendRedirect("/home");//
			
			return false;// false리턴 하여 호출(예정된 컨트롤러 호출x)
		}

		return true;
	}// preHandle

}// LoginCheckInterceptor