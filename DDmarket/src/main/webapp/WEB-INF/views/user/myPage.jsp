<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
        <main>
            <div class="div-logo">
                <a href="#"><img src="img/logo.jpg" alt=""></a>
            </div>
            <div class="head-user">
                <span class="head-user-txt">
                    <a href="/user/logout">로그아웃</a> ㅣ <a href="#">단디톡</a>
                </span>
            </div>
            <section id="pro_Img">
                <h2 class="title">프로필 사진 변경</h2>
                <span class="line"></span>
                
                
                
                <!-- 사진 -->
                <form action="">
                    <div class="pImg">
                        <img src="/res/img/lion.jpg" alt="">
                    </div>
                    <button type="button">사진변경</button>
                    <button type="button">사진삭제</button>
                </form>
                
                
                
            </section>
            <section id="UserChange">
                <h2 class="title">개인 정보 변경</h2>
                <span class="line"></span>
                    <div class="user-info">
                    
                    
                    	<!-- 비번 -->
                        <form action="">
                            <input type="text" name="" id="" placeholder="비밀번호">
                            <br>
                            <input type="text" name="" id="" placeholder="비밀번호 확인">
                            <br>
                            <button type="">변경하기</button>
                        </form>
                        
                        
                        
                        <hr>
                        <div class="user-nick">
                        
                        
                        
                        
                        	<!-- 닉네임 -->
                            <form action="">
                                <input type="text" name="" id="">
                                <button type="button">닉네임 중복체크</button>
                                <br>
                                <button type="">변경하기</button>
                            </form>
                            
                            
                        </div>
                        <hr>
                        
                        
                        <!-- 주소 -->
                        <form action="">
                            <input type="text" name="" id="" placeholder="우편번호">
                            <input type="text" name="" id="" placeholder="주소">
                            <br>
                            <button>변경하기</button>
                        </form>
                        
                        
                        
                        
                    </div>
            </section>
            <hr>
            
            
            
            <!-- 관심사 선택 -->
            <form action="">
                <section id="category">
                    <h2 class="title">관심 카테고리 선택</h2>
                    <button>저장하기</button>
                <span class="line"></span>
                    <input type="checkbox" name="" id="f-wear">
                    <label for="f-wear">여성의류</label>
                    <input type="checkbox" name="" id="m-wear">
                    <label for="m-wear">남성의류</label>
                    <input type="checkbox" name="" id="fashion">
                    <label for="fashion">패션잡화</label>
                    <input type="checkbox" name="" id="digital">
                    <label for="digital">디지털 | 가전</label>
                    <input type="checkbox" name="" id="buti">
                    <label for="buti">뷰티 | 미용</label>
                    <input type="checkbox" name="" id="life">
                    <label for="life">생활 | 가구</label>
                    <input type="checkbox" name="" id="book">
                    <label for="book">도서 | 티켓</label>
                    <input type="checkbox" name="" id="child">
                    <label for="child">유아용 | 출산</label>
                    <input type="checkbox" name="" id="no-change">
                    <label for="no-change">무료나눔</label>
                </section>
            </form>
        </main>
    </div>
</body>
</html>