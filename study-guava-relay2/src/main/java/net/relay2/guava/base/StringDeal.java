package net.relay2.guava.base;

import com.google.common.base.CharMatcher;

public class StringDeal {

	public static void main(String[] args) {
		
		
		String test = CharMatcher.inRange('a', 'z').removeFrom("mmmzzz134aa5666");
		
		System.out.printf("the string mmmzzz1345666 is removed 'a-z' is %s %n", test);
		
		
		test = CharMatcher.is('a').collapseFrom("aaazzz134aa5666", '*');
		
		System.out.printf("the string aaazzz134aa5666 is collapsed by 'a' is %s %n", test);
		
		
		test = CharMatcher.WHITESPACE.trimFrom(" aaa   bbb   bbbbcccc    ");
		
		System.out.printf("the string aaa   bbb   bbbbcccc     is trimed  is [%s] %n", test);
		
		
		test = CharMatcher.WHITESPACE.removeFrom(" aaa   bbb   bbbbcccc    ");
		
		System.out.printf("the string aaa   bbb   bbbbcccc     is removed  is [%s] %n", test);
		
		String str = "Abbb 111 3333 22222      ";
		
		test = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(str);
		
		System.out.printf("the string %s is retained   is [%s] %n",str ,test);
		

	}

}
