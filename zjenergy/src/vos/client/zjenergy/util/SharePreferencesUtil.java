package vos.client.zjenergy.util;

import vos.client.zjenergy.VosApplicaiotn;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferencesUtil {
	private static final String DATA_NAME = "zjenergy_data";
	
	public static void putValue(String key, String value) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	public static void putValue(String key, boolean value) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	public static void putValue(String key, int value) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
	public static void putLong(String key, long value) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		sp.edit().putLong(key, value).commit();
	}
	
	public static String getString(String key) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}
	public static int getInt(String key) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
	public static boolean getBoolean(String key) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}
	public static long getLong(String key) {
		SharedPreferences sp = VosApplicaiotn.applicationContext.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
		return sp.getLong(key, 0);
	}
	

}
