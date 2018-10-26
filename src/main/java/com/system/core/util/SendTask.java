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
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.home.core.dao.JdbcDao;

/**
 * 1分钟执行一次
 * ClassName:com.system.core.util.SendTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-10-19 下午3:31:52 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class SendTask  extends QuartzJobBean {
    
    @Autowired
	private JdbcDao jdbcDao;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");;
    	Calendar curr = Calendar.getInstance();
    	String dqdate=sdf.format(curr.getTime());
    	int h=curr.get(Calendar.HOUR_OF_DAY);
    	//提前1小时给客户发送消息状态
    	jdbcDao=(JdbcDao)ContextLoader.getCurrentWebApplicationContext().getBean("jdbcDao");
    	String[] querysql=new String[2];
    	 querysql[0]="update reservation set ismsg=1 where DATE_FORMAT(startdate,'%Y-%m-%d')='"+dqdate+"' and `status` =1 " +
    			"and (DATE_FORMAT(startdate,'%H')-1)="+h+" and ismsg=0";
    	//把小于当前时间的更新为 过期
    	 querysql[1]="update reservation set status=6 where startdate<STR_TO_DATE('"+sdf2.format(curr.getTime())+"','%Y-%m-%d %H:%i') and `status` in (0,1) ";
    	jdbcDao.batchUpdate(querysql);
    	
    	
    	
    	
    }
}
