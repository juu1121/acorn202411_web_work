package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.dto.MemberDto;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//DB에서 읽어온 회원목록이라고 가정하자
		List<MemberDto> list = new ArrayList<>();
		list.add(new MemberDto(1, "김구라", "노량진"));
		list.add(new MemberDto(2, "해골", "행신동"));
		list.add(new MemberDto(3, "원숭이", "동물원"));
		
		/*
		 * list객체에 들어있는 내용을 이용해서 table형식으로 회원목록을 클라이언트에게 응답
		 * 
		 */
		//응답 인코딩설정
		resp.setCharacterEncoding("utf-8");
		//응답컨텐츠설정
		//()에 뭘 응답할건지 알려주는거!//html형식응답할거야~준비해~
		resp.setContentType("text/html; charset=utf-8");
		//요청을 한 클라이언트에게 문자열을 출력할수있는 객체  
		PrintWriter pw = resp.getWriter();
		pw.println("<!doctype html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='utf-8'>");
		pw.println("<title>MemberListServlet</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		
		pw.println("<table>");
			pw.println("<thead>");
				pw.println("<tr>");
					pw.println("<th>번호</th>");
					pw.println("<th>이름</th>");
					pw.println("<th>주소</th>");
				pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			for(MemberDto tmp:list) {
				pw.println("<tr>");
					pw.println("<td>"+tmp.getNum()+"</td>");
					pw.println("<td>"+tmp.getName()+"</td>");
					pw.println("<td>"+tmp.getAddr()+"</td>");
				pw.println("</tr>");
			}
			pw.println("</tbody>");
		pw.println("</table>");
		
		pw.println("</body>");
		pw.println("</html>");

		pw.close();
		
	}
	

}
