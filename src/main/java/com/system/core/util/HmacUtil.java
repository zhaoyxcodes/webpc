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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
public class HmacUtil {
	//img path
	public static final String path="//home//img";
	
	public static final String tokenKey="tokenKey";
	//微信支付的商户id
	public static final String mch_id = "";
	//微信支付的商户密钥
	public static final String key = "";
	//支付成功后的服务器回调url
	public static final String notify_url = "https://zhao/pay/wxNotify";
	//签名方式，固定值
	public static final String SIGNTYPE = "MD5";
	//交易类型，小程序支付的固定值为JSAPI
	public static final String TRADETYPE = "JSAPI";
	
		
	public  static final String APPID = "wx606ec8b416c01260";
	public static final String SECRET = "6a40c709e9eb56f2881db739ef98ff7a";
	
	private static String accesstoken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;

	private static String model_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

	public static String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static Map<String, Object> wxPay(String openid, HttpServletRequest request) {
		try { // 生成的随机字符串
			String nonce_str = getRandomStringByLength(32);
			// 商品名称
			String body = "测试商品名称";
			// 获取客户端的ip地址
			String spbill_create_ip =getIpAddr(request);
			// 组装参数，用户生成统一下单接口的签名
			Map<String, String> packageParams = new HashMap<String, String>();
			packageParams.put("appid", APPID);
			packageParams.put("mch_id", mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", "123456789");
			// 商户订单号
			packageParams.put("total_fee", "1");// 支付金额，这边需要转成字符串类型，否则后面的签名会失败
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);// 支付成功后的回调地址
			packageParams.put("trade_type", TRADETYPE);// 支付方式
			packageParams.put("openid", openid);
			String prestr = createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			// MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
			String mysign = sign(prestr, key, "utf-8")
					.toUpperCase();
			// 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
			String xml = "<xml>" + "<appid>" + APPID+ "</appid>"
					+ "<body><![CDATA[" + body + "]]></body>" + "<mch_id>"
					+ mch_id + "</mch_id>" + "<nonce_str>"
					+ nonce_str + "</nonce_str>" + "<notify_url>"
					+ notify_url + "</notify_url>" + "<openid>"
					+ openid + "</openid>" + "<out_trade_no>" + "123456789"
					+ "</out_trade_no>" + "<spbill_create_ip>"
					+ spbill_create_ip + "</spbill_create_ip>" + "<total_fee>"
					+ "1" + "</total_fee>" + "<trade_type>"
					+ TRADETYPE + "</trade_type>" + "<sign>"
					+ mysign + "</sign>" + "</xml>";
			System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);
			// 调用统一下单接口，并接受返回的结果
			String result = httpRequest(pay_url, "POST",
					xml);
			System.out.println("调试模式_统一下单接口 返回XML数据：" + result);
			// 将解析结果存储在HashMap中
			Map map = doXMLParse(result);
			String return_code = (String) map.get("return_code");// 返回状态码
			Map<String, Object> response = new HashMap<String, Object>();// 返回给小程序端需要的参数
			if (return_code == "SUCCESS" || return_code.equals(return_code)) {
				String prepay_id = (String) map.get("prepay_id");// 返回的预付单信息
				response.put("nonceStr", nonce_str);
				response.put("package", "prepay_id=" + prepay_id);
				Long timeStamp = System.currentTimeMillis() / 1000;
				response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
				// 拼接签名需要的参数
				String stringSignTemp = "appId=" + APPID
						+ "&nonceStr=" + nonce_str + "&package=prepay_id="
						+ prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
				// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
				String paySign = sign(stringSignTemp, key,
						"utf-8").toUpperCase();
				response.put("paySign", paySign);
			}
			response.put("appid", APPID);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// 获取token
	public static String accessToken() {
		String tokenval=RedisUtil.getToken(tokenKey);
		System.out.println(tokenKey+"缓存"+tokenval);
		if(tokenval!=null&&tokenval.length()>0){
			return tokenval;
		}
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				accesstoken_url, HttpMethod.GET, null, String.class);
		if (responseEntity != null
				&& responseEntity.getStatusCode() == HttpStatus.OK) {
			String sessionData = responseEntity.getBody();
			JSONObject jsonObj = JSON.parseObject(sessionData);
			String access_token = jsonObj.getString("access_token");
			int expires_in = jsonObj.getIntValue("expires_in");
			
			if(access_token!=null&&access_token.length()>0&&expires_in>0){
				RedisUtil.setToken(tokenKey, access_token,expires_in);
			}
			System.out.println(access_token+"//"+expires_in);
			return access_token;
		}
		return null;
	}

	// 发送模板
	public static void sendModel(JSONObject jsonObj) {
		RestTemplate restTemplate = new RestTemplate();
		String model_url2 = model_url + accessToken();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType
				.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> formEntity = new HttpEntity<String>(
				jsonObj.toString(), headers);
		String x = restTemplate.postForEntity(model_url2, formEntity,
				String.class).getBody();
		JSONObject jxObj = JSON.parseObject(x);
		System.out.println(x);
		if("40001".equals(jxObj.getString("errcode"))||"42001".equals(jxObj.getString("errcode"))){//如果请求失败 重新请求
			RedisUtil.delToken(tokenKey);
			sendModel(jsonObj);
		}
		

	}

	public static String SHA1(String str) {
		try {
			// 指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			// 获取字节数组
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
	/**	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。	 
	 */	
	public static Map doXMLParse(String strxml) throws Exception {		
		if(null == strxml || "".equals(strxml)) {			
			return null;		
		}				
		Map m = new HashMap();		
		InputStream in = new ByteArrayInputStream(strxml.getBytes());		
		SAXBuilder builder = new SAXBuilder();

        // 这是优先选择. 如果不允许DTDs (doctypes) ,几乎可以阻止所有的XML实体攻击
        String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
        builder.setFeature(FEATURE, true);

        FEATURE = "http://xml.org/sax/features/external-general-entities";
        builder.setFeature(FEATURE, false);

        FEATURE = "http://xml.org/sax/features/external-parameter-entities";
        builder.setFeature(FEATURE, false);

        FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        builder.setFeature(FEATURE, false);

        Document doc = builder.build(in);
        Element root = doc.getRootElement();

        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            
            m.put(k, v);
        }
        
        //关闭流
        in.close();
        
        return m;
	}
	/**	 * 获取子结点的xml	 * @param children	 * @return String	 */	
	public static String getChildrenText(List children) {		
		StringBuffer sb = new StringBuffer();		
		if(!children.isEmpty()) {			
			Iterator it = children.iterator();			
			while(it.hasNext()) {				
				Element e = (Element) it.next();				
				String name = e.getName();				
				String value = e.getTextNormalize();				
				List list = e.getChildren();				
				sb.append("<" + name + ">");				
				if(!list.isEmpty()) {					
					sb.append(getChildrenText(list));				
				}				
				sb.append(value);				
				sb.append("</" + name + ">");			
			}		
		}				
		return sb.toString();	
	}

	 /**       *       * @param requestUrl请求地址       * @param requestMethod请求方法       * @param outputStr参数       */       
	public static String httpRequest(String requestUrl,String requestMethod,String outputStr){           
		// 创建SSLContext           
		StringBuffer buffer = null;           
		try{   	        
			URL url = new URL(requestUrl);   	        
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();   	        
			conn.setRequestMethod(requestMethod);   	        
			conn.setDoOutput(true);   	        
			conn.setDoInput(true);   	       
			conn.connect();   	        
			//往服务器端写内容   	        
			if(null !=outputStr){   	            
				OutputStream os=conn.getOutputStream();   	            
				os.write(outputStr.getBytes("utf-8"));   	            
				os.close();   	        
			}   	        
			// 读取服务器端返回的内容   	        
			InputStream is = conn.getInputStream();   	        
			InputStreamReader isr = new InputStreamReader(is, "utf-8");   	        
			BufferedReader br = new BufferedReader(isr);   	        
			buffer = new StringBuffer();   	        
			String line = null;   	        
			while ((line = br.readLine()) != null) {   	        	
				buffer.append(line);   	        
			}
			br.close();        
		}catch(Exception e){               
			e.printStackTrace();           
			}       
		return buffer.toString();    
	}
	 /**       * 签名字符串       * @param text需要签名的字符串       * @param key 密钥       * @param input_charset编码格式       * @return 签名结果       */      
	public static String sign(String text, String key, String input_charset) {           
		text = text + "&key=" + key;           
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));       
	}
	 public static byte[] getContentBytes(String content, String charset) {           
		 if (charset == null || "".equals(charset)) {               
			 return content.getBytes();           
		  }           
		 try {               
			 return content.getBytes(charset);           
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);           
			}       
	} 
	 /**       * 除去数组中的空值和签名参数       * @param sArray 签名参数组       * @return 去掉空值与签名参数后的新签名参数组       */      
	 public static Map<String, String> paraFilter(Map<String, String> sArray) {           
		 Map<String, String> result = new HashMap<String, String>();           
		 if (sArray == null || sArray.size() <= 0) {               
			 return result;           
		}           
		 for (String key : sArray.keySet()) {               
			 String value = sArray.get(key);               
			 if (value == null || value.equals("") || key.equalsIgnoreCase("sign")                       
					 || key.equalsIgnoreCase("sign_type")) {                   
				 continue;               
			 }               
			 result.put(key, value);           
		}           
		 
		 return result;       
	}

	
	public static String stringPoitGEO(String strpoit) {
		String geom = "";
		if (strpoit != null && strpoit.length() > 0
				&& strpoit.indexOf(",") != -1) {
			String[] laglog = strpoit.split(",");
			geom = "geomfromtext('point(" + laglog[0] + " " + laglog[1] + ")')";
		}
		return geom;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	/**	 * StringUtils工具类方法	 * 获取一定长度的随机字符串，范围0-9，a-z	 
	 * * @param length：指定字符串长度	 
	 * * @return 一定长度的随机字符串	 */	
	public static String getRandomStringByLength(int length) {        
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";        
		Random random = new Random();        
		StringBuffer sb = new StringBuffer();        
		for (int i = 0; i < length; i++) {            
			int number = random.nextInt(base.length());            
			sb.append(base.charAt(number));        
		}        
		return sb.toString();
	}
	/**   
	 * * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串      
	 *  * @param params 需要排序并参与字符拼接的参数组      
	 *   * @return 拼接后字符串      
	 *    */ 
	public static String createLinkString(Map<String, String> params) {           
		List<String> keys = new ArrayList<String>(params.keySet());           
		Collections.sort(keys);           
		String prestr = "";           
		for (int i = 0; i < keys.size(); i++) {               
			String key = keys.get(i);               
			String value = params.get(key);               
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符                  
				prestr = prestr + key + "=" + value;               
			} else {                   
				prestr = prestr + key + "=" + value + "&";               
			}           
		}           
	return prestr;       
	
	}  

	/**	 * IpUtils工具类方法	 * 获取真实的ip地址	 * @param request	 * @return	 */
	public static String getIpAddr(HttpServletRequest request) {		
		String ip = request.getHeader("X-Forwarded-For");	    
		if(!getStringNull(ip) && !"unKnown".equalsIgnoreCase(ip)){	         
			//多次反向代理后会有多个ip值，第一个ip才是真实ip	    	
			int index = ip.indexOf(",");	        
			if(index != -1){	            
				return ip.substring(0,index);	        
			}else{	            
				return ip;	        
			}	   
		}	    
		ip = request.getHeader("X-Real-IP");	    
		if(!getStringNull(ip) && !"unKnown".equalsIgnoreCase(ip)){	      
			return ip;	   
		}	    
		return request.getRemoteAddr();	
	}

	public static boolean getStringNull(Object obj) {
		if ("null".equals(obj) ||obj == null || obj.toString().length() <= 0
				|| obj.toString().isEmpty()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
	}
}
