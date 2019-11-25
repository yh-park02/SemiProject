package repository;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.Member;

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
		//System.out.println("email:" + email);

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
		//System.out.println(result);
		return result;
	}

	//nickname 중복검사를 위한 메소드
	public String nicknameCheck(String nickname) {
		String result = null;
		
		try {
			pstmt = con.prepareStatement(
				"select nickname from member where nickname = ?");
			pstmt.setString(1, nickname);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("nickname");
			}
			rs.close();
			pstmt.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	//회원가입을 처리해주는 메소드
	public int join(Member member) {
		//System.out.println("DAO:" + member);//
		int result = -1;
		try {
			pstmt = con.prepareStatement(
				"insert into member("
				+ "email, password, name, nickname, image, birthday)"
				+ "values(?,?,?,?,?,?)");
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getNickname());
			pstmt.setString(5, member.getImage());
			pstmt.setDate(6, member.getBirhday());
			
			result = pstmt.executeUpdate();
			//System.out.println("result:" + result);//
			
			pstmt.close();
			//Connection의 AutoCommit 속성을 false로 설정했으면 작업이 끝나고 
			//commit을 해주어야 한다. 
			//이 경우에는 예외가 발생하면 rollback을 해주어야 한다. 
			con.commit();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	//로그인 처리 메소드 
	//없는 이메일이면 null이 리턴되고 
	//존재하는 이메일이면 이메일에 해당하는 정보를 
	//Member 객체에 담아서 리턴한다. 
	public Member login(String email) {
		Member member = null;
		try {
			pstmt = con.prepareStatement(
					"select email, password, nickname, image "
					+ "from member where email =?");
		
			//?에 데이터 바인딩
			pstmt.setString(1, email);
			//SQL 실행 
			rs = pstmt.executeQuery();
			//데이터 읽기
			if(rs.next()) {
				member = new Member();
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setNickname(rs.getString("nickname"));
				member.setImage(rs.getString("image"));
			}
			rs.close();
			pstmt.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return member;
	}

}
