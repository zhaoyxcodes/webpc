/**
 * Project Name:home
 * File Name:GoodsController.java
 * Package Name:com.home.core.web
 * Date:2018-8-21上午10:01:51
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.core.service.OrdersService;
import com.system.core.util.ResponseValue;

/**
 * ClassName:com.home.core.web.GoodsController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018-8-21 上午10:01:51 <br/>
 * 
 * @author yuanxu.zhao
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller
@RequestMapping("/order")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;

	
	@RequestMapping(value = "/orderCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orderCount( String user) {
		try{
			JSONObject userobj = JSONObject.parseObject(user);
			if (userobj.getString(ResponseValue.USER_ID)!=null) {
				System.out.println(" attr is null..");
			} else {
				return ordersService.getOrderCount( userobj.getString(ResponseValue.USER_ID));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryOrder( String user,int num) {
		try{
			JSONObject userobj = JSONObject.parseObject(user);
			if (userobj.getString(ResponseValue.USER_ID)!=null) {
				System.out.println(" attr is null..");
			} else {
				if(num==0){//服务订单
					return ordersService.queryFWOrder( userobj.getString(ResponseValue.USER_ID));
				}else if(num==1){
					return ordersService.queryCXOrder( userobj.getString(ResponseValue.USER_ID));
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

}
