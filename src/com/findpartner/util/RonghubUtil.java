package com.findpartner.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;

public class RonghubUtil {

	public static Random rd = new Random();

	public static JSONObject post(String url, HashMap params) throws Exception {

		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("App-Key", Constants.appKey);
		int nonce = rd.nextInt();
		httpURLConnection.setRequestProperty("Nonce", "" + nonce);
		Long timestamp = (new Date()).getTime();
		httpURLConnection.setRequestProperty("Timestamp", "" + timestamp);
		httpURLConnection.setRequestProperty("Signature",
				DigestUtils.sha1Hex(Constants.appSecret + nonce + timestamp));
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		StringBuffer parameterData = new StringBuffer();

		Iterator iter = params.keySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			i++;
			String key = (String) iter.next();
			System.out.println(key + "\t" + params.get(key));
			if (i < params.size()) {
				parameterData.append(key + "="
						+ URLEncoder.encode(params.get(key).toString()) + "&");
			} else {
				parameterData.append(key + "="
						+ URLEncoder.encode(params.get(key).toString()));
			}
		}
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterData.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(parameterData.toString());
			outputStreamWriter.flush();
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}
		httpURLConnection.disconnect();
		JSONObject json = JSONObject.fromObject(resultBuffer.toString());
		return json;
	}

	public static void createGroup(int groupId, String groupName, String userId)
			throws Exception {
		// 先解散team，避免有重复的team
		HashMap map = new HashMap();
		map.put("userId", userId);
		map.put("groupId", groupId);
		post("https://api.cn.ronghub.com/group/dismiss.json", map);
		// 创建群
		if (groupName != null && !"".equals(groupName)) {
			map.put("groupName", groupName);
		}
		post("https://api.cn.ronghub.com/group/create.json", map);

		// 发送系统消息
		HashMap params2 = new HashMap();
		params2.put("fromUserId", "admin");
		params2.put("toGroupId", groupId);
		params2.put("objectName", "RC:InfoNtf");
		JSONObject content = new JSONObject();
		content.put("message", "【" + groupName + "】活动聊天室已创建");
		content.put("extra", "helloExtra");
		params2.put("content", content);
		post("https://api.cn.ronghub.com/message/group/publish.json", params2);
	}

	public static void joinTeam(int groupId, String userId, String userNickName)
			throws Exception {
		// 把相关人加入到队伍
		HashMap paramsJoin = new HashMap();
		paramsJoin.put("groupId", groupId);
		paramsJoin.put("userId", userId);
		RonghubUtil.post("https://api.cn.ronghub.com/group/join.json",
				paramsJoin);

		// 发送系统消息
		HashMap params = new HashMap();
		params.put("fromUserId", "admin");
		params.put("toGroupId", groupId);
		params.put("objectName", "RC:InfoNtf");
		JSONObject content = new JSONObject();
		content.put("message", "【" + userNickName + "】加入到活动聊天室");
		content.put("extra", "helloExtra");
		params.put("content", content);
		post("https://api.cn.ronghub.com/message/group/publish.json", params);
	}

	public static void exitTeam(int groupId, String userId,String userNickName) throws Exception {

		// 把相关人从队员中删除
		HashMap paramsJoin = new HashMap();
		paramsJoin.put("groupId", groupId);
		paramsJoin.put("userId", userId);
		RonghubUtil.post("https://api.cn.ronghub.com/group/quit.json",
				paramsJoin);

		// 发送系统消息
		HashMap params = new HashMap();
		params.put("fromUserId", "admin");
		params.put("toGroupId", groupId);
		params.put("objectName", "RC:InfoNtf");
		JSONObject content = new JSONObject();
		content.put("message", "【" + userNickName + "】退出活动聊天室");
		content.put("extra", "helloExtra");
		params.put("content", content);
		post("https://api.cn.ronghub.com/message/group/publish.json", params);
	}

	public static void dismissTeam(int groupId, String userId) throws Exception {
		HashMap paramsJoin = new HashMap();
		paramsJoin.put("groupId", groupId);
		paramsJoin.put("userId", userId);
		RonghubUtil.post("https://api.cn.ronghub.com/group/dismiss.json",
				paramsJoin);
	}

	public static void main(String[] args) throws Exception {
		dismissTeam(2, "23456789018");
	}

}
