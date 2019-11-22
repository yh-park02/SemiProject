package service;

import javax.servlet.http.HttpServletRequest;

import repository.MemberDAO;

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
		boolean result = false;
		//파라미터 읽기 
		String email = request.getParameter("email");
		//email 중복 검사를 수행해주는 DAO 메소드를 호출 
		String r = memberDao.emailCheck(email);
		//데이터가 리턴되면 email이 있는 경우이고 null이 리턴되면 없는 email 
		if(r == null) {
			result = true;
		}
		
		return result;
	}
}
