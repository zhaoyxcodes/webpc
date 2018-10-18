/**
 * Project Name:myHome
 * File Name:LoginService.java
 * Package Name:com.home.core.service
 * Date:2018-8-8上午11:28:24
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.home.core.dao.JdbcDao;
import com.home.core.dao.LoginDao;
import com.home.core.entity.User;
import com.system.core.util.HmacUtil;
import com.system.core.util.RedisUtil;
import com.system.core.util.ResponseValue;

/**
 * ClassName:com.home.core.service.LoginService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018-8-8 上午11:28:24 <br/>
 * 
 * @author yuanxu.zhao
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
@Transactional(readOnly = true)
public class LoginService {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginService.class);
	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private LoginDao loginDao;

	public Map<String, Object>  queryuser(String id){
		String querysql="select * from user where  `id`='"+id+"'";
		List<Map<String, Object>> list=jdbcDao.queryForList(querysql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Transactional
	public String get3drSessionKey(String code, String name, String img,String secret) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
				+ HmacUtil.APPID + "&secret=" + secret + "&js_code=" + code
				+ "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET, null, String.class);
		if (responseEntity != null
				&& responseEntity.getStatusCode() == HttpStatus.OK) {
			String sessionData = responseEntity.getBody();
			System.out.println(sessionData);
			logger.info(sessionData);
			JSONObject jsonObj = JSON.parseObject(sessionData);
			String openId = jsonObj.getString(ResponseValue.USER_OPENID);
			String sessionKey = jsonObj
					.getString(ResponseValue.USER_SESSION_KEY);
			// 加密3drsessionid
			String signature = HmacUtil.SHA1(openId + sessionKey);
			int num = loginDao.queryUserCount(openId);
			if (num <= 0) {
				String[] user1 = { HmacUtil.getUUID(), name, openId, img };
				int num1 = loginDao.insertUser(user1);
				if (num1 <= 0) {
					logger.error(name + ":insert user error !");
					return ResponseValue.IS_ERROR;
				}
			}
			User user = loginDao.getUserByOpenId(openId);
			// 保存到缓存
//			RedisUtil.setOpenId(signature, user);
			return "{\"" + ResponseValue.USER_SESSION_KEY + "\":\"" + signature
					+ "\",\"" + ResponseValue.USER_NAME + "\":\""
					+ user.getUsername() + "\",\"" + ResponseValue.USER_IMG
					+ "\":\"" + user.getImg() + "\",\"" + ResponseValue.USER_OPENID
					+ "\":\"" + openId + "\",\"" + ResponseValue.USER_ID
					+ "\":\"" + user.getId() + "\",\"" + ResponseValue.USER_ISADMIN
					+ "\":\"" + user.getIsadmin() + "\"}";

		}
		return ResponseValue.IS_ERROR;
	}
}
