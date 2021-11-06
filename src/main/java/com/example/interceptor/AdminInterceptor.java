package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;


public class AdminInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {// request, response는 톰캣이 주는 객체 , object(최종적으로 호출할 컨트롤러? 관리)

		System.out.println("LoginCheckInterceptor()호출");
		
	//요청 객체로부터 세션 참조 가져오기
	HttpSession session = request.getSession();
	
	String id = (String)session.getAttribute("id");
	// admin이 아니면 관리자페이지 x
	if(id == null && id != "admin") {
		
	response.sendRedirect("/");//
	return false;
	}		
	
	return true;
}//preHandle
	
}//LoginCheckInterceptor