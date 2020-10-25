package com.dandi.ddmarket.board.model;

/*
 * 	테이블 완성시 수정해서 다시 빈 추가하기
 */
public class BoardVO {
	private int i_board;
	private int i_cg;
	private int i_user;
	private String title;
	private String ctnt;
	private String images_1;
	private String images_2;
	private String images_3;
	private String images_4;
	private String images_5;
	private String post;
	private String addr;
	private String road;
	private int hits;
	private int price;
	private int sold; // 0이면 거래 미완료, 1이면 거래완료, 2 거래중
	private String r_dt;
	private String m_dt;
	public int getI_board() {
		return i_board;
	}
	public void setI_board(int i_board) {
		this.i_board = i_board;
	}
	public int getI_cg() {
		return i_cg;
	}
	public void setI_cg(int i_cg) {
		this.i_cg = i_cg;
	}
	public int getI_user() {
		return i_user;
	}
	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtnt() {
		return ctnt;
	}
	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}
	public String getImages_1() {
		return images_1;
	}
	public void setImages_1(String images_1) {
		this.images_1 = images_1;
	}
	public String getImages_2() {
		return images_2;
	}
	public void setImages_2(String images_2) {
		this.images_2 = images_2;
	}
	public String getImages_3() {
		return images_3;
	}
	public void setImages_3(String images_3) {
		this.images_3 = images_3;
	}
	public String getImages_4() {
		return images_4;
	}
	public void setImages_4(String images_4) {
		this.images_4 = images_4;
	}
	public String getImages_5() {
		return images_5;
	}
	public void setImages_5(String images_5) {
		this.images_5 = images_5;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	public String getM_dt() {
		return m_dt;
	}
	public void setM_dt(String m_dt) {
		this.m_dt = m_dt;
	}
	
	
			
}
