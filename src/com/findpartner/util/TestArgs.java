package com.findpartner.util;

public class TestArgs {
	
	public static void main(String[] args) {
		String a[]={"chen","lin","qing"};
		test(a);
	}
	public static void test(String ... args){
		for (String s : args) {  
            System.out.println(s);  
        } 
	}
}
