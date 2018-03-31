package net.relay2.ref;

import java.lang.reflect.Field;

public class Test1 {

	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();  
	    Class<?> clazz = loader.loadClass("net.relay2.ref.Test");  
	    Test t = (Test)clazz.newInstance();  
		Field f = clazz .getDeclaredField("readOnly");
		f.setAccessible(true);
		f.set(t, "test1");
		System.out.println(t.getReadOnly());

	}

}
