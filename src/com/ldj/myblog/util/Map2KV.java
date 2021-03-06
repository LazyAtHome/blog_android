package com.ldj.myblog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Map2KV {

	public static String map(Map<String, Object> map) {
		String endFix = "";
		if (map == null || map.size() == 0) {
			return endFix;
		}
		endFix += "?";
		Set<Entry<String, Object>> entries = map.entrySet();
		Iterator<Entry<String, Object>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			try {
				endFix += entry.getKey() + "=" + entry.getValue() + "&";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (endFix.endsWith("&")) {
			endFix = endFix.substring(0, endFix.length() - 1);
		}

		return endFix;
	}
}
