package com.ldj.myblog.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ldj.myblog.R;

/**
 * Created by Administrator on 2015/9/14.
 */
public class MyActivity extends FindInitActivity implements View.OnClickListener {

    Button btnLeft;
    Button btnRight;
    TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findMyViews() {
        btnLeft = (Button) findViewById(R.id.btn_title_left);
        btnRight = (Button) findViewById(R.id.btn_title_right);
        textTitle = (TextView) findViewById(R.id.text_title);
    }

    @Override
    protected void initMyViews() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText("返回");
        btnLeft.setOnClickListener(this);
        btnRight.setVisibility(View.GONE);
        textTitle.setText("我的博客");

    }

    @Override
    public void onClick(View view) {
        if (view == btnLeft) {
            finish();
        }
    }

    @Override
    protected void handlerMessage(Message msg) {

    }
}
