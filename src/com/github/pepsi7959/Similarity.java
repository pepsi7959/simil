package com.github.pepsi7959;

public class Similarity {

	int tcnt = 0;

	String s1;
	String s2;

	public Similarity(String s1, String s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public double simil() {

		int tlen = 0, s1len = 0, s2len = 0;

		if (this.s1 == null || this.s2 == null) {
			return 0.0;
		}

		s1len = this.s1.length();
		s2len = this.s2.length();

		if (s1len == 0 || s2len == 0) {
			return 0.0;
		}

		tlen = s1len + s2len;

		// call recursive simil
		rsimil(this.s1, s1len, this.s2, s2len);

		return tcnt / (double) tlen;
	}

	private void rsimil(String s1, int s1len, String s2, int s2len) {

		Substring ss = new Substring();

		if (s1len == 0 || s2len == 0) {
			return;
		}

		find_biggest_substring(s1, s1len, s2, s2len, ss);

		if (ss.len > 0) {

			int delta1 = 0, delta2 = 0;
			this.tcnt += (ss.len * 2);

//			System.out.println(String.format("recursive half **LEFT**\n ss.len(%d), s1len(%d), s2len(%d)\n", ss.len,
//					ss.o1, ss.o2));
			//System.out.println(String.format("n1: %s, n2: %s\n", s1, s2));

			rsimil(s1, ss.o1, s2, ss.o2);

			delta1 = ss.o1 + ss.len;
			delta2 = ss.o2 + ss.len;

			if ((delta1 < s1len) && (delta2 < s2len) && delta1 > 0 && delta2 > 0) {
//				System.out.println(
//						String.format("recursive half **RIGHT**\n delta1(%d), s1len(%d), delta2(%d) s2len(%d)\n",
//								delta1, s1len, delta2, s2len));
				String ns1 = s1.substring((delta1 < 0) ? 0 : delta1);
				String ns2 = s2.substring((delta2 < 0) ? 0 : delta2);
//				System.out.println(String.format("n1: %s, n2: %s\n", ns1, ns2));
				rsimil(ns1, s1len - delta1, ns2, s2len - delta2);
			}
		}
	}

	private void find_biggest_substring(String s1, int s1len, String s2, int s2len, Substring ss) {
		int i, j, size = 1;
		ss.o2 = -1;
		// System.out.println("find_biggest_substring " + s1 + " s1len:" + s1len + " " +
		// s2 + " s2len:" + s2len);
		for (i = 0; i <= (s1len - size); i++) {
			for (j = 0; j <= (s2len - size); j++) {

				int test_size = size;

				while (true) {

					if (((test_size) <= (s1len - i)) && (test_size <= (s2len - j))) {
						String sub1 = s1.substring(i, i + test_size);
						String sub2 = s2.substring(j, j + test_size);
						// System.out.print(String.format("cmp: %s , %s\n", sub1, sub2));
						if (sub1.equalsIgnoreCase(sub2)) {
							if ((test_size > size) || (ss.o2 < 0)) {
								ss.o1 = i;
								ss.o2 = j;
								size = test_size;
//								System.out.println("Matched: " + sub1);
							}
							test_size++;
						} else {
							// System.out.println("break case! i:"+i+" j:"+j);
							break;
						}
					} else {
						// System.out.println("Break if i:"+i+" j:"+j);
						break;
					}
				}
			}
		}
		if (ss.o2 < 0) {
			ss.len = 0;
		} else {
			ss.len = size;
		}
	}

	private static class Substring {
		int o1 = 0;
		int o2 = 0;
		int len = 0;
	}

	public static void main(String[] args) {
		// String s1 = "ab123x456";
		// String s2 = "abz123ab1x456";
		String s1 = "การจดทะเบียนผู้ทำหน้าที่บรรทุกหรือขนถ่ายสินค้าเรือเดินทะเลต่างประเทศ";
		String s2 = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxการจดทะเบียนผู้ประกอบกิจการบรรทุกหรือขนถ่ายสินค้าในเขตท่าเรือระนอง พ.ศ. 2558";
		Similarity simil = new Similarity(s1, s2);
		System.out.println("size : " + s1.length());
		System.out.println(s1 + " vs " + s2 + " : " + simil.simil());
	}
}
