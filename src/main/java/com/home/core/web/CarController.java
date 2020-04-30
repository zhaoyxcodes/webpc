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
import com.home.core.service.CarService;
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
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;

	@RequestMapping(value = "/insertCar", method = RequestMethod.POST)
	@ResponseBody
	public String insertCar(String markers, String polyline, String peplenum,
			String phone, String date, String user)  {
		try {
			JSONArray markers_list = JSONArray.parseArray(markers);
			JSONArray line_list = JSONArray.parseArray(polyline);
			JSONArray date_list = JSONArray.parseArray(date);
			JSONObject userobj = JSONObject.parseObject(user);
			if (line_list.size() < 1
					|| markers_list.size() < 2
					|| HmacUtil.getStringNull(polyline)
					|| HmacUtil.getStringNull(peplenum)
					|| HmacUtil.getStringNull(phone)
					|| date_list.size() < 3
					|| HmacUtil.getStringNull(userobj
							.getString(ResponseValue.USER_ID))) {
				System.out.println(" insertCar is null..");
			} else {
				return carService.insertCar(markers_list, line_list, peplenum, phone,
						date_list, userobj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/seachCar", method = RequestMethod.POST)
	@ResponseBody
	public String seachCar(String markers,String peplenum, String date, String user,String start_d,String end_d,String starttw,int pageNo,int pageSize) {
		try{
			JSONArray markers_list = JSONArray.parseArray(markers);
			JSONArray date_list = JSONArray.parseArray(date);
			JSONObject userobj = JSONObject.parseObject(user);
			if ( markers_list.size() < 2
					|| date_list.size() < 3|| HmacUtil.getStringNull(userobj
							.getString(ResponseValue.USER_ID))) {
				System.out.println(" seachCar is null..");
			} else {
				JSONObject jsonobj=carService.seachCarCount( markers_list, date_list, peplenum,userobj, start_d, end_d,starttw,pageSize);
				List<Map<String, Object>>  list= carService.seachCar( markers_list, date_list, peplenum,userobj, start_d, end_d,starttw,pageNo,pageSize);
				if(list!=null&&list.size()>0){
					JSONArray ja=new JSONArray();
					ja.addAll(list);
					jsonobj.put("list", ja);
					return jsonobj.toJSONString();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/getPoint", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPoint(String lineid) {
		try{
			if ( HmacUtil.getStringNull(lineid)) {
				System.out.println(" getPoint is null..");
			} else {
				return carService.getPoint(lineid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/getLineByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getLineByUser(String userid) {
		try{
			if ( HmacUtil.getStringNull(userid)) {
				System.out.println(" getLineByUser is null..");
			} else {
				return carService.seachLineByUser(userid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/getLineById", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getLineById(String lineid) {
		try{
			if ( HmacUtil.getStringNull(lineid)) {
				System.out.println(" getLineById is null..");
			} else {
				return carService.seachLineById(lineid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/reservation", method = RequestMethod.POST)
	@ResponseBody
	public String  insertReservation(String date,String dataval,String user,String modeldata) {
		try{
			JSONObject dataobj = JSONObject.parseObject(dataval);
			JSONObject userobj = JSONObject.parseObject(user);
			JSONObject modelobject = JSONObject.parseObject(modeldata);
			//todo:校验dataobj
			if (   HmacUtil.getStringNull(userobj
					.getString(ResponseValue.USER_ID))) {
				System.out.println(" insertReservation is null..");
			} else {
				return carService.insertReservation(dataobj,date,userobj.getString(ResponseValue.USER_ID),modelobject);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	

}
