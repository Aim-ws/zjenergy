package vos.client.zjenergy;

import vos.client.zjenergy.util.L;
import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;

public class VosApplicaiotn extends Application {
	/**
	 * 小米通道的app_id app_key
	 */
	private static final String MI_APP_ID = "2882303761517463824";
	private static final String MI_APP_KEY = "5231746365824";

	/**
	 * 阿里云的 app_key app_secret
	 */
	/*
	 * private static final String PUSH_KEY = "23349130"; private static final
	 * String PUSH_SECRET = "27810177ee8530bdd7df00b47353930e";
	 */

	// 是否debug模式
	public static boolean isDebug = true;

	public static Context applicationContext;

	private static VosApplicaiotn instance;

	public static VosApplicaiotn getInstance() {
		if (instance == null) {
			instance = new VosApplicaiotn();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		applicationContext = getApplicationContext();

		initOneSDK(getApplicationContext());
		// 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
		MiPushRegister.register(applicationContext, MI_APP_ID, MI_APP_KEY);

		// 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
		HuaWeiRegister.register(applicationContext);

	}

	/**
	 * 初始化AlibabaSDK
	 * 
	 * @param context
	 */
	private void initOneSDK(final Context applicationContext) {
		// TODO Auto-generated method stub
		AlibabaSDK.asyncInit(applicationContext, new InitResultCallback() {

			@Override
			public void onFailure(int code, String message) {
				// TODO Auto-generated method stub
				L.e("init onesdk failed : " + message);
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				L.i("init onesdk success");
				// alibabaSDK初始化成功后，初始化移动推送通道
				initCloudChannel(applicationContext);
			}
		});
	}

	/**
	 * 初始化移动推送通道
	 * 
	 * @param applicationContext
	 */
	@SuppressWarnings("unused")
	protected void initCloudChannel(Context applicationContext) {
		// TODO Auto-generated method stub
		CloudPushService cloudPushService = AlibabaSDK
				.getService(CloudPushService.class);
		L.d("DeviceId:" + cloudPushService.getDeviceId());
		if (cloudPushService != null) {
			cloudPushService.register(applicationContext, new CommonCallback() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					L.i("init cloudchannel success");
				}

				@Override
				public void onFailed(String errorCode, String errorMessage) {
					// TODO Auto-generated method stub
					L.i("init cloudchannel fail" + "err:" + errorCode
							+ " - message:" + errorMessage);
				}
			});
		} else {
			L.i("CloudPushService is null");
		}
	}

	/**
	 * 绑定账号
	 * 
	 * @param user_id
	 */
	public void AlibabaBindAccount(String user_id) {
		CloudPushService cloudPushService = AlibabaSDK
				.getService(CloudPushService.class);
		if (cloudPushService != null) {
			cloudPushService.bindAccount(user_id, new CommonCallback() {
				@Override
				public void onSuccess() {
					L.i("bind account success");
				}

				@Override
				public void onFailed(String errorCode, String errorMessage) {
					L.i("bind account fail" + "err:" + errorCode
							+ " - message:" + errorMessage);
				}
			});
			cloudPushService.addTag(user_id, new CommonCallback() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					L.i("add tag success");
				}

				@Override
				public void onFailed(String errorCode, String errorMessage) {
					// TODO Auto-generated method stub
					L.i("add tag fail" + "err:" + errorCode + " - message:"
							+ errorMessage);
				}
			});

		}
	}

}
