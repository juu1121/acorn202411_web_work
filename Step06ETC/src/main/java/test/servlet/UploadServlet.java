package test.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
//업로드서블릿 => 파일업로드처리(요청파라미터추출+파일정보저장) //응답은 file/upload.jsp가 함!

@WebServlet("/file/upload")

//작성한 소스코드 --컴파일(원본소스코드에 @어노테이션동작이 추가로붙음)--> class파일로변경
//파일업로드할때 있어야함.
@MultipartConfig(
	fileSizeThreshold = 1024*1024*5, //메모리 임계값
	maxFileSize=1024*1024*50, //최대 파일사이즈(업로드사이즈를 제한)
	maxRequestSize = 1024*1024*60 //최대 요청사이즈
)

public class UploadServlet extends HttpServlet{
	//부모가가진 서비스...아무것도없대...
	//서비스메소드는 get과 post둘다 가능했지만, 요청방식에따라 메소드를 고를수있다.	
	//doPost메소드 post방식일때만 요청되도록!
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//"/upload" 디렉터리의 실제 경로를 반환하는 메서드 : this.getServletContext().getRealPath(디렉토리)
		//(메소드로리턴받은이유 => 윈도우면C로시작, 리눅스면/로 시작)
		//업로드 될 실제경로 얻어내기
		String uploadPath=getServletContext().getRealPath("/upload");
		
		File uploadDir = new File(uploadPath); //해당경로에대한 파일객체생성!//파일객체..()안에있는 폴더나파일을 제어할수있는 파일객체!
		//만일 upload폴더가 존재하지않으면, 
		if(!uploadDir.exists()) {
			uploadDir.mkdir(); //실제로 폴더 만들기
		}
		//input type ="text"에 입력한 문자열
		String title = req.getParameter("title");
		
		//파일명이 겹치지않게 저장하기위한 랜덤한문자열 얻어내기
		//UUID클래스에 randomUUID메소드를 사용 
		String uid = UUID.randomUUID().toString();
		System.out.println(uid);
		String orgFileName=null; //원본파일명
		String saveFileName=null; //저장된파일명
		
		//파일 데이터 처리
		//폼에서 업로드한 파일을 Part 객체로 가져오기 //업로드한거여서 리퀘스트로 읽어올수없다...
		Part filePart = req.getPart("myFile"); 
		if(filePart != null) {
			//원본 파일의 이름 얻어내기 //getSubmittedFileName:업로드된 원래파일이름을 반환
			orgFileName = filePart.getSubmittedFileName();
			//저장될 파일의 이름 구성하기
			saveFileName=uid+orgFileName; //원본파일명에 uid붙이기
			
			//저장할 파일의 경로 구성하기 
			//File.separator 환경에맞게 문자열이나옴("\","/")->"윈도우는 \" "리눅스는/"
			//지금 업로드한거 임시경로에있다  -> 임시경로에있는 파일을 최종목적지로 옮길거 (filePath)
			String filePath = uploadPath + File.separator +saveFileName;
			/*
			 * 업로드된 파일은 임시폴더에 임시파일로 저장이된다.
			 * 해당 파일에서 byte알갱이를 읽어 들일수있는 InputStream객체를 얻어내서
			 */
			//파일저장
			InputStream is = filePart.getInputStream(); //파일에 이름을 읽을준비만되어있어..임시저장되어있는곳에 빨대꽂았어(현재파일은 임시폴더에있음!)
			// 원하는 목적지에 copy를 해야한다.
			//Files.copy()는 파일을 복사하는 메소드
			//copy(첫번째인자 inputStream, 복사할경로 Path객체)
			//Paths.get() 문자열 형식의 파일 경로를 Path객체(파일 시스템 내의 경로)로 변환하는 기능
			Files.copy(is, Paths.get(filePath)); //여기서 파일이 옮겨진다!(임시폴더에있는걸 해당경로에 복사!)
			
		}
		
		//파일의 크기 얻어내기 (큰 정수이기 때문에 long type이다.) //파일사이즈는 나중에 다운로드할때 필요하다.
		long fileSize = filePart.getSize();
		
		//응답에 필요한 데이터를 request영역에 담기
		req.setAttribute("title", title);	//타이틀
		req.setAttribute("orgFileName", orgFileName);	//원본파일명
		req.setAttribute("saveFileName", saveFileName);	//저장된파일명
		req.setAttribute("fileSize", fileSize);	//파일사이즈
		req.setAttribute("uploadPath", uploadPath); //경로
		
		//jsp 페이지로 응답을 위임하기(포워드이동)
		//포워드이동은 해당프로젝트에서이동. 다른프로젝트로 이동할수없음.  
		//응답위임할때cpath붙이지않는다.리다일렉트할떄는 cpath경로 필요
		RequestDispatcher rd = req.getRequestDispatcher("/file/upload.jsp");
		rd.forward(req, resp);
	}
}



