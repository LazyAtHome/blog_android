package com.ldj.myblog.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldj.myblog.R;
import com.ldj.myblog.activity.LoginActivity;
import com.ldj.myblog.activity.MyActivity;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.Blog;
import com.ldj.myblog.model.UserInfos;
import com.ldj.myblog.sherepre.UserInfosPref;


/**
 * Created by Administrator on 2015/9/11.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout layoutMyBlog;
    LinearLayout layoutLogout;
    TextView userName;
    TextView userID;
    Blog blog;
    UserInfosPref userInfosPref;
    UserInfos userInfos;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            setView(inflater.inflate(R.layout.fragment_me, container,
                    false));
            initMyViews(getView());
        }
        return getView();
    }

    private void initMyViews(View view) {
        Button btnLeft = (Button) view.findViewById(R.id.btn_title_left);
        Button btnRight = (Button) view.findViewById(R.id.btn_title_right);
        TextView textTitle = (TextView) view.findViewById(R.id.text_title);
        layoutMyBlog = (LinearLayout) view.findViewById(R.id.layout_me_blog);
        layoutLogout = (LinearLayout) view.findViewById(R.id.layout_me_logout);
        layoutLogout.setOnClickListener(this);
        layoutMyBlog.setOnClickListener(this);
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        textTitle.setText("我的");
        userName = (TextView) view.findViewById(R.id.text_me_username);
        userID = (TextView)view.findViewById(R.id.text_me_userId);
//        userID.setText(blog.getUserId());
        userInfos =UserInfosPref.getInstance(getActivity()).getUser();
        userInfosPref = UserInfosPref.getInstance(getActivity());
        userName.setText(userInfosPref.getUserName());
        userID.setText(userInfos.getEmail());
    }

    @Override
    public void onClick(View view) {
        if (view == layoutMyBlog) {
            startActivity(new Intent(getActivity(), MyActivity.class));
        }

        if (view == layoutLogout) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    protected void handlerMessage(Message msg) {

    }

    @Override
    protected void initFragmentDatas() {

    }


}
