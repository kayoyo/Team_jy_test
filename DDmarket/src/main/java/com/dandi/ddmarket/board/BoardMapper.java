package com.dandi.ddmarket.board;

import org.apache.ibatis.annotations.Mapper;

import com.dandi.ddmarket.board.model.BoardPARAM;

@Mapper
public interface BoardMapper {

	// select
	
	
	// insert
	int insBoard(BoardPARAM param);
	
	// update
	
	
	// delete
}
