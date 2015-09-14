package com.findpartner.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

import redis.clients.jedis.Jedis;
import sun.misc.BASE64Encoder;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		String format =  "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
		String str="2013-12-12 01:01:01";
		
		
		String format2="(0[1-9])|(1[0-2])";
		//format2="1[0-2]";
		String str2="11";

*/

		
		Jedis jedis = new Jedis("120.24.181.246");
		//Jedis jedis = new Jedis("localhost");
		///jedis.set("12345678901","dfads");
		//jedis.expire("12345678901", 10);
		System.out.println(jedis.get("12345678901"));
		System.out.println(DigestUtils.md5Hex("123456"));
		
		
	}


}
