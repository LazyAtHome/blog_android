package com.ldj.myblog.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;

public abstract class FindInitActivity extends BaseActivity{
	
	boolean first = true;

	protected BroadcastReceiver networkReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent intent) {

			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

				NetworkInfo info = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
						.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					if(first){
						first = false;
					}else{
						getHandler().obtainMessage(
								Const.Message.MSG_NETWORK_AVAILABLE).sendToTarget();
					}
					
				}
			}
		}

	};

	protected abstract void initData();

	protected abstract void findMyViews();

	protected abstract void initMyViews();
	
	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (isFinishing()) {
				return;
			}
			handlerMessage(msg);
		}
	};

	public Handler getHandler() {
		return handler;
	}

	protected abstract void handlerMessage(Message msg);

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initData();
		findMyViews();
		initMyViews();
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		registerNetwork();
	}
	
	@Override
	protected void onStop() {
		unregisterNetwork();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	
	private void registerNetwork() {
		IntentFilter networkFilter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(networkReceiver, networkFilter);
	}

	private void unregisterNetwork() {
		unregisterReceiver(networkReceiver);
	}

}
