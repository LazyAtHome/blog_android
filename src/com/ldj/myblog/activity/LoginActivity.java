package com.ldj.myblog.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.resp.LoginSuccResp;
import com.ldj.myblog.sherepre.UserInfosPref;

public class LoginActivity extends FindInitActivity implements OnClickListener {

	EditText userNameEdit, passwordEdit;
	Button loginBtn;
	MyVolley loginVolley;
	String name, password;
	UserInfosPref userInfosPref;
	ImageView savePwdImage;
	LinearLayout savePwdParent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		if (v == loginBtn) {
			login();
		} else if (v == savePwdParent) {

			if (savePwdImage.isSelected()) {
				savePwdImage.setSelected(false);
			} else {
				savePwdImage.setSelected(true);
			}
			userInfosPref.setSavePwd(savePwdImage.isSelected());
		}
	}

	private void login() {
		name = userNameEdit.getText().toString().trim();
		password = passwordEdit.getText().toString().trim();

		if (TextUtils.isEmpty(name)) {
			Toast.makeText(this, R.string.username_empty, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, R.string.password_empty, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		loginBtn.setText(R.string.log_in);
		loginBtn.setEnabled(false);

		loginVolley.addParams("userName", name);
		loginVolley.addParams("password", password);
		loginVolley.requestPost(Const.Request.login, getHandler());

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void initData() {
		loginVolley = new MyVolley(this, Const.Message.MSG_LOGIN_SUCC,
				Const.Message.MSG_LOGIN_FAIL);
		userInfosPref = UserInfosPref.getInstance(this);

	}

	@Override
	protected void findMyViews() {
		userNameEdit = (EditText) findViewById(R.id.et_username);
		passwordEdit = (EditText) findViewById(R.id.et_password);
		loginBtn = (Button) findViewById(R.id.btn_login);
		savePwdParent = (LinearLayout) findViewById(R.id.ll_save_pwd);
		savePwdImage = (ImageView) findViewById(R.id.iv_save_pwd);

	}

	@Override
	protected void initMyViews() {
		loginBtn.setOnClickListener(this);
		savePwdParent.setOnClickListener(this);
		String name = userInfosPref.getUserName();
		
		if (!TextUtils.isEmpty(name)) {
			userNameEdit.setText(name);
		}
		if (userInfosPref.isSavePwd()) {
			savePwdImage.setSelected(userInfosPref.isSavePwd());
			passwordEdit.setText(userInfosPref.getPwd());
		}

	}

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case Const.Message.MSG_LOGIN_SUCC:
			loginBtn.setText(R.string.login);
			loginBtn.setEnabled(true);

			LoginSuccResp succResp = (LoginSuccResp) JsonHelper.jsonToObject(
					msg.obj + "", LoginSuccResp.class);

			if (msg.arg1 == Const.Request.REQUEST_SUCC) {
				userInfosPref.saveUserName(name);
				userInfosPref.savePwd(password);
				userInfosPref.saveUser(JsonHelper.ObjectToJsonStr(succResp.getData()));
				dealActivityIntent(MainActivity.class);
				finish();

			} else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
				Toast.makeText(getApplicationContext(), msg.obj + "",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case Const.Message.MSG_LOGIN_FAIL:
			loginBtn.setText(R.string.login);
			loginBtn.setEnabled(true);
			Toast.makeText(getApplicationContext(), R.string.internet_abnormal,
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

}
