/**
 * Project Name:home
 * File Name:GoodsController.java
 * Package Name:com.home.core.web
 * Date:2018-8-21上午10:01:51
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.web;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.home.core.service.HomeService;
import com.system.core.util.HmacUtil;
import com.system.core.util.ResponseValue;

/**
 * ClassName:com.home.core.web.GoodsController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018-8-21 上午10:01:51 <br/>
 * 
 * @author yuanxu.zhao
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private HomeService homeService;
	@RequestMapping("/carvimg")
	@ResponseBody
	public   String carvimg(MultipartHttpServletRequest request,String userid)  {
		try {
			if (HmacUtil.getStringNull(userid)){
				throw new Exception("carvimg is null..");
			}
			String filename="license//"+HmacUtil.getUUID()+".jpg";
			String filepath =HmacUtil.path+"//"+filename;
			Iterator<String> itr =  request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			File targetFile = new File(filepath);  
		    if(!targetFile.exists()){ 
		        boolean succ=targetFile.mkdirs(); 
		    }
			file.transferTo(targetFile);
			return homeService.insertdfile(userid,filename);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return ResponseValue.IS_ERROR;
	}
	@RequestMapping("/getImage")
	@ResponseBody
	public   void getImage(HttpServletRequest request,HttpServletResponse response)  throws Exception{
		String url=request.getParameter("filename");
		System.out.println(url);
		FileInputStream inputStream = new FileInputStream(new File(HmacUtil.path+"//"+url) );
		
        RenderedImage image = ImageIO.read(inputStream);
        response.setContentType("image/jpeg");//设置后网页中才能显示出图片的后缀
        ImageIO.write(image, "jpg", response.getOutputStream());
        inputStream.close();
	}
	
	@RequestMapping(value = "/insertCertification", method = RequestMethod.POST)
	@ResponseBody
	public String insertCertification(String carbrand,String carcolor,String id,String userid,String name,String peoplenum,String phone,String carnum,String carlicense,String driverlicense)  {
		try {
			if (HmacUtil.getStringNull(userid)
					|| HmacUtil.getStringNull(name)
					|| HmacUtil.getStringNull(carbrand)
					|| HmacUtil.getStringNull(carcolor)
					|| HmacUtil.getStringNull(peoplenum)
					|| HmacUtil.getStringNull(phone)
					|| HmacUtil.getStringNull(carnum)
					|| HmacUtil.getStringNull(carlicense)
					|| HmacUtil.getStringNull(driverlicense)) {
				System.out.println(" insertCertification is null..");
			} else {
				return homeService.insertCertification(id,userid,name,peoplenum,phone,carnum,carlicense,driverlicense, carbrand, carcolor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/updateCertificationstatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateCertificationstatus(String status,String id,String remark)  {
		try {
			if (HmacUtil.getStringNull(status)
					|| HmacUtil.getStringNull(id)) {
				System.out.println(" updateCertificationstatus is null..");
			} else {
				return homeService.updateCertificationstatus( status, id, remark);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseValue.IS_ERROR;
	}
	@RequestMapping(value = "/querycertificationByUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querycertificationByUser(String userid,String isstatus)  {
		try {
			if (HmacUtil.getStringNull(userid)) {
				System.out.println(" querycertificationByUser is null..");
			} else {
				if(isstatus!=null&&"1".equals(isstatus)){
					return homeService.queryCertificationByUserStatus(userid);
				}else{
					return homeService.queryCertificationByUser(userid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/querycertification", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> querycertification()  {
		try {
			return homeService.queryCertification();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/querycertificationById", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> querycertificationById(String id)  {
		try {
			return homeService.querycertificationById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
