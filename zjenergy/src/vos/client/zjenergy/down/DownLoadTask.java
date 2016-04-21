package vos.client.zjenergy.down;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpStatus;

import vos.client.zjenergy.down.bean.FileInfo;
import vos.client.zjenergy.down.bean.ThreadInfo;
import vos.client.zjenergy.down.db.ThreadDaoImpl;
import vos.client.zjenergy.down.service.DownLoadService;
import vos.client.zjenergy.util.L;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ws_cjlu on 2016/4/15.
 * <p/>
 * 下载任务类
 */
public class DownLoadTask {

    private Context mContext = null;
    private FileInfo mFileInfo = null;
    private ThreadDaoImpl mThreadDaoImpl = null;
    private int mFinished = 0;
    public boolean isPause = false;
    private int mThreadCount = 1;    //线程数量
    private List<DownLoadThread> mThreadList = null;
    //线程池
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    public DownLoadTask(Context mContext, FileInfo mFileInfo, int mThreadCounnt) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = mThreadCounnt;
        mThreadDaoImpl = new ThreadDaoImpl(mContext);
    }

    public void download() {
        //读取数据库的线程信息
        List<ThreadInfo> threadInfos = mThreadDaoImpl.getThreads(mFileInfo.url);
        if (threadInfos.size() == 0) {
            //第一次下载
            //获得每个线程下载的长度
            int length = mFileInfo.length / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                //创建线程信息
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.url, length * i,
                        length * (length + 1) - 1, 0);
                if (i == mThreadCount - 1) {
                    threadInfo.setEnd(mFileInfo.length);
                }
                //添加到线程信息集合里面
                threadInfos.add(threadInfo);
                //向数据库插入一条线程信息
                mThreadDaoImpl.insertThread(threadInfo);
            }
        }
        //启动多个线程进行下载
        mThreadList = new ArrayList<DownLoadThread>();
        for (ThreadInfo info : threadInfos) {
            DownLoadThread thread = new DownLoadThread(info);
//            thread.start();
            DownLoadTask.sExecutorService.execute(thread);
            //添加线程到集合中
            mThreadList.add(thread);
        }


    }

    /**
     * 同步方法保证同一时间只能有一个对象访问该方法
     * 判断是否所有线程都执行完毕
     */
    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;
        //遍历线程集合，判断线程是否都执行完毕
        for (DownLoadThread thread : mThreadList) {
            if (!thread.isFinished)
                allFinished = false;
            break;
        }
        if (allFinished) {
            //删除线程信息
            mThreadDaoImpl.deleteThread(mFileInfo.url);
            //发送广播通知UI下载完成
            Intent intent = new Intent(DownLoadService.ACTION_FINISH);
            intent.putExtra("fileInfo", mFileInfo);
            mContext.sendBroadcast(intent);
        }
    }


    /**
     * 下载线程
     */
    class DownLoadThread extends Thread {
        private ThreadInfo mThreadInfo = null;
        public boolean isFinished = false;    //标识线程下载是否完毕

        public DownLoadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @SuppressWarnings("resource")
		@Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.start + mThreadInfo.finished;
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.end);

                //设置文件写入位置
                File file = new File(DownLoadService.DOWNLOAD_PATH + mFileInfo.fileName + ".apk");
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                Intent intent = new Intent(DownLoadService.ACTION_UPDATE);
                mFinished += mThreadInfo.finished;
                
                //连接超时处理
                if (conn.getResponseCode() == HttpStatus.SC_REQUEST_TIMEOUT) {
					Toast.makeText(mContext, "网络环境不好，连接超时，请重新开始下载", Toast.LENGTH_SHORT).show();
					return ;
				}

                //开始下载
                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    //读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();

                    while ((len = input.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, len);
                        //累加整个文件的进度
                        mFinished += len;
                        //累加每个线程完成的进度
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + len);
                        //每个1000毫秒更新进度
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            
                            int progress = mFinished * 100 / mFileInfo.getLength();
                            L.i("progress----------"+progress);
                            intent.putExtra("finished", progress);
                            intent.putExtra("id", mFileInfo.id);
                            mContext.sendBroadcast(intent);
                        }
                        //在下载暂停时保存下载进度
                        if (isPause) {
                            mThreadDaoImpl.updateThread(mThreadInfo.url, mThreadInfo.id, mThreadInfo.finished);
                            return;
                        }
                    }
                    L.i("下载完毕");
                    //标识线程执行完毕
                    isFinished = true;
                    //检查下载任务是否执行完毕
                    checkAllThreadFinished();
                }
                
                

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null)
                        conn.disconnect();

                    if (raf != null)
                        raf.close();

                    if (input != null)
                        input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
