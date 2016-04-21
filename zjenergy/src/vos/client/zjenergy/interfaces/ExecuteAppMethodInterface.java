package vos.client.zjenergy.interfaces;

public interface ExecuteAppMethodInterface {
	/**
	 * 根据返回的方法名
	 * 调用本地的方法
	 * @param callBackIndex
	 * @param funaction
	 * @param data
	 */
	public void executeMethod(int callBackIndex, String funaction,String data);
}
