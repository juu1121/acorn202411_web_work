package test.filter;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import test.user.dto.SessionDto;
/*
 * 여긴 jsp가 아니니까, 리퀘스트.get세션 해서 http세션객체를 직접 얻어내야함
 * 원래 가려던 곳으로 보내는것도 잊지말아야 해 
*/
@WebFilter({"/member-only/*", "/staff/*", "/admin/*", "/user/protected/*"}) //자바에서 배열은 중괄호
public class LoginFilter implements Filter{
	
	// @WebFilter()에 명시한 패턴의 요청을 하면 아래의 메소드가 호출된다.
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		//매개변수에 전달된건 부모타입, 부모타입엔 getSession 이 없어서 => 부모타입을 자식타입으로 캐스팅해서, session을 얻어냄.
		//매개변수에 전달된 객체를 이용해서 자식 type객체의 참조값을 얻어낸다.
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //session 영역에서 로그인된 정보를 얻어내기 위한 객체
        HttpSession session = req.getSession();
        //session 영역에 sessionDto라는 키값으로 저장된 값이 있으면 얻어내서 원래 type으로 캐스팅
        SessionDto dto = (SessionDto) session.getAttribute("sessionDto");
        //만일 로그인을 하지 않았다면
        if (dto == null) { 
        	//리다일렉트 : 요청을 다시하라고 응답주는거(다시해! 라는 <--이 자체가 응답)
        	//포워드 : 응답위임
        	//로그인 페이지로 리다일렉트 시키는 메소드를 호출해서 리다일렉트 시킨다.
            redirectToLogin(req, res);
            //메소드를 여기서 끝내기
            return;
        }   
        // role을 확인해서 /admin/* , /staff/* 요청도 필터링을 해준다.
        // Role-based authorization
        String role = dto.getRole();
        String requestURI = req.getRequestURI();
       
        if (requestURI.startsWith(req.getContextPath() + "/admin") && !"ADMIN".equals(role)) {
        	//금지된 요청이라고 응답한다.(에러페이지로 응답)
        	res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            return;
        }

        if (requestURI.startsWith(req.getContextPath() + "/staff") && !"STAFF".equals(role) && !"ADMIN".equals(role)) {
        	//금지된 요청이라고 응답한다.(에러페이지로 응답)
        	res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            return;
        }

        // 여기까지 실행의 흐름이 넘어오면 요청의 흐름을 계속 이어간다.
        //여기서 원래 목적지로 간다.(더 이상 개입 놉)
        chain.doFilter(request, response);
    }
	//리다일렉트(요청을 새로운 경로로 다시 하라는 응답) 응답을 하는 메소드
    private void redirectToLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	//원래 요청 url을 읽어온다.
        String url = req.getRequestURI();
        //혹시 뒤에 query parameter가 있다면 그것 역시 읽어온다. ?뒤에 "nun=xxx&count=xxx" //리다일렉트시킬때, query parameter도 가져가야한다
        String query = req.getQueryString();
        String encodedUrl = query == null ? URLEncoder.encode(url, "UTF-8") : URLEncoder.encode(url + "?" + query, "UTF-8");
        //로그인페이지로 리다일렉트 이동하면서 원래 가려던 목적지 정보도 닽이 넘겨준다.
        res.sendRedirect(req.getContextPath() + "/user/login-form.jsp?url=" + encodedUrl);
    }

}
