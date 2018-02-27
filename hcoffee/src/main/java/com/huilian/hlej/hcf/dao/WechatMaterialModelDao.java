package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.WechatMaterialModelVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 二维码模板DAO
 * @author ZhangZeBiao
 * @date 2017年12月7日 下午7:20:11
 *
 */
@MyBatisDao
public interface WechatMaterialModelDao {

	/**
	 * 保存
	 * @param wechatMaterialModelVo
	 */
	void saveWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 分页
	 * @param wechatMaterialModelVo
	 * @return
	 */
	List<WechatMaterialModelVo> findWechatMaterialModelVoList(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 删除
	 * @param wechatMaterialModelVo
	 */
	void deleteWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 根据ID查
	 * @param id
	 * @return
	 */
	WechatMaterialModelVo getWechatMaterialModelVo(String id);

	/**
	 * 更新
	 * @param wechatMaterialModelVo
	 */
	void updateWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 验证名称或编码是否存在
	 * @param wechatMaterialModelVo
	 * @return
	 */
	int checkNameOfNo(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 获取列表（不分组）
	 * @param wechatMaterialModelVo
	 * @return
	 */
	List<WechatMaterialModelVo> getWechatMaterialModelVoListNotGroup(WechatMaterialModelVo wechatMaterialModelVo);

	/**
	 * 根据模板no获取列表（不分组）
	 * @param wechatMaterialModelVo
	 * @return
	 */
	List<WechatMaterialModelVo> getWechatMaterialModelVoListByModelNo(WechatMaterialModelVo wechatMaterialModelVo);


}
