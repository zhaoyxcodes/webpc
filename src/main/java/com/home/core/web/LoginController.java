/**
 * Project Name:myHome
 * File Name:LoginController.java
 * Package Name:com.myhome.core.web
 * Date:2018-8-8上午11:25:43
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
 */

package com.home.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.core.service.LoginService;

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
@RequestMapping("/login")
public class LoginController {

	@Autowired(required = false)
	private LoginService loginService;

	@RequestMapping(value = "/get3drSessionKey", method=RequestMethod.POST)
	@ResponseBody
	public String get3drSessionKey(@RequestParam("code") String code,@RequestParam("name") String name
			,@RequestParam("img") String img)
			throws Exception {
		System.out.println(code);
		if (code != null && code.length() > 0&&name != null && name.length() > 0
				&&img != null && img.length() > 0) {
			return loginService.get3drSessionKey(code,name,img);
		} else {
			return "";
		}
	}
}
