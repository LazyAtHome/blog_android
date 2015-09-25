package com.ldj.myblog.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.ldj.myblog.R;
import com.ldj.myblog.fragment.HomeFragment;
import com.ldj.myblog.fragment.MeFragment;
import com.ldj.myblog.fragment.PublishFragment;

/**
 * Created by Administrator on 2015/9/21.
 */
public class HomeActivity extends FindInitActivity implements View.OnClickListener {

    Fragment currentFragment;
    PublishFragment publishFragment;
    HomeFragment homeFragment;
    MeFragment meFragment;
    FragmentManager fragmentMgr;

    Button btnPublish;
    Button btnHome;
    Button btnMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        fragmentMgr = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        publishFragment = new PublishFragment();
        meFragment = new MeFragment();
        currentFragment = homeFragment;

    }

    @Override
    protected void findMyViews() {
        btnPublish = (Button) findViewById(R.id.btn_publish);
        btnHome = (Button) findViewById(R.id.btn_home);
        btnMe = (Button) findViewById(R.id.btn_me);
    }

    @Override
    protected void initMyViews() {
        fragmentMgr.beginTransaction().add(R.id.frame_home, currentFragment)
                .commit();
        btnHome.setSelected(true);
        btnHome.setEnabled(false);

        btnPublish.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_publish:
                switchToPublish();
                break;
            case R.id.btn_home:
                switchToHome();
                break;
            case R.id.btn_me:
                switchToMe();
                break;
            default:
                break;
        }
    }

    private void switchToPublish() {
        if (currentFragment == publishFragment) {
            return;
        }

        btnPublish.setEnabled(false);
        btnHome.setEnabled(true);
        btnMe.setEnabled(true);

        if (publishFragment.isAdded()) {
            fragmentMgr.beginTransaction().hide(currentFragment)
                    .show(publishFragment).commit();
        } else {
            fragmentMgr.beginTransaction().hide(currentFragment)
                    .add(R.id.frame_home, publishFragment).commit();
        }

        currentFragment = publishFragment;

        btnPublish.setSelected(true);
        btnHome.setSelected(false);
        btnMe.setSelected(false);
    }

    private void switchToHome() {
        if (currentFragment == homeFragment) {
            return;
        }

        btnPublish.setEnabled(true);
        btnHome.setEnabled(false);
        btnMe.setEnabled(true);

        if (homeFragment.isAdded()) {
            fragmentMgr.beginTransaction().hide(currentFragment)
                    .show(homeFragment).commit();
        } else {
            fragmentMgr.beginTransaction().hide(currentFragment)
                    .add(R.id.frame_home, homeFragment).commit();
        }

        currentFragment = homeFragment;

        btnPublish.setSelected(false);
        btnHome.setSelected(true);
        btnMe.setSelected(false);
    }

    private void switchToMe() {
        if (currentFragment == meFragment) {
            return;
        }

        btnPublish.setEnabled(true);
        btnHome.setEnabled(true);
        btnMe.setEnabled(false);

        if (meFragment.isAdded()) {
            fragmentMgr.beginTransaction().hide(currentFragment)
                    .show(meFragment).commit();
        } else {
            fragmentMgr.beginTransaction().hide(currentFragment)
                    .add(R.id.frame_home, meFragment).commit();
        }

        currentFragment = meFragment;

        btnPublish.setSelected(false);
        btnHome.setSelected(false);
        btnMe.setSelected(true);
    }

    @Override
    protected void handlerMessage(Message msg) {

    }
}
