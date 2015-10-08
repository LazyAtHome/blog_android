package com.ldj.myblog.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.net.MyVolley;

public class RegisterActivity extends FindInitActivity implements
		OnClickListener {
	TextView titleText;
	ImageButton rightTitleBtn;
	Button registerBtn;
	EditText unameEidt, pwdEdit, pwdConfirmEdit, mailEidt;
	MyVolley registerVolley;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_register);
		super.onCreate(arg0);
	}

	@Override
	protected void initData() {
		registerVolley = new MyVolley(this, Const.Message.MSG_REGISTER_SUCC,
				Const.Message.MSG_REGISTER_FAIL);
	}

	@Override
	protected void findMyViews() {

		rightTitleBtn = (ImageButton) findViewById(R.id.btn_title_right);
		titleText = (TextView) findViewById(R.id.tv_title);
		unameEidt = (EditText) findViewById(R.id.et_username);
		pwdEdit = (EditText) findViewById(R.id.et_password);
		pwdConfirmEdit = (EditText) findViewById(R.id.et_password_confirm);
		mailEidt = (EditText) findViewById(R.id.et_usermail);
		registerBtn = (Button) findViewById(R.id.btn_reg);
	}

	@Override
	protected void initMyViews() {
		rightTitleBtn.setVisibility(View.INVISIBLE);
		titleText.setText(R.string.register);
		registerBtn.setOnClickListener(this);
	}

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {

		case Const.Message.MSG_REGISTER_SUCC:
			registerBtn.setText(R.string.register);
			registerBtn.setEnabled(true);
			if (msg.arg1 == Const.Request.REQUEST_SUCC) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.register_succ),
						Toast.LENGTH_SHORT).show();
				finish();
			} else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
				Toast.makeText(getApplicationContext(), msg.obj + "",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case Const.Message.MSG_REGISTER_FAIL:
			registerBtn.setText(R.string.register);
			registerBtn.setEnabled(true);
			break;

		default:
			break;
		}
	}

	private void register() {
		String uname = unameEidt.getText().toString().trim();
		String mail = mailEidt.getText().toString().trim();
		String pwd = pwdEdit.getText().toString().trim();
		String pwdConfirm = pwdConfirmEdit.getText().toString().trim();
		if (TextUtils.isEmpty(uname)) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.login_username_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(mail)) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.register_usermail_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.login_password_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(pwdConfirm)) {
			Toast.makeText(
					getApplicationContext(),
					getResources().getString(
							R.string.register_password_confirm_hint),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!pwd.equals(pwdConfirm)) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.password_not_confirm),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (uname.length() < 7 || uname.length() > 25) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.uname_illegal),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (pwd.length() < 7 || uname.length() > 25) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.pwd_illegal),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!mail.matches(Const.MAIL)) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.mail_illegal),
					Toast.LENGTH_SHORT).show();
			return;
		}

		registerBtn.setText(R.string.register_in);
		registerBtn.setEnabled(false);
		registerVolley.addParams("userName", uname);
		registerVolley.addParams("email", mail);
		registerVolley.addParams("cryptedPassword", pwd);
		registerVolley.requestPost(Const.Request.reg, getHandler());
	}

	@Override
	public void onClick(View view) {
		if (view == registerBtn) {
			register();
		}
	}

}
