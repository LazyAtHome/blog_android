package com.ldj.myblog.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.resp.BaseResp;
import com.ldj.myblog.util.Map2KV;

public class MyVolley implements Response.ErrorListener, Listener<JSONObject> {

	Context context;
	private static RequestQueue myQueue;
	Map<String, Object> params = new HashMap<String, Object>();
	Handler handler;
	int requestSucc = -1;
	int requestFail = -1;

	// LoadingDialog dialog;

	public MyVolley(Context context, int requestSucc, int requestFail) {
		this.context = context;
		myQueue = Volley.newRequestQueue(context);
		this.requestSucc = requestSucc;
		this.requestFail = requestFail;

	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void requestGet(String url, Handler handler) {
		this.handler = handler;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url
				+ Map2KV.map(params), null, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);

	}

	// public void requestGet(String url, LoadingDialog dialog, Handler handler)
	// {
	// this.dialog = dialog;
	// dialog.show();
	// requestGet(url, handler);
	//
	// }

	public void requestPost(String url, Handler handler) {
		this.handler = handler;
		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url + Map2KV.map(params), jsonObject, this, this) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=utf-8");
				
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}
	
	public void requestPost(String url, Handler handler,final String accessToken) {
		this.handler = handler;
		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url + Map2KV.map(params), jsonObject, this, this) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=utf-8");
				headers.put("accessToken", accessToken);
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}

	// public void requestPost(String url, LoadProgressDialog dialog, String
	// msg,
	// Handler handler) {
	// this.dialog = dialog;
	// dialog.show(msg);
	// requestPost(url, handler);
	//
	// }

	public void addParams(String key, Object value) {
		params.put(key, value);
	}

	@Override
	public void onResponse(JSONObject response) {
		// if (dialog != null && dialog.isShowing()) {
		// dialog.cancel();
		// }

		if (response == null) {
			handler.obtainMessage(requestFail,
					context.getString(R.string.request_fail)).sendToTarget();
			return;
		}
		int code = -1;
		try {
			code = new JSONObject(response.toString()).getInt("responseCode");
		} catch (JSONException e) {
			handler.obtainMessage(requestFail,
					context.getString(R.string.request_fail)).sendToTarget();
			return;
		}
		Log.e("---------", "response:" + response);
		Message msg = handler.obtainMessage(requestSucc);
		if (code == BaseResp.OK) {
			msg.arg1 = Const.Request.REQUEST_SUCC;
			msg.obj = response.toString();
		} else {
			msg.arg1 = Const.Request.REQUEST_FAIL;
			BaseResp errorResp =(BaseResp) JsonHelper.jsonToObject(response.toString(), BaseResp.class);
			msg.obj = errorResp.getResponseMsg();
		}
		
		handler.sendMessage(msg);

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// if (dialog != null && dialog.isShowing()) {
		// dialog.cancel();
		// }
		Log.e("---------", "response:" + error);
		handler.obtainMessage(requestFail, error.getMessage()).sendToTarget();

	}

}
