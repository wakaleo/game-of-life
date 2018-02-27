package com.huilian.hlej.hcf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.GoodsManagementDao;
import com.huilian.hlej.hcf.service.GoodsManagementService;
import com.huilian.hlej.hcf.vo.GoodsVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional
public class GoodsManagementServiceImpl implements GoodsManagementService {

	@Autowired
	private GoodsManagementDao goodsManagementDao;
	
	@Override
	public List<GoodsVo> getGoodsVoList(GoodsVo goodsVo) {
		List<GoodsVo> list = null;
		try {
			list = goodsManagementDao.getGoodsVoList(goodsVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean saveGoodsVo(GoodsVo goodsVo) {
		boolean flag = true;
		try {
			
			float priceInto = goodsVo.getPriceInto();
			float priceOut = goodsVo.getPriceOut();
			goodsVo.setPriceInto(priceInto*100);
			goodsVo.setPriceOut(priceOut*100);
			goodsManagementDao.saveGoodsVo(goodsVo);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateGoodsVo(GoodsVo goodsVo) {
		boolean flag = true;
		try {
			if(!goodsVo.getGoodsName().equals("") && goodsVo.getGoodsName() != null){
				float priceInto = goodsVo.getPriceInto();
				float priceOut = goodsVo.getPriceOut();
				goodsVo.setPriceInto(priceInto*100);
				goodsVo.setPriceOut(priceOut*100);	
				goodsManagementDao.updateGoodsVo(goodsVo);
			}else{
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Page<GoodsVo> getGoodsVoPage(Page<GoodsVo> page, GoodsVo goodsVo) {
		goodsVo.setPage(page);
		page.setList(goodsManagementDao.getGoodsVoList(goodsVo));
		return page;
	}

	@Override
	public GoodsVo getGoodsVoById(String id) {
		return goodsManagementDao.getGoodsVoById(id);
	}

	@Override
	public boolean updateIsSale(GoodsVo goodsVo) {
		boolean flag = false;
		try {
			if(!goodsVo.getId().equals("")){
				goodsManagementDao.updateIsSale(goodsVo);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
