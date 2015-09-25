package com.ldj.myblog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.UserInfos;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.resp.BaseResp;
import com.ldj.myblog.resp.LoginSuccResp;
import com.ldj.myblog.resp.PublishSuccResp;
import com.ldj.myblog.sherepre.UserInfosPref;

/**
 * Created by Administrator on 2015/9/14.
 */
public class EditActivity extends FindInitActivity implements View.OnClickListener {
    Button btnLeft;
    Button btnRight;
    TextView textTitle;
    EditText editContent;
    EditText editTitle;
    UserInfos userInfos;
    MyVolley publishVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        publishVolley = new MyVolley(this, Const.Message.MSG_PUBLISH_SUCC, Const.Message.MSG_PUBLISH_FAIL);
        userInfos = UserInfosPref.getInstance(this).getUser();

    }


    @Override
    protected void findMyViews() {
        btnLeft = (Button) findViewById(R.id.btn_title_left);
        btnRight = (Button) findViewById(R.id.btn_title_right);
        textTitle = (TextView) findViewById(R.id.text_title);
        editTitle = (EditText) findViewById(R.id.edit_blog_title);
        editContent = (EditText) findViewById(R.id.edit_blog_content);
    }

    @Override
    protected void initMyViews() {
        textTitle.setText("发布博客");
        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnLeft.setText("取消");
        btnRight.setText("发布");
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLeft) {
            finish();
        }

        if (view == btnRight) {
            btnRight.setEnabled(false);
            btnRight.setText("发布中");
            btnRight.setTextColor(getResources().getColor(R.color.gray_text_common));
            publish();
        }
    }

    private void publish() {
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getApplicationContext(), "博客标题不能为空", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getApplicationContext(), "博客内容不能为空", Toast.LENGTH_SHORT).show();
        }

        publishVolley.addParams("title", title);
        publishVolley.addParams("content", content);
        publishVolley.requestPost(Const.Request.publish, getHandler(), userInfos.getAccessToken());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case Const.Message.MSG_PUBLISH_SUCC:
                if (msg.arg1 == Const.Request.REQUEST_SUCC) {
//                    PublishSuccResp succResp = (PublishSuccResp) JsonHelper.jsonToObject(msg.obj + "", PublishSuccResp.class);
                    Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                    editTitle.setText("");
                    editContent.setText("");
                    startActivity(new Intent(this,HomeActivity.class));
//                    EditActivity.sendBroadcast(new Intent(Const.FILTER_REFRESH_BLOG_LIST));

                } else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
                    Toast.makeText(getApplicationContext(), msg.obj + "", Toast.LENGTH_SHORT).show();
                }
                break;
            case Const.Message.MSG_PUBLISH_FAIL:
                Toast.makeText(getApplicationContext(), "网络异常，请重试", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        btnRight.setText("发布");
        btnRight.setEnabled(true);
        btnRight.setTextColor(getResources().getColor(R.color.orange));
    }
}
