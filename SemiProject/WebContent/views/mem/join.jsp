<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 반복문을 사용하기 위해서 JSTL 태그 라이브러리 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h3>회원가입</h3>
	<form method="post" enctype="multipart/form-data" id="joinform">
		<table align='center' width="50%" height="300" border="1" >
			<!-- image 선택 영역 -->
			<tr>
				<td rowspan="6" align="center">
					<br/>
					<img id="img" width="150" height="150"
					border="1" />
					<br/>
					<input type="file" id="image" name="image"
					accept="image/*" />
				</td>
			</tr>
			<tr>
				<td align='right'>이메일</td>
				<td><input type="email" name="email" id="email"
				required="required" size="30" maxlength="40" />
				<div id="emaildisp"></div>
				</td>
			</tr>
			<tr>
				<td align='right'>비밀번호</td>
				<td><input type="password" name="password" id="password"
				required="required" size="30" maxlength="40" />
				<div id="passworddisp"></div>
				</td>
			</tr>
			<tr>
				<td align='right'>이름</td>
				<td><input type="text" name="name" id="name"
				required="required" size="30" maxlength="40" />
				<div id="namedisp"></div>
				</td>
			</tr>
			<tr>
				<td align='right'>별명</td>
				<td><input type="text" name="nickname" id="nickname"
				required="required" size="30" maxlength="40"
				placeholder="별명은 필수 입력입니다." />
				<div id="nicknamedisp"></div>
				</td>
			</tr>
			<tr>
				<td align='right'>생년월일</td>
				<td>
					<select name="year">
						<c:forEach var="year" begin="1920" end="2019">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>년
					<select name="month">
						<c:forEach var="month" begin="1" end="12">
							<option value="${13-month}">${13-month}</option>
						</c:forEach>
					</select>월
					<select name="day">
						<c:forEach var="day" begin="1" end="31">
							<option value="${day}">${day}</option>
						</c:forEach>
					</select>일
					
				</td>
			</tr>
			
			<tr>
				<td colspan = "3" align="center">
					<input type="submit" value="회원가입" />
					&nbsp;&nbsp;
					<input type="button" value="메인으로" id="mainbtn"/>
				</td>
			</tr>			
		</table>
	</form>
</body>

<script>
	//메인으로 버튼을 눌렀을 때 메인 페이지로 이동하기 
	document.getElementById("mainbtn").addEventListener('click', function(e){
		location.href = "../";
	});

	//파일의 선택이 변경된 경우 선택한 파일을 img 태그에 출력하기 
	document.getElementById("image").addEventListener('change', function(e){
		//선택한 파일이 있는지 확인 
		if(this.files && this.files[0]){
			//선택한 파일 이름 가져오기
			filename = this.files[0].name;
			//파일 읽기 객체 생성 
			var reader = new FileReader();
			//파일 읽기
			reader.readAsDataURL(this.files[0]);
			//파일을 전부 읽으면 img 태그에 출력 
			reader.addEventListener('load', function(e){
				var event = e || window.event;
				document.getElementById('img').src = event.target.result;
			});
		};
	});
	
	var email = document.getElementById("email");
	email.addEventListener("focusout", function(e){
		//입력한 값 가져오기 
		var val = email.value;
		if(val.length > 1){
			alert(val)
		}
	});
</script>
</html>