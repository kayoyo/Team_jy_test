package com.dandi.ddmarket.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dandi.ddmarket.Const;
import com.dandi.ddmarket.SecurityUtils;
import com.dandi.ddmarket.ViewRef;
import com.dandi.ddmarket.mail.MailSendService;
import com.dandi.ddmarket.mail.model.EmailVO;
import com.dandi.ddmarket.user.model.UserDMI;
import com.dandi.ddmarket.user.model.UserPARAM;
import com.dandi.ddmarket.user.model.UserVO;


@Controller
@RequestMapping("/user")
public class UserController {
	static int cerCodeCount = 0; // 인증코드 틀렸을시 카운트 
	
	@Autowired
	private UserService service;	
	
	@Autowired
	private MailSendService mss;  
		
	/*
	 *  	아작스에 hs 세션 값 확인해보고 불필요하면 지우기
	 */
	
	// 아이디 중복체크 (aJax) 
	@RequestMapping(value="/ajaxIdChk", method=RequestMethod.POST)
	@ResponseBody	
	public String ajaxIdChk(@RequestBody UserPARAM param, HttpSession hs) {
		
		int result = service.login(param);
		return String.valueOf(result);
	}
	
	// 이메일 중복체크(aJax)
	@RequestMapping(value="/ajaxEmailChk", method=RequestMethod.POST)
	@ResponseBody	
	public String ajaxEmailChk(@RequestBody UserPARAM param, HttpSession hs) {
		
		int result = service.emailChk(param, hs);	
		return String.valueOf(result);
	}
	
	// 닉네임 중복체크(aJax)
	@RequestMapping(value="/ajaxNickChk", method=RequestMethod.POST)
	@ResponseBody	
	public String ajaxNickChk(@RequestBody UserPARAM param, HttpSession hs) {
		
		int result = service.nickChk(param);
		
		if(result == 0) {
			System.out.println("result : " + result);
		}
		return String.valueOf(result);
	}
	
	// 로그아웃 (logout)
	@RequestMapping("/logout")
	public String logout(Model model, HttpSession hs, RedirectAttributes ra, HttpServletRequest request) {
		
		hs.invalidate();
		model.addAttribute("logoutMsg", "로그아웃 되었습니다");		
		return ViewRef.ORIGIN_TEMP;
	}
	
	
	//	로그인 (login)
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		// 로그인이 되어있다면 로그인페이지로 갈수없게 막아놓음 
		// 메소드 다시 만들기  // 또는 인터셉터에서 추후에 걸러주기
		UserVO param = SecurityUtils.getLoginUser(request);
				
		if(param != null) {	
			model.addAttribute("isLogin","로그인이 되어있는상태에서는 로그인페이지로 갈수없습니다");
			return ViewRef.ORIGIN_TEMP;
		}
		
		model.addAttribute("view",ViewRef.USER_LOGIN);
		return ViewRef.MENU_TEMP;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(Model model, UserPARAM param, HttpSession hs, RedirectAttributes ra) {
		
		int result = service.login(param);
		
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/" + ViewRef.INDEX_MAIN;
		}
		
		String msg = null;
		if (result == Const.NO_ID) {
			msg = "아이디를 확인해 주세요";
		} else if (result == Const.NO_PW) {
			msg = "비밀번호를 확인해 주세요";
		}
		
		ra.addFlashAttribute("data", msg);
		ra.addFlashAttribute("user_id", param.getUser_id());
		return "redirect:/" + ViewRef.USER_LOGIN;
	}
	
		
	//	회원가입 (join)
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(Model model, RedirectAttributes ra) {
		int uNumCode = (int)(Math.random() * 88888888 + 10000000); // 고유번호 8자리 랜덤으로 지정
		model.addAttribute("uNumCode",uNumCode);
		model.addAttribute("joinErrMsg"); // 서버에러시 띄우는 alert창
		model.addAttribute("view",ViewRef.USER_JOIN);
		
		return ViewRef.MENU_TEMP;
	}	

	@RequestMapping(value="/join", method = RequestMethod.POST) 
	public String join(Model model, UserVO param, HttpSession hs, RedirectAttributes ra) {
		int result = service.joinUser(param);
		
		if(result == Const.SUCCESS) {
			return "redirect:/" + ViewRef.USER_LOGIN;

		} else { 
			ra.addFlashAttribute("joinErrMsg","서버에러! 다시 회원가입을 시도해 주세요");
			return "redirect:/" + ViewRef.USER_JOIN;
		}
	}
	
	
	//	비밀번호 찾기1-1 (아이디, 이메일 검사) (findPw)
	@RequestMapping(value="/findPw", method = RequestMethod.GET)
	public String findPw(Model model, HttpServletRequest request) {
		model.addAttribute("view",ViewRef.USER_FINDPW);		
		model.addAttribute("user_id");
		model.addAttribute("Email");		
		model.addAttribute("findPwMsg");
		
		return ViewRef.MENU_TEMP;
	}
	
	@RequestMapping(value="/findPw", method = RequestMethod.POST)
	public String findPw(Model model, UserPARAM param, HttpSession hs, UserDMI dmi, RedirectAttributes ra) {
		int result = service.findPw(param, hs);
		int i_user = 0;  // 노란줄그여도 무시 ( 매개변수로 i_user 넣으니 제대로 파싱안됨 ) 
		
		try { // 만약 service에서 i_user에 세션값을 못박을경우
			i_user = (int)hs.getAttribute("i_user");
			
		} catch(Exception e) {
			result = Const.FAIL;
		}
		
		if(result == Const.SUCCESS) { // 정보가 '일치한다면'
			String authKey = mss.sendAutoMailFindPw(param.getEmail());
			hs.setAttribute("authKey", authKey);
			return "redirect:/" + ViewRef.USER_CERCODE; 
			
		} else { // 정보가 '틀렸다면'			
			ra.addFlashAttribute("user_id", param.getUser_id());
			ra.addFlashAttribute("email", param.getEmail());
			model.addAttribute("view",ViewRef.USER_FINDPW);
			ra.addFlashAttribute("findPwMsg","입력하신 정보를 다시 확인해 주세요");
			return "redirect:/" + ViewRef.USER_FINDPW;
		}
	}
	
	
	// 비번찾기 1-2 (이메일 인증코드 입력) (cerCode)
	@RequestMapping(value="/cerCode", method=RequestMethod.GET)
	public String modal(Model model, UserPARAM param, EmailVO vo) {
		cerCodeCount += 1; 
		
		model.addAttribute("view","/user/cerCode");
		model.addAttribute("cerCodeCount");
		return ViewRef.ORIGIN_TEMP;
	}	

	@RequestMapping(value="/cerCode", method=RequestMethod.POST) // post 확인
	public String modal(Model model, EmailVO param, 
			HttpSession hs, RedirectAttributes ra) {
		
		String authKey = (String)hs.getAttribute("authKey");
		
		if(authKey.equals(param.getCerCode())) { 
			cerCodeCount = 0;
			return "redirect:/" + ViewRef.USER_CHANGEPW; 
			
		} else {
			ra.addFlashAttribute("cerCodeCount", cerCodeCount); 
			ra.addFlashAttribute("cerCodeMsg", "인증번호를 다시 확인해 주세요");
			return "redirect:/" + ViewRef.USER_CERCODE;
		}
	}
	
	
	// 비밀번호 변경 (changePw)
	@RequestMapping(value="/changePw", method = RequestMethod.GET)
	public String changePw(Model model, UserPARAM param) {
		model.addAttribute("view", "/user/changePw");
		model.addAttribute("changePwMsg");
		return ViewRef.MENU_TEMP; 
	}
	
	@RequestMapping(value="/changePw", method = RequestMethod.POST)
	public String changePw(Model model, UserPARAM param, 
			HttpSession hs, RedirectAttributes ra) {
		
		int i_user = (int)hs.getAttribute("i_user");
		param.setI_user(i_user);
		
		int result = service.changePw(param);
		
		if(result == Const.SUCCESS) {
			hs.removeAttribute("i_user"); 
			hs.removeAttribute("authKey");
			ra.addFlashAttribute("changePwMsg", "비밀번호가 변경되었습니다");
			return "redirect:/" + ViewRef.USER_CHANGEPW;
			
		} else {
			ra.addFlashAttribute("changePwMsg", "서버 문제가 발생되었습니다 잠시후 다시 시도해주세요 ");
			return "redirect:/" + ViewRef.USER_CHANGEPW; // DB에러시 (다시 비번찾기 창으로 돌려서 비번만 입력하게끔 만들기)
		}
	}
	
	
	// 아이디 찾기 (findId)
	@RequestMapping(value="findId", method = RequestMethod.GET)
	public String findId(Model model) {
		model.addAttribute("view",ViewRef.USER_FINDID);
		return ViewRef.MENU_TEMP;
	}
	
	@RequestMapping(value="findId", method = RequestMethod.POST)
	public String findId(UserPARAM param, HttpSession hs, RedirectAttributes ra) {
		UserDMI dbUser = new UserDMI();
		
		int result = service.findId(param, hs);
		
		if(result == Const.SUCCESS) {			
			dbUser.setUser_id((String)hs.getAttribute("user_id")); 
			mss.sendAutoMailFindId(param.getEmail(), dbUser.getUser_id());
			hs.removeAttribute("user_id"); // service에서 날아온 세션을 다사용했으니 세션삭제
			ra.addFlashAttribute("findIdSuccessMsg", "가입하신 이메일로 아이디가 전송되었습니다");
			return "redirect:/" + ViewRef.USER_FINDID;
			
		} else {
			ra.addFlashAttribute("findIdMsg", "가입하신 이메일을 다시 확인해 주세요");
			return "redirect:/" + ViewRef.USER_FINDID;
		}
	}
	
	
	// 마이페이지 (myPage)
	@RequestMapping(value="/myPage", method = RequestMethod.GET)
	public String myPage(Model model) {
		model.addAttribute("view",ViewRef.USER_MYPAGE);
		return "redirect:/" + ViewRef.MENU_TEMP;
	}
	
	@RequestMapping(value="/myPage", method = RequestMethod.POST)
	public String myPage(Model model, UserPARAM param) {
		model.addAttribute("view",ViewRef.USER_MYPAGE);
		return "redirect:/" + ViewRef.MENU_TEMP;
	}
	
	
	// 개인정보변경 (info)
	@RequestMapping(value="/info", method = RequestMethod.GET)
	public String info(Model model, UserPARAM param, HttpSession hs) {
		
		try {
			int i_user = SecurityUtils.getLoginUserPk(hs);
			param.setI_user(i_user);
			
			model.addAttribute("infoMsg");
			model.addAttribute("data",service.selUser(param));
			model.addAttribute("view", ViewRef.USER_INFO);			
			return ViewRef.MENU_TEMP;
			
		} catch (Exception e) {
			model.addAttribute("loginMsg", "로그인을 해주세요");
			return ViewRef.ORIGIN_TEMP;
		}
	}
	
	@RequestMapping(value="/info", method = RequestMethod.POST)
	public String info(Model model, UserPARAM param, HttpServletRequest request,
			HttpSession hs, RedirectAttributes ra) {
		
		int i_user = SecurityUtils.getLoginUserPk(hs); // 유저pk값을 받아와 mapper에서 그 where절에 pk값을 넣음
		param.setI_user(i_user);
		
		int result = Integer.parseInt(request.getParameter("result"));
				
		/*
		 *	1 사진변경	 2 사진삭제  3 비번변경  4 닉넴변경  5 주소변경  6 이메일 변경  7 관심사 변경  
		 */
		
		//String infoMsg = "";	// ( )가 변경되었습니다  띄울 alert값
		if(result == 1) {
			System.out.println("1번 사진변경");
			
			
		} else if (result == 2) {
			System.out.println("2번 사진삭제");
			
			
		} else if (result == 3) { 
			System.out.println("3번 비밀번호 변경");			 
			int chk = service.changePw(param);
			
					
		} else if (result == 4) {
			System.out.println("4번 닉네임변경");
			int chk = service.changeNick(param);
			
			
		} else if (result == 5) {
			System.out.println("5번 주소변경");
			int chk = service.changeAddr(param);
						
		} else if (result == 6) {
			System.out.println("6번 이메일 변경");
			int chk = service.changeEmail(param);
			
		} else {
			System.out.println("7번 관심사 변경");
		}
		
		return "redirect:/" + ViewRef.USER_INFO;
	}
	
	
	
}
