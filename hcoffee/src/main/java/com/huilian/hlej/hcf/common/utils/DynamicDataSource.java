package com.huilian.hlej.hcf.common.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @author ZhangZeBiao
 * @date 2017年12月15日 上午10:21:20
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDBType();  
	}

}
