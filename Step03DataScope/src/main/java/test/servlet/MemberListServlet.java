package test.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.member.dto.MemberDto;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DB에서 읽어온 회원 목록이라고 가정하자
		MemberDto mem1 = new MemberDto(1, "김구라", "노량진");
		MemberDto mem2 = new MemberDto(2, "해골", "행신동");
		MemberDto mem3 = new MemberDto(3, "원숭이", "동물원");
		List<MemberDto> list = new ArrayList<>();
		list.add(mem1);
		list.add(mem2);
		list.add(mem3);
		
		//webapp/member/list.jsp페이지에서 회원목록을 table요소를 이용하여 출력
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("/member/list.jsp");
		rd.forward(request, response);
	}
}
