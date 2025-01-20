package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Ping요청을 처리할 Servlet // 이 클래스로 생성된 객체가 tomcat서버에서 /ping요청이 오면 직접응답을 하도록

//3. 어떤 요청이 왔을떄 이 클래스로 생성된 객체로 응답을 할지 정해야한다.
@WebServlet("/ping")   //contextpath경로는 쓰지않는다(ex) "/Hello/ping")
public class PingServlet extends HttpServlet{ //1. HttpServlet클래스를 상속받는다.
	//2. service()메소드를 오버라이딩한다.
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpServletResponse 이 객체를 이용해응답..
		//(HttpServletRequest request, HttpServletResponse respons )여기에 참조값이 알아서 전달...전달됐다는가정하에 톰캣서버가 저 객체의 참조값을 전달..
		
		//ping라는 요청이 오면 톰캣서버가알아서 ()여기에 요청에관한정보랑, 응답에관한정보를 저기에 있는 객체에 전달하고
		//그 값을 {}에서 쓰는거!
		
		//클라이언트에게 문자열을 출력할수있는 객체를 얻어낸다.
		PrintWriter pw =response.getWriter();
		//이 객체를 이용해서 출력하는 문자열은 요청을 한 클라이언트에게 출력된다.
		pw.println("<!doctype html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='utf-8'>");
		pw.println("<title>나의페이지</title");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1>pong!</h1>");
		pw.println("</body>");
		pw.println("</html>");
		//pw.println("pong!");
		pw.close();
	}

}
