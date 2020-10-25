package com.dandi.ddmarket.board;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dandi.ddmarket.SecurityUtils;
import com.dandi.ddmarket.ViewRef;
import com.dandi.ddmarket.board.model.BoardPARAM;
import com.dandi.ddmarket.user.UserService;
import com.dandi.ddmarket.user.model.UserPARAM;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;		// 보드 서비스
	
	@Autowired
	private UserService userService;	// 유저 서비스
	
	
		
	// 판매글 등록
	@RequestMapping(value="/saleReg", method = RequestMethod.GET)
	public String saleReg(Model model, HttpSession hs, UserPARAM param) {
		try { // 비로그인 상태로 접근시 로그인페이지로		
			int i_user = SecurityUtils.getLoginUserPk(hs);
			param.setI_user(i_user);
			
		} catch (Exception e) {
			model.addAttribute("loginMsg", "로그인을 해주세요");
			return ViewRef.ORIGIN_TEMP;
		}
		
		model.addAttribute("categoryList", userService.selCategory());
		model.addAttribute("view", ViewRef.BOARD_SALEREG);		
		return ViewRef.DEFAULT_TEMP;
	}
		
	
	@RequestMapping(value="/saleReg", method = RequestMethod.POST)
	public String saleReg(Model model, BoardPARAM param, HttpSession hs) {
		
		System.out.println("1");
		int result = service.insBoard(param, hs);
		System.out.println("2");
	
		
		return "redirect:/" + ViewRef.BOARD_DETAIL;
	}
	
	
	
	
	
	
	
	
	
	
	// 판매글 상세페이지
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public String detail(Model model) {
		
		model.addAttribute("view", ViewRef.BOARD_DETAIL);		
		return ViewRef.DEFAULT_TEMP;
	}
		
	// 상세글 수정시에..
	@RequestMapping(value="/detail", method = RequestMethod.POST)
	public String detail(Model model, BoardPARAM param) {
			
				
		return "redirect:/" + ViewRef.BOARD_DETAIL;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//욕 필터
	private String swearWordFilter(final String ctnt) {
		String[] filters = {"개새끼", "미친년", "ㄱ ㅐ ㅅ ㅐ ㄲ ㅣ", "씨발년"};
		String result = ctnt;
		for(int i=0; i<filters.length; i++) {
			result = result.replace(filters[i], "***");
		}
		return result;
	}
	
	//스크립트 필터
	private String scriptFilter(final String ctnt) {
		String[] filters = {"<script>", "</script>"};
		String[] filterReplaces = {"&lt;script&gt;", "&lt;/script&gt;"};
		
		String result = ctnt;
		for(int i=0; i<filters.length; i++) {
			result = result.replace(filters[i], filterReplaces[i]);
		}
		return result;
	}
}
