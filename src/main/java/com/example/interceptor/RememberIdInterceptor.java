package com.example.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RememberIdInterceptor implements HandlerInterceptor {

	@Override//컨트롤러가 호출되기전에 먼저 실행됨
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//요청 사용자의 세션 가져오기
		HttpSession session = request.getSession();
		
		//세션에 로그인 아이디가 있는지 확인(이미 로그인 되어 있나 확인)
		String id = (String)session.getAttribute("id");//login 할떄의 seesion id
		
		//세션에 로그인 아이디가 없으면 쿠키에서 아이디 찾아서 세션에 저장(로그인처리)
		Cookie[] cookies = request.getCookies();
		
		//로그인 상태 유지용 쿠키정보찾기
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("loginId")) {
					id = cookie.getValue();
					
					//세션에 저장(로그인 인증 처리)
					session.setAttribute("id", id);
				}//if
			}//for
		}//if
		
		return  true;
	}

	@Override//컨트롤러 메서드가 실행 한 후 view 페이지 랜더링 되기전에 호출된다
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override//컨트롤러 메서드가 실행 한 후 view 페이지 랜더링 된후 호출된다.
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	
		
	}

	
	
	
	
	
	
}//RememberIdInterceptor
