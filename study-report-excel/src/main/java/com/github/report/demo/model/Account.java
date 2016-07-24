package com.github.report.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
	 private String name;
	 private Integer apNumbers;
	 private List<Alert> alerts = new ArrayList<Alert>();
	 
	public Account(String theName, int theApNumbers){
		name = theName;
		apNumbers = theApNumbers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getApNumbers() {
		return apNumbers;
	}
	public void setApNumbers(Integer apNumbers) {
		this.apNumbers = apNumbers;
	}
	public List<Alert> getAlerts() {
		return alerts;
	}
	 
	 
}
