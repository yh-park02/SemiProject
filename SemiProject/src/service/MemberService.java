package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	//한겨레 기사 rss를 request 객체에 저장하는 메소드 
	public boolean getHani(HttpServletRequest request);
	
	//0-9까지의 랜덤한 숫자를 클라이언트에게 전송하는 메소드 
	public void push(HttpServletRequest request, HttpServletResponse response);
}
