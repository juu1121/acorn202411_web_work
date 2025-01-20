package test.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/file/download")
public class FileDownServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//다운로드 작업에 필요한 3가지 정보 (원본파일명, 저장된파일명, 파일의 크기) 얻어오기
		//지금은 파라미터로 전달되지만 실제로는 DB에 저장된 정보를 읽어와서 다운로드해야한다.
		String orgFileName=req.getParameter("orgFileName");
		String saveFileName = req.getParameter("saveFileName");
		long fileSize=Long.parseLong(req.getParameter("fileSize"));
		//웹브라우저에 파일을 전송하기위해 미리 3가지 정보를 알려준다.
		//응답 헤더 정보 설정
		resp.setHeader("Content-Type", "application/octet-stream; charset=UTF-8"); //내가 응답할 컨텐트의 내용, 1. 파일을 전송할거야!
		//다운로드 시켜줄 파일명 인코딩  
	   	String encodedName=URLEncoder.encode(orgFileName, "utf-8");
		//파일명에 공백이있는 경우 처리 
		encodedName=encodedName.replaceAll("\\+"," ");
		
		//encodedName 이걸보고 파일명을 알려준다.
		resp.setHeader("Content-Disposition", "attachment;filename="+encodedName); //`encodedName` 2. 파일명은 이거야(다운로드할때 생성되는이름(확장자까지))
		resp.setHeader("Content-Transfer-Encoding", "binary");
		
		//다운로드할 파일의 크기
		resp.setContentLengthLong(fileSize); //3.파일의 크기는 이거야
		
		//다운로드 시켜줄 파일의 실제 경로
		String path=getServletContext().getRealPath("/upload")+File.separator+saveFileName; 
		
		FileInputStream fis=null;
		BufferedOutputStream bos=null;
		try {
			//파일에서 byte 을 읽어들일 객체
			fis=new FileInputStream(path);
			
			//클라이언트에게 출력할수 있는 스트림 객체 얻어오기
			bos=new BufferedOutputStream(resp.getOutputStream());
			//BufferedOutputStream(resp.getOutputStream()); 데이터를 버퍼에 모아두었다가 한 번에 처리
			//response.getOutputStream(): HttpServletResponse 객체에서 클라이언트로 전송할 출력 스트림을 가져온것.
			//FileOutputStream은 파일에 바이트 단위로 데이터를 쓸 때 사용하는 클래스
			
			//한번에 최대 1M byte 씩 읽어올수 있는 버퍼
		   	byte[] buffer=new byte[1024*1024];
		   	int readedByte=0;
		   	//반복문 돌면서 출력해주기
		   	while(true) {
		   		//byte[] 객체를 이용해서 파일에서 byte 알갱이 읽어오기
		   		readedByte = fis.read(buffer);
		   		if(readedByte == -1)break; //더이상 읽을 데이터가 없다면 반복문 빠져 나오기
		   		//읽은 만큼 출력하기
		   		bos.write(buffer, 0, readedByte);
		   		bos.flush(); //출력
		   	}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(fis!=null)fis.close();
			if(bos!=null)bos.close();
		}	
	}
}
