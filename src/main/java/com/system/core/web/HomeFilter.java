/**
 * Project Name:home
 * File Name:HomeFilter.java
 * Package Name:com.system.core.web
 * Date:2018-8-21上午9:35:00
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.system.core.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.system.core.util.HmacUtil;
import com.system.core.util.ResponseValue;


/**
 * ClassName:com.system.core.web.HomeFilter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-8-21 上午9:35:00 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class HomeFilter implements Filter{

	@Override
	public void destroy() {
		
		// TODO Auto-generated method stub
		
	}
	
	//校验是否登录
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		String userstr=arg0.getParameter("user");
		System.out.println(userstr);
		System.out.println(arg0.getAttribute("user"));
		JSONObject userjson=JSONObject.parseObject(userstr);
		if(userjson!=null){
			if(userjson.getString(ResponseValue.USER_SESSION_KEY).length()>0){
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		//未登录
		arg1.getWriter().write("{\""+ResponseValue.WECHAT_LOGIN_CODE_ERROR+"\":\"true\"}");
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		// TODO Auto-generated method stub
		
	}

}

