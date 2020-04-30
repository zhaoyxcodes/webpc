/**
 * Project Name:home
 * File HomeService
 * Package Name:com.home.core.service
 * Date:2018-8-21上午10:20:35
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.service;

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
 * ClassName:com.home.core.service.HomeService <br/>
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
public class HomeService {

	@Autowired
	private JdbcDao jdbcDao;
	
	@Transactional(readOnly = false)
	public String updateUser(String username,String phone,String imgid,String userid){
		if(imgid!=null&&imgid.length()>0){
			List<Map<String, Object>> list=jdbcDao.queryForList("select * from documentfile where id=?",new String[]{imgid});
			if(list==null||list.size()<=0){
				return ResponseValue.IS_ERROR;
			}
			String path=list.get(0).get("pathfile".toUpperCase()).toString();
			jdbcDao.update("update documentfile set status=0 where id=? ",new String[]{imgid});
			String insql="update user set username=?,phone=?,imgid=?,img=? where id=?";
			String [] li=new String[]{username, phone,imgid,path, userid};
			int num= jdbcDao.update(insql,li);
			if(num>0){
				return path;
			}
		}
		String [] li=new String[]{username, phone, imgid, userid};
		String insql="update user set username=?,phone=? where id=?";
		int num= jdbcDao.update(insql,li);
		if(num>0){
			return ResponseValue.IS_SUCCESS;
		}
		return ResponseValue.IS_ERROR;
	}
	public Map<String, Object> queryCertificationByUser(String userid){
		String querysql="select t.*,a.pathfile as driverpath,b.pathfile as carfile from certification  t,documentfile a,documentfile b where" +
				" t.driverlicense=a.id and b.id=t.carlicense and t.user_id='"+userid+"' and a.`status`=1 and b.`status`=1";
		List<Map<String, Object>> list=jdbcDao.queryForList(querysql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public Map<String, Object> queryCertificationByUserStatus(String userid){
		String querysql="select t.*,a.pathfile as driverpath,b.pathfile as carfile from certification  t,documentfile a,documentfile b where" +
				" t.driverlicense=a.id and b.id=t.carlicense and t.user_id='"+userid+"' and a.`status`=1 and b.`status`=1 and t.`status`=1";
		List<Map<String, Object>> list=jdbcDao.queryForList(querysql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public List<Map<String, Object>> queryCertification(){
		String querysql="select t.*,a.pathfile as driverpath,b.pathfile as carfile,DATE_FORMAT(t.createdate,'%Y-%m-%d %H:%i') as sdate from certification  t,documentfile a,documentfile b where " +
				"t.driverlicense=a.id and b.id=t.carlicense and  a.`status`=1 and b.`status`=1  order by  t.`status` asc,t.createdate asc";
		return jdbcDao.queryForList(querysql);
	}
	public Map<String, Object>  querycertificationById(String id){
		String querysql="select t.*,a.pathfile as driverpath,b.pathfile as carfile,DATE_FORMAT(t.createdate,'%Y-%m-%d %H:%i') as sdate from certification  t,documentfile a,documentfile b where " +
				"t.driverlicense=a.id and b.id=t.carlicense and  a.`status`=1 and b.`status`=1 and t.`id`='"+id+"'";
		List<Map<String, Object>> list=jdbcDao.queryForList(querysql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Transactional(readOnly = false)
	public String updateCertificationstatus(String status,String id,String remark){
		String insql="update certification set status=?,refuseremark=? where id=?";
		int num= jdbcDao.update(insql,new String[]{status,remark,id});
		if(num>0){
			return ResponseValue.IS_SUCCESS;
		}
		return ResponseValue.IS_ERROR;
	}
	@Transactional(readOnly = false)
	public String insertCertification(String id,String user_id,String name,String peoplenum,String phone,String carnum,
			String carlicense,String driverlicense,String carbrand,String carcolor){
		String insql="";
		String[] plist=null;
		System.out.println("insert id:"+id);
		if(HmacUtil.getStringNull(id)){
			id = HmacUtil.getUUID();
			plist=new String[]{id,user_id,name,peoplenum,phone,carnum,carlicense,driverlicense,"0",carbrand,carcolor};
			 insql="insert into certification(id,user_id,name,peoplenum,phone,carnum,carlicense,driverlicense,status,carbrand,carcolor)values(?,?,?,?,?,?,?,?,?,?,?)";
		}else{
			jdbcDao.update("update documentfile set status=0 where id in(select carlicense from certification where id='"+id+"')");
			jdbcDao.update("update documentfile set status=0 where id in(select driverlicense from certification where id='"+id+"')");
			plist=new String[]{user_id,name,peoplenum,phone,carnum,carlicense,driverlicense,carbrand,carcolor,id};
			insql="update certification set user_id=?,name=?,peoplenum=?,phone=?,carnum=?,carlicense=?,driverlicense=?,status=0,carbrand=?,carcolor=? where id=?";
		}
		jdbcDao.update("update documentfile set status=1 where id='"+carlicense+"'");
		jdbcDao.update("update documentfile set status=1 where id='"+driverlicense+"'");
		int num= jdbcDao.update(insql,plist);
		if(num>0){
			return ResponseValue.IS_SUCCESS;
		}
		return ResponseValue.IS_ERROR;
	}
	@Transactional(readOnly = false)
	public String insertdfile(String user_id,String pathfile){
		String id = HmacUtil.getUUID();
		String insql="insert into documentfile(id,pathfile,user_id)values(?,?,?)";
		int num= jdbcDao.update(insql,new String[]{id,pathfile,user_id});
		if(num>0){
			return id;
		}
		return ResponseValue.IS_ERROR;
	}
	
	

}
