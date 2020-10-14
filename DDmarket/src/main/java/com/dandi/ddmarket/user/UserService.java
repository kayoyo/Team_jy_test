package com.dandi.ddmarket.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dandi.ddmarket.Const;
import com.dandi.ddmarket.SecurityUtils;
import com.dandi.ddmarket.user.model.UserDMI;
import com.dandi.ddmarket.user.model.UserPARAM;
import com.dandi.ddmarket.user.model.UserVO;

@Service
public class UserService {

	@Autowired
	private UserMapper mapper;

	// 이메일 체크
	// 코드 좀 거지같음 수정하기
	public int emailChk(UserPARAM param, HttpSession hs) {
		
		if(param.getEmail().equals("")) {
			return 3;
		}		
		UserDMI dbUser = mapper.emailChk(param);
		
		if(dbUser == null) {
			return 1; 
		}		
		if(dbUser.getEmail().equals(param.getEmail())) {
			return 2;
		}		
		return 4;
	}
		
		
	// SUCCESS 1:로그인 성공,  NO_ID 2:아이디 없음,  NO_PW 3:비번 틀림
	public int login(UserPARAM param) {
		if(param.getUser_id().equals("")) {
			return Const.BLANK_ID; 
		}
		UserDMI dbUser = mapper.selUser(param);
		
		if(dbUser == null) {
			return Const.NO_ID; 
		} 
						
		String cryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		if(!cryptPw.equals(dbUser.getUser_pw())) {return Const.NO_PW;} // 3
		
		param.setI_user(dbUser.getI_user());
		param.setUser_pw(null);
		param.setNm(dbUser.getNm());
		param.setNick(dbUser.getNick());
		param.setEmail(dbUser.getEmail());
		param.setProfile_img(dbUser.getProfile_img());		
		param.setAddr(dbUser.getAddr());
		param.setPost(dbUser.getPost());
		param.setRoad(dbUser.getRoad());
		param.setJoinPass(dbUser.getJoinPass());
		param.setR_dt(dbUser.getR_dt());
		param.setM_dt(dbUser.getM_dt());
		
		return Const.SUCCESS;
	}

	
	// 회원가입
	public int joinUser(UserVO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.joinUser(param);
	}
	
	
	// 비밀번호 찾기  1. 아디,이멜 검사
	public int findPw (UserPARAM param, HttpSession hs) {
		UserDMI dbUser = mapper.findPwChk(param);
		
		if(dbUser == null) { return Const.FAIL; }
		
		if(dbUser.getUser_id().equals(param.getUser_id())
				&& dbUser.getEmail().equals(param.getEmail())) {
			
			hs.setAttribute("i_user", dbUser.getI_user());
			return Const.SUCCESS;
		} else {
			return Const.FAIL;
		}
	}
		
	
	// 비밀번호 변경 
	public int changePw(UserPARAM param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		int result = mapper.changePw(param);
		return result; 
	}
	
	
	// 아이디 찾기
	public int findId(UserPARAM param, HttpSession hs) {
		UserDMI dbUser = new UserDMI();
		dbUser = mapper.findId(param);
		
		if(dbUser == null) {
			return Const.FAIL;
		} else {
			hs.setAttribute("user_id", dbUser.getUser_id());
			return Const.SUCCESS;
		}		 
	}
}