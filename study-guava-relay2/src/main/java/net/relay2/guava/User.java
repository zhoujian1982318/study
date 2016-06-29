package net.relay2.guava;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class User implements Comparable<User> {
	private String id;
	private String username;
	private String phone;
	private String email;
	private int age;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("age", age).add("id", id).add("username", username)
			   .add("phone", phone).add("email", email).omitNullValues().toString();
	}
	@Override
	public int compareTo(User o) {
		return ComparisonChain.start().compare(id, o.id)
			   .compare(username, o.username).result();
	}
	
}
