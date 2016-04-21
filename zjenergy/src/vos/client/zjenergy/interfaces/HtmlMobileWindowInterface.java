package vos.client.zjenergy.interfaces;

public interface HtmlMobileWindowInterface {
	// 关闭窗口
	void closeMobileWindow();

	// 关闭所有窗口
	void closeAllMobileWindow();

	/**
	 * 设置窗口标题
	 * 
	 * @param title
	 */
	void setMobileWindowTitle(String title);

	/**
	 * 打开窗口
	 * 
	 * @param url
	 * @param title
	 * @param callBack
	 */
	void openMobileWindow(String url, String title, String callBack);

	// 返回上一页
	public void goBack();

	// 返回上一页并带数据
	public void goResultBack();
}
