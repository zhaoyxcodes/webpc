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
				System.out.println(" attr is null..");
			} else {

				carService.insertCar(markers_list, line_list, peplenum, phone,
						date_list, userobj);
				return ResponseValue.IS_SUCCESS;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/seachCar", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> seachCar(String markers,String peplenum, String date, String user) {
		try{
			JSONArray markers_list = JSONArray.parseArray(markers);
			JSONArray date_list = JSONArray.parseArray(date);
			if ( markers_list.size() < 2
					|| date_list.size() < 3) {
				System.out.println(" attr is null..");
			} else {
				return carService.seachCar( markers_list, date_list, peplenum);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/getPoint", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPoint(String lineid) {
		try{
			if ( HmacUtil.getStringNull(lineid)) {
				System.out.println(" lineid is null..");
			} else {
				return carService.getPoint(lineid);
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
			JSONArray date_list = JSONArray.parseArray(date);
			JSONObject dataobj = JSONObject.parseObject(dataval);
			JSONObject userobj = JSONObject.parseObject(user);
			JSONObject modelobject = JSONObject.parseObject(modeldata);
			//todo:校验dataobj
			if (  date_list.size() < 3|| HmacUtil.getStringNull(userobj
					.getString(ResponseValue.USER_ID))) {
				System.out.println(" lineid is null..");
			} else {
				return carService.insertReservation(dataobj,date_list,userobj.getString(ResponseValue.USER_ID),modelobject);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}

}