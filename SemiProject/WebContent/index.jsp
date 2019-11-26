<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<!-- 조건문을 작성하기 위한 JSTL Core 라이브러리를 import -->
	<%@ taglib prefix = "c"
	uri = "http://java.sun.com/jsp/jstl/core" %>
	
	<a href="member/join">회원가입</a><br/>	
	<c:if test="${member == null}">
		<a href="member/login">로그인</a><br/>
	</c:if>
	<c:if test="${member != null}">
		<img src="images/${member.image}" width="50" height="50" />
		${member.nickname}님 <a href="member/logout">로그아웃</a><br/>
	</c:if>
	
</body>
</html>