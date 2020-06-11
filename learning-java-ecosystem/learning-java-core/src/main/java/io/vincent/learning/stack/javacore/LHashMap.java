package io.vincent.learning.stack.javacore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 12/10/18.
 *
 * @author Vincent
 * @since 1.0, 12/10/18
 */
public class LHashMap {

	static Map<String, Integer> stringIntegerMap() {
		Map<String, Integer> map = new HashMap<>();

		map.put("algorithm", 0);
		map.put("java-code", 1);
		map.put("design-patterns", 2);
		map.put("java-concurrency", 3);
		map.put("JVM", 4);
		map.put("network", 1);
		map.put("mysql", 6);
		map.put("netty", 7);
		map.put("tomcat", 8);
		map.put("elasticsearch", 9);
		map.put("compilation", 10);
		map.put("architecture", 11);
		map.put("distribution-id", 12);
		map.put("distribution-cache", 13);
		map.put("distribution-lock", 14);
		map.put("distribution-transaction", 15);

		return map;
	}

	static Map<Integer, String> integerKeyMap() {
		Map<Integer, String> map = new HashMap<>();

		map.put(0, "algorithm");
		map.put(1, "java-code");
		map.put(2, "design-patterns");
		map.put(3, "java-concurrency");
		map.put(4, "JVM");
		map.put(5, "network");
		map.put(6, "mysql");
		map.put(7, "netty");
		map.put(8, "tomcat");
		map.put(9, "elasticsearch");
		map.put(10, "compilation");
		map.put(11, "architecture");
		map.put(12, "distribution-id");
		map.put(13, "distribution-cache");
		map.put(14, "distribution-lock");
		map.put(15, "distribution-transaction");

		return map;
	}

	static Map<String, Integer> stringKeyMap() {
		Map<String, Integer> map = new HashMap<>();

		map.put("语文", 1);
		map.put("数学", 2);
		map.put("英语", 3);
		map.put("历史", 4);
		map.put("政治", 5);
		map.put("地理", 6);
		map.put("生物", 7);
		map.put("化学", 8);

		return map;
	}

	public void m4() {
		A[] a2 = new A[3];
	}
}

class A {
	int x;
	int y;
}
