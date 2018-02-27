package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.WechatMaterialVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 
 * @author ZhangZeBiao
 * @date 2017年12月6日 下午1:54:10
 *
 */
@MyBatisDao
public interface WechatMaterialDao {

	/**
	 * 保存二维码素材
	 * @param wechatMaterialVo
	 */
	void saveWechatMaterialVo(WechatMaterialVo wechatMaterialVo);

	/**
	 * 获取分页
	 * @param wechatMaterialVo
	 * @return
	 */
	List<WechatMaterialVo> findWechatMaterialVoList(WechatMaterialVo wechatMaterialVo);

	/**
	 * 删除指定二维码素材
	 * @param wechatMaterialVo
	 */
	void deleteWechatMaterialVo(WechatMaterialVo wechatMaterialVo);

	/**
	 * 更新二维码素材
	 * @param wechatMaterialVo
	 */
	void updateWechatMaterialVo(WechatMaterialVo wechatMaterialVo);

	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	WechatMaterialVo getWechatMaterialVo(String id);

	/**
	 * 验证名称或编码是否存在
	 * @param wechatMaterialVo
	 * @return
	 */
	int checkNameOfNo(WechatMaterialVo wechatMaterialVo);


}
