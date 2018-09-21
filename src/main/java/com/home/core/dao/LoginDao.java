/**
 * Project Name:home
 * File Name:LoginDao.java
 * Package Name:com.home.core.dao
 * Date:2018-8-14下午4:50:42
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.home.core.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.home.core.entity.User;

/**
 * ClassName:com.home.core.dao.LoginDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-8-14 下午4:50:42 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public class LoginDao {

	private static final Logger logger = LoggerFactory.getLogger(LoginDao.class);
	@Autowired
	private JdbcDao jdbcDao;
	public User getUserByOpenId(String openId){
		
		List userlist= jdbcDao.queryForObjectList("select * from user where wxid=?",new String[]{openId},User.class);
		if(userlist!=null&&userlist.size()>0){
			return (User)userlist.get(0);
		}
		return null;
	}
	public int insertUser(String[] user){
		String sql="insert into user(id,username,wxid,img)values('"+user[0]+"','"+user[1]+"','"+user[2]+"','"+user[3]+"')";
		return jdbcDao.update(sql);
	}
	public int queryUserCount(String openId){
		Map<String, Object> map=jdbcDao.queryForMap("select count(*) as COUNT from  user where wxid=?",new String[]{openId});
		return Integer.parseInt(map.get("COUNT").toString());
	}
	
	
	
	
}

