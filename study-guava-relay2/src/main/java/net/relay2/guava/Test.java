package net.relay2.guava;

import java.text.ParseException;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class Test {
	public static void main(String[] args) throws ParseException {
		User a = new User();
		a.setAge(25);
		User b  =  new User();
		b.setAge(19);
		User c = new User();
		c.setAge(26);
		User d = new User();
		d.setAge(27);
		List<User> people = Lists.newArrayList(a,b,c,d);
		List<User> oldPeople = Lists.newArrayList(Collections2.filter(people, new Predicate<User>() {
			public boolean apply(User person) {
				return person.getAge() >= 20;
			}
		}));
		System.out.println(oldPeople);
		
	}
}
