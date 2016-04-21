package vos.client.zjenergy.util;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {

	/**
	 * 返回版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		PackageManager pManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = pManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开应用
	 * @param context
	 * @param pkg  应用报名
	 * @param cls  应用入口的Activity (包名 + 类名)
	 */
	public static void openApp(Context context, String pkg, String cls) {
		if (isInstall(context, pkg)) {
			Intent mIntent = new Intent();
			mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ComponentName comp = new ComponentName(pkg, cls);
			mIntent.setComponent(comp);
			mIntent.setAction(Intent.ACTION_VIEW);
			context.startActivity(mIntent);
		}
	}
	
	/**
	 * 打开应用
	 * @param context
	 * @param pkg  应用报名
	 * 没有指定launcherActvitiy时使用
	 * AndroidManifest.xml中category设置为
	 * android.intent.category.DEFAULT的情况
	 * 是当Intent启动的时候没有明确指定启动哪个activity时候就要这样设置
	 */
	public static void openApp(Context context, String pkg) {
		if (isInstall(context, pkg)) {
			PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager.getLaunchIntentForPackage(pkg);
			context.startActivity(intent);
		}
	}

	/**
	 * 是否安装App
	 * 
	 * @param context
	 * @param pkg
	 * @return
	 */
	public static boolean isInstall(Context context, String pkg) {
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++) {
			if (pinfo.get(i).packageName.equalsIgnoreCase(pkg))
				return true;
		}
		return false;
	}

}
