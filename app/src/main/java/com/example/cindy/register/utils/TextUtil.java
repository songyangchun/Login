package com.example.cindy.register.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	/**
	 * 类名：textUtil
	 * 说明：判断字符串是否空
	 */
	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0) //去掉空格的字符串长度
			return true;
		return false;
	}



	/**
	 * 正则匹配手机号码
	 *
	 * @param tel
	 * @return
	 */
	public static boolean checkTel(String tel) {
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Matcher matcher = p.matcher(tel);
		return matcher.matches();
	}

	/**
	 * 类名：textUtil
	 * 说明：验证账号 密码是否合法（6-18位数字字母下划线）
	 */
	private static Pattern ACCOUT_PATTERN = Pattern.compile("^[a-z0-9_-]{6,18}$");

	public static boolean matchAccount(String text) {
		if (ACCOUT_PATTERN.matcher(text).matches()) {
			return true;
		}
		return false;
	}





	// Storage Permissions SD卡权限  可以写在BaseActivity里
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			"android.permission.READ_EXTERNAL_STORAGE",
			"android.permission.WRITE_EXTERNAL_STORAGE"};

	public static void verifyStoragePermissions(Activity activity) {
		try {
			//检测是否有写的权限
			int permission = ActivityCompat.checkSelfPermission(activity,
					"android.permission.WRITE_EXTERNAL_STORAGE");
			if (permission != PackageManager.PERMISSION_GRANTED) {
				// 没有写的权限，去申请写的权限，会弹出对话框
				ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
