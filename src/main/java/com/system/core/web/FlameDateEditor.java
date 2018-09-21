package com.system.core.web;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;




public class FlameDateEditor extends PropertyEditorSupport {
	private static String datePattern = "yyyy-MM-dd";	//日期格式
	private static String timePattern = "yyyy-MM-dd HH:mm:ss";	//时间格式
	
	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			setValue(null);
		}else if (text != null && text.length()!=datePattern.length() && text.length() != timePattern.length()) {
			throw new IllegalArgumentException(
					"不能将["+text+"]正常转为时间或日期格式");
		}else {
			try {
				setValue(text);//DateUtils.convertStringToDate()
			}catch (Exception ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		SimpleDateFormat sdf=new SimpleDateFormat(datePattern);
		return (value != null ?sdf.format(value) : "");
	}
}
