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
	//查询服务订单信息
	public List<Map<String, Object>> queryFWOrder(String userid){
		String queryc="select c.*,u.username,a.everyday as carday,DATE_FORMAT(c.startdate,'%Y-%m-%d %H:%i')  as sdate,AsText(c.gatherstart) as fgatherstart,AsText(c.downend) as fdownend,AsText(c.end) as fend,AsText(c.start) as fstart" +
				",AsText(a.end_geom) as fcarend,a.start_title,a.end_title  from car a,line b,reservation c,user u where  c.user_id=u.id and a.id=b.car_id and b.id=c.line_id and a.user_id='"+userid+"' order by a.createdate desc";
		return jdbcDao.queryForList(queryc);
	}
	//查询预订订单信息
	public List<Map<String, Object>> queryCXOrder(String userid){
		String queryc="select c.*,u.username,a.everyday as carday,DATE_FORMAT(c.startdate,'%Y-%m-%d %H:%i') as sdate,AsText(c.gatherstart) as fgatherstart,AsText(c.downend) as fdownend,AsText(c.end) as fend,AsText(c.start) as fstart" +
				",AsText(a.end_geom) as fcarend,a.start_title,a.end_title from car a,line b,reservation c,user u where  a.user_id=u.id and a.id=b.car_id and b.id=c.line_id and c.user_id='"+userid+"' order by  c.createdate desc";
		return jdbcDao.queryForList(queryc);
	}
	//查询订单详细
	public List<Map<String, Object>> queryOrder(String rid){
		String queryc="select c.*,u.username,u.img,a.everyday as carday,DATE_FORMAT(c.startdate,'%Y-%m-%d %H:%i') as sdate,AsText(c.gatherstart) as fgatherstart,AsText(c.downend) as fdownend,AsText(c.end) as fend,AsText(c.start) as fstart" +
				",AsText(a.end_geom) as fcarend,a.start_title,a.end_title from car a,line b,reservation c,user u where  a.user_id=u.id and a.id=b.car_id and b.id=c.line_id and c.id='"+rid+"' order by  c.createdate desc";
		return jdbcDao.queryForList(queryc);
	}
	@Transactional(readOnly = false)
	public String updateOrderStatus(String rid,String status,JSONObject modelobject){
		String updatesql="update reservation set status=? where id=?";
		int num=jdbcDao.update(updatesql, new String[]{status,rid});
		if(num>0){
			if("1".equals(status)||"4".equals(status)){//车主确定或取消
				HmacUtil.sendModel(modelobject);
			}
			return ResponseValue.IS_SUCCESS;
		}
		return ResponseValue.IS_ERROR;
	}
	@Transactional(readOnly = false)
	public String updateOrderPrice(String rid,String price,String remark,JSONObject modelobject){
		String updatesql="update reservation set offer=?,remark=? where id=?";
		int num=jdbcDao.update(updatesql, new String[]{price,remark,rid});
		if(num>0){
			HmacUtil.sendModel(modelobject);
			return ResponseValue.IS_SUCCESS;
		}
		return ResponseValue.IS_ERROR;
	}
	public static void main(String[] args) {
	
	}

}
