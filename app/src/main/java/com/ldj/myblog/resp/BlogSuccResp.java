package com.ldj.myblog.resp;

import com.ldj.myblog.model.PagerBlogs;

public class BlogSuccResp extends BaseResp {
	PagerBlogs data;

	public PagerBlogs getData() {
		return data;
	}

	public void setData(PagerBlogs data) {
		this.data = data;
	}

}
