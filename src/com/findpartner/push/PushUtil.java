package com.findpartner.push;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.findpartner.bean.PushInfo;
import com.findpartner.util.Constants;

public class PushUtil {

	private static final String appKey = Constants.jpushKey;
	private static final String masterSecret = Constants.jpushSecret;
	private static JPushClient jpushClient = new JPushClient(masterSecret,
			appKey, false, 1000);
	private static PushUtil instance = null;

	private PushUtil() {

	};

	public synchronized static PushUtil getInstance() {
		if (instance == null) {
			instance = new PushUtil();
		}
		return instance;
	}

	public void applyJoin(PushInfo leader, PushInfo member) {
		// to leader push

		try {
			PushPayload leaderPayload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(leader.getPhone()))
					.setNotification(Notification.alert("申请信息"))
					.setMessage(
							Message.newBuilder()
									.setMsgContent(leader.getInfo()).build())
					.build();
			jpushClient.sendPush(leaderPayload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// to member push
		if (member != null) {
			try {
				PushPayload memberPayload = PushPayload
						.newBuilder()
						.setPlatform(Platform.all())
						.setAudience(Audience.alias(member.getPhone()))
						.setNotification(Notification.alert("通知"))
						.setMessage(
								Message.newBuilder()
										.setMsgContent(member.getInfo())
										.build()).build();
				jpushClient.sendPush(memberPayload);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void leaderCheck(PushInfo leader, PushInfo member) {
		// to leader push
		try {
			PushPayload leaderPayload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(leader.getPhone()))
					.setNotification(Notification.alert("通知"))
					.setMessage(
							Message.newBuilder()
									.setMsgContent(leader.getInfo()).build())
					.build();
			jpushClient.sendPush(leaderPayload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// to member push
		try {
			PushPayload memberPayload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(member.getPhone()))
					.setNotification(Notification.alert("通知"))
					.setMessage(
							Message.newBuilder()
									.setMsgContent(member.getInfo()).build())
					.build();
			jpushClient.sendPush(memberPayload);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void dismissTeam(int teamId, String teamName) {
		try {
			PushPayload allPayload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.tag("" + teamId))
					.setNotification(Notification.alert("通知"))
					.setMessage(
							Message.newBuilder()
									.setMsgContent("【" + teamName + "】已解散")
									.build()).build();
			jpushClient.sendPush(allPayload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exitTeam(PushInfo leader, PushInfo member) {
		// to leader push
		try {
			PushPayload leaderPayload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(leader.getPhone()))
					.setNotification(Notification.alert("通知"))
					.setMessage(
							Message.newBuilder()
									.setMsgContent(leader.getInfo()).build())
					.build();
			jpushClient.sendPush(leaderPayload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// to member push
		try {
			PushPayload memberPayload = PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(member.getPhone()))
					.setNotification(Notification.alert("通知"))
					.setMessage(
							Message.newBuilder()
									.setMsgContent(member.getInfo()).build())
					.build();
			jpushClient.sendPush(memberPayload);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

	}
	/*
	 * // 发到单个用户的通知（ios） public void pushIos(String title, String info, String
	 * alias) { try { PushPayload payload = buildPushObject_all_alias_alert();
	 * jpushClient.sendPush(payload); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 * 
	 * public static void main(String[] args) { try {
	 * 
	 * PushUtil.getInstance().pushIos("title:test", "info:臭老鼠", "23456789012");
	 * System.out.println("=====================finish"); } catch (Exception e)
	 * { e.printStackTrace(); } }
	 * 
	 * 
	 * public static PushPayload
	 * buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() { return
	 * PushPayload.alertAll("chenlinqing alert tt"); }
	 * 
	 * public static PushPayload buildPushObject_all_alias_alert() { return
	 * PushPayload.newBuilder() .setPlatform(Platform.all())
	 * .setAudience(Audience.alias("jj"))
	 * .setNotification(Notification.alert("臭老鼠"))
	 * .setMessage(Message.newBuilder() .setMsgContent("content")
	 * .addExtra("from", "JPush") .addExtra("aa", "aaaaaaaa") .build())
	 * 
	 * .build()
	 * 
	 * ; }
	 */

}
