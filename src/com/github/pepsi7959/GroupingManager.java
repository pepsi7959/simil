package com.github.pepsi7959;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GroupingManager {

	String filename = "";
	LinkedList<String> datasets;
	ArrayList<Pair> outputs;

	double threshole = 0.8;

	public GroupingManager(String filename, double threshole) {
		this.filename = filename;
		this.datasets = new LinkedList<String>();
		this.threshole = threshole;
	}

	public void readFile() {
		File file = new File(this.filename);
		BufferedReader br;
		this.datasets = new LinkedList<String>();
		try {
			
			br = new BufferedReader(new FileReader(file));
			String header = br.readLine();
			String line = "";
			System.out.println("header: " + header);
			int header_sz = header.split(",").length;

			while ((line = br.readLine()) != null) {

				System.out.println(line);
				String str[] = line.split(",");

				/*
				 * TODO: Ignore checking size if( str.length != header_sz ) { br.close(); throw
				 * new RuntimeException(String.
				 * format("Invalid input: size of header is not match header(%s)\\{%d\\} input(%s)\\{%d\\}"
				 * , header, header_sz, line, str.length)); }
				 */
				
				String s = str[0].trim();
				datasets.addLast(s);
			}
			System.out.println("Number of elements : " + datasets.size());
			br.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void group() {
		ArrayList<Pair> groupedDatasets = new ArrayList<Pair>();
		LinkedList<String> datasets = this.datasets;
		int task = datasets.size();
		while (datasets.size() > 0) {
			System.out.println("============== %"+(double)(task-datasets.size())/(double)task+"==============");
			String first = datasets.removeFirst();
			groupedDatasets.add(new Pair(first, first, 1.0));
			LinkedList<String> tmpDatasets = new LinkedList<String>();
			
			while (datasets.size() > 0) {
				String next = datasets.removeFirst();
				double s = new Similarity(first, next).simil();
				//System.out.println("Similarity: " + first + ", " + next + " : " + s);
				
				if (s > this.threshole) {
					groupedDatasets.add(new Pair(next, first, s));
				} else {
					tmpDatasets.add(next);
				}

			}
			datasets = tmpDatasets;
		}
		this.outputs = groupedDatasets;
	}

	public void println() {
		Iterator<Pair> outs = this.outputs.iterator();
		int i = 1;
		while (outs.hasNext()) {
			Pair p = outs.next();
			System.out.println(String.format("%d) %s , %s, %f", i++, p.s1, p.s2, p.similarity));
		}
	}

}
