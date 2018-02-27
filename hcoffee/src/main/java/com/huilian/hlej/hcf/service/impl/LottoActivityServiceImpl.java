package com.huilian.hlej.hcf.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.DealerManagermentDao;
import com.huilian.hlej.hcf.dao.LottoActivityDao;
import com.huilian.hlej.hcf.service.DealerManagermentService;
import com.huilian.hlej.hcf.service.LottoActivityService;
import com.huilian.hlej.hcf.service.LottoVendService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 经销商Service实现类
 * @author LongZhangWei
 * @date 2017年8月24日 下午7:27:50
 */
@Service
@Transactional
public class LottoActivityServiceImpl implements LottoActivityService {

	
	@Autowired
	private LottoActivityDao lottoActivityDao;
	

	@Override
	public Page<LottoActivityVo> getLottoActivityVoPage(Page<LottoActivityVo> page, LottoActivityVo lottoActivityVo) {
		lottoActivityVo.setPage(page);
		//设置活动状态：1-未进行  2-进行中  3-已结束
		List<LottoActivityVo> findLottoActivityVoList = lottoActivityDao.findLottoActivityVoList(lottoActivityVo);
		if (findLottoActivityVoList!=null&&findLottoActivityVoList.size()>0) {
			for (LottoActivityVo lactivityVo : findLottoActivityVoList) {
				//设置活动状态：1-未进行  2-进行中  3-已结束  和抽奖、中奖次数
				setActivityStatus(lactivityVo);
			}
		}
		page.setList(findLottoActivityVoList);
		return page;
	}

	@Override
	public List<LottoActivityVo> getLottoActivityVoList(LottoActivityVo lottoActivityVo) {
		//设置活动状态：1-未进行  2-进行中  3-已结束
		List<LottoActivityVo> findLottoActivityVoList = lottoActivityDao.findLottoActivityVoList(lottoActivityVo);
		if (findLottoActivityVoList!=null&&findLottoActivityVoList.size()>0) {
			for (LottoActivityVo lactivityVo : findLottoActivityVoList) {
				//设置活动状态：1-未进行  2-进行中  3-已结束
				setActivityStatus(lactivityVo);
			}
		}
		return findLottoActivityVoList;
	}

	@Override
	public LottoActivityVo getLottoActivityVo(String activityNo) {
		LottoActivityVo lactivityVo = null;
		try {
			lactivityVo = lottoActivityDao.getLottoActivityVo(activityNo);
			//设置活动状态：1-未进行  2-进行中  3-已结束
			setActivityStatus(lactivityVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lactivityVo;
	}

	/**
	 * 设置活动状态方法：1-未进行  2-进行中  3-已结束
	 * @param lactivityVo
	 */
	private void setActivityStatus(LottoActivityVo lactivityVo) {
		Date nowTime = lactivityVo.getNowTime()!=null?lactivityVo.getNowTime():new Date();
		//根据当前时间设置
		if (nowTime.getTime()<lactivityVo.getStartTime().getTime()) {
			lactivityVo.setActivityStatus(1);
		}else if (nowTime.getTime()>lactivityVo.getEndTime().getTime()) {
			lactivityVo.setActivityStatus(3);
		}else {
			lactivityVo.setActivityStatus(2);
		}
		//抽奖次数
		Integer drawNum=lottoActivityDao.getDrawNum(lactivityVo.getActivityNo());
		//中奖次数
		Integer winnerNum=lottoActivityDao.getWinnerNum(lactivityVo.getActivityNo());
		
		lactivityVo.setDrawNum(drawNum);
		lactivityVo.setWinnerNum(winnerNum);
	}

	@Override
	public List<LottoEqRelationVo> getLottoEqRelationVoList(String activityNo) {
		List<LottoEqRelationVo> list = null;
		try {
			list = lottoActivityDao.getLottoEqRelationVoList(activityNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LottoEqRelationVo> getLottoEqRelationVoAll() {
		List<LottoEqRelationVo> list = null;
		try {
			list = lottoActivityDao.getLottoEqRelationVoAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



	@Override
	public boolean checkActivityName(String activityName) {
		boolean flag = false;
		try {
			Integer result = lottoActivityDao.checkActivityName(activityName);
			if(null != result && result > 0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Map<String, String> saveLottoActivityVo(LottoActivityVo lottoActivityVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Date date = new Date();
			lottoActivityVo.setCreateTime(date);
			//String format = new SimpleDateFormat("yyyyMMdd").format(date);
			//lottoActivityVo.setActivityNo(format.toString()+(int)((Math.random()*9+1)*100000));
			if (lottoActivityVo.getId()!=null&&lottoActivityVo.getId().length()>0) {
				lottoActivityDao.updateLottoActivityVo(lottoActivityVo);
			}
			lottoActivityDao.saveLottoActivityVo(lottoActivityVo);
			result.put("code", "0");
			result.put("msg", "添加成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, String> updateLottoActivityVo(LottoActivityVo lottoActivityVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (lottoActivityVo.getId()!=null&&lottoActivityVo.getId().length()>0) {
				lottoActivityDao.updateLottoActivityVo(lottoActivityVo);
			}result.put("code", "0");
			result.put("msg", "添加成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean checkActivityNo(String activityNo) {
		boolean flag = false;
		try {
			Integer result = lottoActivityDao.checkActivityNo(activityNo);
			if(null != result && result > 0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
