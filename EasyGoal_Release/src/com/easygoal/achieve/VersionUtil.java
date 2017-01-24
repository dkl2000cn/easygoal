package com.easygoal.achieve;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * �汾������ VersionUtil.java
 * 
 * @author Cloay 2011-11-23
 */
public class VersionUtil {
	/**
	 * ��ȡ�汾��
	 * 
	 * @param context
	 *            ������
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String getVersionName(Context context)
			throws NameNotFoundException {
		// ��ȡPackageManager ʵ��
		PackageManager packageManager = context.getPackageManager();
		// ���context������İ�����0��ʾ��ȡ�汾��Ϣ
		PackageInfo packageInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		return packageInfo.versionName;
	}
}
