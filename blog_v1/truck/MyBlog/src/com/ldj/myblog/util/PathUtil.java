package com.ldj.myblog.util;

import android.content.Context;

/** 
 * @author peng
 *
 */
public class PathUtil {
	
	public static String getLogPath(Context context) {
		return FileUtil.getExternalFilesDir(context, "log") + "/";
	}
	
	public static String getDBPath(Context context) {
		return FileUtil.getExternalFilesDir(context, "db") + "/";
	}
	
	public static String getDownLoadPath(Context context) {
		return FileUtil.getExternalFilesDir(context, "download") + "/";
	}
	
	public static String getLocationPath(Context context) {
		return FileUtil.getExternalFilesDir(context, "location") + "/";
	}
	
	public static String getCachePath(Context context) {
		return context.getExternalCacheDir() + "/";
	}
}
