package test.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/fortune")
public class FortuneServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//오늘의 운세를 얻어오는 비즈니스 로직을 수행(DB에서 읽어왔다고 가정)
		String fortune="동쪽으로가면 귀인을 만나요!";
		//오늘의 운세를 request scope에 담는다.
		//"fortuneToday"라는 키값으로 String type담기
		request.setAttribute("fortuneToday", fortune);
		//응답은 jsp페이지에 위임한다(forward이동)
		// .getRequestDispatcher("응답을 위임할 jsp페이지의 위치")
		//응답을 위임받을 jsp페이지지정
		RequestDispatcher rd = request.getRequestDispatcher("/test/fortune.jsp");
		//지정된 url로 요청과 응답을 전달
		rd.forward(request, response);
		
		
	}
}
