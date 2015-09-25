package com.ldj.myblog.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.Blog;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.sherepre.UserInfosPref;

public class BlogDetailActivity extends FindInitActivity implements OnClickListener {
    TextView textTitle;
    Blog blog;
    TextView userName, time, title, content;
    //    TextView topCreaterText, topTimeText, blogTitleText, blogContentText;
    Button btnLeft, btnRight;
    //    ImageButton titleDelBtn;
    String userId;
    String accessToken;
    MyVolley deleteBlogVolley;
    ImageButton btnModify;

    @Override
    protected void onCreate(Bundle arg0) {
        setContentView(R.layout.activity_blog_detail);
        super.onCreate(arg0);
    }

    @Override
    protected void initData() {
        blog = (Blog) JsonHelper.jsonToObject(getIntent()
                .getStringExtra("blog"), Blog.class);
        userId = UserInfosPref.getInstance(this).getUser().getId();
        accessToken = UserInfosPref.getInstance(this).getUser().getAccessToken();
        deleteBlogVolley = new MyVolley(this,
                Const.Message.MSG_DELETE_BLOG_SUCC,
                Const.Message.MSG_DELETE_BLOG_FAIL);

    }

    @Override
    protected void findMyViews() {
        textTitle = (TextView) findViewById(R.id.text_title);
        btnLeft = (Button) findViewById(R.id.btn_title_left);
        btnRight = (Button) findViewById(R.id.btn_title_right);
        userName = (TextView) findViewById(R.id.text_blog_detail_username);
        time = (TextView) findViewById(R.id.text_blog_detail_time);
        title = (TextView) findViewById(R.id.text_blog_detail_title);
        content = (TextView) findViewById(R.id.text_blog_detail_content);
        btnModify = (ImageButton) findViewById(R.id.imgbtn_detail_modify);
    }

    @Override
    protected void initMyViews() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText("返回");
        btnLeft.setOnClickListener(this);
        btnRight.setText("删除");
        btnRight.setOnClickListener(this);
        textTitle.setText("博客详情");
        userName.setText("作者 " + blog.getCreatedBy());
        time.setText("发布时间 " + blog.getCreatedDate());
        title.setText(blog.getTitle());
        content.setText(blog.getContent());
        if (userId.equals(blog.getUserId())) {
            btnRight.setVisibility(View.VISIBLE);
            btnModify.setVisibility(View.VISIBLE);
        } else {
            btnRight.setVisibility(View.INVISIBLE);
            btnModify.setVisibility(View.INVISIBLE);
        }
        btnModify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRight) {
            dialog();
        }
        if (view == btnLeft) {
            finish();
        }
        if (view == btnModify) {
            modify();
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要删除此条博客？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void delete() {
        deleteBlogVolley.requestDelete(Const.Request.delete,
                getHandler(), accessToken, blog.getId());
    }

    //This is the 'modify' function.
    private void modify() {
        dealActivityIntent(EditActivity.class);
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case Const.Message.MSG_DELETE_BLOG_SUCC:
                if (msg.arg1 == Const.Request.REQUEST_SUCC) {
                    setResult(RESULT_OK);
                    finish();
                } else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
                    Toast.makeText(this, msg.obj + "", Toast.LENGTH_LONG).show();
                }
                break;

            case Const.Message.MSG_DELETE_BLOG_FAIL:

                break;

            default:
                break;
        }
    }

}
