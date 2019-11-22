package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	//데이터베이스 연동에 필요한 변수 
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	//싱글톤 만들기 위한 코드 
	private MemberDAO() {
		try {
			//데이터베이스 연결 
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/DBConn");
			con = ds.getConnection();	
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
		}
	}
	private static MemberDAO memberDao;
	
	public static MemberDAO sharedInstance() {
		if(memberDao == null) {
			memberDao = new MemberDAO();
		}
		return memberDao;
	}
	
	//email 중복검사를 위한 메소드
	public String emailCheck(String email) {
		String result = null;
		try {
			//생성할 SQL 생성
			pstmt = con.prepareStatement(
					"select email from member where email=?");
			//필요한 매개변수를 바인딩
			pstmt.setString(1, email);
			//SQL 실행 - select
			rs = pstmt.executeQuery();
			//email은 중복되지 않기 때문에 데이터가 2개 이상 리턴될 수 없다. 
			if(rs.next()) {
				result = rs.getString("email");
			}
			//사용한 자원 정리 
			rs.close();
			pstmt.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
		return result;
	}
}
