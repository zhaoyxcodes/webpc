/**
 * Project Name:pc
 * File Name:TaskUpdate.java
 * Package Name:com.system.core.util
 * Date:2018-10-19下午3:31:52
 * Copyright (c) 2018, 神州数码 All Rights Reserved.
 *
*/

package com.system.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.home.core.dao.JdbcDao;

/**
 * 每天凌晨1点
 * ClassName:com.system.core.util.TaskUpdate <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-10-19 下午3:31:52 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TaskUpdate  extends QuartzJobBean {
    
    @Autowired
	private JdbcDao jdbcDao;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar curr = Calendar.getInstance();
    	String dqdate=sdf.format(curr.getTime());
    	jdbcDao=(JdbcDao)ContextLoader.getCurrentWebApplicationContext().getBean("jdbcDao");
    	String querysql="update  car set startdate=CONCAT("+dqdate+",' ',DATE_FORMAT(startdate,'%H:%i')) where status=1  and everyday=1";
    	//date_add(startdate,interval 1 day)
    	jdbcDao.update(querysql);
    	
    }
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application-root.xml");

    }
}
