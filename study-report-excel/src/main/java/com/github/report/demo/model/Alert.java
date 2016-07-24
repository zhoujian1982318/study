package com.github.report.demo.model;

public class Alert {
	private int mon;
	private int tue;
	private int wed;
	private int thur;
	private int fri;
	
	public Alert(int theMon, int theTue, int theWed, int theThur, int theFri){
		mon = theMon;
		tue = theTue;
		wed = theWed;
		thur = theThur;
		fri =  theFri;
	}

	public int getMon() {
		return mon;
	}

	public void setMon(int mon) {
		this.mon = mon;
	}

	public int getTue() {
		return tue;
	}

	public void setTue(int tue) {
		this.tue = tue;
	}

	public int getWed() {
		return wed;
	}

	public void setWed(int wed) {
		this.wed = wed;
	}

	public int getThur() {
		return thur;
	}

	public void setThur(int thur) {
		this.thur = thur;
	}

	public int getFri() {
		return fri;
	}

	public void setFri(int fri) {
		this.fri = fri;
	}
}
