package vos.client.zjenergy.interfaces;

public interface HtmlBaseInterface {
	
	void send();
	
	/**
	 * 
	 * @param data
	 * @param id
	 */
	void send(String data,int id);
	
	/**
	 * 
	 * @param data
	 * @param id
	 * @param callBack
	 */
	void send(String data,int id,String callBack);
	

}
