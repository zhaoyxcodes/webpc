/**
 * Project Name:myHome
 * File Name:HmacUtil.java
 * Package Name:com.system.core.util
 * Date:2018-8-8下午1:21:02
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.system.core.util;
/**
 * ClassName:com.system.core.util.HmacUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-8-8 下午1:21:02 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
 
public class HmacUtil {
 
	private static String accesstoken_url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx606ec8b416c01260&secret=0f5134082241f9f9dca9c8f397d952f2";
	
	private static String model_url="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
	
	public static String accessToken(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(accesstoken_url,
				HttpMethod.GET, null, String.class);
		if (responseEntity != null
				&& responseEntity.getStatusCode() == HttpStatus.OK) {
			String sessionData = responseEntity.getBody();
			JSONObject jsonObj = JSON.parseObject(sessionData);
			String access_token = jsonObj.getString("access_token");
			System.out.println(access_token);
			return access_token;
		}
		return null;
	}
	public static void sendModel(JSONObject jsonObj){
		RestTemplate restTemplate = new RestTemplate();
		model_url=model_url+accessToken();
		HttpHeaders headers = new HttpHeaders();
	    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
	    headers.setContentType(type);
	    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
	    HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
	     String x=restTemplate.postForEntity(model_url,formEntity,
	            String.class).getBody();
	     System.out.println(x);
		
		
	}
    public static String SHA1(String str){  
        try {  
            //指定sha1算法  
            MessageDigest digest = MessageDigest.getInstance("SHA-1");  
            digest.update(str.getBytes());  
            //获取字节数组  
            byte messageDigest[] = digest.digest();  
            // Create Hex String  
            StringBuffer hexString = new StringBuffer();  
            // 字节数组转换为 十六进制 数  
            for (int i = 0; i < messageDigest.length; i++) {  
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
                if (shaHex.length() < 2) {  
                    hexString.append(0);  
                }  
                hexString.append(shaHex);  
            }  
            return hexString.toString().toLowerCase();  
  
        } catch (NoSuchAlgorithmException e) {  
           return "";
        }  
    }  
    
    public static String stringPoitGEO(String strpoit){
    	String geom="";
    	if(strpoit!=null&&strpoit.length()>0&&strpoit.indexOf(",")!=-1){
    		String[] laglog=strpoit.split(",");
    		geom="geomfromtext('point("+laglog[0]+" "+laglog[1]+")')";
    	}
    	return geom;
    }
    public static String getUUID(){
    	return UUID.randomUUID().toString().replace("-", "");
    }
    public static boolean getStringNull(Object obj){
    	if(obj==null||obj.toString().length()<=0||obj.toString().isEmpty()){
    		return true;
    	}
    	return false;
    }
    public static void main(String[] args) {
	}
}

