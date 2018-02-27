package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.hcf.vo.WechatMaterialVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 二维码素材管理service
 * @author ZhangZeBiao
 * @date 2017年12月6日 下午1:50:57
 *
 */
public interface WechatMaterialService {


	/**
	 * 保存二维码素材
	 * @param dealerManagermentVo 
	 * @return
	 */
	public Map<String, String> saveWechatMaterialVo(WechatMaterialVo wechatMaterialVo);
	
	/**
	 * 二维码素材分页
	 * @param page
	 * @param lottoVendVo
	 * @return
	 */
	public Page<WechatMaterialVo> getWechatMaterialVoPage(Page<WechatMaterialVo> page, WechatMaterialVo wechatMaterialVo);


	/**
	 * 根据ID获取一条数据
	 * @param id
	 * @return
	 */
	public WechatMaterialVo getWechatMaterialVo(String id);

	/**
	 * 删除二维码素材
	 * @param wechatMaterialVo
	 * @return
	 */
	public boolean deleteWechatMaterialVo(WechatMaterialVo wechatMaterialVo);

	/**
	 * 更新
	 * @param wechatMaterialVo
	 * @return
	 */
	public Map<String, String> updateWechatMaterialVo(WechatMaterialVo wechatMaterialVo);

	/**
	 * 获取所有
	 * @return
	 */
	public List<WechatMaterialVo> getWechatMaterialVoAll();

	/**
	 * 验证名称或编码是否存在 true:可用；  false：不可用
	 * @param wechatMaterialVo
	 * @return
	 */
	public boolean checkNameOfNo(WechatMaterialVo wechatMaterialVo);



	
	
	

}
