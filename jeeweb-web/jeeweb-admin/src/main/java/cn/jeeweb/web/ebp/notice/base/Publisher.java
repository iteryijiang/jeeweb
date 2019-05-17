package cn.jeeweb.web.ebp.notice.base;

public interface Publisher {

	/**
	 * 
	 * 对指定人员推送消息
	 * 
	 * @param userId
	 *            用户ID 不能为空
	 * @param message
	 *            推送的消息内容
	 */
	void publish(String userId, String message);

	/**
	 * 对指定门店的指定人员推送消息
	 * 
	 * @param shopId
	 *            店铺ID 不能为空
	 * @param userId
	 *            用户ID 不能为空
	 * @param message
	 *            推送消息内容
	 */
	void publish(String shopId, String userId, String message);

	/**
	 * 对所有在线用户推送消息
	 * 
	 * @param message
	 *            推送消息内容
	 */
	void publish(String message);

}
