package net.relay2.ref;

import java.lang.reflect.Field;

public class Test {
	private String readOnly;

	public String getReadOnly() {
		return readOnly;
	}

	public static void main(String[] args)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
	}
} 