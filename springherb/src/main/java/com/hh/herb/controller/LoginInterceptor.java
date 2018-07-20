package com.hh.herb.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
//public class LoginInterceptor implements HandlerInterceptor{
	private static final Logger logger
		=LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 컨트롤러(핸들러) 수행 전 호출되는 메서드
		logger.info("preHandle() - 컨트롤러 수행전!!");
		
		String userid =(String) request.getSession().getAttribute("userid");
		if(userid==null || userid.isEmpty()) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println("<script type='text/javascript'>");
			out.print("alert('먼저 로그인하세요!!!');");
			out.print("location.href='"+ request.getContextPath()
					+"/login/login.do';");
			out.println("</script>");
			
			return false;  //=> 다음 컨트롤러(핸들러)를 실행하지 않는다
		}
		
		return true;  //다음 컨트롤러를 실행함
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 컨트롤러 수행 후 호출되는 메서드
		logger.info("postHandle() - 컨트롤러 수행후!!");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 클라이언트의 요청을 처리한 뒤, 
		//즉 뷰를 통해서 클라이언트에 응답을 전송한 뒤에 실행됨
		logger.info("afterCompletion() - 클라이언트에 응답 전송 후!!");
		
	}

}
