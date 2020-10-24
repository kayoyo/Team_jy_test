<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/res/css/saleReg.css">
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
	<div id="container">
        <main>
            <form id="frm" action="/board/saleReg" enctype="multipart/form-data" method="post"  onsubmit="return chk()">
	            <h2 class="title">상품 사진</h2>
	            <span class="line"></span>
	            <section class="sale-pic">
                    <div class="pics">
                    	<c:forEach var="i" begin="0" end="4">
	                    	<label for="mfile">
	                            <img src="/res/img/등록.jpg" alt="">
	                        </label>
	                        <%-- 사진은  images 라는 이름으로 컨트롤러에서 카테고리처럼 배열로 getParameterValues로 받아주기 --%>
	                        <input type="file" name="images" id="mfile" onchange="setPics(event)" style="display: none;" multiple accept="image/png, image/jpeg, image/jpg">	
                    	</c:forEach>
                    </div>
	            </section>
	            <h2 class="title">제목</h2>
	            <span class="line"></span>
	            <section class="goods-title">
                    <div class="div-title">
                        <input type="text" name="title" placeholder="상품 제목을 입력해 주세요">
                    </div>
	            </section>
	            <h2 class="title">카테고리</h2>
	            <span class="line"></span>
	            <section class="category">
	            	<div class="category-box">
	            		<%-- 카테고리 받을 sql 컬럼   t_board에 만들던가 t_category 에 join으로 하기 --%>
		            	<c:forEach items="${categoryList}" var="item">
		            		<input type="checkbox" name="categoryLike" id="f-wear" value="${item.i_cg }" onclick="count_ck(this);">
				            <label for="f-wear">${item.cg_nm }</label>
		            	</c:forEach>    
	                </div>
	            </section>
	            
	            
	            
	            <h2 class="title">거래지역</h2>
	            <span class="line"></span>
	            <section class="deal-area">
                    <div class="div-area">
                        <input type="text" id="sample4_postcode" name="post" class="addr_input" placeholder="클릭할시 주소검색창이 나타납니다" onclick="sample4_execDaumPostcode()"><br>
                    	<input id="addrUnChk" name="addrUnChk" type="hidden" value="unChk">
						<input type="text" id="sample4_jibunAddress" name="addr" class="addr_input" placeholder="지번주소" onclick="sample4_execDaumPostcode()"><br>
						<input type="text" id="sample4_roadAddress" name="road" class="addr_input" placeholder="도로명주소" onclick="sample4_execDaumPostcode()">
						<input id="postChk" name="postChk" type="hidden" value="unChk">
						<span id="guide" style="color:#999;display:none"></span>
						<input type="hidden" id="sample4_detailAddress" placeholder="상세주소">
						<input type="hidden" id="sample4_extraAddress" placeholder="참고항목">
						<input type="hidden" name="result" value="5">
                    </div>
	            </section>
	            
	            
	            
	            
	            <h2 class="title">가격</h2>
	            <span class="line"></span>
	            <section class="goods-price">
                    <div class="div-price">
                    	<input type="number" name="price" class="" placeholder="가격을 입력 해 주세요">
                    </div>
	            </section>
	            <h2 class="title">상품 설명</h2>
	            <span class="line"></span>
	            <section class="goods-info">
                    <div class="info-txt">
                        <textarea name="ctnt" rows="6" id="" placeholder="상품을 설명 해 주세요"></textarea>
                    </div>
	            </section>
	            <div class="div-btn">
	                <button class="goods-btn" type="submit">상품등록</button>
	            </div>
            </form>
        </main>
    </div>
</body>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$('#sample4_jibunAddress').hide();
$('#sample4_roadAddress').hide();

$('#sample4_postcode').click(function() {
	frm.addrUnChk.value = 'chk'
})
	
// 주소api
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
        	if(frm.addrUnChk.value == 'chk') {
        		$('#sample4_jibunAddress').show();
        		$('#sample4_roadAddress').show();
        	}
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample4_postcode').value = data.zonecode;
            document.getElementById("sample4_roadAddress").value = roadAddr;
            document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
            
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if(roadAddr !== ''){
                document.getElementById("sample4_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
        }
    }).open();
}	


</script>
</html>