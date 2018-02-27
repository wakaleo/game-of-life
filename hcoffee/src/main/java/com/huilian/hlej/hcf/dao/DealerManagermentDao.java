package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
/**
 * 经销商管理Dao
 * @author LongZhangWei
 * @date 2017年8月24日 下午7:22:26
 */
@MyBatisDao
public interface DealerManagermentDao extends CrudDao<DealerManagermentVo> {
	/**
	 * 保存经销商基本信息
	 * @param dealerManagermentVo
	 */
	void saveDealerManagermentVo(DealerManagermentVo dealerManagermentVo);
	/**
	 * 保存经销商关联的所有设备
	 * @param list
	 */
	void saveEquimentAndDealerId(List<DealerEqRelationVo> list);
	/**
	 * 查询所有的经销商信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<DealerManagermentVo> findDealerManagermentVoList(DealerManagermentVo dealerManagermentVo);
	/**
	 * 通过登录名查询单个具体的经销商
	 * @param loginName
	 * @return
	 */
	public DealerManagermentVo getDealerManagermentVo(String loginName);
	/**
	 * 修改经销商基本信息
	 * @param dealerManagermentVo
	 */
	void updateDealerManagermentVo(DealerManagermentVo dealerManagermentVo);
	/**
	 * 通过经销商的ID查询其关联的设备
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
	 * 根据登录名查询单个经销商
	 * @param loginName
	 * @return
	 */
	public int queryDealerManagermentVoByLoginName(String loginName);
	
	/**
	 * 更新经销商关联的所有设备
	 * @param list
	 */
	void updateDealerEqRelationVo(DealerEqRelationVo dealerEqRelationVo);
	
	/**
	 * 删除关联设备
	 * @param id
	 */
	void deleteDealerEqRelationVo(String id);
	
	/**
	 * 判断插入的机械编码是否与数据库已存在的重复
	 * @param dealerEqRelationVo
	 * @return
	 */
	public Integer isRepeat(DealerEqRelationVo dealerEqRelationVo);
	
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
	public List<Map<String, Object>> superiorAgentListByGrade(Map<String,Object> parms);
	
	/**
	 * 查询代理商姓名根据代理商id
	 * @param id
	 * @return
	 */
	public String getdealerNameById(Map<String,Object> parms);
	
	/**
	 * 保存代理商类型为公司时  征信查询结果上传文件路径  和证件照片路径到临时表中
	 * @param parms
	 * @return
	 */
	public void saveFileOrImage(Map<String,Object> parms);
	
	/**
	 * 获取临时表里的 文件url
	 * @param parms
	 * @return
	 */
	public String getFileUrl(Map<String,Object> parms);
	
	/**
	 * 删除临时表的数据
	 * @param parms
	 */
	public void deleteTempData();
	
	/**
	 * 删除临时表对应的FilePath
	 * @param filePath
	 */
	public void deleteFilePath(String filePath);
	
	/**
	 * 获取上传文件的文件原名
	 * @param parms
	 * @return
	 */
	public String getFileName(Map<String,Object> parms);
	
	/**
	 * 查 询模版列表
	 * @return
	 */
	List<DivideAccountVo> getDivideAccountVoList(DivideAccountVo divideAccountVo);
	
	/**
	 * 保存绑定模版与代理商对应关系
	 * @param dealerTemplateVo
	 */
	public void saveBandDingInfo(DealerTemplateVo dealerTemplateVo);
	
	/**
	 * 根据登录名查询分帐模版列表
	 * @param loginName
	 * @return
	 */
	public List<DealerTemplateVo> getDealerTemplateList(String loginName);
}
