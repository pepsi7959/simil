package com.github.pepsi7959;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GroupingManager {

	String filename = "";
	LinkedList<Pair> datasets;
	ArrayList<Pair> outputs;

	double threshole = 0.8;

	public GroupingManager(String filename, double threshole) {
		this.filename = filename;
		this.datasets = new LinkedList<Pair>();
		this.threshole = threshole;
	}

	public void readFile() {
		File file = new File(this.filename);
		BufferedReader br;
		this.datasets = new LinkedList<Pair>();
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
				Pair p = new Pair(str[0].trim(), "", 0.0);
				// String s = str[0].trim();
				datasets.addLast(p);
			}
			System.out.println("Number of elements : " + datasets.size());
			br.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void group() {

		ArrayList<Pair> groupedDatasets = new ArrayList<Pair>(datasets.size());
		LinkedList<Pair> datasets = this.datasets;
		LinkedList<Pair> tmpDatasets;
		int task = datasets.size();
		Similarity simil = new Similarity();

		while (datasets.size() > 0) {
			System.out.println(String.format("> %.3f%%\n", (double) (task - datasets.size()) / (double) task));
			Pair first = datasets.removeFirst();
			first.init(first.s1, first.s1, 1.0);
			groupedDatasets.add(first);

			tmpDatasets = new LinkedList<Pair>();

			while (datasets.size() > 0) {
				Pair next = datasets.removeFirst();
				simil.init(first.s1, next.s1);
				double s = simil.simil();
				// System.out.println("Similarity: " + first + ", " + next + " : " + s);

				if (s > this.threshole) {
					next.init(next.s1, first.s1, s);
					groupedDatasets.add(next);
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
			System.out.println(String.format("%d,%s,%s,%f", i++, p.s1, p.s2, p.similarity));
		}
	}

	public void write() {
		Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.filename +"-threshold-"+this.threshole+".csv"), "UTF-8"));
			Iterator<Pair> outs = this.outputs.iterator();
			int i = 1;
			while (outs.hasNext()) {
				Pair p = outs.next();
				out.write(String.format("%d,%s,%s,%f\n", i++, p.s1, p.s2, p.similarity));
			}
			out.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
