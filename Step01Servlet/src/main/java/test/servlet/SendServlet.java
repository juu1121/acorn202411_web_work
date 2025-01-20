package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 클라이언트는 msg라는 파라미터명으로 문자열을 전송한 상황이다.
 * 이 서블릿에서는 그 파라미터를 추출해서 콘솔에 출력하는 테스트를 할것임.
 * 요청 파라미터 추출은 HttpServletRequest 객체를 이용해서 추출하면 된다.
 */
@WebServlet("/send")
public class SendServlet extends HttpServlet {
	/*
	 *  /send?msg=xxx
	 *  <input type = "text" name = "mag">
	 *  .getParameter("전송되는파라미터명") 메소드는 get방식, post방식 전송 파라미터를 모두 추출가능
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//클라이언트가 뭔가를 요청하면서 msg라는 파라미터형태로 문자열을 전송..
		//HttpServletRequest를 사용해 전송된 msg문자열 추출! //요청의 객체req에 해당정보가 들어있는거!
		
		//req.getParameter() : 어떤이름으로 전달된 파라미터를 읽어올것인가!
		//파라미터명을 전달을해주면 : 스트링타입으로 리턴해준다! //지금은 하나로 전달됐지만 파라미터가 여러개가 될 수도있음!
		String msg = req.getParameter("msg");
		System.out.println("폼 전송된 내용:"+msg);
		
		//간단응답
		PrintWriter pw = resp.getWriter();
		pw.println("/send okay~");
		pw.close();
		
	}
}
