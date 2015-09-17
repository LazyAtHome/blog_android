package com.ldj.myblog.resp;

import com.ldj.myblog.model.UserInfos;


/**
 * Created by Administrator on 2015/9/17.
 */
public class LoginSuccResp extends BaseResp {
    public UserInfos getData() {
        return data;
    }

    public void setData(UserInfos data) {
        this.data = data;
    }

    UserInfos data;
}
