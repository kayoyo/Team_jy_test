package com.dandi.ddmarket.board;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dandi.ddmarket.CommonUtils;
import com.dandi.ddmarket.Const;
import com.dandi.ddmarket.FileUtils;
import com.dandi.ddmarket.SecurityUtils;
import com.dandi.ddmarket.board.model.BoardDMI;
import com.dandi.ddmarket.board.model.BoardPARAM;
import com.dandi.ddmarket.board.model.BoardVO;

@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	
	// 판매글 등록
//	public int insBoard(BoardPARAM param, HttpSession hs) {
//		int i_user = SecurityUtils.getLoginUserPk(hs);		
//		param.setI_user(i_user);
//	
//		int result = mapper.insBoard(param);
//		
//		return result;
//	}
	
	
	// 판매글 등록
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());		
		int i_board = Integer.parseInt(mReq.getParameter("i_board"));
		if(_authFail(i_board, i_user)) {
			return Const.FAIL;
		}
		
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		 
		// Const.realPath = Const.java  ,  IndexController.java 참고 (기본패키지)
		String path = Const.realPath + "/resources/img/board/" + i_board + "/";
		
		List<BoardVO> list = new ArrayList();
		
		for(int i=0; i<menuNmArr.length; i++) {
			BoardVO vo = new BoardVO();
			list.add(vo);
			
			String title = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setTitle(title);
			vo.setPrice(menu_price);
			vo.setPost(post);
			vo.setAddr(addr);			
			vo.setRoad(road);
			vo.setCtnt(ctnt);
			vo.setI_board(i_board);
			
			// 파일 값 저장
			MultipartFile mf = fileList.get(i);
			
			if(mf.isEmpty()) { continue; }
			
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			
			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setImages(saveFileNm);	// 이미지 한개로 하든가 하기	
											// 맛집 쿼리문 알아보기
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		for(BoardVO vo : list) {
			mapper.insRestRecMenu(vo);
		}
		
		return i_board;
	}

	//유저pk값이랑 쓴글쓴이랑 맞으면 true반환하고 insRestMenu()메뉴 발동함
	private boolean _authFail(int i_board, int i_user) {
		BoardPARAM param = new BoardPARAM();
		param.setI_board(i_board);
		
		BoardDMI dbBoard = mapper.selRest(param);
		if(dbBoard == null || dbBoard.getI_user() != i_user) {
			return true;
		}
		
		return false;
	}


}
