package com.ldj.myblog.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ldj.myblog.R;

public class EditBlogFragment extends BaseFragment {
	TextView titleText;
	ImageButton publishBtn;
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
	
	private void initMyViews(View view){
		titleText = (TextView) view.findViewById(R.id.tv_title);
		titleText.setText(R.string.publish_blog);
		publishBtn = (ImageButton) view.findViewById(R.id.btn_title_right);
		publishBtn.setBackgroundResource(R.drawable.btn_publish_blog_selector);
	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initFragmentDatas() {

	}

}
