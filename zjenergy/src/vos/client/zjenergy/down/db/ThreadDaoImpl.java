package vos.client.zjenergy.down.db;

import java.util.ArrayList;
import java.util.List;

import vos.client.zjenergy.down.bean.ThreadInfo;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ws_cjlu on 2016/4/15.
 * <p/>
 * 数据访问接口实现
 */
public class ThreadDaoImpl implements ThreadDao {
    private DBHelper dbHelper = null;

    public ThreadDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }


    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{
                        threadInfo.id,
                        threadInfo.url,
                        threadInfo.start,
                        threadInfo.end,
                        threadInfo.finished}
        );
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ?",
                new Object[]{url}
        );
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, thread_id}
        );
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        List<ThreadInfo> list = new ArrayList<ThreadInfo>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?",
                new String[]{url, "" + thread_id});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;

    }
}
