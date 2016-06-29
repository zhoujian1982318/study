package net.relay2.guava.base;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

public class RangTest {
	
	public static void main(String[] args) {
		RangeSet<Integer> rangeSet = TreeRangeSet.create();
		rangeSet.add(Range.closed(1, 10)); // {[1,10]}
		rangeSet.add(Range.closed(20, 34)); // {[1,10]}
		boolean ex = rangeSet.contains(32);
		System.out.printf("32 exist in rang [1,10] %b %n", ex);
		Range<Integer> r = rangeSet.rangeContaining(21);
		System.out.printf("the range contain 2  is  %s %n", r);
		r = rangeSet.span();
		System.out.printf("range set span is %s %n", rangeSet.span());
		
		
		RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.closed(1, 10), "foo");
		rangeMap.put(Range.open(3, 6), "bar"); 
		String value = rangeMap.get(2);
		System.out.println(String.format("the key is 2,  and the value is %s, ", value));
		value = rangeMap.get(7);
		System.out.println(String.format("the key is 7,  and the value is %s, ", value));
		value = rangeMap.get(4);
		System.out.println(String.format("the key is 4,  and the value is %s, ", value));
		value = rangeMap.get(5);
		System.out.println(String.format("the key is 5,  and the value is %s, ", value));
		
		Map.Entry<Range<Integer>, String> entry =  rangeMap.getEntry(4);
		System.out.println(String.format("the enty is %s", entry));
		
	}
}
