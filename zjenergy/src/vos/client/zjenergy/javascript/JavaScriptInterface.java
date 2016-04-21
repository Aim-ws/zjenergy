package vos.client.zjenergy.javascript;

import org.json.JSONException;
import org.json.JSONObject;

import android.webkit.JavascriptInterface;
import vos.client.zjenergy.interfaces.ExecuteAppMethodInterface;
import vos.client.zjenergy.interfaces.HtmlBaseInterface;
import vos.client.zjenergy.util.L;

public class JavaScriptInterface implements HtmlBaseInterface{
	public static final String NAME = "name";
	public static final String DATA = "data";
	
	private ExecuteAppMethodInterface appMethodInterface;
	

	public JavaScriptInterface(ExecuteAppMethodInterface methodInterface) {
		this.appMethodInterface = methodInterface;
	}

	@JavascriptInterface
	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

	@JavascriptInterface
	@Override
	public void send(String json, int id) {
		// TODO Auto-generated method stub
		L.i(json+"\n"+id);
		JSONObject object;
		try {
			object = new JSONObject(json);
			String function = object.optString(NAME);
			String data = object.optString(DATA);
			if (appMethodInterface != null) {
				appMethodInterface.executeMethod(id, function, data);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@JavascriptInterface
	@Override
	public void send(String data, int id, String callBack) {
		// TODO Auto-generated method stub
		L.i(data+"\n"+id+"\n"+callBack);
	}

	
	

}
