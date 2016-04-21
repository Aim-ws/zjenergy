package vos.client.zjenergy;

import org.json.JSONException;
import org.json.JSONObject;

import vos.client.zjenergy.interfaces.ExecuteAppMethodInterface;
import vos.client.zjenergy.javascript.JavaScriptInterface;
import vos.client.zjenergy.util.ActManager;
import vos.client.zjenergy.util.AppMethod;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HtmlWebViewActivity extends BaseActivity implements ExecuteAppMethodInterface{
	WebView webView;
	
	@Override
	protected int findLayoutById() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        
        setActionBarTitle(getIntent().getStringExtra("title"));
        webView.loadUrl(getIntent().getStringExtra("url"));
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(this), "WebViewJavascriptBridge");
        
	}
	
	@Override
	protected void onActionBarBack(View v) {
		// TODO Auto-generated method stub
		super.onActionBarBack(v);
		finish();
	}

	@Override
	public void executeMethod(int id,String funaction, String data) {
		// TODO Auto-generated method stub
		if (AppMethod.CLOSE_MOBILE_WIMDOW.equals(funaction)) {
			setResult(RESULT_OK, new Intent());
			finish();
		}
		if (AppMethod.CLOSE_ALL_MOBILE_WIMDOW.equals(funaction)) {
			ActManager.getActManager().popAllActivity();
		}
		if (AppMethod.SET_MOBILE_WIMDOW_TITLE.equals(funaction)) {
			try {
				JSONObject object = new JSONObject(data);
				setActionBarTitle(object.optString("title"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	
	
	
	
	
}
