package com.ldj.myblog.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ldj.myblog.R;
import com.ldj.myblog.adapter.HomeBlogAdapter;
import com.ldj.myblog.model.Blog;
import com.ldj.myblog.view.XListView;

public class HomeBlogListFragment extends BaseFragment implements OnClickListener{
	
	XListView mBlogList;
	TextView titleText;
	ProgressBar refreshBar;
	ImageButton refreshBtn;
	List<Blog> blogs;
	HomeBlogAdapter blogAdapter;
	
	@Override
	protected void initFragmentDatas() {
		blogs = new ArrayList<Blog>();
		blogs.add(new Blog("1937年10月10日，时任国名党53军691团团长的吕征", "10", "18分钟前", "凤凰卫视", "20"));
		blogAdapter = new HomeBlogAdapter(getActivity());
		blogAdapter.setBlogs(blogs);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(getView() == null){
			setView(inflater.inflate(R.layout.fragment_home_blog_list, container, false));
			initMyViews(getView());
		}
		return getView();
	}
	
	private void initMyViews(View view){
		titleText = (TextView) view.findViewById(R.id.tv_title);
		titleText.setText(R.string.home_page);
		refreshBar = (ProgressBar) view.findViewById(R.id.pb_title_refresh);
		refreshBtn = (ImageButton) view.findViewById(R.id.btn_title_right);
		refreshBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.title_btn_refresh));
		refreshBtn.setOnClickListener(this);
		mBlogList = (XListView) view.findViewById(R.id.lv_home_blog);
		mBlogList.setAdapter(blogAdapter);
		mBlogList.setPullLoadEnable(false);
		mBlogList.setPullRefreshEnable(false);
		mBlogList.removeMyFooter();
		
	}

	@Override
	protected void handlerMessage(Message msg) {
		
	}

	@Override
	public void onClick(View view) {
		if(view == refreshBtn){
			refreshBtn.setVisibility(View.INVISIBLE);
			refreshBar.setVisibility(View.VISIBLE);
		}
	}

	

}
