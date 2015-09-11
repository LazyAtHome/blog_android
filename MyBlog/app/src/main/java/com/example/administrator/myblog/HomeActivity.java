package com.example.administrator.myblog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/9/11.
 */
public class HomeActivity extends FragmentActivity implements PublishFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {

    Fragment currentFragment;
    PublishFragment publishFragment;
    HomeFragment homeFragment;
    MeFragment meFragment;

    Button btnPublish;
    Button btnHome;
    Button btnMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    private void initView() {
        btnPublish = (Button) findViewById(R.id.btn_publish);
        btnHome = (Button) findViewById(R.id.btn_home);
        btnMe = (Button) findViewById(R.id.btn_me);
        btnPublish.setOnClickListener(btnClick);
        btnHome.setOnClickListener(btnClick);
        btnMe.setOnClickListener(btnClick);
        switchToHome();
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_publish:
                    switchToPublish();
                    break;

                case R.id.btn_home:
                    switchToHome();
                    break;

                case R.id.btn_me:
                    switchToMe();
                    break;
            }
        }
    };

    private void switchToPublish() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (publishFragment != null) {
            ft.remove(publishFragment);
            publishFragment = null;
        }
        if (publishFragment == null) {
            publishFragment = PublishFragment.newInstance("", "");
            ft.add(R.id.frame_home, publishFragment);
        }
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        currentFragment = publishFragment;
        ft.show(currentFragment);
        ft.commit();

        btnPublish.setSelected(true);
        btnHome.setSelected(false);
        btnMe.setSelected(false);
    }

    private void switchToHome() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (homeFragment != null) {
            ft.remove(homeFragment);
            homeFragment = null;
        }
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance("", "");
            ft.add(R.id.frame_home, homeFragment);
        }
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        currentFragment = homeFragment;
        ft.show(currentFragment);
        ft.commit();

        btnPublish.setSelected(false);
        btnHome.setSelected(true);
        btnMe.setSelected(false);
    }

    private void switchToMe() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (meFragment != null) {
            ft.remove(meFragment);
            meFragment = null;
        }
        if (meFragment == null) {
            meFragment = MeFragment.newInstance("", "");
            ft.add(R.id.frame_home, meFragment);
        }
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        currentFragment = meFragment;
        ft.show(currentFragment);
        ft.commit();

        btnPublish.setSelected(false);
        btnHome.setSelected(false);
        btnMe.setSelected(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("down");
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
