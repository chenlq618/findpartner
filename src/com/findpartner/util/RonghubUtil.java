package com.findpartner.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;

public class RonghubUtil {

	public static Random rd = new Random();

	public static JSONObject post(String url, HashMap params)
			throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("App-Key", Constants.appKey);
		int nonce=rd.nextInt();
		httpURLConnection.setRequestProperty("Nonce", ""+nonce);
		Long timestamp=(new Date()).getTime();
		httpURLConnection.setRequestProperty("Timestamp",""+timestamp);
		httpURLConnection.setRequestProperty("Signature", DigestUtils
				.sha1Hex(Constants.appSecret + nonce+timestamp));
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		
		StringBuffer parameterData = new StringBuffer();

		Iterator iter=params.keySet().iterator();
		int i=0;
		while(iter.hasNext()){
			i++;
			String key=(String)iter.next();
			if(i<params.size()){
				parameterData.append(key+"="+params.get(key)+"&");
			}else{
				parameterData.append(key+"="+params.get(key) );
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
	
	public static void main(String[] args) throws Exception {
		HashMap map=new HashMap();
		map.put("userId", "chen");
		System.out.println(post("https://api.cn.ronghub.com/user/getToken.json",map));
	}

}
