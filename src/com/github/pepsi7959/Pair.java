package com.github.pepsi7959;

public class Pair {
	
	String s1 = "";
	String s2 = "";
	double similarity = 0.0;
	
	public Pair(String s1, String s2 , double similarity) {
		this.s1 = s1;
		this.s2 = s2;
		this.similarity = similarity;
	}
	
	public void init(String s1, String s2 , double similarity) {
		this.s1 = s1;
		this.s2 = s2;
		this.similarity = similarity;
	}
}
