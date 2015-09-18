package com.ldj.myblog.resp;

import com.ldj.myblog.model.UserInfos;

/**
 * Created by Administrator on 2015/9/18.
 */
public class PublishSuccResp {
    UserInfos data;

    public UserInfos getData() {
        return data;
    }

    public void setData(UserInfos data) {
        this.data = data;
    }
}
