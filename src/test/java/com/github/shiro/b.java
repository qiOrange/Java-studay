package com.github.shiro;


public class  b{
		public static int count=0;
		static{
			count++;
		}
	public b() {
		count++;
		}

	public static void main(String[] args) {
		int count2 = b.count;
		System.out.println();
	}
}