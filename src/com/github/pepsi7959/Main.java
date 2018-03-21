package com.github.pepsi7959;

public class Main {
	public static void main(String[] args) {
		GroupingManager gm = new GroupingManager("C:\\Users\\narongsak.mala\\Documents\\ProceduralOnly.csv", 0.7);
		gm.readFile();
		gm.group();
		gm.println();
	}
}
