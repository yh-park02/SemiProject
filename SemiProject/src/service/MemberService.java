package service;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
	//email 중복 검사를 위한 메소드
	public boolean emailCheck(HttpServletRequest request);
}
