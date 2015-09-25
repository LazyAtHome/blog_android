package com.ldj.myblog.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ldj.myblog.R;
import com.ldj.myblog.net.MyVolley;

public class TestActivity extends Activity {

	MyVolley loginVolley;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		loginVolley = new MyVolley(this, 1, 2);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 1:
//					Log.e("")
					break;
				case 2:

					break;

				default:
					break;
				}
			}
		};
	}

	public void login(View view) {
		loginVolley.addParams("userName", "yanpengtest");
		loginVolley.addParams("password", "testtestyanpeng");
		loginVolley.requestPost("http://jason.tunnel.mobi/blogserver/users/login", handler);
	}
}
