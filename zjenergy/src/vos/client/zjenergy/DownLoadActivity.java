package vos.client.zjenergy;

import java.util.ArrayList;
import java.util.List;

import vos.client.zjenergy.down.bean.FileInfo;
import vos.client.zjenergy.down.service.DownLoadService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class DownLoadActivity extends Activity{
	
	private ListView mLvFile;
    private List<FileInfo> mFileList;
    private FileListAdapter mAdapter;


    private String[] urls = {
            "http://p.gdown.baidu.com/b6778e08e2ba571df5a0978c66ea03888b709a65f1fd29d98779ca0193b1ece494e43ede556b25c62c479e39426362ec953d45bf5f4da1f47b8de78f68414eb1d80a92b80017d6da620979e9da08350e52c1349663f50d4b0c4389c8c5e1c41598864100dd215aeddd5acfcdb318e8d6212f4a6a88b41d452093102d7e6c0d3edc607b2ee2a1c574fabd833ea385f8d25775c78b170e25f20db0c953960759cb0b98d8d684e431e3b452954446ee225e672f6c9b43e082bbf11c976847607e9e02cbd22df064ec672bbdf070dbada9f8",
            "http://p.gdown.baidu.com/4c117e04b4d993f68c30111b88bdebb9531740af3750dc3e399e4c12f06ec37e053ea8cea788d8eda9f58aa98e62914a1c5c046c73c93b7b5ed05d5f50e6f53bbd0da847699a7ec4e9718e52a588e9ff78264b600f724f4c61351f60297bbad447449d20b46a0bb0a956243c0e6399ac7e131e742ea22bde31ad231f7f83751f44840312a925aab90f333b1893a62586a0fec938c0f09f52b9cdf1051542b15b6d780b48fad7078c09e88d3d9b368ff3aecb4cf63ea3c5633ce1cf3fc2b52fc0e9419ed1e6d8334081b39780cbe2f275a38a3562d981ab1a844f2508a3aa4af88bfff40f271403ee4dd4e14dc8d79310ade146d3d15c811c37004a8d533cc54724cfd4cf00c49776120c5215e62ac8a48db354d868998a3e6cbb05d36da0f8043ae8e3ae9052777d129b47131e2dc6ae71ed9e673e8ee4aaf1212f4d89f1baaa",
            "http://p.gdown.baidu.com/c834bb2eb34d13681c28577a19dd35dcc8228f4ade857ad6848a615080476a8d7e2f98e88860df91e3222a1596a7e2a61605476a729fcbd35e64c2cf072a0a2924a972e556d64696d4f4711b3ef0cc547903c2840b1bbda41461cd39a6608999ea5e8ca152541c81f6937bdf8b4ae8bc05def343b52902ba32d9dbd34c241916b71ceb83e9fa929f76a83a96c408501b13809f8c37646b6b3245b946b44e29d4945d98d1f42d8c122a95e820db64adc8b0afee0d0cf19d456f3cee0ca64c6ee6507850de32565caf98a3ef6661a4c56d75b5a6bcde0ad3eb62269888fb65cb862a82b6827cc635cc"
    };
    private String[] fileNames = {
            "今日头条",
            "冲浪快讯",
            "天天快报",
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.activity_download);

	        mLvFile = (ListView) findViewById(R.id.lvFile);
	        //创建文件集合
	        mFileList = new ArrayList<FileInfo>();
	        for (int i = 0; i < urls.length; i++) {
	            FileInfo fileInfo = new FileInfo(i, fileNames[i], urls[i], 0, 0);
	            mFileList.add(fileInfo);
	        }
	        mAdapter = new FileListAdapter(this, mFileList);
	        mLvFile.setAdapter(mAdapter);



	        IntentFilter filter = new IntentFilter();
	        filter.addAction(DownLoadService.ACTION_UPDATE);
	        filter.addAction(DownLoadService.ACTION_FINISH);
	        registerReceiver(mReceiver, filter);
	}
	
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownLoadService.ACTION_UPDATE.equals(intent.getAction())) {
                //更新进度条
                int mFinished = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                mAdapter.updateProgress(id, mFinished);

            } else if (DownLoadService.ACTION_FINISH.equals(intent.getAction())) {
                //下载结束
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                //更新进度为0
                mAdapter.updateProgress(fileInfo.id, 0);
                Toast.makeText(DownLoadActivity.this, fileNames[fileInfo.id] + "下载完毕", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
