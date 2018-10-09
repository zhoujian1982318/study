package com.examples.thread;

import java.util.*;

public class PriorityQueueTest {
	
	
		public static void main(String[] args) {
			PriorityQueue<Integer> q = new PriorityQueue<Integer>(2);
			System.out.println("the size of queue " + q.size());
			
			List<Book> books  = new ArrayList<>();
			Book book1 = new Book();
			book1.setName("java in action");
			book1.setAge(10);
			
			Book book2 = new Book();
			book2.setName("spring  in action");
			book2.setAge(5);
			
			books.add(book1);
			books.add(book2);
			Collections.sort(books,(Book a,  Book b)->{
				return a.getAge() - b.getAge();
			});

			q.offer(3);
			q.offer(1);
			q.offer(5);
			q.offer(4);

			for(Iterator<Integer> item = q.iterator(); item.hasNext();){
				System.out.println(item.next());
			}

			System.out.println("the size of queue " + q.size());

			while (!q.isEmpty()){

				System.out.println(q.poll());
			}
			
			System.out.println(books);
		}
}
