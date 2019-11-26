<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 페이지</title>
</head>
<body>
	<h3>웹 소켓 채팅</h3>
	보내는 메시지<input type="text" id="message" size="30" />
	<input type="button" id="send" value="전송" /><br/>
	받은 메시지<br/>
	<textarea id="disp" rows="30" cols="60"></textarea>	
</body>
<script>
	//웹 소켓 연결 
	var webSocket = new WebSocket("ws://localhost:8080/SemiProject/websocket")
	//웹 소켓이 연결되면 호출되는 콜백 함수 등록
	webSocket.addEventListener('open', function(e){
		document.getElementById('disp').value ="웹 소켓에 연결되었습니다."
	});
</script>
</html>