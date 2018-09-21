/**
 * File Name:FaceValidate.java
 *
*/
package com.system.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:com.dc.messagecenter.webservice.template.FaceValidate <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018-2-1 ????3:23:44 <br/>
 * @author   yuanxu.zhao
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class FaceValidate {

	//图片目录
	public   String  img_dirpath="";
	//原图保存地址
	public static String originalImg="IMG";
	//保存原图裁剪的脸部
	public static String faceDir="FACE";
	//保存校验图片的地址
	public static String validateDir="VALIDATE";
	//python 程序目录
	public  static String  dirpath="C:/Users/yuanxu.zhao/Desktop/face/facenet-master/src/";
	public static String pix="_BAK";
	/**
	 * 比较2张图片是否一致
	 * @author yuanxu.zhao
	 * @param faceimg1
	 * @param faceimg2
	 * @return
	 * @throws IOException 
	 * @since JDK 1.7
	 */
	public  List<String> getIsReadFace(String faceimg2) throws IOException{
		
		String ytface=img_dirpath+"/"+faceDir;
		String dir="python "+dirpath+"newface.py  "+faceimg2+" "+ytface;
		List<String> listurl=new ArrayList<String>();
		try {
			System.out.println(dir);
            Process process = Runtime.getRuntime().exec(dir);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            BufferedReader input = new BufferedReader(ir);
           
            String str="";
            while(( str=input.readLine())!=null){
            	int num1=str.lastIndexOf("/");
            	String ytpath=img_dirpath+"/"+originalImg+str.substring(num1);
            	listurl.add(ytpath);
            	System.out.println(ytpath);
            }
           
            input.close();
            ir.close();
            process.destroy();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		
		return listurl;
	}
	/**
	 * python set inputimg outputimg
	 * @author yuanxu.zhao
	 * @param inputimg
	 * @return
	 * @since JDK 1.7
	 */
	public  String setOtherPeopleFace(String inputimg,String facepath){
		if(inputimg.indexOf(".")!=-1&&facepath.indexOf(".")!=-1){
			System.out.println(inputimg);
			System.out.println(facepath);
			String dir="python "+dirpath+"set_other_people.py "+inputimg+" "+facepath;
			String result="";
			try {
	            Process process = Runtime.getRuntime().exec(dir);
	            InputStreamReader ir = new InputStreamReader(process.getInputStream());
	            LineNumberReader input = new LineNumberReader(ir);
	            result = input.readLine();
	            input.close();
	            ir.close();
	            process.destroy();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
			if(result!=null&&result.length()>0&&"true".equals(result.trim())){
				return facepath;
			}
			
		}
		return null;
		
	}

	public String getImg_dirpath() {
		return img_dirpath;
	}
	public void setImg_dirpath(String img_dirpath) {
		this.img_dirpath = img_dirpath;
	}
	public static void main(String[] args) {
		FaceValidate f=new FaceValidate();
		f.img_dirpath="/home/facefiles";
		try {
		f.getIsReadFace("C:/Users/yuanxu.zhao/Desktop/py/my_faces/4.jpg");
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
}

