package com.home.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;



public class JdbcDao  extends JdbcTemplate{


	private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);
	
	public List<T> queryForObjectList(String sql, String[] args,Class ObjectClass){
		try{
			Collection<T> results = this.query(sql, args, new RowMapperResultSetExtractor<T>(new BeanPropertyRowMapper<T>(ObjectClass), 1));
			return  new ArrayList(results);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	
	
	/**
	 * 根据属性删除表记录
	 * @Methods Name removeByField
	 * @Create In 2012-2-8 By lee
	 * @param tableName
	 * @param fieldName
	 * @param fieldValue void
	 */
	public void removeByField(String tableName, String fieldName, Object fieldValue) {
		String sql = "DELETE FROM "+tableName+" WHERE "+fieldName+"=?";
		this.update(sql, new Object[]{fieldValue});
	}
	
	/**
	 * 新增实体
	 * @param tableName 表名
	 * @param map 
	 * @return
	 */
	public Integer insertObject(String tableName,Map<String, Object> map){
	   	if(map==null||map.size()==0){
	   		return 0;
	   	}
	   	StringBuffer sql=new StringBuffer();
	   	sql.append("insert into ").append(tableName).append("(");
	   	StringBuffer keySql=new StringBuffer();
	   	StringBuffer valSql=new StringBuffer();
	   	Object[] obj=new Object[map.size()];
	   	Iterator it=map.entrySet().iterator();
	   	int i=0;
	   	while(it.hasNext()){
	   		Map.Entry entry = (Map.Entry) it.next(); 
			String key=entry.getKey().toString();
	   	     if(keySql.length()==0){
	   	    	 keySql.append(key);
	   	     }else{
	   	    	 keySql.append(",").append(key);
	   	     }
	   	     
	   	     if(valSql.length()==0){
	   		    valSql.append("?");
	   	     }else{
	   	    	valSql.append(",?");
	   	     }
	   	     obj[i]=map.get(key);
	   	     i++;
	   	}
	   	sql.append(keySql.toString()).append(")");
	   	sql.append(" values(");
	   	sql.append(valSql.toString());
	   	sql.append(")");
	   	logger.info(sql.toString());
	   	return this.update(sql.toString(), obj);
	}
	public static String getSQlKeyWord(String col){
		return "`"+col.toUpperCase()+"`";
	}
	/**
	 * 更新实体
	 * @param tableName
	 * @param where
	 * @param map
	 * @return
	 */
	public Integer updateObject(String tableName,Map<String,Object> where,Map<String, Object> map){
	   	if(map==null||map.size()==0){
	   		return 0;
	   	}
	   	StringBuffer sql=new StringBuffer();
	   	sql.append("update ").append(tableName).append(" set ");
	   	StringBuffer keySql=new StringBuffer();
	   	Object[] obj=null;
	   	 if(where!=null&&where.size()>0){
	   	   obj=new Object[map.size()+where.size()];
	   	 }else{
	   		obj=new Object[map.size()]; 
	   	 }
	   	Iterator it = map.entrySet().iterator();
	   	int i=0;
	   	while(it.hasNext()){
	   		Map.Entry entry = (Map.Entry) it.next(); 
			String key=entry.getKey().toString(); 
	   	     if(keySql.length()==0){
	   	    	 keySql.append(this.getSQlKeyWord(key)).append("=?");
	   	     }else{
	   	    	 keySql.append(",").append(this.getSQlKeyWord(key)).append("=?");
	   	     }
	   	     obj[i]=map.get(key);
	   	     i++;
	   	}
	   	sql.append(keySql.toString());
	   	if(where!=null&&where.size()>0){
	   		sql.append(" where 1=1");
	     	StringBuffer whereSql=new StringBuffer();
	     	Iterator whereIt=where.entrySet().iterator();
		    
		    while(whereIt.hasNext()){
		    	 Map.Entry entry = (Map.Entry) whereIt.next(); 
				 String key=entry.getKey().toString(); 
		    	 whereSql.append(" and ").append(this.getSQlKeyWord(key)).append("=?");
		    	 obj[i]=where.get(key);
		    	 i++;
		    }
		   sql.append(whereSql.toString()); 
	   	}
	   	logger.info("sql11=="+sql.toString());
	   	
	   	return this.update(sql.toString(), obj);
	   	
	}
}
