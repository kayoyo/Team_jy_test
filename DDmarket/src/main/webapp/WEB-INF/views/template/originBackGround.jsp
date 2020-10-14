<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
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
	    	<section>
	    		<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
	    	</section>			      
	    </main>
	</div>
</body>
</html>