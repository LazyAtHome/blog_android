package com.ldj.myblog.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    UserInfosPref userInfosPref;
    CheckBox checkSavePassword;
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            loginBtn.setText(R.string.log_in);
            loginBtn.setBackgroundColor(getResources().getColor(R.color.bg_dark_gray));
            loginBtn.setEnabled(false);
            login();
        }
        if (v == checkSavePassword) {
//            if (savePwdImage.isSelected()) {
//                savePwdImage.setSelected(false);
//            } else {
//                savePwdImage.setSelected(true);
//            }
            userInfosPref.setSavePwd(checkSavePassword.isSelected());
        }
    }

    private void login() {
        name = userNameEdit.getText().toString();
        password = passwordEdit.getText().toString();

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
        userInfosPref = UserInfosPref.getInstance(this);
    }

    @Override
    protected void findMyViews() {
        userNameEdit = (EditText) findViewById(R.id.edit_login_username);
        passwordEdit = (EditText) findViewById(R.id.edit_login_password);
        loginBtn = (Button) findViewById(R.id.btn_login_login);
        checkSavePassword = (CheckBox) findViewById(R.id.check_save_password);
    }

    @Override
    protected void initMyViews() {
        checkSavePassword.setPressed(true);
        loginBtn.setOnClickListener(this);
        checkSavePassword.setOnClickListener(this);
        String name = userInfosPref.getUserName();

        if (!TextUtils.isEmpty(name)) {
            userNameEdit.setText(name);
        }
        if (userInfosPref.isSavePwd()) {
            checkSavePassword.setSelected(userInfosPref.isSavePwd());
            passwordEdit.setText(userInfosPref.getPwd());
        }
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case Const.Message.MSG_LOGIN_SUCC:
                LoginSuccResp succResp = (LoginSuccResp) JsonHelper.jsonToObject(msg.obj + "", LoginSuccResp.class);
                if (msg.arg1 == Const.Request.REQUEST_SUCC) {
                    Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                    userInfosPref.saveUserName(name);
                    userInfosPref.savePwd(password);
                    userInfosPref.saveUser(JsonHelper.ObjectToJsonStr(succResp.getData()));
                    dealActivityIntent(HomeActivity.class);
                    finish();
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
        loginBtn.setText(R.string.login);
        loginBtn.setBackgroundColor(getResources().getColor(R.color.orange));
        loginBtn.setEnabled(true);
    }

}
