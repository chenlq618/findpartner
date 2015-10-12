package com.findpartner.util;

public class Constants {

	//http://findpartners.wicp.net:47396/findpartner/user/regist.json
	public static final int PAGE_SIZE=5;//分页操作，每页的大小
	public static final String Ip="139.129.129.15";
	public static final String Host="http://"+Ip+":8080/findpartner/";
	public static final String IconUrl=Host+"userIcon/";
	public static final String TeamIcon=Host+"teamIcon/";
	public static final String TeamImgs=Host+"teamimgs/";
	public static final String RedisIp="139.129.129.15";
	
	//极光相关参数
	public static final String jpushKey="717edbb881b71d16c43330ee";
	public static final String jpushSecret="5337b3e5c7a627f95fa4ed8e";
	
	
	//融云相关参数
	public static final String appKey="3argexb6rthxe";
	public static final String appSecret="6swV6ERc9knbMv";
	//token过期时间
	public static final int delTime=1000;
}
