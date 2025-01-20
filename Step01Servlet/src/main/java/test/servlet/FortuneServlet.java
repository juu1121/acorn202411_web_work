package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 요청 경로를 작성할때 주의점!
 * 
 * 1. context path는 생략한다!
 * 2. 반드시 /로 시작한다.
 */
@WebServlet("/fortune")
public class FortuneServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//오늘의 운세를 어떤 로직에 의해서 얻어왔다고 가정하자
		String foutuneToday="동쪽으로 가면 귀인을 만나용";
		
		// 응답 인코딩설정
		resp.setCharacterEncoding("utf-8");
		// 응답컨텐츠설정
		// ()에 뭘 응답할건지 알려주는거!//html형식응답할거야~준비해~
		resp.setContentType("text/html; charset=utf-8");
		// 요청을 한 클라이언트에게 문자열을 출력할수있는 객체
		PrintWriter pw = resp.getWriter();
		pw.println("<!doctype html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='utf-8'>");
		pw.println("<title>오늘의 운세 페이지</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<p> 오늘의 운세 : <strong>"+foutuneToday+"</strong></p>");
		pw.println("</body>");
		pw.println("</html>");

		
		
		pw.close();
	}
}
