package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.OrderImportVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ImportDao {

	void save(List<OrderImportVo> list);
	
	void saveVo(OrderImportVo vo);
}
