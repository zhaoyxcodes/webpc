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
public class CarService {

	@Autowired
	private JdbcDao jdbcDao;
	private static int start_distance=1000;//周边1公里
	private static int end_distance=1000;//周边1公里
	private static int timeWeight=5;//左右5分钟
	
	@Transactional(readOnly = false)
	public String insertReservation(JSONObject object,JSONArray date_list,String userid,JSONObject modelobject){
		String re_id = HmacUtil.getUUID();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar curr = Calendar.getInstance();
		int everyday=0;
		int dateobj=date_list.getInteger(0);
		int hourobj=date_list.getInteger(1);
		int timeobj=date_list.getInteger(2);
		if(dateobj==0){//每天
			everyday=1;
		}else{
			int adddate=dateobj-1;
			curr.add(Calendar.DAY_OF_MONTH,adddate);
		}
		curr.set(Calendar.HOUR_OF_DAY,hourobj);
		curr.set(Calendar.MINUTE,timeobj*5);
		String insql="insert into Reservation(id,line_id,user_id,start,end,gatherstart,downend,offer,phone,everyday,startdate,remark)values('"+re_id
		+"','"+object.getString("line_id")+"','"+userid+"',geomfromtext('"+object.getString("start")+"'),geomfromtext('"+object.getString("end")+"'),geomfromtext('" +
		object.getString("gatherstart")+"'),geomfromtext('"+object.getString("downend")+"'),'"+object.getString("offer")+"','"+object.getString("phone")+"',"+everyday+",'"+sdf.format(curr.getTime())+"','"+object.getString("remark")+"')";
		int num= jdbcDao.update(insql);
		if(num>0){
			HmacUtil.sendModel(modelobject);
			return ResponseValue.IS_SUCCESS;
		}
		return ResponseValue.IS_ERROR;
	}
	public List<Map<String, Object>> getPoint(String lineid){
		return jdbcDao.queryForList("select  AsText(geom)as geom from point where  line_id='"+lineid+"' ");
	}
	public List<Map<String, Object>> seachCar(JSONArray markers_list,JSONArray date_list,String peplenum){
		JSONObject startmark=markers_list.getJSONObject(0);
		JSONObject endmark=markers_list.getJSONObject(1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar curr = Calendar.getInstance();
		int everyday=0;
		int dateobj=date_list.getInteger(0);
		int hourobj=date_list.getInteger(1);
		int timeobj=date_list.getInteger(2);
		if(dateobj==0){//每天
			everyday=1;
		}else{
			int adddate=dateobj-1;
			curr.add(Calendar.DAY_OF_MONTH,adddate);
		}
		curr.set(Calendar.HOUR_OF_DAY,hourobj);
		curr.set(Calendar.MINUTE,timeobj*5);
		
		//用户选择的时间 curr
		
		int min=timeobj*5;
		int starttime=min-timeWeight;
		int endtime=min+timeWeight;
		//0和60时需要加减hh
		if(min>55){
			 starttime=min-timeWeight;
			 endtime=min;
		}else if(min<5){
			starttime=min;
			 endtime=min+timeWeight;
		}
		//未过滤预约的
		String querysql="SELECT AsText(aa.geom)as endgeom,u.img,u.username,aa.id as carid ,minstart,minend,linename,lineid,peplenum,start_title,end_title,start_address,end_address,aa.phone,everyday,DATE_FORMAT(startdate,'%Y-%m-%d %H:%i') as startdate,distance,duration,AsText(start_geom) as start_geom,AsText(end_geom) as end_geom FROM "+
			
				"(SELECT min(startdistance) AS minstart,min(enddistance) AS minend,allin.* FROM (SELECT a.*,  enddistance,c.geom,b.id AS lineid ,b.distance,b.duration,b.name as linename,b.s_index FROM"+
				"(SELECT *, ST_Distance_Sphere (Point ("+startmark.getString("longitude")+","+startmark.getString("latitude")+"),start_geom) AS startdistance FROM car "+
				"WHERE STATUS = 1 AND  DATE_FORMAT(startdate,'%H:%i')>='"+hourobj+":"+starttime+"' AND DATE_FORMAT(startdate,'%H:%i')<='"+hourobj+":"+endtime+"'  ";
		if(everyday==1){
			querysql+="AND (DATE_FORMAT(startdate,'%Y-%m-%d')>='"+sdf.format(curr.getTime())+"' OR EVERYDAY=1)";
		}else{
			querysql+="AND (DATE_FORMAT(startdate,'%Y-%m-%d')='"+sdf.format(curr.getTime())+"' OR EVERYDAY=1)";
		}	
		querysql+=") a,line b,(select a.* from (select * from (select ST_Distance_Sphere (Point ("+endmark.getString("longitude")+","+endmark.getString("latitude")+"),a.geom) AS enddistance,a.* from point a) a where a.enddistance<"+end_distance+" ) a" +
				" ,(select min(enddistance) as enddistance, line_id from(select ST_Distance_Sphere (Point ("+endmark.getString("longitude")+","+endmark.getString("latitude")+"),geom) AS enddistance,point.line_id from point )a " +
				" where enddistance<"+end_distance+"  group by line_id )b where a.enddistance=b.enddistance and a.line_id=b.line_id) c  " +
				"WHERE a.startdistance < "+start_distance+" AND a.id = b.car_id AND c.line_id = b.id AND b.`status` = 1  ) allin "+
				" GROUP BY lineid ) aa,`user` u where aa.user_id=u.id  ORDER BY minstart ASC,minend ASC,duration asc,s_index asc ";
		System.out.println(querysql);
		return jdbcDao.queryForList(querysql);
	}
	@Transactional(readOnly = false)
	public void  insertCar(JSONArray markers_list,JSONArray line_list,String peplenum,String phone,JSONArray date,JSONObject user) throws Exception{
		try{
			List<String> sql = new ArrayList<String>();
			String car_id = HmacUtil.getUUID();
			JSONObject startmark=markers_list.getJSONObject(0);
			JSONObject endmark=markers_list.getJSONObject(1);
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar curr = Calendar.getInstance();
			int everyday=0;
			int dateobj=date.getInteger(0);
			int hourobj=date.getInteger(1);
			int timeobj=date.getInteger(2);
			if(dateobj==0){//每天
				everyday=1;
			}else{
				int adddate=dateobj-1;
				curr.add(Calendar.DAY_OF_MONTH,adddate);
			}
			curr.set(Calendar.HOUR_OF_DAY,hourobj);
			curr.set(Calendar.MINUTE,timeobj*5);
			
			sql.add(  "insert into CAR(id,user_id,peplenum,phone,everyday,startdate,start_geom,end_geom,start_title,end_title,start_address,end_address)values('"
					+ car_id
					+ "','"
					+ user.getString(ResponseValue.USER_ID)
					+ "',"
					+ peplenum
					+ ",'"
					+ phone
					+ "',"+everyday+",'"+sdf.format(curr.getTime())+"',"+HmacUtil.stringPoitGEO(startmark.getString("longitude")+","+startmark.getString("latitude"))
					+","+HmacUtil.stringPoitGEO(endmark.getString("longitude")+","+endmark.getString("latitude"))
					+",'"+startmark.getString("title")+"','"+endmark.getString("title")+"','"+startmark.getString("address")+"','"+endmark.getString("address")+"')");
			for(int i=0;i<line_list.size();i++){
				JSONObject lineobj=line_list.getJSONObject(i);
				String line_id = HmacUtil.getUUID();
				sql.add("insert into LINE(id,car_id,name,s_index,distance,duration)values('"
						+ line_id+ "','"+ car_id+ "','"+lineobj.getString("name")+ "',"+ i+ ",'"+lineobj.getString("distance")+"','"+lineobj.getString("duration")+"')");
				JSONArray pointslist=lineobj.getJSONArray("points");
				for(int j=0;j<pointslist.size();j++){
					JSONObject pointobj=pointslist.getJSONObject(j);
					sql.add( "insert into point(id,line_id,geom)values('"+HmacUtil.getUUID()+"','"+line_id+"',"+
							HmacUtil.stringPoitGEO(pointobj.getString("longitude")+","+pointobj.getString("latitude"))+")");
				}
			}
			String[] sqlli=new String[sql.size()];
			sqlli=sql.toArray(sqlli);
			jdbcDao.batchUpdate(sqlli);
		}catch(Exception ex){
			throw ex;
		}
	}
	public static void main(String[] args) {
	
	}

}
