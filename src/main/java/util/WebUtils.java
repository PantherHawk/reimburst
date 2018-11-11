package util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class WebUtils {

	public static Map<String, String> getHeadersInfo(HttpServletRequest req) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		Enumeration headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = req.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
	
	public static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
}
