package com.system.core.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
/**
 * 对象传值的参数绑定处理
 * @author lee
 *
 */
public class BeanArgumentResolver implements WebArgumentResolver {
	private static final Logger logger = LoggerFactory.getLogger(BeanArgumentResolver.class);
	@SuppressWarnings("rawtypes")
	public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception{
		RequestBean requestBean = param.getParameterAnnotation(RequestBean.class);
		/*try{*/
			if (requestBean != null) {
				String _param = requestBean.value();
				if (_param.equals("_def_param_name")) {
					_param = param.getParameterName();
				}
				Class clazz = param.getParameterType();
				Object object = clazz.newInstance();
				HashMap<String, String[]> paramsMap = new HashMap<String, String[]>();
				Iterator<String> itor = request.getParameterNames();
				while (itor.hasNext()) {
					String webParam = (String) itor.next();
					String[] webValue = request.getParameterValues(webParam);
					List<String> webValueList = new ArrayList<String>();
					for(int i = 0;i<webValue.length;i++){
						if(webValue[i]!=null&&!"".equals(webValue[i])){
							webValueList.add(webValue[i]);
						}
					}
					if (webParam.startsWith(_param)&&!webValueList.isEmpty()) {
						paramsMap.put(webParam, webValueList.toArray(new String[webValueList.size()]));
					}
				}
				BeanWrapper obj = new BeanWrapperImpl(object);
				//obj.findCustomEditor(paramClass, paramString)
				obj.registerCustomEditor(Date.class, null, new FlameDateEditor());
				//WebDataBinder.
				logger.info(obj.findCustomEditor(Date.class, null).getAsText());
				Iterator it = paramsMap.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry) it.next(); 
					String propName=entry.getKey().toString(); 
					Object propVals = paramsMap.get(propName);
					String[] props = propName.split("\\.");
					if (props.length == 2) {
						obj.setPropertyValue(props[1], propVals);
					} else if (props.length == 3) {
						Object tmpObj = obj.getPropertyValue(props[1]);
						if (tmpObj == null)
							obj.setPropertyValue(props[1], obj.getPropertyType(props[1]).newInstance());
						obj.setPropertyValue(props[1] + "." + props[2], propVals);
					}

				}
				return object;
			}
		/*}catch(Exception e){
			e.printStackTrace();
			
		}*/
		return WebArgumentResolver.UNRESOLVED;
	}
}