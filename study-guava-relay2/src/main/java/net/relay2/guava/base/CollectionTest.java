package net.relay2.guava.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;

public class CollectionTest {
	public static void main(String[] args) {
		BiMap<String, Integer> userId = HashBiMap.create();
		
		userId.put("test", 2);
		userId.put("jason", 3);
		
		String userForId = userId.inverse().get(3);
		
		System.out.println(String.format("the id is 3, the user is %s ", userForId));
		
		//ContigousSet.create(Range.open(1, 5), DiscreteDomain.integers());
		
		ListMultimap<Integer, String> listMultimap =  ArrayListMultimap.create();
		listMultimap.put(1,"2");
		listMultimap.put(1,"3");
		listMultimap.put(1,"4");
		
		listMultimap.put(2,"x");
		
		System.out.println(listMultimap.asMap());
		
		System.out.println(listMultimap.values());
		
		List<String> test = new ArrayList<> ();
		test.add("zhou");
		test.add("jian");
		//Iterators.addAll(test, iterator)
		//test.remove(0);
		test.add("zhou");
		
		//Arrays.asList(a)
		System.out.printf("the size of test is %d  %n", Iterators.size(test.iterator()));
		int fre = Collections.frequency(test, "zhou");
		
		System.out.printf("the frequency of zhou  is %d  %n", fre);
		
		fre = Iterators.frequency(test.iterator(), "zhou");
		
		System.out.printf("the frequency of zhou  is %d  %n", fre);
		
		fre = Iterables.frequency(test, "zhou");
		
		System.out.printf("the frequency of zhou(using Iterables)  is %d  %n", fre);
		
		
		List<Integer> intList = Lists.newArrayList(2,3,5,6,7,8,9);
		
		List<Integer> gtFive = Lists.newArrayList(Collections2.filter(intList, new Predicate<Integer>() {
            public boolean apply(Integer i) {
                return i >5;
            }
        }));
		
		System.out.printf("the greather than 5 in the list  is %s  %n", gtFive);
		

	}
}
