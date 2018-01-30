package com.tos.test;

import android.util.Log;

/**
 * 系统工具类
 * @author xiaoCheng
 *
 */
public class SystemUtil {

	/**
	 * 判断是否为linux系统
	 * @return
	 */
	public static boolean systemIsLinux(){
		String osName = System.getProperties().getProperty("os.name");
		Log.e("osName",osName);
		if("Windows 7".equals(osName)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("os name:"+System.getProperties().getProperty("os.name"));
	}
}
