package com.ldj.myblog.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ldj.myblog.R;

public class RegisterActivity extends FindInitActivity{
	TextView titleText;
	ImageButton rightTitleBtn;
	
	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_register);
		super.onCreate(arg0);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void findMyViews() {
		rightTitleBtn = (ImageButton) findViewById(R.id.btn_title_right);
		titleText =  (TextView) findViewById(R.id.tv_title);
	}

	@Override
	protected void initMyViews() {
		rightTitleBtn.setVisibility(View.INVISIBLE);
		titleText.setText(R.string.register);
	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

}