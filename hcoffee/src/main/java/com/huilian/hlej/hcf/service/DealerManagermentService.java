package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 经销商管理Service
 * 
 * @author LongZhangWei
 * @date 2017年8月24日 下午7:24:47
 */
public interface DealerManagermentService {

	/**
	 * 保存经销商
	 * @param dealerManagermentVo 
	 * @return
	 */
	public Map<String, String> saveDealerManagermentVo(DealerManagermentVo dealerManagermentVo);
	
	public Page<DealerManagermentVo> getDealerManagermentVoPage(Page<DealerManagermentVo> page, DealerManagermentVo dealerManagermentVo);
	/**
	 * 查询所有的经销商基本信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<DealerManagermentVo> getDealerManagermentVoList(DealerManagermentVo dealerManagermentVo);
	/**
	 * 通过登录查询单个的经销商
	 * @param loginName
	 * @return
	 */
	public DealerManagermentVo getDealerManagermentVo(String loginName);
	/**
	 * 修改经销商基本信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public Map<String, String> updateDealerManagermentVo(DealerManagermentVo dealerManagermentVo);
	/**
	 * 查询经销商及其所关联的设备
	 * @param dealerId
	 * @return
	 */
	public List<DealerEqRelationVo> getDealerEqRelationVoList(String loginName);
	/**
	 * 查询所有机械设备与投放的位置
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
	 * 上级代理列表
	 * @return
	 */
	public List<DealerManagermentVo> superiorAgentList();
	
	/**
	 * 查询某个级别下的代理商
	 * @param dealerGrade
	 * @return
	 */
	public List<Map<String, Object>> superiorAgentListByGrade(String dealerGrade);
	
	/**
	 * 保存代理商类型为公司时  征信查询结果上传文件路径  和证件照片路径到临时表中
	 * @param parms
	 * @return
	 */
	public boolean saveFileOrImage(Map<String,Object> parms);
	
	/**
	 * 删除临时表对应的FilePath
	 * @param filePath
	 */
	public boolean deleteFilePath(String filePath);
	
	/**
	 * 获取临时表里的文件url
	 * @return
	 */
	public Map<String, String> getTempFileUrl(String loginName);
	
	/**
	 * 查询模版列表
	 * @return
	 */
	Page<DivideAccountVo> getDivideAccountVoPage(Page<DivideAccountVo> page, DivideAccountVo divideAccountVo);
	
	/**
	 * 保存绑定模版与代理对应关系
	 * @param dealerTemplateVo
	 */
	public boolean saveBandDingInfo(DealerTemplateVo dealerTemplateVo);
	
	/**
	 * 根据登录名查询分帐模版列表
	 * @param loginName
	 * @return
	 */
	public List<DealerTemplateVo> getDealerTemplateList(String loginName);
}
