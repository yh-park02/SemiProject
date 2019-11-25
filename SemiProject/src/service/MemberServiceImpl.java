package service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import repository.MemberDAO;
import vo.Member;

public class MemberServiceImpl implements MemberService {
	//DAO 클래스의 참조형 변수 
	private MemberDAO memberDao;
	
	//싱글톤 만들기 위한 코드 
	private MemberServiceImpl() {
		memberDao = MemberDAO.sharedInstance();
	};
	
	private static MemberService memberService;
	
	public static MemberService sharedInstance() {
		if(memberService == null) {
			memberService = new MemberServiceImpl();
		}
		return memberService;
	}

	@Override
	public boolean emailCheck(HttpServletRequest request) {
		//System.out.println("이메일 중복 체크 서비스");
		boolean result = false;
		//파라미터 읽기 
		String email = request.getParameter("email");
		//System.out.println("email:" + email);
		
		//email 중복 검사를 수행해주는 DAO 메소드를 호출 
		String r = memberDao.emailCheck(email);
		//데이터가 리턴되면 email이 있는 경우이고 null이 리턴되면 없는 email 
		if(r == null) {
			result = true;
		}
		
		return result;
	}

	@Override
	public JSONObject nicknameCheck(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		
		//파리미터 읽기
		String nickname = request.getParameter("nickname");
		
		//DAO 메소드 호출
		String result = memberDao.nicknameCheck(nickname);
		
		if(result == null) {
			obj.put("result", "true");
		}else {
			obj.put("result", "false");
		}
		
		return obj;
	}

	@Override
	public boolean join(HttpServletRequest request) {
		//System.out.println("회원가입 요청처리");//
		boolean result = false;
		try {
			//파일이 업로드 될 디렉토리의 절대 경로 만들기 
			String uploadPath = 
					request.getServletContext().getRealPath("/images");
			//파일 업로드 
			MultipartRequest mRequest = 
				new MultipartRequest(request, uploadPath, 10*1024*1024
						,"utf-8", new DefaultFileRenamePolicy());
			//데이터베이스에 저장하기 위해서 파라미터 읽어오기 -  MultipartRequest로 읽기
			String email = mRequest.getParameter("email");
			String password = mRequest.getParameter("password");
			String name = mRequest.getParameter("name");
			String nickname = mRequest.getParameter("nickname");
			
			//year, month, day 값을 가지고 java.sql.Date 만들기 
			String year = mRequest.getParameter("year");
			String month = mRequest.getParameter("month");
			String day = mRequest.getParameter("day");
			
			Calendar cal = new GregorianCalendar(
					Integer.parseInt(year),
					Integer.parseInt(month)-1,
					Integer.parseInt(day));
			Date birthday = new Date(cal.getTimeInMillis());
			
			//파일 경로 만들기 
			//업로드 되기 위해서 변경된 파일이름 가져오기 
			String image = "default.png";
			//업로드된 파일이 있다면 
			//전체 파일이름을 가져온 후 첫번째 데이터를 가져온다. 
			Enumeration <String> files = mRequest.getFileNames();
			String imsi = files.nextElement();
			//System.out.println("imsi:" + imsi);//
			//첫번째 데이터가 있다면 그 이름으로 변경된 이름을 찾아온다. 
			if(imsi != null && imsi.length() > 0) {
				image = mRequest.getFilesystemName(imsi);
				//선택한 이미지가 없다면 default.png를 대입 
				if(image == null) {
					image = "default.png";
				}
			}
			
			//DAO의 매개변수 만들기 
			Member member = new Member();
			member.setEmail(email);
			//비밀번호는 암호화해서 설정 
			member.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			member.setName(name);
			member.setNickname(nickname);
			member.setImage(image);
			member.setBirhday(birthday);
			//System.out.println("DAO 파라미터:" +member);//
			
			//DAO 메소드 호출 
			int r = memberDao.join(member);
			
			//성공한 경우 result에 true 대입 
			if(r > 0) {
				result = true;
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}
}
