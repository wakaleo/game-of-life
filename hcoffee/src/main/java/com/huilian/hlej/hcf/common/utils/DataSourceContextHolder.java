package com.huilian.hlej.hcf.common.utils;

/**
 * 数据源设置
 * @author ZhangZeBiao
 * @date 2017年12月15日 上午10:24:49
 *
 */
public class DataSourceContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDBType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDBType() {
		return ((String) contextHolder.get());
	}

	public static void clearDBType() {
		contextHolder.remove();
	}
}
