package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.WechatMaterialModelVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hcf.vo.WechatMaterialModelVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 二维码模板管理Service
 * 
 * @author ZhangZeBiao
 * @date 2017年12月7日 下午5:56:44
 */
public interface WechatMaterialModelService {

	/**
	 * 保存二维码模板
	 * @param wechatMaterialModelVo 
	 * @return
	 */
	public Map<String, String> saveWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo);
	
	/**
	 * 分页
	 * @param page
	 * @param wechatMaterialModelVo
	 * @return
	 */
	public Page<WechatMaterialModelVo> getWechatMaterialModelVoPage(Page<WechatMaterialModelVo> page, WechatMaterialModelVo wechatMaterialModelVo);
	/**
	 * 查询所有的二维码模板
	 * @param wechatMaterialModelVo
	 * @return
	 */
	public List<WechatMaterialModelVo> getWechatMaterialModelVoAll();
	/**
	 * 通过id查询单个的二维码模板
	 * @param id
	 * @return
	 */
	public WechatMaterialModelVo getWechatMaterialModelVo(String id);
	/**
	 * 修改二维码模板
	 * @param wechatMaterialModelVo
	 * @return
	 */
	public Map<String, String> updateWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo);
	/**
	 * 查询模板及其所关联的素材
	 * @param dealerId
	 * @return
	 */
	public List<DealerEqRelationVo> getDealerEqRelationVoList(String loginName);
	/**
	 * 查询所有模板
	 * @return
	 */
	public List<DealerEqRelationVo> getDealerEqRelationVoAll();
	/**
	 * 检测用户是否存在
	 * @param loginName
	 * @return
	 */
	public boolean checkLoginName(String loginName);
	
	/**
	 * 删除关联设备
	 * @param id
	 */
	public boolean deleteDealerEqRelationVo(String id);
	
	/**
	 * 检测机械编码是否已关联
	 * @param loginName
	 * @return
	 */
	public boolean isRelevance(DealerEqRelationVo dealerEqRelationVo);

	/**
	 * 删除
	 * 0：删除成功；1：删除失败
	 * @param wechatMaterialModelVo
	 * @return
	 */
	Map<String, String> deleteWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 验证名称或编码是否存在 true:可用；  false：不可用
	 * @param wechatMaterialModelVo
	 * @return
	 */
	boolean checkNameOfNo(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 根据素材No 获取列表
	 * @param wechatMaterialModelVo
	 * @return
	 */
	public List<WechatMaterialModelVo> getWechatMaterialModelListByWechatNo(
			WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 根据模板no获取列表（不分组）
	 * @param wechatMaterialModelVo
	 * @return
	 */
	public List<WechatMaterialModelVo> getWechatMaterialModelVoListByModelNo(
			WechatMaterialModelVo wechatMaterialModelVo);
	
	
	
}
