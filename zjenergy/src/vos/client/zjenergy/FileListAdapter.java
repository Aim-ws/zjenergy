package vos.client.zjenergy;

import java.util.List;

import vos.client.zjenergy.down.bean.FileInfo;
import vos.client.zjenergy.down.service.DownLoadService;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by ws_cjlu on 2016/4/16.
 * <p/>
 * 文件列表适配器
 */
public class FileListAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<FileInfo> fileList = null;


    public FileListAdapter(Context mContext, List<FileInfo> fileList) {
        this.mContext = mContext;
        this.fileList = fileList;
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final FileInfo fileInfo = (FileInfo) getItem(position);
        if (convertView == null) {
            //加载视图
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_item, null);
            //获取布局中的空间
            viewHolder = new ViewHolder();
            viewHolder.mTvFileName = (TextView) convertView.findViewById(R.id.tvFileName);
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.progress);
            viewHolder.mBtnStart = (Button) convertView.findViewById(R.id.start);
            viewHolder.mBtnStop = (Button) convertView.findViewById(R.id.stop);
            convertView.setTag(viewHolder);
            //优化初次创建时调用
            viewHolder.mTvFileName.setText(fileInfo.fileName);
            viewHolder.mProgressBar.setMax(100);
            viewHolder.mBtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //intent传递参数到service
                    Intent intent = new Intent(mContext, DownLoadService.class);
                    intent.setAction(DownLoadService.ACTION_START);
                    intent.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent);
                }
            });
            viewHolder.mBtnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //intent传递参数到service
                    Intent intent = new Intent(mContext, DownLoadService.class);
                    intent.setAction(DownLoadService.ACTION_STOP);
                    intent.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent);
                }
            });

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置视图中的控件

        viewHolder.mProgressBar.setProgress(fileInfo.finished);

        return convertView;
    }


    /**
     * 更新列表中的进度条
     *
     * @param id
     * @param progess
     */
    public void updateProgress(int id, int progess) {
        FileInfo fileInfo = fileList.get(id);
        fileInfo.setFinished(progess);
        notifyDataSetChanged();
    }


    /**
     * 保证只加载一次
     */
    static class ViewHolder {
        private TextView mTvFileName;
        private ProgressBar mProgressBar;
        private Button mBtnStop;
        private Button mBtnStart;
    }
}
