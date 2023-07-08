/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.dstz.auth.constant;

/**
 * 授权校验常量
 *
 * @author lightning
 */
public interface AuthConstant {

	/**
	 * 表字段
	 */
	String CLIENT_FIELDS = "client_id_, CONCAT('{noop}',client_secret_) as client_secret_, resource_ids_, scope_, grant_types_, " +
		"redirect_uri_, authorities_, access_token_validity_, " +
		"refresh_token_validity_, commentate_, autoapprove_, status_, is_deleted_, create_time_, create_by_, create_org_id_, update_time_,updater_,update_by_";

	/**
	 * 查询语句
	 */
	String BASE_STATEMENT = "select " + CLIENT_FIELDS + " from oauth_client";

	/**
	 * 查询排序
	 */
	String DEFAULT_FIND_STATEMENT = BASE_STATEMENT + " order by client_id_";

	/**
	 * 查询条件
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_STATEMENT + " where client_id_ = ?";

    /**
     * oauth内置接口集合
     */
	String [] OAUTH_DEFAULT_URL = new String[]{"/oauth/token","/oauth/authorize"};

    /**
     * oauth grant_type
     */
    String GRANT_TYPE = "grant_type";

    /**
     * grant_type authorization_code
     */
    String AUTHORIZATION_CODE = "authorization_code";

}
