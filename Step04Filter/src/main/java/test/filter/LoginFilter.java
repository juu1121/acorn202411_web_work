package test.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//자바에서 {}는 = 배열이다. {1,2,3} {"kim", "lee"}
//배열로 필터링할 요청경로 적기
@WebFilter({"/test/protected/*", "/xxx/protected/*"})
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpSession session=request.getSession();
		
		//session 영역에 id라는 키값으로 저장된 값이 있는지 확인한다 (로그인된 사용자인지 확인)
		String id=(String)session.getAttribute("id");
		//만일 로그인하지않았다면
		if(id==null) {
			//로그인페이지로 리다일렉트이동시킨다.
			String cPath=request.getContextPath();
			HttpServletResponse response=(HttpServletResponse)resp;
			response.sendRedirect(cPath+"/user/loginform.jsp");
		}else {//로그인을 한 사용자라면
			//관여하지않고 요청의 흐름을 이어간다
			chain.doFilter(req, resp);
		}
	}
}
