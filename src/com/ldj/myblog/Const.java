package com.ldj.myblog;

public class Const {
	
	public static final int PAGE_SIZE = 10;

	public static class Message {
		public static final int MSG_NETWORK_AVAILABLE = 1;
		public static final int MSG_LOGIN_SUCC = 2;
		public static final int MSG_LOGIN_FAIL = 3;
		public static final int MSG_PUBLISH_SUCC = 4;
		public static final int MSG_PUBLISH_FAIL = 5;
		public static final int MSG_ALL_SUCC = 6;
		public static final int MSG_ALL_FAIL = 7;
		public static final int MSG_MORE_BLOG_SUCC = 8;
		public static final int MSG_MORE_BLOG_FAIL = 9;

	}

	public static class Request {
		public static final int REQUEST_SUCC = 200;
		public static final int REQUEST_FAIL = 201;
//		public static final String SERVER = "http://jason.tunnel.mobi/blogserver";
//		 public static final String SERVER =
//		 "http://192.168.1.124:8888/blogserver";
		 public static final String SERVER =
				 "http://192.168.1.183:8080/blogserver";
		public static final String login = SERVER + "/users/login";
		public static final String publish = SERVER + "/posts";
		public static final String all = SERVER + "/posts/all";

	}

}
