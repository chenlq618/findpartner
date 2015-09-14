package com.findpartner.push;

import cn.jpush.api.JPushClient;

public class PushUtil {

	private static final String appKey = "c56ad4cd81873ae2e040e626";
	private static final String masterSecret = "aaae42f3b09de1825aef8d15";
	private static JPushClient jpushClient = new JPushClient(masterSecret,
			appKey, true, 1000);
	private static PushUtil instance = null;

	private PushUtil() {

	};

	public static PushUtil getInstance() {
		if (instance == null) {
			instance = new PushUtil();
		}
		return instance;
	}

	// 发到单个用户的通知（android）
	public void pushAndroid(String title, String info, String[] alias) {
		try {

			jpushClient.sendAndroidNotificationWithAlias(title, info, null,
					alias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 发到单个用户的通知（ios）
	public void pushIos(String title, String info, String[] alias) {
		try {
			// jpushClient.sendIosMessageWithAlias(title, info, null, alias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			jpushClient.sendAndroidNotificationWithAlias("title", "alert",
					null, "23");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
