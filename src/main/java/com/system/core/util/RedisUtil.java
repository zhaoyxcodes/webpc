/**
 * Project Name:home
 * File Name:RedisUtil.java
 * Package Name:com.system.core.util
 * Date:2018-8-10上午10:12:32
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.system.core.util;

import redis.clients.jedis.Jedis;

import com.home.core.entity.User;

/**
 * ClassName:com.system.core.util.RedisUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-8-10 上午10:12:32 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class RedisUtil {

	private static  Jedis jedis;

    static {
        //连接服务器
        jedis = new Jedis("127.0.0.1",6379);
        //权限认证
//        jedis.auth("");
    }
    public static void setToken(String key,String value,int min){
    	jedis.del(key);
    	if(min<=0){
    		min=7200;//2小时
    	}
    	jedis.set(key, value);
    	jedis.expire(key, min);
    }
    public static String getToken(String key){
        return jedis.get(key);
    }
    public static void delToken(String key){
         jedis.del(key);
    }
    public static String setOpenId(String drSessionId,User user) {
    	jedis.del(drSessionId.getBytes());
        return jedis.set(drSessionId.getBytes(), SerializeUtil.serialize(user));
    }
    public static User getOpenId(String drSessionId){
    	 return (User)SerializeUtil.unserialize(jedis.get(drSessionId.getBytes()));
    }
    //1是成功 0是失败
    public static Long delOpenId(String drSessionId){
    	return jedis.del(drSessionId.getBytes());
    }
    public static void main(String[] args) {
    	System.out.println(RedisUtil.delOpenId("555"));
	}
}

