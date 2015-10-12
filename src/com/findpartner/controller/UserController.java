package com.findpartner.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.Jedis;

import com.findpartner.bean.BaseUserInfo;
import com.findpartner.bean.UserInfo;
import com.findpartner.service.UserService;
import com.findpartner.util.Constants;
import com.findpartner.util.RonghubUtil;

@Controller
@RequestMapping(value = "user")
public class UserController {

	static Jedis jedis;
	static {
		jedis = new Jedis(Constants.RedisIp);
	}

	@Autowired(required = true)
	private UserService userService;

	@RequestMapping(value = "regist.json")
	@ResponseBody
	public Map<String, Object> userRegist(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap params = new HashMap();
		String info = "";
		String phone = request.getParameter("phone");
		params.put("userId", phone);
		if (userService.getUserInfo(phone) != null) {
			result.put("requestState", 0);
			info += "该号码已注册\n";
		} else {
			UserInfo user = new UserInfo();
			user.setPhone(phone);
			// 获取相应参数
			user.setPassword(DigestUtils.md5Hex(request
					.getParameter("password")));
			user.setNickname(request.getParameter("nickname"));
			try {

			} catch (Exception e) {

			}
			String sex = request.getParameter("sex");
			if (sex != null) {
				user.setAge(Integer.parseInt(sex));
			}

			String age = request.getParameter("age");
			if (age != null) {
				user.setAge(Integer.parseInt(age));
			}
			user.setSelIntroduce(request.getParameter("selIntroduce"));
			user.setAddress(request.getParameter("address"));
			user.setUniversity(request.getParameter("university"));
			user.setSchool(request.getParameter("school"));
			String grade = request.getParameter("grade");
			if (grade != null) {
				user.setGrade(Integer.parseInt(grade));
			}
			user.setMajor(request.getParameter("major"));

			// 用户icon处理
			if (file != null) {
				// 图标统一放置位置
				String realPath = request.getSession().getServletContext()
						.getRealPath("/userIcon");
				File path = new File(realPath);
				if (!path.exists()) {
					path.mkdirs();
				}
				String fileName = file.getOriginalFilename();
				if (fileName.toUpperCase().contains(".JPG")
						|| fileName.toUpperCase().contains(".PNG")) {
					if (fileName.toUpperCase().contains(".JPB")) {
						fileName = phone + ".jpg";
					} else {
						fileName = phone + ".png";
					}
					File destFile = new File(realPath, fileName);
					if (!destFile.exists()) {
						destFile.mkdirs();
					}
					file.transferTo(destFile);
					String imgURL = Constants.IconUrl + fileName;
					result.put("imgURL", imgURL);
					user.setUserIcon(imgURL);
					params.put("portraitUri", imgURL);
				}
			}
			if (request.getParameter("nickname") != null) {
				params.put("name", request.getParameter("nickname"));
			}
			userService.inserUser(user);
			result.put("requestState", 1);
			result.put("token", request.getAttribute("token"));
			JSONObject rong = RonghubUtil.post(
					"https://api.cn.ronghub.com/user/getToken.json", params);
			if (rong.getString("code") != null && rong.getInt("code") == 200) {
				result.put("talkToken", rong.getString("token"));
				jedis.set(phone + "talkToken", rong.getString("token"));
			}
			result.put("userInfo", user);
		}
		return result;
	}

	@RequestMapping(value = "login.json")
	@ResponseBody
	public Map<String, Object> userLogin(HttpServletRequest request)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		String phone = request.getParameter("phone");
		UserInfo user = userService.getUserInfo(phone);
		if (user == null) {
			result.put("loginState", 0);
			info.put("infoRegist", "该号码未注册，请先注册");
		} else {
			if (user.getPassword().equals(
					DigestUtils.md5Hex(request.getParameter("password")))) {
				result.put("loginState", 1);
				result.put("token", request.getAttribute("token"));
				user.setPassword(null);
				result.put("userInfo", user);
				if (jedis.get(phone + "talkToken") != null) {
					result.put("talkToken", jedis.get(phone + "talkToken"));
				} else {
					HashMap params = new HashMap();
					params.put("userId", phone);
					JSONObject rong = RonghubUtil.post(
							"https://api.cn.ronghub.com/user/getToken.json",
							params);
					if (rong.getString("code") != null
							&& rong.getInt("code") == 200) {
						result.put("talkToken", rong.getString("token"));
						jedis.set(phone + "talkToken", rong.getString("token"));
					}
				}
				info.put("infoRegist", "登陆成功");
			} else {
				result.put("loginState", 0);
				info.put("infoRegist", "密码错误");
			}
		}
		return result;

	}

	@RequestMapping(value = "updateUserPwdL.json")
	@ResponseBody
	public Map<String, Object> updateUserPwd(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		UserInfo user = new UserInfo();
		user.setPhone(request.getParameter("phone"));
		user.setPassword(request.getParameter("password"));
		userService.updateUserInfo(user);
		info.put("infoUpdateUserPwd", "修改密码成功");
		return result;

	}

	@RequestMapping(value = "updateUserIconL.json")
	@ResponseBody
	public Map<String, Object> uploadUserIcon(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		String phone = request.getParameter("phone");

		UserInfo user = new UserInfo();
		user.setPhone(phone);
		// 用户icon处理
		if (file != null) {
			// 图标统一放置位置
			String realPath = request.getSession().getServletContext()
					.getRealPath("/userIcon");
			File path = new File(realPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			String fileName = file.getOriginalFilename();
			if (fileName.toUpperCase().contains(".JPG")
					|| fileName.toUpperCase().contains(".PNG")) {
				if (fileName.toUpperCase().contains(".JPB")) {
					fileName = phone + ".jpg";
				} else {
					fileName = phone + ".png";
				}
				File destFile = new File(realPath, fileName);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				file.transferTo(destFile);
				info.put("infoImage", "图标保存成功");
				String imgURL = Constants.IconUrl + fileName;
				result.put("imgURL", imgURL);
				user.setUserIcon(imgURL);
				userService.updateUserInfo(user);
				HashMap params = new HashMap();
				params.put("userId", phone);
				params.put("portraitUri", imgURL);
				result.put("rongCode", RonghubUtil.post(
						"https://api.cn.ronghub.com/user/refresh.json", params).get("code"));
			} else {
				info.put("infoImage", "文件不是jpg或者png图片");
			}

		} else {
			info.put("updateUserIcon", "图标文件不能为空");
		}

		return result;

	}

	@RequestMapping(value = "updateUserInfoL.json")
	@ResponseBody
	public Map<String, Object> updateUserInfo(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		HashMap params = new HashMap();

		result.put("info", info);
		String phone = request.getParameter("phone");
		params.put("userId", phone);
		UserInfo user = new UserInfo();
		user.setPhone(phone);
		// 获取相应参数
		user.setPassword(request.getParameter("password"));
		user.setNickname(request.getParameter("nickname"));
		if (request.getParameter("nickname") != null) {
			params.put("name", request.getParameter("nickname"));
		}
		String sex = request.getParameter("sex");
		user.setSex(sex);

		String age = request.getParameter("age");
		if (age != null) {
			try{
			user.setAge(Integer.parseInt(age));
			}catch (Exception e) {
			}
		}
		user.setSelIntroduce(request.getParameter("selIntroduce"));
		user.setAddress(request.getParameter("address"));
		user.setUniversity(request.getParameter("university"));
		user.setSchool(request.getParameter("school"));
		String grade = request.getParameter("grade");
		if (grade != null) {
			user.setGrade(Integer.parseInt(grade));
		}
		user.setMajor(request.getParameter("major"));

		// 用户icon处理
		if (file != null) {
			// 图标统一放置位置
			String realPath = request.getSession().getServletContext()
					.getRealPath("/userIcon");
			File path = new File(realPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			String fileName = file.getOriginalFilename();
			if (fileName.toUpperCase().contains(".JPG")
					|| fileName.toUpperCase().contains(".PNG")) {
				if (fileName.toUpperCase().contains(".JPB")) {
					fileName = phone + ".jpg";
				} else {
					fileName = phone + ".png";
				}
				File destFile = new File(realPath, fileName);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				file.transferTo(destFile);
				info.put("infoImage", "图标保存成功");
				String imgURL = Constants.IconUrl + fileName;
				result.put("imgURL", imgURL);
				user.setUserIcon(imgURL);
				params.put("portraitUri", imgURL);
			} else {
				info.put("infoImage", "文件不是jpg或者png后缀的图片");
			}

		}
		info.put("infoUpdateUserInfoL", "修改用户资料成功");
		userService.updateUserInfo(user);
		if (params.size() > 1) {
			result.put("rongCode", RonghubUtil.post(
					"https://api.cn.ronghub.com/user/refresh.json", params).get("code"));
		}
		UserInfo user2 = userService.getUserInfo(request.getParameter("phone"));
		result.put("userInfo", user2);
		result.put("talkToken", jedis.get(user.getPhone()+"talkToken"));
		return result;

	}

	@RequestMapping(value = "getUserInfoL.json")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		UserInfo user = userService.getUserInfo(request.getParameter("phone"));
		if (user != null) {
			user.setPassword(null);
		}
		result.put("userInfo", user);
		result.put("talkToken", jedis.get(user.getPhone()+"talkToken"));
		return result;
	}
	
	@RequestMapping(value = "getBaseUserInfo.json")
	@ResponseBody
	public Map<String, Object> getBaseUserInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		BaseUserInfo user = userService.getBaseUserInfo(request.getParameter("phone"));
		result.put("userInfo", user);
		return result;
	}

	// 根据学校id，获取学院信息
	@RequestMapping("getSchool.json")
	@ResponseBody
	public Map<String, Object> getSchool(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);

		String universityId = request.getParameter("universityId");
		if (universityId == null || universityId.equals("")) {
			info.put("infoGetSchool", "请求参数为空");
		} else {
			result.put("schools", userService.getSchoolInfo(universityId));
			info.put("infoGetSchool", "请求成功");
		}
		return result;
	}

	@RequestMapping("exit.json")
	@ResponseBody
	public Map<String, Object> exit(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> info = new HashMap<String, String>();
		result.put("info", info);
		info.put("infoExit", "成功退出");
		return result;
	}

	// 根据学校id，获取学院信息
	@RequestMapping("reLogin.json")
	@ResponseBody
	public Map<String, Object> reLogin(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		result.put("info", info);
		result.put("reLogin", 1);
		info.put("info", request.getAttribute("info"));
		return result;
	}

}
