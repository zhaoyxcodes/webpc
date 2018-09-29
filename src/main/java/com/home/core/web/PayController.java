/**
 * Project Name:myHome
 * File Name:LoginController.java
 * Package Name:com.myhome.core.web
 * Date:2018-8-8上午11:25:43
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.core.util.HmacUtil;

/**
 * ClassName:com.myhome.core.web.LoginController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018-8-8 上午11:25:43 <br/>
 * 
 * @author yuanxu.zhao
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	@RequestMapping(value="/order")    
	@ResponseBody    
	public Map<String, Object> pay(HttpServletRequest request,String openid) throws Exception{  
		return HmacUtil.wxPay(openid,request);
	}
	@RequestMapping(value="/wxNotify")    
	@ResponseBody    
	public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{        
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));        
		String line = null;        
		StringBuilder sb = new StringBuilder();        
		while((line = br.readLine()) != null){            
			sb.append(line);        
		}        
		br.close();        
		//sb为微信返回的xml        
		String notityXml = sb.toString();        
		String resXml = "";        
		System.out.println("接收到的报文：" + notityXml);         
		Map map = HmacUtil.doXMLParse(notityXml);         
		String returnCode = (String) map.get("return_code");        
		if("SUCCESS".equals(returnCode)){            
			//验证签名是否正确           
			Map<String, String> validParams = HmacUtil.paraFilter(map);  
			//回调验签时需要去除sign和空值参数           
			String validStr = HmacUtil.createLinkString(validParams);
			//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串            
			String sign = HmacUtil.sign(validStr, HmacUtil.key, "utf-8").toUpperCase();
			//拼装生成服务器端验证的签名            
			//根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等           
			if(sign.equals(map.get("sign"))){                
				/**此处添加自己的业务逻辑代码start**/                  /**此处添加自己的业务逻辑代码end**/                
				//通知微信服务器已经支付成功                
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"                        
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";            
			}        
			
		}else{           
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"                    
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";        
		}        
		System.out.println(resXml);        
		System.out.println("微信支付回调数据结束");          
		BufferedOutputStream out = new BufferedOutputStream(              
				response.getOutputStream());        
		out.write(resXml.getBytes());        
		out.flush();        
		out.close();    
		}

}
