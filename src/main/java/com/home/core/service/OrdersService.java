/**
 * Project Name:home
 * File Name:GoodsService.java
 * Package Name:com.home.core.service
 * Date:2018-8-21上午10:20:35
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.core.dao.JdbcDao;
import com.system.core.util.HmacUtil;
import com.system.core.util.ResponseValue;

/**
 * ClassName:com.home.core.service.GoodsService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018-8-21 上午10:20:35 <br/>
 * 
 * @author yuanxu.zhao
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
@Transactional(readOnly = true)
public class OrdersService {

	@Autowired
	private JdbcDao jdbcDao;
	
	public Map<String, Object> getOrderCount(String userid){
		Map<String, Object> map=new HashMap<String, Object>();
		String queryc="select  count(c.id) as count from car a,line b,reservation c where a.id=b.car_id and b.id=c.line_id and a.user_id='"+userid+"' order by a.createdate desc";
		List<Map<String, Object>> list=jdbcDao.queryForList(queryc);
		map.put("fw", list.get(0).get("COUNT"));//服务
		queryc="select count(id) as count from reservation where user_id='"+userid+"' order by createdate desc";
		List<Map<String, Object>> list2=jdbcDao.queryForList(queryc);
		map.put("cx", list2.get(0).get("COUNT"));//出行
		return map;
	}
	public List<Map<String, Object>> queryFWOrder(String userid){
		String queryc="select c.* from car a,line b,reservation c where a.id=b.car_id and b.id=c.line_id and a.user_id='"+userid+"' order by a.createdate desc";
		return jdbcDao.queryForList(queryc);
	}
	public List<Map<String, Object>> queryCXOrder(String userid){
		String queryc="select * from reservation where user_id='"+userid+"' order by createdate desc";
		return jdbcDao.queryForList(queryc);
	}
	public static void main(String[] args) {
	
	}

}
