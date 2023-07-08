package com.dstz.auth.authentication.api;

import com.dstz.auth.authentication.vo.SysApplicationVO;

import java.util.List;

/**
 * 系统应用接口
 *
 * @author wacxhs
 */
public interface SysApplicationApi{

	/**
	 * 根据编码获取系统应用
	 *
	 * @param code 编码
	 * @return 系统应用
	 */
	SysApplicationVO getByCode(String code);

	/**
	 * 获取所有应用编码
	 *
	 * @return 所有应用编码
	 */
	List<String> getAllCode();

	/**
	 * 获取当前可用的移动端主应用
	 * @return
	 */
	SysApplicationVO getEnabledMobileApp();
}
