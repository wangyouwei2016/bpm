package com.dstz.component.pubsub;

/**
 * ab message 消息发布
 *
 * @author wacxhs
 */
public interface AbMessagePublisher {

	/**
	 * 消息发布
	 *
	 * @param channel 发布通道
	 * @param body    消息体
	 */
	void publish(String channel, Object body);

}
