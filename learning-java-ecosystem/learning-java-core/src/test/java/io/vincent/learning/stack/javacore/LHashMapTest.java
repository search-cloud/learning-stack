package io.vincent.learning.stack.javacore;

import org.junit.jupiter.api.Test;

import java.util.Map;



/**
 * Created by Vincent on 12/11/18.
 *
 * @author Vincent
 * @since 1.0, 12/11/18
 */
public class LHashMapTest {

	@Test
	public void stringIntegerMap() {
		Map<String, Integer> map = LHashMap.stringIntegerMap();
		map.forEach((key, value) -> System.out.println(key+ ": "+ value));
	}

	@Test
	public void integerKeyMap() {
		Map<Integer, String> integerStringMap = LHashMap.integerKeyMap();
		integerStringMap.forEach((key, value) -> System.out.println(key + ": " + value));
	}

	@Test
	public void stringKeyMap() {
		Map<String, Integer> stringIntegerMap = LHashMap.stringKeyMap();
		stringIntegerMap.forEach((key, value) -> System.out.println(key + ": " + value));
	}
}
