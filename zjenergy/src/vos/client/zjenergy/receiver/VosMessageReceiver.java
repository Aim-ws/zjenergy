package vos.client.zjenergy.receiver;

import java.util.Map;

import vos.client.zjenergy.util.L;

import android.content.Context;
import android.content.Intent;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

public class VosMessageReceiver extends MessageReceiver{

	@Override
	public void onHandleCall(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onHandleCall(context, intent);
		L.e("intent:  "+intent.getAction());
	}

	@Override
	protected void onMessage(Context context, CPushMessage cPushMessage) {
		// TODO Auto-generated method stub
		super.onMessage(context, cPushMessage);
		L.e(" cPushMessage  title:"+cPushMessage.getTitle() + "  content:"+cPushMessage.getContent());
	}

	@Override
	protected void onNotification(Context context, String title, String summary,
			Map<String, String> extraMap) {
		// TODO Auto-generated method stub
		super.onNotification(context, title, summary, extraMap);
		L.e("title:  "+title + "\nsummary: "+summary + "\nextraMap:"+extraMap.toString());
	}
	
	

}
