package com.ldj.myblog.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ldj.myblog.R;
import com.ldj.myblog.activity.MyBlogListActivity;

public class MoreFragment extends BaseFragment implements OnClickListener{
	
	TextView titleText;
	ImageButton rightImage;
	LinearLayout myBlogsParent;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		if(getView() == null){
			setView(inflater.inflate(R.layout.fragment_more, container, false));
			initMyViews(getView());
		}
		return getView();
	}
	
	private void initMyViews(View view){
		titleText = (TextView) view.findViewById(R.id.tv_title);
		titleText.setText(R.string.me);
		rightImage = (ImageButton) view.findViewById(R.id.btn_title_right);
		rightImage.setVisibility(View.INVISIBLE);
		myBlogsParent = (LinearLayout) view.findViewById(R.id.ll_my_blogs);
		myBlogsParent.setOnClickListener(this);
	}
	

	@Override
	protected void handlerMessage(Message msg) {
		
	}

	@Override
	protected void initFragmentDatas() {
		
	}

	@Override
	public void onClick(View view) {
		if(view == myBlogsParent){
			dealActivityIntent(MyBlogListActivity.class);
		}
	}

}
