package test;

import org.junit.Test;

import repository.MemberDAO;

public class TestClass {
	@Test
	public void testMethod() {
		MemberDAO dao = MemberDAO.sharedInstance();
		System.out.println(dao.emailCheck("yh_park02@naver.com"));
	}
}