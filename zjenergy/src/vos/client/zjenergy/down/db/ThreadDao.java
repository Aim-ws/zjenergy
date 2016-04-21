package vos.client.zjenergy.down.db;

import java.util.List;

import vos.client.zjenergy.down.bean.ThreadInfo;

/**
 * Created by ws_cjlu on 2016/4/15.
 *
 * 数据访问接口
 */
public interface ThreadDao {

    /**
     * 插入线程信息
     * @param threadInfo
     */
    void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     * @param url
     */
    void deleteThread(String url);

    /**
     * 更新线程下载进度
     * @param url
     * @param thread_id
     * @param finished
     */
    void updateThread(String url,int thread_id,int finished);

    /**
     * 查询文件的线程信息
     * @param url
     * @return
     */
    List<ThreadInfo> getThreads(String url);

    /**
     * 判断线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    boolean isExists(String url,int thread_id);

}
