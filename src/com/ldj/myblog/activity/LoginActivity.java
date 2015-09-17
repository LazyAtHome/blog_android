package com.ldj.myblog.activity;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ldj.myblog.R;

public class LoginActivity extends FindInitActivity implements OnClickListener {

	EditText userNameEdit, passwordEdit;
	Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		if(v == loginBtn){
			login();
		}
	}

	private void login() {
		String name = userNameEdit.getText().toString();
		String password = passwordEdit.getText().toString();

		if (TextUtils.isEmpty(name)) {
			return;
		}

		if (TextUtils.isEmpty(password)) {
			return;
		}


	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void findMyViews() {
		userNameEdit = (EditText) findViewById(R.id.et_username);
		passwordEdit = (EditText) findViewById(R.id.et_password);
		loginBtn = (Button) findViewById(R.id.btn_login);
	}

	@Override
	protected void initMyViews() {
		loginBtn.setOnClickListener(this);
	}

	@Override
	protected void handlerMessage(Message msg) {

	}

}
