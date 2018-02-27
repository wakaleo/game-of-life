package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.PayVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 渠道dao
 * @author yangbo
 * @version 2017/03/1
 */
@MyBatisDao
public interface PayMentgerDao extends CrudDao<ChannelVo> {
	public   List<PayVo> getPayMentgerPage(PayVo payVo);
	
	public void savePayMenter(PayVo payVo);

	public PayVo getPayById(PayVo payVo);

	public  void updatePayById(PayVo payVo);

	public void updateChannelStatus(PayVo payVo);

	public PayVo validateChannelName(PayVo payVo);
}
