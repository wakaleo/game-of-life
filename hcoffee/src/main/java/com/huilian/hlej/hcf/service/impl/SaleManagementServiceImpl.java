package com.huilian.hlej.hcf.service.impl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.SaleManagementDao;
import com.huilian.hlej.hcf.service.SaleManagementService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.OrderDetailVo;
import com.huilian.hlej.hcf.vo.SaleDetailVo;
import com.huilian.hlej.hcf.vo.SaleManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional
public class SaleManagementServiceImpl implements SaleManagementService {

	@Autowired
	private SaleManagementDao saleManagementDao;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Page<SaleManagementVo> getSaleManagementVoPage(Page<SaleManagementVo> page, SaleManagementVo saleManagementVo) {
		saleManagementVo.setPage(page);
		saleManagementVo.setStartTimeStr(saleManagementVo.getStartTime()!=null?format.format(saleManagementVo.getStartTime()):null);
		saleManagementVo.setEndTimeStr(saleManagementVo.getEndTime()!=null?format.format(saleManagementVo.getEndTime()):null);
		List<SaleManagementVo> saleManagementList = saleManagementDao.getSaleManagementList(saleManagementVo);
 		page.setList(saleManagementList);
		return page;
	}

	@Override
	public Page<SaleDetailVo> getSaleDetailVoPage(Page<SaleDetailVo> page, SaleDetailVo saleDetailVo) {
		saleDetailVo.setPage(page);
		saleDetailVo.setStartTimeStr(saleDetailVo.getStartTime()!=null?format.format(saleDetailVo.getStartTime()):null);
		saleDetailVo.setEndTimeStr(saleDetailVo.getEndTime()!=null?format.format(saleDetailVo.getEndTime()):null);
		page.setList(saleManagementDao.getSaleDetailVoList(saleDetailVo));
		return page;
	}

	@Override
	public Page<OrderDetailVo> getOrderDatailVoPage(Page<OrderDetailVo> page, OrderDetailVo orderDetailVo) {
		orderDetailVo.setPage(page);
		Timestamp shipTime = orderDetailVo.getShipTime();
		String vendCodes = orderDetailVo.getVendCodes();
		if(null != shipTime){
			String shipTimeStr = format.format(shipTime);
			orderDetailVo.setShipTimeStr(shipTimeStr);
		}
		if(!"".equals(vendCodes) && null != vendCodes){
			orderDetailVo.setVendCodes(MethodUtil.getFormatStr(vendCodes));
		}
		page.setList(saleManagementDao.getOrderDatailVoList(orderDetailVo));
		return page;
	}

	@Override
	public List<DealerEqRelationVo> getVendCodesListByLoginName(String loginName) {
		List<DealerEqRelationVo> list = null;
		try {
			list = saleManagementDao.getVendCodesListByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
