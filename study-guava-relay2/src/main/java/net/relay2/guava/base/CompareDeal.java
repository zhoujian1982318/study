package net.relay2.guava.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class CompareDeal {
	
	public class InnerComparator implements Comparator<InnerNoComparable>{

		@Override
		public int compare(InnerNoComparable o1, InnerNoComparable o2) {
			if(o1==o2)
				return 0;
			return Ints.compare(o1.i, o2.i);
		}
		
	}
	public class InnerTest implements Comparable<InnerTest> {
		private int i;
		public InnerTest() {
		}
		public InnerTest(int theI) {
			this.i = theI;
		}
		@Override
		public int compareTo(InnerTest theObject) {
			if(theObject == null)
				return 1;
			return Ints.compare(i, theObject.i);
		}
		
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this).add("i", i).toString();
		}
		
		
	}
	
	public class InnerNoComparable  {
		private int i;
		public InnerNoComparable() {
		}
		public InnerNoComparable(int theI) {
			this.i = theI;
		}
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this).add("i", i).toString();
		}
	}
	
	
	public static void main(String[] args) {
		CompareDeal a = new CompareDeal();
		a.sortTest();
	}
	
	
	private void sortTest() {
		List<InnerTest>  list = new ArrayList<InnerTest> ();
		
		list.add(new InnerTest(3));
		list.add(new InnerTest(2));
		list.add(new InnerTest(5));
		
		// sort you can do this
		//Collections.sort(list);
		//System.out.printf("the sort list is %s %n", list);
		
		Ordering<InnerTest> naturalOrdering = Ordering.natural();
		InnerTest min = naturalOrdering.min(list);
		System.out.printf("the min element in the list is %s %n", min);
		
		
		List<InnerTest> greatest =  naturalOrdering.greatestOf(list, 2);
		System.out.printf("the max 2  element in the list is %s %n", greatest);
		
		List<InnerNoComparable>  noCmplist = new ArrayList<InnerNoComparable> ();
		
		noCmplist.add(new InnerNoComparable(3));
		noCmplist.add(new InnerNoComparable(2));
		noCmplist.add(new InnerNoComparable(5));
		noCmplist.add(new InnerNoComparable(10));
		// you can 't do this;
		//Ordering.natural().min(noCmplist);
		
		Ordering<InnerNoComparable> noCmpOrder = Ordering.natural().onResultOf(
				new Function<InnerNoComparable, Integer>() {
					@Override
					public Integer apply(InnerNoComparable input) {
						return input.i;
					}
					
		});
		InnerNoComparable minNoCmp =  noCmpOrder.min(noCmplist);
		System.out.printf("the min no comparable element is %s , %n", minNoCmp);
		// don't do this
		//TreeSet<InnerNoComparable> treeSet = new TreeSet<InnerNoComparable>();
		//treeSet.add(new InnerNoComparable(2));
		//you can do it 
		TreeSet<InnerNoComparable> treeSet = new TreeSet<InnerNoComparable>(
				new InnerComparator());
		treeSet.add(new InnerNoComparable(3));
		treeSet.add(new InnerNoComparable(2));
		
		System.out.println(treeSet);
		
	}
}
