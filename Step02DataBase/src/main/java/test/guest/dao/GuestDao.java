package test.guest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.guest.dto.GuestDto;
import test.util.DbcpBean;
/*
 * application 전역에서 GuestDao 객체는 오직한개만생성되어서 사용되도록 클래스를 설계한다.
 * 한정된 자원인 Connection 객체를 좀 더 효율적으로 사용하기 위해서
 */
public class GuestDao {
	//스태틱필드는 클래스당 하나씩//클래스랑같이 스태틱영역 한번만 올라간다는것!
	//자신의 참조값을 저장할 static 필드
	private static GuestDao dao;
	//static 초기화 블럭 (이 클래스가 최초로 사용될때 오직 한번만 수행된다.)
	static {
		//객체를 생성해서 static필드에 담는다.
		dao = new GuestDao();
	}
	//외부에서 객체생성하지 못하도록 생성자의 접근 지정자를 private로 설정
	private GuestDao() {}
	
	//Dao생성하는법 : GuestDao dao = GuestDao.getInstance();
	//static필드에 저장된 GuestDao의 참조값을 리턴해주는 static메소드
	public static GuestDao getInstance() {
		return dao;
	}
	
	//메소드기능들추가
	// insert
		public boolean insert(GuestDto dto) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int rowCount = 0;
			try {
				conn = new DbcpBean().getConn();
				//실행할 미완성의 sql 문
				String sql = """
						INSERT INTO board_guest
						(num, writer, content, pwd, regdate)
						VALUES(board_guest_seq.NEXTVAL, ?, ?, ?, SYSDATE)
						""";
				pstmt = conn.prepareStatement(sql);
				// ? 에 값을 여기서 바인딩한다.
				pstmt.setString(1, dto.getWriter());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getPwd());
				// sql 문 실행하고 변화된 row 의 갯수 리턴받기
				rowCount = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.close();
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
				}
			}
			if (rowCount > 0) {
				return true;
			} else {
				return false;
			}
		}//insert
	
	
	//update 번호를이용해 작성자와 글 내용수정 
	public boolean update(GuestDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			//실행할 미완성의 sql 문
			String sql = """
					UPDATE board_guest
					SET writer=?, content=?
					WHERE num=?
					""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 값을 여기서 바인딩한다.
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			// sql 문 실행하고 변화된 row 의 갯수 리턴받기
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
		}
		if (rowCount > 0) {
			return true;
		} else {
			return false;
		}
	}//update
	
	
	//delete
		public boolean delete(int num) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int rowCount = 0;
			try {
				conn = new DbcpBean().getConn();
				//실행할 미완성의 sql 문
				String sql = """
						DELETE FROM board_guest
						WHERE num=?
						""";
				pstmt = conn.prepareStatement(sql);
				// ? 에 값을 여기서 바인딩한다.
				pstmt.setInt(1, num);
				// sql 문 실행하고 변화된 row 의 갯수 리턴받기
				rowCount = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.close();
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
				}
			}
			if (rowCount > 0) {
				return true;
			} else {
				return false;
			}
		}//delete
	
	//매개변수로 전달되는 번호에 해당하는 글 목록 리턴하는메소드
	public GuestDto getData(int num) {
		GuestDto dto =null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//Connection Pool 로 부터 Connection 객체 하나 가져오기 
			conn = new DbcpBean().getConn();
			//실행할 sql 문 작성
			String sql = """
					SELECT writer, content, pwd, regdate
					FROM board_guest
					WHERE num=?
					""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 값 바인딩할게 있으면 여기서 하기
			pstmt.setInt(1, num);
			//sql 문 실행하고 결과를 ResultSet 객체로 리턴받기
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto=new GuestDto();
				dto.setNum(num);
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setPwd(rs.getString("pwd"));
				dto.setRegdate(rs.getString("regate"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return dto;
	}//getData
	
	
	
	//글 목록 리턴하는메소드
	public List<GuestDto> getList(){
		List<GuestDto> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//Connection Pool 로 부터 Connection 객체 하나 가져오기 
			conn = new DbcpBean().getConn();
			//실행할 sql 문 작성
			String sql = """
					SELECT num, writer, content, regdate
					FROM board_guest
					ORDER BY num DESC
					""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 값 바인딩할게 있으면 여기서 하기

			//sql 문 실행하고 결과를 ResultSet 객체로 리턴받기
			rs = pstmt.executeQuery();
			while (rs.next()) {
				//select된 row하나의 정보를 GuestDto 객체에 담고
				GuestDto dto = new GuestDto();
				dto.setNum(rs.getInt("num"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				//GuestDto객체를 List객체에 누적시킨다.
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		//list객체리턴
		return list;
	}//get list()
	
}//class
