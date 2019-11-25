package service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

public interface MemberService {
	//email 중복 검사를 위한 메소드
	public boolean emailCheck(HttpServletRequest request);
	
	//nickname 중복 검사를 위한 메소드
	public JSONObject nicknameCheck(HttpServletRequest request);
	
	//회원가입을 처리해주는 메소드 
	public boolean join(HttpServletRequest request);
	
	//로그인을 처리해주는 메소드 
	public boolean login(HttpServletRequest request);
}
