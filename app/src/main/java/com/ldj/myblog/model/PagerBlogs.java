package com.ldj.myblog.model;

import java.util.List;

public class PagerBlogs {
	List<Blog> posts;
	PageInfo pageInfo;

	public List<Blog> getPosts() {
		return posts;
	}

	public void setPosts(List<Blog> posts) {
		this.posts = posts;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}
