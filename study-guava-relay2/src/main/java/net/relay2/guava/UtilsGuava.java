package net.relay2.guava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

public class UtilsGuava {

	public static void main(String[] args) {
		String b = null;
		Optional<String> possible = Optional.fromNullable(b);
	
		String a = possible.or("test");
		System.out.println(a);
		
		
		String x = Objects.firstNonNull(null, "second");
		System.out.println(String.format("if first is null return %s", x));
		
		x = Objects.firstNonNull("first", "second");
		System.out.println(String.format("if first is not null return %s", x));
		
		boolean exp = Strings.isNullOrEmpty("");
		System.out.println(String.format("the string is empty, return %s" , exp));
		
		exp = Strings.isNullOrEmpty("1");
		System.out.println(String.format("the string is not null, return %s" , exp));
		
		String xx = null;
		// exp = xx.equals("2"), throw null exception;
		exp = Objects.equal(xx, 2);
		System.out.println(String.format("when the xx is null, return %s" , exp));
		
		User user = new User();
		user.setId("0001");
		user.setEmail("2667446@qq.com");
		System.out.println(String.format("the toSting of user is %s" , user.toString()));
		
		
		User user1 = new User();
		user1.setId("0001");
		user1.setUsername("a");
		
		User user2 = new User();
		user2.setId("0001");
		user2.setUsername("b");
		
		int i = user1.compareTo(user2);
		System.out.printf("user1 compare with use2 ,the result  is %-5dxxx %n" , i);
		
		List<String> list = new ArrayList<String> ();
		
		list.add("a");
		
		List<String> imList =   Collections.unmodifiableList(list);
		System.out.println(imList.toString());
		
		list.add("b");
		
		System.out.println(imList.toString());
		
		//imList.add("c");
		
		ImmutableList<String>  im2List = ImmutableList.of("a","b");
		
		System.out.println(im2List);
		
		
		
			
	}
}
