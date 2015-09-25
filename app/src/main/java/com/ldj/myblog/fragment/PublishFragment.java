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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ldj.myblog.R;
import com.ldj.myblog.activity.EditActivity;


/**
 * Created by Administrator on 2015/9/11.
 */
public class PublishFragment extends BaseFragment implements View.OnClickListener {
    RelativeLayout layoutPublish;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            setView(inflater.inflate(R.layout.fragment_publish, container,
                    false));
            initMyViews(getView());
        }
        return getView();
    }

    private void initMyViews(View view) {
        Button btnLeft = (Button) view.findViewById(R.id.btn_title_left);
        Button btnRight = (Button) view.findViewById(R.id.btn_title_right);
        TextView textTitle = (TextView) view.findViewById(R.id.text_title);
        layoutPublish = (RelativeLayout) view.findViewById(R.id.layout_publish);
        textTitle.setText("发博客");
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        layoutPublish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == layoutPublish) {
            startActivity(new Intent(getActivity(),EditActivity.class));
        }
    }

    @Override
    protected void handlerMessage(Message msg) {

    }

    @Override
    protected void initFragmentDatas() {

    }
}

