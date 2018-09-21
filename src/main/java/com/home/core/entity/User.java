/**
 * Project Name:home
 * File Name:User.java
 * Package Name:com.home.core.entity
 * Date:2018-8-14下午5:34:57
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.home.core.entity;

import java.io.Serializable;

/**
 * ClassName:com.home.core.entity.User <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-8-14 下午5:34:57 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class User implements Serializable{
	
	private String id;
	private String username;
	private String wxid;
	private String phone;
	private String img;
	private String home;
	private String geom;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWxid() {
		return wxid;
	}
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	


}

