package com.ldj.myblog.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
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
    TextView titleText;
    Blog blog;
    TextView topCreaterText, topTimeText, blogTitleText, blogContentText;
    ImageButton titleDelBtn;
    String userId;
    String accessToken;
    MyVolley deleteBlogVolley;

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
        titleText = (TextView) findViewById(R.id.tv_title);
        topCreaterText = (TextView) findViewById(R.id.tv_blog_detail_creater);
        topTimeText = (TextView) findViewById(R.id.tv_blog_detail_time);
        blogTitleText = (TextView) findViewById(R.id.tv_blog_detail_title);
        blogContentText = (TextView) findViewById(R.id.tv_blog_detail_content);
        titleDelBtn = (ImageButton) findViewById(R.id.btn_title_right);

    }

    @Override
    protected void initMyViews() {
        titleDelBtn.setBackgroundResource(R.drawable.delete);
        titleText.setText(R.string.detail);
        if (userId.equals(blog.getUserId())) {
            titleDelBtn.setVisibility(View.VISIBLE);
        } else {
            titleDelBtn.setVisibility(View.INVISIBLE);
        }
        topCreaterText.setText(blog.getCreatedBy());
        topTimeText.setText(blog.getCreatedDate());
        blogTitleText.setText(blog.getTitle());
        blogContentText.setText(blog.getContent());
        titleDelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == titleDelBtn) {
            dialog();
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_blog);
        builder.setTitle(R.string.mention);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
