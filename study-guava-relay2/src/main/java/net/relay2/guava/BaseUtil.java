package net.relay2.guava;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class BaseUtil {
	public static void main(String[] args) {
		String []  array = ",a,,b,".split(",");
		
		System.out.printf("the size of split by , %d, the content is %s, %n", array.length, Arrays.asList(array) );
		
		
		Iterable<String> it = Splitter.on(',').omitEmptyStrings().split(",a,,b,");
		
		List<String> list = Lists.newArrayList(it);
		
		System.out.printf("the size of split by , %d, the content is %s, %n", list.size(), list );
		
		String matcher = CharMatcher.WHITESPACE.collapseFrom("xxx    xxxx    xxxx", ',');
		
		System.out.printf("the matcher is %s, %n", matcher);
	}
}
