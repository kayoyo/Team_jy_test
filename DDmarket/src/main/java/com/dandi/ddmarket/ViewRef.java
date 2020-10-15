package com.dandi.ddmarket;

// 주소관련
public class ViewRef {	
	//  /user = header, footer
	public static final String DEFAULT_TEMP = "template/defaultTemp";	// index 화면 템플릿
	public static final String MENU_TEMP = "template/menuTemp";  // 로그인박스, 회원가입, 아이디찾기 등 템플릿
	public static final String ORIGIN_TEMP = "template/originBackGround"; // 기타 alert창띄우는용도 기본 뒷배경 템플릿
	
	
	// /user
	public static final String USER_LOGIN = "user/login";	    // 로그인
	public static final String USER_JOIN = "user/join";  	    // 회원가입 
	public static final String USER_FINDPW = "user/findPw";     // 비밀번호 찾기	
	public static final String USER_CERCODE = "user/cerCode";   //인증번호
	public static final String USER_CHANGEPW = "user/changePw";	// 비밀번호 변경
	public static final String USER_FINDID = "user/findId";

	
	// /index 
	public static final String INDEX_MAIN = "index/main"; // 단디마켓 메인화면
}
