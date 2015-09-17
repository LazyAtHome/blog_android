package com.ldj.myblog.helper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonHelper {

	public static String ObjectToJsonStr(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj).toString();
	}

	/**
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject ObjectToJson(Object obj) throws JSONException {
		Gson gson = new Gson();
		String str = gson.toJson(obj);
		return new JSONObject(str);
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static <T extends Object> String objectListToJson(List<T> list)
			throws JSONException {
		JSONArray array = new JSONArray();
		if (list == null) {
			array.toString();
		}
		for (T obj : list)
			try {
				array.put(ObjectToJson(obj));
			} catch (JSONException e) {

				e.printStackTrace();
			}
		return array.toString();
	}

	/**
	 * 
	 * @param json
	 * @param obj
	 * @return
	 */
	public static Object jsonToObject(String json, Class<?> cla) {
		try {
			if (json != null) {
				Gson gson = new Gson();
				return gson.fromJson(json, cla);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}
