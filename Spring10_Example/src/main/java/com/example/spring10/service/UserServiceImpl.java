package com.example.spring10.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring10.dto.UserDto;
import com.example.spring10.exception.PasswordException;
import com.example.spring10.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	//SecurityConfig클래스에 @Bean 설정으로 bean이 된 PasswordEncoder 객체 주입 받기
	@Autowired
	private PasswordEncoder encoder;
	
	//업로드 된 이미지를 저장할 위치 걷어내기
	@Value("${file.location}") private String fileLocation;
	
	@Override
	public UserDto getByNum(long num) {
		return dao.getData(num);
	}

	@Override
	public UserDto getByUserName(String userName) {
		
		return dao.getData(userName);
	}

	@Override
	public void createUser(UserDto dto) {
		//저장할때 비밀번호를 암호화 한 후에 저장되도록 한다. //1. 전달받은 dto에는 내가 입력한 비번이있음
		String encodedPwd = encoder.encode(dto.getPassword()); //2. 그 비번을 암호화하고
		//인코딩 된 비밀번호를 다시 dto객체에 넣어주고 //3. 입력해서 암호화 된 비밀번호를 dto객체에 다시 넣어줌
		dto.setPassword(encodedPwd);
		//DB에 저장한다. //4. so, 입력한 dto가 db에 저장되므로, 내가 입력한 비번이 저장되는것!
		int rowCount = dao.insert(dto);
		if(rowCount == 0) {
			throw new RuntimeException("회워정보 저장 실패!");
		}
		
	}

	@Override
	public void updateUserInfo(UserDto dto) {
		//MultipartFile 객체
		MultipartFile image = dto.getProfileFile();
		//만일 파일이 업로드됐다면! //07예제와 다르게업로드됐다면으로 변경함!
		if(!image.isEmpty()) {
			//원본파일명
			String orgFileName=image.getOriginalFilename();
			System.out.println("orgFileName"+orgFileName); 
			
			//저장할 파일의 이름을 Universal Unique 한 문자열로 얻어내기
			//이미지 확장자를 위해 뒤에 원본파일명도 추가한다.
			String saveFileName=UUID.randomUUID().toString()+orgFileName;
			System.out.println("saveFileName"+saveFileName); 
			
			//저장할 파일의 전체경로 구성하기
			String filePath=fileLocation+File.separator+saveFileName;
			System.out.println("filePath"+filePath);
			
			try {
				//업로드 된 파일을 저장할 파일 객체 생성
				File saveFile=new File(filePath);
				image.transferTo(saveFile);
			}catch(Exception e) {
				e.printStackTrace();
			}			
			//UserDto에 저장된 이미지의 이름을 넣어준다.
			dto.setProfileImage(saveFileName);
		}
		//로그인된 userName도 dto에 담아준다
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		dto.setUserName(userName);
		
		//dao를 이용해서 수정반영한다.
		dao.update(dto);

	}
		
	

	@Override
	public void changePassword(UserDto dto) {
		//1. 로그인 된 userName을 얻어낸다.
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		//2. 기본의 비밀번호를 DB에서 읽어와서 (암호화된 비밀번호)
		String encodedPwd = dao.getData(userName).getPassword();
		//3. 입력한(암호화되지않은 구 비밀번호)와 일치하는지 비교해서
		// .checkpw(암호화되지않은 비밀번호, 암호화된 비밀번호)
		boolean isValid = BCrypt.checkpw(dto.getPassword(), encodedPwd);
		//4. 만일 일치하지않으면 Exception을 발생시킨다
		if(!isValid) {
			throw new PasswordException("기존 비밀번호가 일치하지 않습니다.");
		}
		
		//5. 일치하면 새 비밀번호를 암호화해서 dto에 담은 다음
		dto.setNewPassword(encoder.encode(dto.getNewPassword()));
		//6. userName도 dto에 담고
		dto.setUserName(userName);
		//7. DB에 비밀번호 수정반영을 한다.
		dao.updatePassword(dto);
	}

}
