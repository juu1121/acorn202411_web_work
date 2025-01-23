package test.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import test.user.dao.UserDao;
import test.user.dto.SessionDto;
import test.user.dto.UserDto;

@WebServlet("/user/protected/update-profile")
@MultipartConfig( //이게 있어야 밑에 소스가 정상동작
		fileSizeThreshold = 1024*1024*5, // 메모리 임계값
		maxFileSize = 1024*1024*50,  //최대 파일 사이즈
		maxRequestSize = 1024*1024*60 //최대 요청 사이즈
	)
public class UpdateProfileServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SessionDto sessionDto=(SessionDto)req.getSession().getAttribute("sessionDto");
		UserDto dto=UserDao.getInstance().getData(sessionDto.getNum());
		//이미 저장된 프로필 이미지가 있는지 여부
		boolean hasProfileImage= dto.getProfileImage() == null ? false : true;
		
		String email=req.getParameter("email");
		dto.setEmail(email);
		
		//업로드될 실제 경로 얻어내기
		String uploadPath=this.getServletContext().getRealPath("/upload");
		
		File uploadDir = new File(uploadPath);
		//만일 upload 폴더가 존재 하지 않으면 
		if(!uploadDir.exists()) {
			uploadDir.mkdir(); //실제로 폴더 만들기
		}
		
		String orgFileName=null;
		String saveFileName=null;
		
		//파일 데이터 처리
		Part filePart = req.getPart("profileFile");
		
		//만일 업로드된 프로필 이미지가 있다면 
		//이미지가 있는데 선택을 안 하면 => 이미지가있으니까null은 아니지만// 선택을 안했으니까 size가 0 : 그래서 여기 실행의흐름이 else로 간다!
		if(filePart != null && filePart.getSize() > 0) {
			//원본 파일의 이름 얻어내기
			orgFileName=filePart.getSubmittedFileName();
			//파일명이 겹치지 않게 저장하기 위한 랜덤한 문자열 얻어내기
			String uid = UUID.randomUUID().toString();
			//저장될 파일의 이름 구성하기
			saveFileName=uid+orgFileName;
			//저장할 파일의 경로 구성하기
			String filePath=uploadPath + File.separator + saveFileName;
			/*
			 * 업로드된 파일은 임시 폴더에 임시 파일로 저장이 된다.
			 * 해당 파일에서 byte 알갱이를 읽어 들일수 있는 InputStream 객체를 얻어내서 
			 */
			InputStream is=filePart.getInputStream();
			// 원하는 목적지에 copy 를 해야 한다 
			Files.copy(is,  Paths.get(filePath));
			
			//기존에 업로드 파일이 있다면 기존 파일을 삭제한다.
			if(hasProfileImage) {
				String deleteFilePath=uploadPath + File.separator+dto.getProfileImage();
				//File객체를 이용해서 삭제
				new File(deleteFilePath).delete();
			}
			//새롭게 업로드된 파일의 이름을 dto 에 담는다. 
			dto.setProfileImage(saveFileName);	
			//여기서는 프로필 이미지를 수정해야한다. 
			UserDao.getInstance().updateEmailProfile(dto); //이메일과 프로필 동시수정
		}else {//선택한 프로필 이미지가 없는 경우 
			//여기서는 프로필 이미지가 수정되면 안된다.
			UserDao.getInstance().updateEmail(dto); //이메일만수정 
		}
		
		String cPath=req.getContextPath();
		resp.sendRedirect(cPath+"/user/protected/info.jsp");
	}
}
