package com.huilian.hlej.hcf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.GoodsTypeManagementDao;
import com.huilian.hlej.hcf.service.GoodsTypeManagementService;
import com.huilian.hlej.hcf.vo.GoodsTypeVo;
import com.huilian.hlej.hcf.vo.GoodsVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional
public class GoodsTypeManagementServiceImpl implements GoodsTypeManagementService {

	@Autowired
	private GoodsTypeManagementDao goodsTypeManagementDao;
	
	@Override
	public List<GoodsTypeVo> getGoodsTypeVoList(GoodsTypeVo goodsTypeVo) {
		List<GoodsTypeVo> list = null;
		try {
			list = goodsTypeManagementDao.getGoodsTypeVoList(goodsTypeVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean saveGoodsTypeVo(GoodsTypeVo goodsTypeVo) {
		boolean flag = true;
		try {
			goodsTypeManagementDao.saveGoodsTypeVo(goodsTypeVo);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateGoodsTypeVo(GoodsTypeVo goodsTypeVo) {
		boolean flag = true;
		try {
			goodsTypeManagementDao.updateGoodsTypeVo(goodsTypeVo);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Page<GoodsTypeVo> getGoodsTypeVoPage(Page<GoodsTypeVo> page, GoodsTypeVo goodsTypeVo) {
		goodsTypeVo.setPage(page);
		page.setList(goodsTypeManagementDao.getGoodsTypeVoList(goodsTypeVo));
		return page;
	}

	@Override
	public GoodsTypeVo getGoodsTypeVoById(String id) {
		return goodsTypeManagementDao.getGoodsTypeVoById(id);
	}

	@Override
	public boolean deleteGoodsTypeVoById(String id) {
		boolean falg = true;
		try {
			goodsTypeManagementDao.deleteGoodsTypeVoById(id);
		} catch (Exception e) {
			falg = false;
			e.printStackTrace();
		}
		return falg;
	}

	@Override
	public boolean isCanDeleteGoodsType(String id) {
		boolean falg = false;
		try {
			Integer result = goodsTypeManagementDao.isCanDeleteGoodsType(id);
			if(null != result && result > 0){
				falg = true;
			}
		} catch (Exception e) {
			falg = false;
			e.printStackTrace();
		}
		return falg;
	}

}
