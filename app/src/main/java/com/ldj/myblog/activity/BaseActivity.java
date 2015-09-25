package com.ldj.myblog.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{
	protected void dealActivityIntent(Class<?> activity) {
		Intent intent = new Intent(this, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
