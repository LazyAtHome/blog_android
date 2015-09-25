package com.ldj.myblog.sherepre;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.UserInfos;

public class UserInfosPref {

	private static UserInfosPref instance;

	private SharedPreferences userInfos;

	private UserInfosPref(Context context) {
		userInfos = context.getSharedPreferences("user_info", 0);
	}

	public static UserInfosPref getInstance(Context context) {

		if (instance == null) {
			instance = new UserInfosPref(context);
		}
		return instance;
	}

	public void saveUserName(String userName) {
		userInfos.edit().putString("user_name", userName).commit();
	}

	public String getUserName() {
		return userInfos.getString("user_name", "");
	}

	public void savePwd(String pwd) {
		userInfos.edit().putString("pwd", pwd).commit();
	}

	public String getPwd() {
		return userInfos.getString("pwd", "");
	}

	public void setSavePwd(boolean isSave) {
		userInfos.edit().putBoolean("save_pwd", isSave).commit();

	}

	public boolean isSavePwd() {
		return userInfos.getBoolean("save_pwd", false);
	}

	public void saveUser(String user) {
		userInfos.edit().putString("user", user).commit();
	}

	

	public UserInfos getUser() {
		String json = userInfos.getString("user", "");
		if (TextUtils.isEmpty(json))
			return null;
		return (UserInfos) JsonHelper.jsonToObject(json, UserInfos.class);
	}

	
}
