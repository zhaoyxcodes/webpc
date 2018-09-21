/**
 * Project Name:home
 * File Name:Goods.java
 * Package Name:com.home.core.entity
 * Date:2018-8-21上午10:06:45
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.home.core.entity;


/**
 * ClassName:com.home.core.entity.Goods <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-8-21 上午10:06:45 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class Car {

	private String id;
	private String title;//标题
	private String category_id;//类型
	private String price;//原价
	private String saleprice;//折扣价
	private String paytype;//支付方式
	private String distributiontype;//配送方式
	private String describe;//商品详情
	private String time;//折扣天数
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(String saleprice) {
		this.saleprice = saleprice;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getDistributiontype() {
		return distributiontype;
	}
	public void setDistributiontype(String distributiontype) {
		this.distributiontype = distributiontype;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}

