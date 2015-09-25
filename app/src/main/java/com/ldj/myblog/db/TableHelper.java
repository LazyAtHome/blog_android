package com.ldj.myblog.db;

/**
 * 表单帮助类
 * 
 * @author Mars
 * 
 */
public class TableHelper {
	/***
	 * @author peng
	 * 
	 */
	public class BlogTable {
		public static final String TABLENAME = "blogTable";
		public static final String BLOGID = "blogId";
		public static final String CREATER = "creater";
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
		public static final String READTIME = "readtime";
		public static final String STATE = "state";
		public static final String CREATETIME = "createTime";
		public static final String INFO_ID = "_id";
		public static final String CREATETABLESQL = "CREATE TABLE IF NOT EXISTS "
				+ TableHelper.BlogTable.TABLENAME
				+ "("
				+ INFO_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ BLOGID
				+ " INTEGER,"
				+ CREATER
				+ " TEXT,"
				+ TITLE
				+ " TEXT,"
				+ CONTENT + " TEXT,"
				+ CREATETIME + " TEXT,"
				+ STATE + " INTEGER," + READTIME + " TEXT" + ")";
		
		public static final String DELETETABLESQL = "DROP TABLE IF EXISTS "
				+ TABLENAME;
	}

}
