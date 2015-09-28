package com.ldj.myblog.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.fragment.BaseFragment;
import com.ldj.myblog.fragment.EditBlogFragment;
import com.ldj.myblog.fragment.HomeBlogListFragment;
import com.ldj.myblog.fragment.MoreFragment;

public class MainActivity extends FindInitActivity implements OnClickListener {

	FrameLayout contentFrame;
	BaseFragment currentFragment;
	EditBlogFragment editBlogFragment;
	MoreFragment moreFragment;
	HomeBlogListFragment homeFragment;
	FragmentManager fragmentMgr;
	ImageView homeTabImage, editTabImage, moreTabImage;
	
	
	BroadcastReceiver refreshBlogReceiver  = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			homeTabImage.performClick();
			if(homeFragment != null && homeFragment.isAdded()){
				homeFragment.onRefresh();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		registerReceiver(refreshBlogReceiver, new IntentFilter(Const.FILTER_REFRESH_BLOG_LIST));
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(refreshBlogReceiver);
	}

	

	@Override
	protected void initData() {
		fragmentMgr = getSupportFragmentManager();
		homeFragment = new HomeBlogListFragment();
		editBlogFragment = new EditBlogFragment();
		moreFragment = new MoreFragment();
		currentFragment = homeFragment;
	}

	@Override
	protected void findMyViews() {
		contentFrame = (FrameLayout) findViewById(R.id.fl_main);
		homeTabImage = (ImageView) findViewById(R.id.iv_bottom_home);
		editTabImage = (ImageView) findViewById(R.id.iv_bottom_edit);
		moreTabImage = (ImageView) findViewById(R.id.iv_bottom_more);

	}

	@Override
	protected void initMyViews() {
		fragmentMgr.beginTransaction().add(R.id.fl_main, currentFragment)
				.commit();
		homeTabImage.setEnabled(false);
		homeTabImage.setOnClickListener(this);
		editTabImage.setOnClickListener(this);
		moreTabImage.setOnClickListener(this);

	}

	@Override
	protected void handlerMessage(Message msg) {
		// TODO Auto-generated method stub

	}

	private void switchFragment(View view) {
		if (view == homeTabImage) {
			if (currentFragment == homeFragment) {
				return;
			}

			homeTabImage.setEnabled(false);
			editTabImage.setEnabled(true);
			moreTabImage.setEnabled(true);

			if (homeFragment.isAdded()) {
				fragmentMgr.beginTransaction().hide(currentFragment)
						.show(homeFragment).commit();
			} else {
				fragmentMgr.beginTransaction().hide(currentFragment)
						.add(R.id.fl_main, homeFragment).commit();
			}

			currentFragment = homeFragment;
		} else if (view == editTabImage) {

			if (currentFragment == editBlogFragment) {
				return;
			}

			homeTabImage.setEnabled(true);
			editTabImage.setEnabled(false);
			moreTabImage.setEnabled(true);

			if (editBlogFragment.isAdded()) {
				fragmentMgr.beginTransaction().hide(currentFragment)
						.show(editBlogFragment).commit();
			} else {
				fragmentMgr.beginTransaction().hide(currentFragment)
						.add(R.id.fl_main, editBlogFragment).commit();
			}
			currentFragment = editBlogFragment;
		} else if (view == moreTabImage) {

			if (currentFragment == moreFragment) {
				return;
			}

			homeTabImage.setEnabled(true);
			editTabImage.setEnabled(true);
			moreTabImage.setEnabled(false);

			if (moreFragment.isAdded()) {
				fragmentMgr.beginTransaction().hide(currentFragment)
						.show(moreFragment).commit();
			} else {

				fragmentMgr.beginTransaction().hide(currentFragment)
						.add(R.id.fl_main, moreFragment).commit();
			}
			currentFragment = moreFragment;

		}
	}

	@Override
	public void onClick(View view) {
		switchFragment(view);
	}
}
