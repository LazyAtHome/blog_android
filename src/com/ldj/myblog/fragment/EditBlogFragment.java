package com.ldj.myblog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.UserInfos;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.resp.PublishSuccResp;
import com.ldj.myblog.sherepre.UserInfosPref;

public class EditBlogFragment extends BaseFragment implements
		View.OnClickListener {
	TextView titleText;
	ImageButton publishBtn;
	EditText editTitle;
	EditText editContent;
	ProgressBar refreshBar;
	MyVolley publishVolley;
	UserInfos userInfos;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (getView() == null) {
			setView(inflater.inflate(R.layout.fragment_edit_blog, container,
					false));
			initMyViews(getView());
		}
		return getView();
	}

	private void initMyViews(View view) {
		titleText = (TextView) view.findViewById(R.id.tv_title);
		titleText.setText(R.string.publish_blog);
		refreshBar = (ProgressBar) view.findViewById(R.id.pb_title_refresh);
		publishBtn = (ImageButton) view.findViewById(R.id.btn_title_right);
		publishBtn.setBackgroundResource(R.drawable.btn_publish_blog_selector);
		publishBtn.setOnClickListener(this);
		editTitle = (EditText) view.findViewById(R.id.edit_blog_title);
		editContent = (EditText) view.findViewById(R.id.edit_blog_content);
	}

	@Override
	public void onClick(View view) {
		if (view == publishBtn) {
			publish();
		}
	}

	private void publish() {
		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();

		if (TextUtils.isEmpty(title)) {
			Toast.makeText(getActivity(), R.string.title_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(content)) {
			Toast.makeText(getActivity(), R.string.content_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}
		publishBtn.setVisibility(View.INVISIBLE);
		refreshBar.setVisibility(View.VISIBLE);
		publishVolley.addParams("title", title);
		publishVolley.addParams("content", content);
		publishVolley.requestPost(Const.Request.publish, getHandler(),
				userInfos.getAccessToken());
	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case Const.Message.MSG_PUBLISH_SUCC:
			if (msg.arg1 == Const.Request.REQUEST_SUCC) {
				PublishSuccResp succResp = (PublishSuccResp) JsonHelper
						.jsonToObject(msg.obj + "", PublishSuccResp.class);
				refreshBar.setVisibility(View.INVISIBLE);
				publishBtn.setVisibility(View.VISIBLE);
				editTitle.setText("");
				editContent.setText("");
				Toast.makeText(getActivity(),
						getResources().getString(R.string.publish_succ),
						Toast.LENGTH_LONG).show();
				getActivity().sendBroadcast(
						new Intent(Const.FILTER_REFRESH_BLOG_LIST));
			} else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
				Toast.makeText(getActivity(), msg.obj + "", Toast.LENGTH_SHORT)
						.show();
				refreshBar.setVisibility(View.INVISIBLE);
				publishBtn.setVisibility(View.VISIBLE);
			}
			break;
		case Const.Message.MSG_PUBLISH_FAIL:
			Toast.makeText(getActivity(), R.string.internet_abnormal_publish,
					Toast.LENGTH_SHORT).show();
			refreshBar.setVisibility(View.INVISIBLE);
			publishBtn.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void initFragmentDatas() {
		publishVolley = new MyVolley(getActivity(),
				Const.Message.MSG_PUBLISH_SUCC, Const.Message.MSG_PUBLISH_FAIL);
		userInfos = UserInfosPref.getInstance(getActivity()).getUser();

	}

}
