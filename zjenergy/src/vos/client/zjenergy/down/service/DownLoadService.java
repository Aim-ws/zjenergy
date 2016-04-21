package vos.client.zjenergy.down.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

import vos.client.zjenergy.VosApplicaiotn;
import vos.client.zjenergy.down.DownLoadTask;
import vos.client.zjenergy.down.bean.FileInfo;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ws_cjlu on 2016/4/15.
 */
public class DownLoadService extends Service {
	public static final String TAG = DownLoadService.class.getSimpleName();
	public static final String ACTION_START = "ACTION_START";
	public static final String ACTION_STOP = "ACTION_STOP";
	public static final String ACTION_UPDATE = "ACTION_UPDATE";
	public static final String ACTION_FINISH = "ACTION_FINISH";
	public static final String DOWNLOAD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";

	private static final int MSG_INIT = 0;

	// 下载任务的集合
	private Map<Integer, DownLoadTask> mTasks = new LinkedHashMap<Integer, DownLoadTask>();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 获取activity传过来的参数
		if (ACTION_START.equals(intent.getAction())) {
			FileInfo fileInfo = (FileInfo) intent
					.getSerializableExtra("fileInfo");
			Log.i(TAG, "start-------" + fileInfo.toString());

			// 启动初始化线程
			InitThread initThread = new InitThread(fileInfo);
			DownLoadTask.sExecutorService.execute(initThread);

		} else if (ACTION_STOP.equals(intent.getAction())) {
			FileInfo fileInfo = (FileInfo) intent
					.getSerializableExtra("fileInfo");
			Log.i(TAG, "stop------" + fileInfo.toString());

			// 从集合中取出下载任务
			DownLoadTask task = mTasks.get(fileInfo.id);
			if (task != null) {
				task.isPause = true;
			}

		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_INIT:
				FileInfo mFileInfo = (FileInfo) msg.obj;
				Log.i(TAG, "init--------" + mFileInfo.toString());
				// 启动下载任务
				DownLoadTask task = new DownLoadTask(DownLoadService.this,
						mFileInfo, 1);
				task.download();
				// 把下载任务添加到集合中
				mTasks.put(mFileInfo.id, task);
				break;
			}
		}
	};

	/**
	 * 初始化子线程
	 */
	class InitThread extends Thread {
		private FileInfo fileInfo;

		public InitThread(FileInfo fileInfo) {
			this.fileInfo = fileInfo;
		}

		@Override
		public void run() {
			HttpURLConnection conn = null;
			RandomAccessFile raf = null;
			try {
				// 连接网络
				URL url = new URL(fileInfo.getUrl());
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");

				// 连接超时处理
				if (conn.getResponseCode() == HttpStatus.SC_REQUEST_TIMEOUT) {
					Toast.makeText(VosApplicaiotn.applicationContext,
							"网络环境不好，连接超时，请重新开始下载", Toast.LENGTH_SHORT).show();
					return;
				}

				int length = -1;
				if (conn.getResponseCode() == HttpStatus.SC_OK) {
					// 获取文件长度
					length = conn.getContentLength();
				}
				if (length <= 0) {
					Log.i(TAG, "文件有误");
					return;
				}

				File dir = new File(DOWNLOAD_PATH);
				if (!dir.exists()) {
					dir.mkdir();
				}
				// 在本地创建文件
				File file = new File(dir, fileInfo.getFileName() + ".apk");
				raf = new RandomAccessFile(file, "rwd");

				// 设置文件长度
				raf.setLength(length);
				fileInfo.setLength(length);
				mHandler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (raf != null)
						raf.close();

					if (conn != null)
						conn.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
