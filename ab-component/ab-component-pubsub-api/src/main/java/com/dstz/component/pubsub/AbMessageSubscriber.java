package com.dstz.component.pubsub;

/**
 * 消息订阅者
 *
 * @author wacxhs
 */
public interface AbMessageSubscriber<T> {

	/**
	 * 获取消息订阅通道
	 *
	 * @return 消息订阅通道
	 */
	String getChannel();

	/**
	 * 订阅消息
	 * 
	 * @param body 订阅消息体
	 */
	void subscribe(T body);
}
