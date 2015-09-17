package com.ldj.myblog.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.resp.LoginSuccResp;

public class LoginActivity extends FindInitActivity implements OnClickListener {

    EditText userNameEdit, passwordEdit;
    Button loginBtn;
    MyVolley loginVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            login();
        }
    }

    private void login() {
        String name = userNameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.username_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.password_empty, Toast.LENGTH_SHORT).show();
            return;
        }

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
        loginVolley = new MyVolley(this, Const.Message.MSG_LOGIN_SUCC, Const.Message.MSG_LOGIN_FAIL);

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
        switch (msg.what) {
            case Const.Message.MSG_LOGIN_SUCC:
                LoginSuccResp succResp = (LoginSuccResp) JsonHelper.jsonToObject(msg.obj + "", LoginSuccResp.class);
                Toast.makeText(getApplicationContext(), "id: " + succResp.getData().getId(), Toast.LENGTH_SHORT).show();
                if (msg.arg1 == Const.Request.REQUEST_SUCC) {
                    loginBtn.setText(R.string.log_in);
                    loginBtn.setEnabled(false);
                    dealActivityIntent(MainActivity.class);
                } else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
                    Toast.makeText(getApplicationContext(), msg.obj + "", Toast.LENGTH_SHORT).show();
                }
                break;
            case Const.Message.MSG_LOGIN_FAIL:
                Toast.makeText(getApplicationContext(), R.string.internet_abnormal, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

}
