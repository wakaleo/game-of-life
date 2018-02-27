package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.OrderImportVo;

public interface ImportService {

	boolean save(List<OrderImportVo> list);
	
	boolean saveVo(OrderImportVo vo);
}
