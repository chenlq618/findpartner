package com.findpartner.util;

import redis.clients.jedis.Jedis;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*
		String format =  "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
		String str="2013-12-12 01:01:01";
		
		
		String format2="(0[1-9])|(1[0-2])";
		//format2="1[0-2]";
		String str2="11";

*/

		
		Jedis jedis = new Jedis("139.129.129.15");
		//Jedis jedis = new Jedis("localhost");
		jedis.set("12345678901","dfads");
		//jedis.expire("12345678901", 10);
		System.out.println(jedis.get("12345678901"));
		
		
		
		/*
		HashMap<String,Integer> a1=new HashMap<String,Integer>();
		a1.put("1", 1);
		a1.put("2",2);
		HashMap<String,Integer> a2=new HashMap<String,Integer>();
		a2.put("3",3);
		a2.put("4",4);
		a2.put("1",5);
		
		a1.putAll(a2);
		
		System.out.println(a2.keySet());
		*/
		
		/*
		 * {"message":"你已经加入到活动聊天室","extra":"helloExtra"}

		HashMap params2 = new HashMap();
		params2.put("fromUserId", "admin");
		params2.put("toGroupId", 1);
		params2.put("objectName", "RC%3AInfoNtf");
		params2.put(
				"content",
				"%7B%22message%22%3A%22%E4%BD%A0%E5%B7%B2%E7%BB%8F%E5%8A%A0%E5%85%A5%E5%88%B0%E6%B4%BB%E5%8A%A8%E8%81%8A%E5%A4%A9%E5%AE%A4%22%2C%22extra%22%3A%22helloExtra%22%7D");
		RonghubUtil.post("https://api.cn.ronghub.com/message/group/publish.json", params2);
		*/
		/*
		String aa="%7B%22message%22%3A%22%E4%BD%A0%E5%B7%B2%E7%BB%8F%E5%8A%A0%E5%85%A5%E5%88%B0%E6%B4%BB%E5%8A%A8%E8%81%8A%E5%A4%A9%E5%AE%A4%22%2C%22extra%22%3A%22helloExtra%22%7D";
		//String b=new String(aa.getBytes("utf8"),"gbk");
		//System.out.println(b);
		JSONObject j=new JSONObject();
		j.put("message", "你已经加入到活动聊天室");
		j.put("extra", "helloExtra");
		 
		 String c=URLEncoder.encode(j.toString());
		 
		 System.out.println(c);
		 
		 if(aa.equals(c)){
			 System.out.println("equal");
		 }else{
			 System.out.println("not");
		 }
		*/
		//String c="陈林清";
		//System.out.println(new String(c.getBytes("utf8"),"gbk"));
		//JSONObject json = JSONObject.fromObject("%7B%22message%22%3A%22%E4%BD%A0%E5%B7%B2%E7%BB%8F%E5%8A%A0%E5%85%A5%E5%88%B0%E6%B4%BB%E5%8A%A8%E8%81%8A%E5%A4%A9%E5%AE%A4%22%2C%22extra%22%3A%22helloExtra%22%7D");
		//System.out.println(json.toString());
	}


}
