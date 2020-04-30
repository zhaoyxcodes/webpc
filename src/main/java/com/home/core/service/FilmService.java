package com.home.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.home.core.dao.JdbcDao;

@Component
@Transactional(readOnly = true)
public class FilmService {
	@Autowired
	private JdbcDao jdbcDao;
	
	public List<Map<String, Object>> seachFilm(String type,int pageNo,int pageSize,String title) {
		String querysql = "select id,imgname,imgurl,actor,sketch,bdurl,bdpwd,type,DATE_FORMAT(cdate,'%Y-%m-%d %H:%i')as credate,cdate from film " ;
		String where="";
		if(!"all".equals(type)){
			where+= " type='"+type+"'";
		}
		if(title.trim().length()>0){
			if(where.length()>0){
				where+=" and ";
			}
			where+= " imgname like'%"+title+"%'";
		}
		if(where.length()>0){
			querysql+=" where "+where;
		}
		
		querysql+=" order by cdate desc,id asc   LIMIT "+((pageNo-1)*pageSize)+","+pageSize;
		System.out.println(querysql);
		return jdbcDao.queryForList(querysql);
	}
}
