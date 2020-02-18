package com.huadong.hdgate.test;

import org.junit.Test;

public class StrSubTest {
	@Test
	public void subStrTest(){
		String str = "ftp://127.0.0.1/g01/gate/";
		System.out.println(str.indexOf("ftp://"));
		String s = str.substring(6);
		System.out.println(s);
		int i = s.indexOf("/");
		System.out.println(i);
		System.out.println(s.substring(i+1));
		
		System.out.println(str.endsWith("/"));
		
		String sss = null;
		//System.out.println(sss.indexOf("ftp"));
	}
	
	@Test
	public void toUper(){
		String str = "鲁f1abcdefghijklmnopqrstuvwxtyz23";
		str = str.toUpperCase();
		System.out.println(str);
	}

	@Test
	public void numberTest(){
		int a = 88;
		int b = 55;
		System.out.println((a-b)*100*100/88);
	}
}
