/**
 * 
 */
package com.examples.redis.domain;

/**
 * @author Administrator
 *
 */
public class User {
	
	private String  name;
	private int age;
	
	public User() {
	}
	public User(String name, int age) {
		this.name = name;
		this.age  =age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
