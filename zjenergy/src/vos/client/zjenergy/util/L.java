package vos.client.zjenergy.util;

import vos.client.zjenergy.VosApplicaiotn;
import android.util.Log;

public class L {

	public static void i(String log) {
		if (VosApplicaiotn.isDebug)
			Log.i("", log);
	}

	public static void e(String log) {
		if (VosApplicaiotn.isDebug)
			Log.e("", log);
	}

	public static void d(String log) {
		if (VosApplicaiotn.isDebug)
			Log.d("", log);
	}

	public static void w(String log) {
		if (VosApplicaiotn.isDebug)
			Log.w("", log);
	}

}
