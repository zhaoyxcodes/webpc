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
import com.system.core.util.HmacUtil;
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

	@RequestMapping(value = "/queryIsMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIsMsg( String user) {
		try{
			JSONObject userobj = JSONObject.parseObject(user);
			if (HmacUtil.getStringNull(userobj
					.getString(ResponseValue.USER_ID))) {
				System.out.println(" orderCount is null..");
			} else {
				return ordersService.queryIsMsg( userobj.getString(ResponseValue.USER_ID));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/orderCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orderCount( String user) {
		try{
			JSONObject userobj = JSONObject.parseObject(user);
			if (HmacUtil.getStringNull(userobj
					.getString(ResponseValue.USER_ID))) {
				System.out.println(" orderCount is null..");
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
			if ( HmacUtil.getStringNull(userobj
					.getString(ResponseValue.USER_ID))) {
				System.out.println(" queryOrder is null..");
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
	@RequestMapping(value = "/queryReservationByLine", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryReservationByLine( String lineid) {
		try{
			if ( HmacUtil.getStringNull(lineid)) {
				System.out.println(" queryReservationByLine is null..");
			} else {
				return ordersService.queryReservationByLine( lineid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/queryOrderById", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryOrderById( String id) {
		try{
			if ( HmacUtil.getStringNull(id)) {
				System.out.println(" queryOrderById is null..");
			} else {
				return ordersService.queryOrder( id);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/priceMin", method = RequestMethod.POST)
	@ResponseBody
	public String  priceMin(String modeldata) {
		try{
			JSONObject modelobject = JSONObject.parseObject(modeldata);
			if ( modelobject.isEmpty()) {
				System.out.println(" priceMin is null..");
			} else {
				HmacUtil.sendModel(modelobject);
				return ResponseValue.IS_SUCCESS;
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/updateOrderPrice", method = RequestMethod.POST)
	@ResponseBody
	public String  updateOrderPrice(String rid,String price,String remark,String modeldata) {
		try{
			JSONObject modelobject = JSONObject.parseObject(modeldata);
			if ( HmacUtil.getStringNull(price)|| HmacUtil.getStringNull(rid)|| HmacUtil.getStringNull(remark)) {
				System.out.println(" updateOrderPrice is null..");
			} else {
				return ordersService.updateOrderPrice( rid, price,remark,modelobject);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public String  updateOrderStatus(String rid,String status,String modeldata) {
		try{
			JSONObject modelobject = JSONObject.parseObject(modeldata);
			if ( HmacUtil.getStringNull(status)|| HmacUtil.getStringNull(rid)) {
				System.out.println(" updateReservationStatus is null..");
			} else {
				return ordersService.updateOrderStatus( rid, status,modelobject);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}

}
