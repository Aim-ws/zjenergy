package vos.client.zjenergy.util;

import java.util.HashMap;

public class AppMethod {
	
	/**
	 * Window 操作方法名;
	 */
	public static final String OPEN_MOBILE_WIMDOW = "openMobileWindow";
	public static final String CLOSE_MOBILE_WIMDOW = "closeMobileWindow";
	public static final String CLOSE_ALL_MOBILE_WIMDOW = "closeAllMobileWindow";
	public static final String SET_MOBILE_WIMDOW_TITLE = "setMobileWindowTitle";
	
	
	public static final int OPEN_MOBILE_WIMDOW_CODE = 1;
	public static final int CLOSE_MOBILE_WIMDOW_CODE = 2;
	public static final int CLOSE_ALL_MOBILE_WIMDOW_CODE = 3;
	public static final int SET_MOBILE_WIMDOW_TITLE_CODE = 4;
	
	/**
	 * App操作方法名
	 */
	public static final String APP_OPEN = "openApp";
	public static final String APP_INSTALL = "installApp";
	public static final String APP_UPGRADE = "upgradeApp";
	public static final String APP_STATU = "appStatus";
	
	public static final int APP_OPEN_CODE = 5;
	public static final int APP_INSTALL_CODE = 6;
	public static final int APP_UPGRADE_CODE = 7;
	public static final int APP_STATUS_CODE = 8;
	
	public static final String SET_STORAGE = "setStorage";
	public static final String GET_STORAGE = "getStorage";
	public static final String BIND_ACCOUNT = "bindAccount";
	
	public static final int SET_STORAGE_CODE = 9;
	public static final int GET_STORAGE_CODE = 10;
	public static final int BIND_ACCOUNT_CODE = 11;
	
	
	
	public static HashMap<String, Integer> AppMethodID = new HashMap<String, Integer>();
	
	static{
		AppMethodID.put(OPEN_MOBILE_WIMDOW, OPEN_MOBILE_WIMDOW_CODE);
		AppMethodID.put(CLOSE_ALL_MOBILE_WIMDOW, CLOSE_ALL_MOBILE_WIMDOW_CODE);
		AppMethodID.put(CLOSE_MOBILE_WIMDOW, CLOSE_MOBILE_WIMDOW_CODE);
		AppMethodID.put(SET_MOBILE_WIMDOW_TITLE, SET_MOBILE_WIMDOW_TITLE_CODE);
		AppMethodID.put(APP_INSTALL, APP_INSTALL_CODE);
		AppMethodID.put(APP_OPEN, APP_OPEN_CODE);
		AppMethodID.put(APP_STATU, APP_STATUS_CODE);
		AppMethodID.put(APP_UPGRADE, APP_UPGRADE_CODE);
		AppMethodID.put(SET_STORAGE, SET_STORAGE_CODE);
		AppMethodID.put(GET_STORAGE, GET_STORAGE_CODE);
		AppMethodID.put(BIND_ACCOUNT, BIND_ACCOUNT_CODE);
	}

}
