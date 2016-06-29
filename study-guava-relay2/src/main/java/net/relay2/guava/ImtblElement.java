package net.relay2.guava;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ImtblElement {
	private final static Map<String,String> modelMap;
	static {
		Map<String,String> test = new HashMap<>();
		init(test);
		modelMap = ImmutableMap.copyOf(test);
	}
	private static void init(Map<String,String> tmp) {
		tmp.put("1","test");
		tmp.put("2","tear");
		tmp.put("3","tmp");
	}
	public static Map<String, String> getModelmap() {
		return modelMap;
	}
	public static void main(String[] args) {
		Map<String,String> test = ImtblElement.getModelmap();
		System.out.println(test);
		test.put("4", "mmm");
	}
	
}
