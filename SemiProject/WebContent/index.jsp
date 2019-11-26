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
	
	<!-- 채팅 페이지로 이동 -->
	<a href="member/chat">채팅</a><br/>
	
	<!-- 웹 푸시 출력 영역 -->
	<h3>Web Push</h3>
	<div id="pushdisp"></div>
	
	<!-- 신문기사 출력 영역 -->
	<h3>한겨레 신문 실시간 기사</h3>
	<ul id="article"></ul>
	
	
</body>

<!-- jquery 사용을 위한 링크 설정 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	//5분마다 동작하는 타이머를 생성 
	setInterval(function(){
		$.ajax({
			url:'member/hani',
			data:{},
			datyType:'xml',
			success:function(data){
				//item 태그를 전부 찾아서 순서대로 item에 대입하고 
				//index에 번호를 대입
				output = "";
				//item 태그 안에서 title 태그의 내용을 가져와서 출력 
				$(data).find('item').each(function(index, item){
					output += '<li>' + $(this).find('title').text() + 
					'</li>';
				});
				$('#article').html(output);
			},
			error:function(req, err){
				alert('실패');
			}
		});
	}, 1000*5*60);
</script>

<script>
	
	//웹 푸시를 요청하는 코드 작성 
	var eventSource = new EventSource('member/push');
	eventSource.addEventListener('message', function(e){
		document.getElementById('pushdisp').innerHTML = e.data;
	});
</script>
</html>