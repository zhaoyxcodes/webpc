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
	private static int start_distance = 1000;// 周边1公里
	private static int end_distance = 1000;// 周边1公里
	private static int timeWeight = 5;// 左右5分钟出发时间

	@Transactional(readOnly = false)
	public String insertReservation(JSONObject object, String datedf,
			String userid, JSONObject modelobject) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (sdf.parse(sdf.format(Calendar.getInstance().getTime())).after(
					sdf.parse(datedf))) {
				return "12";// 小于当前时间
			}
		} catch (Exception dateex) {
			dateex.printStackTrace();
			return "12";
		}
		synchronized (userid) {
			// 增加
			String re_id = HmacUtil.getUUID();
			// 校验是否已经下单过前后1小时的订单
			String queryv = "select count(*) as count from reservation t where t.startdate>date_add(STR_TO_DATE('"
					+ datedf
					+ "','%Y-%m-%d %H:%i'), interval -1 hour) "
					+ "and t.startdate<date_add(STR_TO_DATE('"
					+ datedf
					+ "','%Y-%m-%d %H:%i'), interval 1 hour) and user_id='"
					+ userid + "' and status not in(0,1)";
			List<Map<String, Object>> list = jdbcDao.queryForList(queryv);
			if (list != null && list.size() > 0) {
				int count = Integer.parseInt(list.get(0)
						.get("count".toUpperCase()).toString());
				if (count > 0) {
					return "1";
				}
			}
			// 检查当天是否已经预定满了
			String querysql = "select case when count=peplenum then 'true' else 'false' end as bol from(select count(a.id) as count,c.peplenum from"
					+ " Reservation a,line b,car c where a.line_id=b.id and b.car_id=c.id and a.`status` in(0,1) "
					+ "and DATE_FORMAT(a.startdate,'%Y-%m-%d')=DATE_FORMAT(STR_TO_DATE('"
					+ datedf
					+ "','%Y-%m-%d %H:%i'),'%Y-%m-%d') and a.line_id='"
					+ object.getString("line_id") + "' group by c.peplenum )a";
			List<Map<String, Object>> list2 = jdbcDao.queryForList(querysql);
			if (list2 != null && list2.size() > 0) {
				String bol = list2.get(0).get("bol".toUpperCase()).toString();
				if ("true".equals(bol)) {// 已经预定满了
					return "10";
				}
			}

			int everyday = 0;// 这个字段无用了

			String insql = "insert into Reservation(car_id,downaddress,downmi,startmi,id,line_id,user_id,start,end,gatherstart,downend,offer,phone,everyday,startdate,remark,endtitle,starttitle)values('"
					+ object.getString("carid")
					+ "','"
					+ object.getString("downaddress")
					+ "','"
					+ object.getString("downmi")
					+ "','"
					+ object.getString("startmi")
					+ "','"
					+ re_id
					+ "','"
					+ object.getString("line_id")
					+ "','"
					+ userid
					+ "',geomfromtext('"
					+ object.getString("start")
					+ "'),geomfromtext('"
					+ object.getString("end")
					+ "'),geomfromtext('"
					+ object.getString("gatherstart")
					+ "'),geomfromtext('"
					+ object.getString("downend")
					+ "'),'"
					+ object.getString("offer")
					+ "','"
					+ object.getString("phone")
					+ "',"
					+ everyday
					+ ",'"
					+ datedf
					+ "','"
					+ object.getString("remark")
					+ "','"
					+ object.getString("endtitle")
					+ "','"
					+ object.getString("starttitle") + "')";
			int num = jdbcDao.update(insql);
			if (num > 0) {
				
				String pagenew = modelobject.getString("page") + re_id;
				modelobject.put("page", pagenew);
				HmacUtil.sendModel(modelobject);
				return re_id;
			}
		}
		return ResponseValue.IS_ERROR;
	}

	public List<Map<String, Object>> getPoint(String lineid) {
		return jdbcDao
				.queryForList("select  AsText(geom)as geom from point where  line_id='"
						+ lineid + "' ");
	}

	public List<Map<String, Object>> seachLineByUser(String userid) {
		return jdbcDao
				.queryForList(
						"select a.*,b.start_title,b.end_title,b.peplenum,b.everyday,DATE_FORMAT(b.startdate,'%Y-%m-%d %H:%i') as sdate from line a,car b where a.car_id=b.id  and b.user_id=? order by a.status desc, b.createdate desc",
						new String[] { userid });
	}

	public List<Map<String, Object>> seachLineById(String lineid) {
		return jdbcDao
				.queryForList(
						"select a.*,b.start_title,b.end_title,b.peplenum,b.everyday,DATE_FORMAT(b.startdate,'%Y-%m-%d %H:%i') as sdate,AsText(b.end_geom) as end_geom,AsText(b.start_geom) as start_geom"
								+ ",u.img,u.username,cc.phone as cz_phone,cc.carnum as cz_carnum,cc.carbrand as cz_carbrand,cc.carcolor as cz_carcolor from user u,line a,car b,certification cc where a.car_id=b.id and cc.user_id=u.id and cc.status=1 and b.user_id=u.id and   a.id=? order by b.createdate desc",
						new String[] { lineid });
	}

	public List<Map<String, Object>> seachCar(JSONArray markers_list,
			JSONArray date_list, String peplenum, JSONObject userobj,
			String start_d, String end_d, String starttw) {
		System.out.println(end_distance);
		if (start_d != null && start_d.length() > 0) {
			start_distance = Integer.parseInt(start_d);// 周边1公里
		}
		if (end_d != null && end_d.length() > 0) {
			end_distance = Integer.parseInt(end_d);// 周边1公里
		}
		if (starttw != null && starttw.length() > 0) {
			timeWeight = Integer.parseInt(starttw);
		}
		JSONObject startmark = markers_list.getJSONObject(0);
		JSONObject endmark = markers_list.getJSONObject(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar curr = Calendar.getInstance();
		int everyday = 0;
		int dateobj = date_list.getInteger(0);
		int hourobj = date_list.getInteger(1);
		int timeobj = date_list.getInteger(2);
		if (dateobj == 0) {// 每天
			everyday = 1;// 没用
		} else {
			int adddate = dateobj - 1;
			curr.add(Calendar.DAY_OF_MONTH, adddate);
		}
		curr.set(Calendar.HOUR_OF_DAY, hourobj);
		curr.set(Calendar.MINUTE, timeobj * 5);
		// 用户选择的时间 curr

		int min = timeobj * 5;
		int starttime = min - timeWeight;
		int endtime = min + timeWeight;
		// 0和60时需要加减hh
		if (starttime < 0) {
			starttime = 0;
		}
		if (endtime > 59) {
			endtime = 59;
		}
		String strstarttime = starttime + "";
		String strendtime = endtime + "";
		if (starttime < 10) {
			strstarttime = "0" + starttime;
		}
		if (endtime < 10) {
			strendtime = "0" + endtime;
		}
		String querysql = "SELECT AsText(aa.geom)as endgeom,u.img,u.username,aa.id as carid ,minstart,minend,linename,lineid,peplenum,start_title,end_title,start_address,end_address,aa.phone,everyday,DATE_FORMAT(startdate,'%Y-%m-%d %H:%i') as startdate,distance,duration,AsText(start_geom) as start_geom,AsText(end_geom) as end_geom "
				+ ",cc.phone as cz_phone,cc.carnum as cz_carnum,cc.carbrand as cz_carbrand,cc.carcolor as cz_carcolor,"
				+ "(select  count(*) from Reservation where line_id=aa.lineid and DATE_FORMAT(startdate,'%Y-%m-%d')='"
				+ sdf.format(curr.getTime())
				+ "' and status in(0,1)) as ydcount FROM "
				+

				"(SELECT min(startdistance) AS minstart,min(enddistance) AS minend,allin.* FROM (SELECT a.*,  enddistance,c.geom,b.id AS lineid ,b.distance,b.duration,b.name as linename,b.s_index FROM"
				+ "(SELECT *, ST_Distance_Sphere (Point ("
				+ startmark.getString("longitude")
				+ ","
				+ startmark.getString("latitude")
				+ "),start_geom) AS startdistance FROM car "
				+ "WHERE STATUS = 1 AND  DATE_FORMAT(startdate,'%H:%i')>='"
				+ hourobj
				+ ":"
				+ strstarttime
				+ "' AND DATE_FORMAT(startdate,'%H:%i')<='"
				+ hourobj
				+ ":"
				+ strendtime + "'  ";
		if (everyday == 1) {
			querysql += "AND (DATE_FORMAT(startdate,'%Y-%m-%d')>='"
					+ sdf.format(curr.getTime()) + "' OR EVERYDAY=1)";
		} else {
			querysql += "AND (DATE_FORMAT(startdate,'%Y-%m-%d')='"
					+ sdf.format(curr.getTime()) + "' OR EVERYDAY=1)";
		}
		querysql += ") a,line b,(select a.* from (select * from (select ST_Distance_Sphere (Point ("
				+ endmark.getString("longitude")
				+ ","
				+ endmark.getString("latitude")
				+ "),a.geom) AS enddistance,a.* from point a where a.status=1) a where a.enddistance<"
				+ end_distance
				+ " ) a"
				+ " ,(select min(enddistance) as enddistance, line_id from(select ST_Distance_Sphere (Point ("
				+ endmark.getString("longitude")
				+ ","
				+ endmark.getString("latitude")
				+ "),geom) AS enddistance,point.line_id from point )a "
				+ " where enddistance<"
				+ end_distance
				+ "  group by line_id )b where a.enddistance=b.enddistance and a.line_id=b.line_id) c  "
				+ "WHERE b.id not in(" +
				"select c.id from line c where c.car_id in(select car_id from reservation a where DATE_FORMAT(startdate,'%Y-%m-%d')='"+sdf.format(curr.getTime())+"' "+
" and `status` in(0,1)) and c.id not in(select line_id from reservation a where DATE_FORMAT(startdate,'%Y-%m-%d')='"+sdf.format(curr.getTime())+"' "+
" and `status` in(0,1))"+//过滤没有预定的其他线路id
				") and  a.startdistance < "
				+ start_distance
				+ " AND a.id = b.car_id AND c.line_id = b.id AND b.`status` = 1  ) allin "
				+ " GROUP BY lineid ) aa,`user` u,certification cc where aa.user_id=u.id and cc.user_id=u.id and cc.status=1 and u.id not in('"
				+ userobj.getString(ResponseValue.USER_ID)
				+ "') ORDER BY minstart ASC,minend ASC,duration asc,s_index asc ";
		System.out.println(querysql);
		return jdbcDao.queryForList(querysql);
	}

	@Transactional(readOnly = false)
	public String insertCar(JSONArray markers_list, JSONArray line_list,
			String peplenum, String phone, JSONArray date, JSONObject user)
			throws Exception {
		try {
			List<String> sql = new ArrayList<String>();
			String car_id = HmacUtil.getUUID();
			JSONObject startmark = markers_list.getJSONObject(0);
			JSONObject endmark = markers_list.getJSONObject(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar curr = Calendar.getInstance();
			int everyday = 0;
			int dateobj = date.getInteger(0);
			int hourobj = date.getInteger(1);
			int timeobj = date.getInteger(2);
			if (dateobj == 0) {// 每天
				everyday = 1;
			} else {
				int adddate = dateobj - 1;
				curr.add(Calendar.DAY_OF_MONTH, adddate);
			}
			curr.set(Calendar.HOUR_OF_DAY, hourobj);
			curr.set(Calendar.MINUTE, timeobj * 5);
			// 校验
			String time = sdf.format(curr.getTime());
			try {
				if (sdf.parse(sdf.format(Calendar.getInstance().getTime()))
						.after(sdf.parse(time)) && dateobj != 0) {
					return "12";// 小于当前时间
				}
			} catch (Exception dateex) {
				dateex.printStackTrace();
				return "12";
			}

			String minH = (hourobj - 1) + ":" + (timeobj * 5);
			String maxH = (hourobj + 1) + ":" + (timeobj * 5);
			int minnum = hourobj - 1;
			if (minnum < 10) {
				minH = "0" + (hourobj - 1) + ":" + (timeobj * 5);
			}
			minnum = hourobj + 1;
			if (minnum < 10) {
				maxH = "0" + (hourobj + 1) + ":" + (timeobj * 5);
			}
			if (hourobj == 0) {
				minH = "00:00";
			}
			if (hourobj == 23) {
				maxH = "23:59";
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String queryv = "select   count(*) as count from car where  startdate>=date_add(STR_TO_DATE('"
					+ time
					+ "','%Y-%m-%d %H:%i'), interval -1 hour)"
					+ " and startdate<=date_add(STR_TO_DATE('"
					+ time
					+ "','%Y-%m-%d %H:%i'), interval 1 hour) "
					+ "and status=1 and  user_id='"
					+ user.getString(ResponseValue.USER_ID) + "' ";
			if (dateobj == 0) {
				queryv = "select   count(*) as count from car where  startdate>=STR_TO_DATE('"
						+ sdf1.format(time)
						+ "','%Y-%m-%d')  and DATE_FORMAT(startdate,'%H:%i')>='"
						+ minH
						+ "' and   DATE_FORMAT(startdate,'%H')<='"
						+ maxH
						+ "'"
						+ "and status=1 and  user_id='"
						+ user.getString(ResponseValue.USER_ID) + "' ";
			}
			List<Map<String, Object>> list = jdbcDao.queryForList(queryv);
			if (list != null && list.size() > 0) {
				int count = Integer.parseInt(list.get(0)
						.get("count".toUpperCase()).toString());
				if (count > 0) {
					return "11";
				}
			}
			sql.add("insert into CAR(id,user_id,peplenum,phone,everyday,startdate,start_geom,end_geom,start_title,end_title,start_address,end_address)values('"
					+ car_id
					+ "','"
					+ user.getString(ResponseValue.USER_ID)
					+ "',"
					+ peplenum
					+ ",'"
					+ phone
					+ "',"
					+ everyday
					+ ",'"
					+ time
					+ "',"
					+ HmacUtil.stringPoitGEO(startmark.getString("longitude")
							+ "," + startmark.getString("latitude"))
					+ ","
					+ HmacUtil.stringPoitGEO(endmark.getString("longitude")
							+ "," + endmark.getString("latitude"))
					+ ",'"
					+ startmark.getString("title")
					+ "','"
					+ endmark.getString("title")
					+ "','"
					+ startmark.getString("address")
					+ "','"
					+ endmark.getString("address") + "')");
			for (int i = 0; i < line_list.size(); i++) {
				JSONObject lineobj = line_list.getJSONObject(i);
				String line_id = HmacUtil.getUUID();
				sql.add("insert into LINE(id,car_id,name,s_index,distance,duration)values('"
						+ line_id
						+ "','"
						+ car_id
						+ "','"
						+ lineobj.getString("name")
						+ "',"
						+ i
						+ ",'"
						+ lineobj.getString("distance")
						+ "','"
						+ lineobj.getString("duration") + "')");
				JSONArray pointslist = lineobj.getJSONArray("points");
				for (int j = 0; j < pointslist.size(); j++) {
					JSONObject pointobj = pointslist.getJSONObject(j);
					sql.add("insert into point(id,line_id,geom)values('"
							+ HmacUtil.getUUID()
							+ "','"
							+ line_id
							+ "',"
							+ HmacUtil.stringPoitGEO(pointobj
									.getString("longitude")
									+ ","
									+ pointobj.getString("latitude")) + ")");
				}
			}
			String[] sqlli = new String[sql.size()];
			sqlli = sql.toArray(sqlli);
			jdbcDao.batchUpdate(sqlli);
			return ResponseValue.IS_SUCCESS;
		} catch (Exception ex) {
			throw ex;
		}

	}
	public static void main(String[] args) {
	}

}
