package com.github.pepsi7959;

public class Main {
	public static void main(String[] args) {
		GroupingManager gm = new GroupingManager("C:\\Users\\narongsak.mala\\Desktop\\original-procedure-0-1000.txt", 0.65);
		gm.readFile();
		gm.group();
		gm.write();
		
		gm = new GroupingManager("C:\\Users\\narongsak.mala\\Desktop\\original-procedure-1001-2000.txt", 0.65);
		gm.readFile();
		gm.group();
		gm.write();
		
		gm = new GroupingManager("C:\\Users\\narongsak.mala\\Desktop\\original-procedure-2001-3000.txt", 0.65);
		gm.readFile();
		gm.group();
		gm.write();
		
		gm = new GroupingManager("C:\\Users\\narongsak.mala\\Desktop\\original-procedure-3001-4398.txt", 0.65);
		gm.readFile();
		gm.group();
		gm.write();
		
		
	}
}
