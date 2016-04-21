package vos.client.zjenergy;

import java.util.Timer;
import java.util.TimerTask;

import vos.client.zjenergy.util.ActManager;
import vos.client.zjenergy.util.L;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	protected RelativeLayout actionBar;
	protected TextView actionBarTitle;
	protected ImageView actionBarBack;
	protected ImageView actionBarSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		ActManager.getActManager().pushActivity(this);

		View convertView = getLayoutInflater().inflate(R.layout.activity_base, null);
		FrameLayout container = (FrameLayout) convertView.findViewById(R.id.id_container);
		initHeader(convertView);
		int layoutId = findLayoutById();
		if (layoutId > 0) {
			View childView = getLayoutInflater().inflate(layoutId, null);
			container.addView(childView, -1, -1);
		}
		setContentView(convertView);

	}

	private void initHeader(View convertView) {
		// TODO Auto-generated method stub
		actionBar = (RelativeLayout) convertView.findViewById(R.id.action_bar);
		actionBarTitle = (TextView) convertView.findViewById(R.id.id_tv_title);
		actionBarBack = (ImageView) convertView.findViewById(R.id.id_iv_back);
		actionBarSetting = (ImageView) convertView.findViewById(R.id.id_iv_setting);
		actionBarBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onActionBarBack(v);
			}
		});
	}

	/**
	 * 返回监听
	 * 
	 * @param v
	 */
	protected void onActionBarBack(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		L.i("wq 1103 activity onDestroy");
		ActManager.getActManager().popActivity(this);

	}

	/**
	 * <退出系统> <绑定退出按钮>
	 * 
	 * @param v
	 * @see [类、类#方法、类#成员]
	 */
	public void exitSystem(View v) {
		ActManager.getActManager().popAllActivityExceptMain(getClass());
		finish();
	}

	/**
	 *  菜单、返回键响应  
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (ActManager.getActManager().getActivitysSize() <= 1) {
				exitBy2Click(); // 调用双击退出函数
			} else {
				finish();
			}
			return true;
		}
		return false;
	}

	/**
	 * 双击退出函数  
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			// System.exit(0);
		}
	}

	/**
	 * 是否显示actionBar VISIBLE INVISIBLE GOE
	 * 
	 * @param visible
	 */
	public void setActionBarVisible(int visible) {
		actionBar.setVisibility(visible);
	}

	/**
	 * 
	 * @param visible
	 */
	public void setActionBarBack(int visible) {
		actionBarBack.setVisibility(visible);
	}

	public void setActionBarTitle(String title) {
		actionBarTitle.setText(title);
	}

	protected abstract int findLayoutById();

}
