package com.ldj.myblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldj.myblog.R;
import com.ldj.myblog.model.Blog;

import java.util.List;

public class BlogListAdapter extends BaseAdapter {
    Context context;

    public BlogListAdapter(Context context) {
        this.context = context;
    }

    List<Blog> blogs;


    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return blogs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return blogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Blog blog = (Blog) getItem(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_blog_list, null);
            initMyHolder(convertView, holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        setMyHolder(holder, blog);

        return convertView;
    }

    private void initMyHolder(View view, Holder holder) {
//		holder.profile = (ImageView) view
//				.findViewById(R.id.iv_blog_list_profile);
        holder.userName = (TextView) view
                .findViewById(R.id.text_blog_list_username);
        holder.createTime = (TextView) view
                .findViewById(R.id.text_blog_list_time);
        holder.title = (TextView) view
                .findViewById(R.id.text_blog_list_title);
        holder.content = (TextView) view
                .findViewById(R.id.text_blog_list_context);
//		holder.reponse = (TextView) view
//				.findViewById(R.id.tv_list_blog_response);
        view.setTag(holder);
    }

    private void setMyHolder(Holder holder, Blog blog) {
        holder.userName.setText("From " + blog.getCreatedBy());
        holder.createTime.setText(blog.getCreatedDate());
        holder.title.setText(blog.getTitle());
        holder.content.setText(blog.getContent());
//		holder.reponse.setText(context.getResources().getString(
//				R.string.lable_vistors)
//				+ "(" + blog.getViewCount() + ")");
    }

    class Holder {
        public ImageView profile;
        public TextView userName;
        public TextView createTime;
        public TextView title;
        public TextView content;
        public TextView reponse;
    }

}
