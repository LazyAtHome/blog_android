package com.ldj.myblog.fragment;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.activity.BlogDetailActivity;
import com.ldj.myblog.adapter.HomeBlogAdapter;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.Blog;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.resp.BlogSuccResp;
import com.ldj.myblog.view.XListView;
import com.ldj.myblog.view.XListView.IXListViewListener;

public class HomeBlogListFragment extends BaseFragment implements
		OnClickListener, IXListViewListener, OnItemClickListener {

	XListView mBlogList;
	TextView titleText;
	ProgressBar refreshBar;
	ImageButton refreshBtn;
	List<Blog> blogs;
	HomeBlogAdapter blogAdapter;
	MyVolley allVolley;
	MyVolley moreBlogVolley;
	private static final int REQUEST_DETAIL = 1;
	
	

	@Override
	protected void initFragmentDatas() {
		blogAdapter = new HomeBlogAdapter(getActivity());
		allVolley = new MyVolley(getActivity(), Const.Message.MSG_ALL_SUCC,
				Const.Message.MSG_ALL_FAIL);

		allVolley.addParams("limit", Const.PAGE_SIZE);

		moreBlogVolley = new MyVolley(getActivity(),
				Const.Message.MSG_MORE_BLOG_SUCC,
				Const.Message.MSG_MORE_BLOG_FAIL);
		moreBlogVolley.addParams("limit", Const.PAGE_SIZE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (getView() == null) {
			setView(inflater.inflate(R.layout.fragment_home_blog_list,
					container, false));
			initMyViews(getView());
		}
		return getView();
	}

	private void initMyViews(View view) {
		titleText = (TextView) view.findViewById(R.id.tv_title);
		titleText.setText(R.string.home_page);
		refreshBar = (ProgressBar) view.findViewById(R.id.pb_title_refresh);
		refreshBtn = (ImageButton) view.findViewById(R.id.btn_title_right);
		refreshBtn.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_btn_refresh));
		refreshBtn.setOnClickListener(this);
		mBlogList = (XListView) view.findViewById(R.id.lv_home_blog);
		mBlogList.setOnItemClickListener(this);
		// mBlogList.setAdapter(blogAdapter);
		mBlogList.setXListViewListener(this);
		onRefresh();

	}

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case Const.Message.MSG_ALL_SUCC:

			refreshBtn.setVisibility(View.VISIBLE);
			refreshBar.setVisibility(View.INVISIBLE);
			if (msg.arg1 == Const.Request.REQUEST_SUCC) {
				mBlogList.stopRefresh(getResources().getString(
						R.string.refresh_succ));
				BlogSuccResp blogs = (BlogSuccResp) JsonHelper.jsonToObject(
						msg.obj + "", BlogSuccResp.class);
				blogAdapter.setBlogs(blogs.getData().getPosts());
				mBlogList.setAdapter(blogAdapter);
				if (blogs.getData().getPageInfo().isHasNextPage()) {
					mBlogList.setPullLoadEnable(true);
					moreBlogVolley.addParams("page", blogs.getData()
							.getPageInfo().getNextPage());
				} else {
					mBlogList.setPullLoadEnable(false);

				}
			} else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
				mBlogList.stopRefresh(getResources().getString(
						R.string.refresh_fail));
			}
			break;

		case Const.Message.MSG_ALL_FAIL:
			mBlogList.stopRefresh(getResources().getString(
					R.string.refresh_fail));
			refreshBtn.setVisibility(View.VISIBLE);
			refreshBar.setVisibility(View.INVISIBLE);

			break;

		case Const.Message.MSG_MORE_BLOG_SUCC:
			if (msg.arg1 == Const.Request.REQUEST_SUCC) {
				BlogSuccResp moreBlogs = (BlogSuccResp) JsonHelper
						.jsonToObject(msg.obj + "", BlogSuccResp.class);
				loadMoreList(moreBlogs.getData().getPosts());
				if (moreBlogs.getData().getPageInfo().isHasNextPage()) {
					mBlogList.setPullLoadEnable(true);
					moreBlogVolley.addParams("page", moreBlogs.getData()
							.getPageInfo().getNextPage());
				} else {
					mBlogList.setPullLoadEnable(false);
				}

			} else if (msg.arg1 == Const.Request.REQUEST_FAIL) {

			}
			break;
		case Const.Message.MSG_MORE_BLOG_FAIL:

			break;

		default:
			break;
		}
	}

	private void loadMoreList(List<Blog> blogs) {
		mBlogList.stopLoadMore();
		if (blogs == null) {
			return;
		}
		blogAdapter.getBlogs().addAll(blogs);
		blogAdapter.notifyDataSetChanged();

	}

	@Override
	public void onClick(View view) {
		if (view == refreshBtn) {
			onRefresh();
		}
	}

	@Override
	public  void onRefresh() {
		allVolley.addParams("page", 1);
		allVolley.requestGet(Const.Request.all, getHandler());
		refreshBtn.setVisibility(View.INVISIBLE);
		refreshBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadMore() {
		moreBlogVolley.requestGet(Const.Request.all, getHandler());
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Log.e("----------", "position:" + position);
		Blog blog = (Blog) adapterView.getAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
		intent.putExtra("blog", JsonHelper.ObjectToJsonStr(blog));
		startActivityForResult(intent, REQUEST_DETAIL);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_DETAIL:
				onRefresh();
				Toast.makeText(getActivity(),
						getResources().getString(R.string.delete_succ),
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	}

}
