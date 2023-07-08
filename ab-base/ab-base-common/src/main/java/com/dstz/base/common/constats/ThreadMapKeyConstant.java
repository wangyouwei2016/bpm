package com.dstz.base.common.constats;

/**
 * 线程变量KEY常量定义
 *
 * @author wacxhs
 */
public abstract class ThreadMapKeyConstant {

	public static class DataPrivilege {

		public static final String PREFIX = "DataPrivilege";

		/**
		 * 当前用户数据权限
		 */
		public static final String CURRENT_USER = PREFIX + ".currentUser";
	}

	public static class QueryFilter {

		/**
		 *
		 */
		public static final String PREFIX = "QueryFilter";

		/**
		 * 允许参数过滤
		 */
		public static final String ACCESS_QUERY_FILTERS = PREFIX + ".accessQueryFilters";
	}

	public static class BizFormDesign {
		public static final String PREFIX = "BizFormDesign";

		public static final String BO = PREFIX + ".bo";
		
		public static final String BOS = PREFIX + ".bos";
		
		public static final String TABLES = PREFIX + ".tables";
		
	}
	
	public static class BizData {
		public static final String PREFIX = "BizData";
		
		public static final String IS_FLOW = PREFIX + ".isFlow";
		
	}

}
