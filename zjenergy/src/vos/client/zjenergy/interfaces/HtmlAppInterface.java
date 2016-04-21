package vos.client.zjenergy.interfaces;

public interface HtmlAppInterface {
	
	/**
	 * 打开指定应用
	 * @param packageName  包名
	 * @param cls  应用的launcherActivity (包名 + 类名)
	 */
	public void openApp(String packageName, String cls);
	
	/**
	 * 判断应用是否安装
	 * @param packageName 包名
	 * @return
	 */
	public boolean isInstallApp(String packageName);
	
	/**
	 * 下载应用
	 * @param id    应用ID
	 * @param loadUrl     应用下载地址
	 */
	public void loadApp(String id,String loadUrl);
	
	/**
	 * 更新下载进度
	 * @param id   应用ID
	 * @param loadUrl     应用下载地址
	 * @return
	 */
	public int updateLoadProgress(String id,String loadUrl);
	
	/**
	 * 暂停下载
	 * @param id
	 * @param loadUrl
	 */
	public void pauseLoadApp(String id,String loadUrl);
	
	/**
	 * 是否下载完成
	 * @param id
	 * @param loadUrl
	 * @return
	 */
	public boolean isLoadFinished(String id,String loadUrl);

}
