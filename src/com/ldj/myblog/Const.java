package com.ldj.myblog;

public class Const {

    public static class Message {
        public static final int MSG_NETWORK_AVAILABLE = 1;
        public static final int MSG_LOGIN_SUCC = 2;
        public static final int MSG_LOGIN_FAIL = 3;
        public static final int MSG_PUBLISH_SUCC = 4;
        public static final int MSG_PUBLISH_FAIL = 5;

    }

    public static class Request {
        public static final int REQUEST_SUCC = 200;
        public static final int REQUEST_FAIL = 201;
        //        public static final String SERVER = "http://jason.tunnel.mobi/blogserver";
        public static final String SERVER = "http://192.168.1.124:8888/blogserver";
        public static final String login = SERVER + "/users/login";
        public static final String publish = SERVER + "/posts";

    }

}
