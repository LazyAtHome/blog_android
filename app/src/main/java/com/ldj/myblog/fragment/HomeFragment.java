package com.ldj.myblog.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ldj.myblog.Const;
import com.ldj.myblog.R;
import com.ldj.myblog.activity.BlogDetailActivity;
import com.ldj.myblog.activity.MyActivity;
import com.ldj.myblog.adapter.BlogListAdapter;
import com.ldj.myblog.helper.JsonHelper;
import com.ldj.myblog.model.Blog;
import com.ldj.myblog.net.MyVolley;
import com.ldj.myblog.resp.BlogSuccResp;
import com.ldj.myblog.view.XListView;

import java.util.List;


/**
 * Created by Administrator on 2015/9/11.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, XListView.IXListViewListener {

    Button btnRight;
    XListView mBlogList;

    List<Blog> blogs;
    //    HomeBlogAdapter blogAdapter;
    BlogListAdapter blogListAdapter;
    MyVolley allVolley;
    MyVolley moreBlogVolley;
    private static final int REQUEST_DETAIL = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            setView(inflater.inflate(R.layout.fragment_home, container,
                    false));
            initMyViews(getView());
        }
        return getView();
    }

    private void initMyViews(View view) {
        Button btnLeft = (Button) view.findViewById(R.id.btn_title_left);
        btnRight = (Button) view.findViewById(R.id.btn_title_right);
        TextView textTitle = (TextView) view.findViewById(R.id.text_title);
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("我的");
        btnRight.setOnClickListener(this);
        textTitle.setText("首页");
        mBlogList = (XListView) view.findViewById(R.id.list_blog_home);
        mBlogList.setOnItemClickListener(this);
        // mBlogList.setAdapter(blogAdapter);
        mBlogList.setXListViewListener(this);
        onRefresh();
    }

    @Override
    public void onClick(View view) {
        if (view == btnRight) {
            startActivity(new Intent(getActivity(), MyActivity.class));
        }
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case Const.Message.MSG_ALL_SUCC:

                if (msg.arg1 == Const.Request.REQUEST_SUCC) {
                    mBlogList.stopRefresh(getResources().getString(R.string.refresh_succ));
                    BlogSuccResp blogs = (BlogSuccResp) JsonHelper.jsonToObject(
                            msg.obj + "", BlogSuccResp.class);
//                    blogAdapter.setBlogs(blogs.getData().getPosts());
//                    mBlogList.setAdapter(blogAdapter);
                    blogListAdapter.setBlogs(blogs.getData().getPosts());
                    mBlogList.setAdapter(blogListAdapter);
                    if (blogs.getData().getPageInfo().isHasNextPage()) {
                        mBlogList.setPullLoadEnable(true);
                        moreBlogVolley.addParams("page", blogs.getData()
                                .getPageInfo().getNextPage());
                    } else {
                        mBlogList.setPullLoadEnable(false);

                    }
                } else if (msg.arg1 == Const.Request.REQUEST_FAIL) {
                    mBlogList.stopRefresh(getResources().getString(R.string.refresh_fail));
                }
                break;

            case Const.Message.MSG_ALL_FAIL:
                mBlogList.stopRefresh(getResources().getString(R.string.refresh_fail));

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

    @Override
    protected void initFragmentDatas() {
//        blogAdapter = new HomeBlogAdapter(getActivity());
        blogListAdapter = new BlogListAdapter(getActivity());
        allVolley = new MyVolley(getActivity(), Const.Message.MSG_ALL_SUCC,
                Const.Message.MSG_ALL_FAIL);

        allVolley.addParams("limit", Const.PAGE_SIZE);


        moreBlogVolley = new MyVolley(getActivity(),
                Const.Message.MSG_MORE_BLOG_SUCC,
                Const.Message.MSG_MORE_BLOG_FAIL);
        moreBlogVolley.addParams("limit", Const.PAGE_SIZE);
    }

    private void loadMoreList(List<Blog> blogs) {
        mBlogList.stopLoadMore();
        if (blogs == null) {
            return;
        }
//        blogAdapter.getBlogs().addAll(blogs);
//        blogAdapter.notifyDataSetChanged();
        blogListAdapter.getBlogs().addAll(blogs);
        blogListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        allVolley.addParams("page", 1);
        allVolley.requestGet(Const.Request.all, getHandler());
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
        startActivityForResult(intent,REQUEST_DETAIL);

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
                            getResources().getText(R.string.delete_succ),
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    }


}
