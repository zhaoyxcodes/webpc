package com.home.core.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.home.core.service.FilmService;
import com.system.core.util.HmacUtil;

@Controller
@RequestMapping("/film")
public class FilmController {

	@Autowired
	private FilmService filmService;
	@RequestMapping("/seachFilm")
	@ResponseBody
	public   List<Map<String, Object>>  seachFilm(String type,String pageNo,String pageSize,String title)  {
		if ( HmacUtil.getStringNull(type)||HmacUtil.getStringNull(pageNo)||HmacUtil.getStringNull(pageSize)) {
			System.out.println("seachFilm is null..");
		} else {
			return filmService.seachFilm(type, Integer.valueOf(pageNo), Integer.valueOf(pageSize), title);
			
		}
		return null;
	}
	
}
