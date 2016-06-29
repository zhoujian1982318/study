package net.relay2.redis;

public class TestInClass {
	
	public class Main {
		
	}
	
	public static class StaticMain {
		
		public static void main() {
			new TestInClass().print();

		}
	}
	
	public void print(){
		System.out.println("test.........");
	}
}
