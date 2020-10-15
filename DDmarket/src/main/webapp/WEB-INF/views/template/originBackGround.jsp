<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<!-- 비밀번호 찾기 인증코드 입력할때 백그라운드 -->
<style>		
	img {
		background-color: #6e6868;
		margin-left: 692px;
		margin-top: 75px;
		width: 480px;
		height: 190px;
	}
</style>
<body>
	<div class="backContainer">
		<img class="logo" src="/res/img/logo.jpg">
		<main>
			<c:if test="${view != null }">
		    	<section>
		    		<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
		    	</section>			      
	    	</c:if>
	    </main>
	</div>
</body>
<script>

if(${logoutMsg != null}) {
	alert('${logoutMsg}');
	location.href="/index/main";
}

</script>
</html>