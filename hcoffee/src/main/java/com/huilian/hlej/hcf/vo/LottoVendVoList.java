package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 抽奖活动管理实体类
 * 
 * @author ZhangZeBiao
 * @date 2017年10月24日 上午11:48:03
 */
public class LottoVendVoList  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<LottoVendVo> lottoVendVoList;

	public List<LottoVendVo> getLottoVendVoList() {
		return lottoVendVoList;
	}

	public void setLottoVendVoList(List<LottoVendVo> lottoVendVoList) {
		this.lottoVendVoList = lottoVendVoList;
	}
	

}
