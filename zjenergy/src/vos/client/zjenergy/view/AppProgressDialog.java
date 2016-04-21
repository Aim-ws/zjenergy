package vos.client.zjenergy.view;

import vos.client.zjenergy.R;
import vos.client.zjenergy.util.DensityUtil;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

public class AppProgressDialog extends Dialog {

	public AppProgressDialog(Context context) {
		this(context,R.style.customDialog);
		// TODO Auto-generated constructor stub
	}

	public AppProgressDialog(Context context, int themeResId) {
		super(context, themeResId);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.layout_progress_dialog);
		setCancelable(false);
		
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = DensityUtil.dip2px(context, 100);
		lp.height = DensityUtil.dip2px(context, 100);
		lp.dimAmount = 0.5f;
		lp.gravity = Gravity.CENTER;
		getWindow().setAttributes(lp);
		
	}

}
