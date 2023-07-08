package com.dstz.component.upload.api;

import java.io.InputStream;

/**
 * <pre>
 * 描述：系统的上传流接口
 * </pre>
 *
 * @author lightning
 */
public interface IUploader {
	/**
	 * <pre>
	 * 类型
	 * </pre>
	 * 
	 * @return
	 */
	String type();


	/**
	 * <pre>
	 * 上传流
	 * </pre>
	 * 
	 * @param is
	 *            流
	 * @param name
	 *            流名
	 * @param type
	 * 			  文件类型
	 * @return 流路径，take的时候以这个路径能获取到对应的流
	 */
	String upload(InputStream is, String name,String type);


	/**
	 * <pre>
	 * 根据流路径 获取流
	 * </pre>
	 * 
	 * @param path
	 * @return
	 */
	InputStream take(String path);
	
	/**
	 * <pre>
	 * 删除流
	 * </pre>
	 * 
	 * @param path
	 */
	void remove(String path);
}
