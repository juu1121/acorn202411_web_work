package test.post.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.post.dto.PostDto;
import test.util.DbcpBean;

public class PostDao {
    private static PostDao dao;

    static {
        dao = new PostDao();
    }

    private PostDao() {}

    public static PostDao getInstance() {
        return dao;
    }
	//글의 갯수를 리턴하는 메소드
    public int getCount(PostDto dto) {
        int count = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new DbcpBean().getConn();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) AS count FROM posts ");

            // 검색 조건 추가
            if (dto.getCondition() != null && dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
                if (dto.getCondition().equals("title_content")) {
                    sql.append("WHERE title LIKE ? OR content LIKE ? ");
                } else {
                    sql.append("WHERE ").append(dto.getCondition()).append(" LIKE ? ");
                }
            }

            pstmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (dto.getCondition() != null && dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
                if (dto.getCondition().equals("title_content")) {
                    pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                    pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                } else {
                    pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                }
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    
	//삭제한 글에 대한 참조를 삭제하는 메소드
	public boolean deleteRef(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			//실행할 sql 문
			String sql = """
				DELETE FROM readed_data
				WHERE num=?
			""";
			pstmt = conn.prepareStatement(sql);
			//? 에 바인딩 할 내용이 있으면 바인딩
			pstmt.setInt(1, num);
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return rowCount>0;
	}
	
	//글을 읽었다고 표시해주는 메소드 
	public boolean insertReaded(int num, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			//실행할 sql 문
			String sql = """
				INSERT INTO readed_data
				(num, session_id)
				VALUES(?, ?)
			""";
			pstmt = conn.prepareStatement(sql);
			//? 에 바인딩 할 내용이 있으면 바인딩
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return rowCount>0;
	}
	//이미 글을 읽었는지 여부를 리턴해주는 메소드
	public boolean isReaded(int num, String id) {
		boolean isReaded=false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			//실행할 sql 문
			String sql = """
				SELECT *
				FROM readed_data
				WHERE num=? AND session_id=?
			""";
			pstmt = conn.prepareStatement(sql);
			//? 에 바인딩할 내용이 있으면 여기서 한다.
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			//query 문 수행하고 결과(ResultSet) 얻어내기
			rs = pstmt.executeQuery();
			//만일 select 된 row 가 존재 한다면 이미 읽은것이다 
			if (rs.next()) {
				isReaded=true;
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
					conn.close(); //Connection 객체의 close() 메소드를 호출하면 Pool 에 반납된다.
			} catch (Exception e) {
			}
		}
		return isReaded;
	}
	
	//글 조회수를 증가 시키는 메소드
	public boolean addViewCount(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			//실행할 sql 문
			String sql = """
				UPDATE posts
				SET viewCount=viewCount+1
				WHERE num=?
			""";
			pstmt = conn.prepareStatement(sql);
			//? 에 바인딩 할 내용이 있으면 바인딩
			pstmt.setInt(1, num);
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return rowCount>0;
	}
    
	public PostDto getData(PostDto dto) {
	    PostDto data = null;

	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = new DbcpBean().getConn();

	        StringBuilder sql = new StringBuilder();
	        sql.append("SELECT * FROM ");
	        sql.append("(SELECT num, writer, title, content, viewCount, ");
	        sql.append("TO_CHAR(createdAt, 'YYYY.MM.DD HH24:MI') AS createdAt, ");
	        sql.append("LAG(num, 1, 0) OVER (ORDER BY num DESC) AS prevNum, ");
	        sql.append("LEAD(num, 1, 0) OVER (ORDER BY num DESC) AS nextNum ");
	        sql.append("FROM posts ");

	        // 검색 조건 및 키워드 추가
	        if (dto.getCondition() != null && dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
                if (dto.getCondition().equals("title_content")) {
                    sql.append("WHERE title LIKE ? OR content LIKE ? ");
                } else {
                    sql.append("WHERE ").append(dto.getCondition()).append(" LIKE ? ");
                }
	        }

	        sql.append(") WHERE num = ?");

	        pstmt = conn.prepareStatement(sql.toString());

	        int paramIndex = 1;
	        if (dto.getCondition() != null && dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
	        	if (dto.getCondition().equals("title_content")) {
                    pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                    pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                } else {
                    pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                }
	        }
	        pstmt.setLong(paramIndex, dto.getNum());

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            data = new PostDto();
	            data.setNum(rs.getInt("num"));
	            data.setWriter(rs.getString("writer"));
	            data.setTitle(rs.getString("title"));
	            data.setContent(rs.getString("content"));
	            data.setViewCount(rs.getInt("viewCount"));
	            data.setCreatedAt(rs.getString("createdAt"));
	            data.setPrevNum(rs.getInt("prevNum"));
	            data.setNextNum(rs.getInt("nextNum"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return data;
	}

	

    public PostDto getData(long num) {
        PostDto dto = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new DbcpBean().getConn();
            String sql = """
                SELECT writer, title, content, viewCount, createdAt, updatedAt
                FROM posts
                WHERE num=?
            """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, num);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new PostDto();
                dto.setNum(num);
                dto.setWriter(rs.getString("writer"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dto;
    }

    public boolean insert(PostDto dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowCount = 0;
        try {
            conn = new DbcpBean().getConn();
            String sql = """
                INSERT INTO posts (num, writer, title, content)
                VALUES (posts_seq.NEXTVAL, ?, ?, ?)
            """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getWriter());
            pstmt.setString(2, dto.getTitle());
            pstmt.setString(3, dto.getContent());
            rowCount = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowCount > 0;
    }

    public boolean update(PostDto dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowCount = 0;
        try {
            conn = new DbcpBean().getConn();
            String sql = """
                UPDATE posts
                SET title=?, content=?, updatedAt=SYSDATE
                WHERE num=?
            """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setLong(3, dto.getNum());
            rowCount = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowCount > 0;
    }

    public boolean delete(long num) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowCount = 0;
        try {
            conn = new DbcpBean().getConn();
            String sql = """
                DELETE FROM posts
                WHERE num=?
            """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, num);
            rowCount = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowCount > 0;
    }
    
    public List<PostDto> getList(PostDto dto) {
        List<PostDto> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new DbcpBean().getConn();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append("(SELECT result1.*, ROWNUM AS rnum FROM ");
            sql.append("(SELECT num, writer, title, viewCount, TO_CHAR(createdAt, 'YYYY.MM.DD HH24:MI') AS createdAt FROM posts ");
            //만일 검색 조건이 있다면 (컨디션, 키워드도 null이 아니어야하고 /빈문자열도 아니어야한다)
            if (dto.getCondition() != null && dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            	//조건에 맞는 where절을 구성한다. (title_content일경우, title이나 content일경우 )
                if (dto.getCondition().equals("title_content")) {
                    sql.append("WHERE title LIKE ? OR content LIKE ? ");
                } else {
                    sql.append("WHERE ").append(dto.getCondition()).append(" LIKE ? ");
                }
            }

            sql.append("ORDER BY num DESC) result1) WHERE rnum BETWEEN ? AND ?");
            //StringBuilder에 누적된 내용을 String type으로 얻어내서 sql문을 실행한다.
          //StringBuilder객체, 위에서부터 append한 문자열을 toString로 문자열로 한번에 얻어낸다
            pstmt = conn.prepareStatement(sql.toString()); 
            
            // 검색 키워드가 있다면 검색 키워드에 관련된 값을 바인딩한다.
            int paramIndex = 1;
            if (dto.getCondition() != null && dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            	 if (dto.getCondition().equals("title_content")) {
                     pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                     pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                 } else {
                     pstmt.setString(paramIndex++, "%" + dto.getKeyword() + "%");
                     // %는와일드카드 ex) %stone% => 앞이나 뒤에 몇글자는 상관없고 stone가 들어가기만 하면 된다 //이렇게 구성하기위한 코드임
                     //증감연산자 뒤에 ++면 사용하고나서 증가, 앞에++면 증가하고 사용
                 }
            }
            //페이징 처리에 관련된 값도 바인딩해서 
            pstmt.setInt(paramIndex++, dto.getStartRowNum());
            pstmt.setInt(paramIndex, dto.getEndRowNum());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                PostDto tmp = new PostDto();
                tmp.setNum(rs.getInt("num"));
                tmp.setWriter(rs.getString("writer"));
                tmp.setTitle(rs.getString("title"));
                tmp.setViewCount(rs.getInt("viewCount"));
                tmp.setCreatedAt(rs.getString("createdAt"));
                list.add(tmp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<PostDto> selectAll() {
        List<PostDto> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = new DbcpBean().getConn();
            String sql = """
                SELECT num, writer, title, content, viewCount, createdAt, updatedAt
                FROM posts
                ORDER BY num DESC
            """;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PostDto dto = new PostDto();
                dto.setNum(rs.getLong("num"));
                dto.setWriter(rs.getString("writer"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setCreatedAt(rs.getString("createdAt"));
                dto.setUpdatedAt(rs.getString("updatedAt"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}