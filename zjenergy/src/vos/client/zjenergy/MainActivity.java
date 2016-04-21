package vos.client.zjenergy;

import org.json.JSONException;
import org.json.JSONObject;

import vos.client.zjenergy.interfaces.ExecuteAppMethodInterface;
import vos.client.zjenergy.javascript.HtmlWebViewClient;
import vos.client.zjenergy.javascript.JavaScriptInterface;
import vos.client.zjenergy.util.ActManager;
import vos.client.zjenergy.util.AppMethod;
import vos.client.zjenergy.util.L;
import vos.client.zjenergy.util.SharePreferencesUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends BaseActivity implements ExecuteAppMethodInterface {
	WebView webView;
	private int callBackId;

	@Override
	protected int findLayoutById() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		JavaScriptInterface object = new JavaScriptInterface(this);
		webView.addJavascriptInterface(object, "WebViewJavascriptBridge");
		webView.loadUrl("file:///android_asset/api.html");
		setActionBarBack(View.INVISIBLE);

		webView.setWebViewClient(new HtmlWebViewClient(this));
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}
		});

		/*
		 * titleTextView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub // startActivity(new Intent(MainActivity.this,
		 * DownLoadActivity.class));
		 * webView.loadUrl("javascript:window._Bridge.sendBack(" + "\"" +
		 * callBackId + "\"" + ","+ "\"" + "helloWord" + "\"" + ")");
		 * webView.loadUrl
		 * ("javascript:window._Bridge.sendBack('"+callBackId+"','helloWord')");
		 * } });
		 */

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			JSONObject object = new JSONObject();
			try {
				object.put("result", "HelloWord");
				webView.loadUrl("javascript:window._Bridge.sendBack('" + callBackId + "','" + object.toString() + "')");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void openActivity(String url, String title) {
		Intent intent = new Intent(this, HtmlWebViewActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		startActivityForResult(intent, 100);
	}

	public void setTopTitle(String title) {
		setActionBarTitle(title);
	}

	@Override
	public void executeMethod(int id, final String funaction, final String data) {
		// TODO Auto-generated method stub
		L.i(funaction + "\n" + data);
		callBackId = id;
		MainActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject object;
				try {
					object = new JSONObject(data);

					switch (AppMethod.AppMethodID.get(funaction)) {
					case AppMethod.CLOSE_ALL_MOBILE_WIMDOW_CODE:
						ActManager.getActManager().popAllActivity();
						break;
					case AppMethod.OPEN_MOBILE_WIMDOW_CODE:
						openActivity("file:///android_asset/" + object.optString("url"), object.optString("title"));
						break;
					case AppMethod.SET_MOBILE_WIMDOW_TITLE_CODE:
						setTopTitle(object.optString("title"));
						break;
					case AppMethod.APP_INSTALL_CODE:
					case AppMethod.APP_OPEN_CODE:
					case AppMethod.APP_UPGRADE_CODE:
					case AppMethod.APP_STATUS_CODE:
					case AppMethod.SET_STORAGE_CODE:
						SharePreferencesUtil.putValue(object.optString("key"), object.optString("value"));
						break;
					case AppMethod.GET_STORAGE_CODE:
						String value = SharePreferencesUtil.getString(object.optString("key"));
						webView.loadUrl("javascript:window._Bridge.sendBack('" + callBackId + "','" + value + "')");
						L.i("value:" + value);
						break;
					case AppMethod.BIND_ACCOUNT_CODE:
						VosApplicaiotn.getInstance().AlibabaBindAccount(object.optString("account"));
						break;

					default:
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
