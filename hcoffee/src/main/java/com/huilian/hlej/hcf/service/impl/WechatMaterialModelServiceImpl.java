package com.huilian.hlej.hcf.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hdf.extractor.NewOleFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.VendingMachineDao;
import com.huilian.hlej.hcf.dao.WechatMaterialModelDao;
import com.huilian.hlej.hcf.service.WechatMaterialModelService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.WechatMaterialModelVo;
import com.huilian.hlej.hcf.vo.WechatMaterialVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 二维码模板管理Service
 * 
 * @author ZhangZeBiao
 * @date 2017年12月7日 下午5:56:44
 */
@Service
@Transactional
public class WechatMaterialModelServiceImpl implements WechatMaterialModelService {

	@Autowired
	private WechatMaterialModelDao wechatMaterialModelDao;
	
	@Autowired
	private VendingMachineDao vendingMachineDao;
	
	/*@Override
	public Map<String, String> saveDealerManagermentVo(DealerManagermentVo dealerManagermentVo) {
		md5Password(dealerManagermentVo);
		int dealerType = dealerManagermentVo.getDealerType();
		if(2 == dealerType){//公司代理商   需要查询临时表里的征信查询结果文件url 和证件照片url
			Map<String, Object> parms1 = new HashMap<String, Object>();
			parms1.put("type", 1);
			parms1.put("loginName", dealerManagermentVo.getLoginName());
			Map<String, Object> parms2 = new HashMap<String, Object>();
			parms2.put("type", 2);
			parms2.put("loginName", dealerManagermentVo.getLoginName());
			String zjzpUrl = dealerManagermentDao.getFileUrl(parms1);//证件照片url
			String zxcxjgUrl = dealerManagermentDao.getFileUrl(parms2);//征信查询结果url
			
			
			String zjzpFileName = dealerManagermentDao.getFileName(parms1);
			String zxjgFileName = dealerManagermentDao.getFileName(parms2);
			
			
			dealerManagermentVo.setZjzpUrl(zjzpUrl);
			dealerManagermentVo.setZxcxjgUrl(zxcxjgUrl);
			
			dealerManagermentVo.setZjzpFileName(zjzpFileName);
			dealerManagermentVo.setZxjgFileName(zxjgFileName);
			
			//删除监时表的数据
			dealerManagermentDao.deleteTempData();
		}	
		Map<String, String> result = new HashMap<String, String>();
		try {
			int rseult = dealerManagermentDao.queryDealerManagermentVoByLoginName(dealerManagermentVo.getLoginName());
			if(rseult > 0)
				dealerManagermentDao.updateDealerManagermentVo(dealerManagermentVo);
			else
				dealerManagermentDao.saveDealerManagermentVo(dealerManagermentVo);
			List<DealerEqRelationVo> list = new ArrayList<DealerEqRelationVo>();
			
			String[] equimentNo = dealerManagermentVo.getArr_no();
			String[] equimentLo = dealerManagermentVo.getArr_lo();
			if(null != equimentNo && equimentNo.length != 0){
				for(int i=0;i<equimentNo.length;i++){
					DealerEqRelationVo de = new DealerEqRelationVo();
					de.setLoginName(dealerManagermentVo.getLoginName());
					if(null != equimentNo[i] && null != equimentLo[i]){
						de.setVendCode(equimentNo[i]);
						de.setLocation(equimentLo[i]);
						list.add(de);
					}
				}
				saveDealerAndEqRelation(list);	
			}
			result.put("code", "0");
			result.put("msg", "添加成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	private void saveDealerAndEqRelation(List<DealerEqRelationVo> list)throws Exception {
		for(int i=0;i<list.size();i++){
			int result = dealerManagermentDao.isRepeat(list.get(i));
			if(result > 0)
				list.remove(i);
		}
		if(null != list && list.size() >0)
			dealerManagermentDao.saveEquimentAndDealerId(list);
	}
	
	private void updateDealerAndEqRelation(List<DealerEqRelationVo> list)throws Exception {
		List<DealerEqRelationVo> tempList = new ArrayList<DealerEqRelationVo>();
		try {
			for(DealerEqRelationVo dv : list){
				if(dv.getId() != null && dv.getId() != ""){
					dealerManagermentDao.updateDealerEqRelationVo(dv);
				}else{
					tempList.add(dv);
				}
			}
			for(int i=0;i<tempList.size();i++){
				int result = dealerManagermentDao.isRepeat(tempList.get(i));
				if(result > 0)
					tempList.remove(i);
			}
			if(null != tempList && tempList.size() >0)
				dealerManagermentDao.saveEquimentAndDealerId(tempList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<DealerManagermentVo> getDealerManagermentVoPage(Page<DealerManagermentVo> page, DealerManagermentVo dealerManagermentVo) {
		page.setList(dealerManagermentDao.findDealerManagermentVoList(dealerManagermentVo));
		dealerManagermentVo.setPage(page);
		return page;
	}

	@Override
	public List<DealerManagermentVo> getDealerManagermentVoList(DealerManagermentVo dealerManagermentVo) {
		return dealerManagermentDao.findDealerManagermentVoList(dealerManagermentVo);
	}

	@Override
	public DealerManagermentVo getDealerManagermentVo(String loginName) {
		DealerManagermentVo dv = null;
		try {
			dv = dealerManagermentDao.getDealerManagermentVo(loginName);
			
			String zxjgFileName = dv.getZxjgFileName();
			String zjzpFileName = dv.getZjzpFileName();
			
			
			
			//删除监时表的数据
			dealerManagermentDao.deleteTempData();
			
			 * 把征信查询结果和证件照片  的url存入临时表
			 
			String zxcxjgUrl = dv.getZxcxjgUrl();
			String zjzpUrl = dv.getZjzpUrl();
			if(null != zxcxjgUrl){
				if(zxcxjgUrl.indexOf(",") != -1){
					String[] zxcxjgUrls = zxcxjgUrl.split(",");
					String[] zxjgFileNames = zxjgFileName.split(",");
					for(int i=0;i<zxcxjgUrls.length;i++){
						Map<String,Object> parms = new HashMap<String,Object>();
						parms.put("fileName", zxjgFileNames[i]);
						parms.put("loginName", loginName);
						parms.put("filePath", zxcxjgUrls[i]);
						parms.put("type", 2);
						saveFileOrImage(parms);
					}
				}else{
					Map<String,Object> parm = new HashMap<String,Object>();
					parm.put("fileName", zxjgFileName);
					parm.put("loginName", loginName);
					parm.put("filePath", zxcxjgUrl);
					parm.put("type", 2);
					saveFileOrImage(parm);
				}
			}
			
			if(null != zjzpUrl){
				if(zjzpUrl.indexOf(",") != -1){
					String[] zjzpUrls = zjzpUrl.split(",");
					String[] zjzpFileNames = zjzpFileName.split(",");
					for(int i=0;i<zjzpUrls.length;i++){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("fileName", zjzpFileNames[i]);
						map.put("loginName", loginName);
						map.put("filePath", zjzpUrls[i]);
						map.put("type", 1);
						saveFileOrImage(map);
					}
				}else{
					Map<String,Object> maps = new HashMap<String,Object>();
					maps.put("fileName", zjzpFileName);
					maps.put("loginName", loginName);
					maps.put("filePath", zjzpUrl);
					maps.put("type", 1);
					saveFileOrImage(maps);
				}
			}
			
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("id", dv.getParentId());
			String parentName = dealerManagermentDao.getdealerNameById(parms);
			dv.setParentName(parentName!=null?parentName:"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dv;
	}

	@Override
	public Map<String, String> updateDealerManagermentVo(DealerManagermentVo dealerManagermentVo) {
		md5Password(dealerManagermentVo);
		int dealerType = dealerManagermentVo.getDealerType();
		if(2 == dealerType){//公司代理商   需要查询临时表里的征信查询结果文件url 和证件照片url
			Map<String, Object> parms1 = new HashMap<String, Object>();
			parms1.put("type", 1);
			parms1.put("loginName", dealerManagermentVo.getLoginName());
			
			Map<String, Object> parms2 = new HashMap<String, Object>();
			parms2.put("type", 2);
			parms2.put("loginName", dealerManagermentVo.getLoginName());
			
			String zjzpUrl = dealerManagermentDao.getFileUrl(parms1);//证件照片url
			String zxcxjgUrl = dealerManagermentDao.getFileUrl(parms2);//征信查询结果url
			
			String zjzpFileName = dealerManagermentDao.getFileName(parms1);
			String zxjgFileName = dealerManagermentDao.getFileName(parms2);
			
			dealerManagermentVo.setZjzpUrl(zjzpUrl);
			dealerManagermentVo.setZxcxjgUrl(zxcxjgUrl);
			
			dealerManagermentVo.setZjzpFileName(zjzpFileName);
			dealerManagermentVo.setZxjgFileName(zxjgFileName);
			
			//删除监时表的数据
			dealerManagermentDao.deleteTempData();
		}	
		Map<String, String> result = new HashMap<String, String>();
		try {
			dealerManagermentDao.updateDealerManagermentVo(dealerManagermentVo);
			List<DealerEqRelationVo> list = new ArrayList<DealerEqRelationVo>();
			
			String[] equimentNo = dealerManagermentVo.getArr_no();
			String[] equimentLo = dealerManagermentVo.getArr_lo();
			String[] arr_id = dealerManagermentVo.getArr_id();
			if(null != equimentNo && equimentNo.length != 0){
				for(int i=0;i<equimentNo.length;i++){
					DealerEqRelationVo de = new DealerEqRelationVo();
					de.setLoginName(dealerManagermentVo.getLoginName());
					if(null != equimentNo[i] && null != equimentLo[i]){
						de.setId(arr_id[i]!=null?arr_id[i]:"");
						de.setVendCode(equimentNo[i]);
						de.setLocation(equimentLo[i]);
						list.add(de);
					}
				}
				updateDealerAndEqRelation(list);
			}
			result.put("code", "0");
			result.put("msg", "修改成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	*//**
	 * md5加密密码
	 * @param dealerManagermentVo
	 *//*
	private void md5Password(DealerManagermentVo dealerManagermentVo) {
		String password = dealerManagermentVo.getPassword();
		if(null != password && !"".equals(password))
			dealerManagermentVo.setPassword(MethodUtil.md5Password(password));
	}

	@Override
	public List<DealerEqRelationVo> getDealerEqRelationVoList(String loginName) {
		List<DealerEqRelationVo> list = null;
		try {
			list = dealerManagermentDao.getDealerEqRelationVoList(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DealerEqRelationVo> getDealerEqRelationVoAll() {
		List<DealerEqRelationVo> list = null;
		try {
			list = dealerManagermentDao.getDealerEqRelationVoAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean checkLoginName(String loginName) {
		boolean flag = true;
		try {
			int rseult = dealerManagermentDao.queryDealerManagermentVoByLoginName(loginName);
			if(rseult > 0)
				flag = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteDealerEqRelationVo(String id) {
		boolean flag = true;
		try {
			dealerManagermentDao.deleteDealerEqRelationVo(id);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isRelevance(DealerEqRelationVo dealerEqRelationVo) {
		boolean flag = false;
		try {
			Integer result = dealerManagermentDao.isRepeat(dealerEqRelationVo);
			if(null != result && result > 0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<DealerManagermentVo> superiorAgentList() {
		List<DealerManagermentVo> list = null;
		try {
			list = dealerManagermentDao.superiorAgentList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> superiorAgentListByGrade(String dealerGrade) {
		List<Map<String, Object>> resultMap = null;
		try {
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("dealerGrade", dealerGrade);
			resultMap = dealerManagermentDao.superiorAgentListByGrade(parms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public boolean saveFileOrImage(Map<String, Object> parms) {
		boolean flag = true;
		try {
			 dealerManagermentDao.saveFileOrImage(parms);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean deleteFilePath(String filePath) {
		boolean flag = true;
		try {
			 dealerManagermentDao.deleteFilePath(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	

	@Override
	public Map<String, String> getTempFileUrl(String loginName) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Object> parms1 = new HashMap<String, Object>();
		parms1.put("type", 1);
		parms1.put("loginName", loginName);
		Map<String, Object> parms2 = new HashMap<String, Object>();
		parms2.put("type", 2);
		parms2.put("loginName", loginName);
		String zjzpUrl = dealerManagermentDao.getFileUrl(parms1);//证件照片url
		String zxcxjgUrl = dealerManagermentDao.getFileUrl(parms2);//征信查询结果url
		String zjzpFileName = dealerManagermentDao.getFileName(parms1);//证件照片文件原名
		String zxjgFileName = dealerManagermentDao.getFileName(parms2);//征信查询结果文件原名
		resultMap.put("ZJZPURL", zjzpUrl);
		resultMap.put("ZXCXJGURL", zxcxjgUrl);
		resultMap.put("ZJZPFILENAME", zjzpFileName);
		resultMap.put("ZXJGFILENAME", zxjgFileName);
		return resultMap;
	}

	@Override
	public Page<DivideAccountVo> getDivideAccountVoPage(Page<DivideAccountVo> page, DivideAccountVo divideAccountVo) {
		page.setList(dealerManagermentDao.getDivideAccountVoList(divideAccountVo));
		divideAccountVo.setPage(page);
		return page;
	}

	@Override
	public boolean saveBandDingInfo(DealerTemplateVo dealerTemplateVo) {
		boolean flag = true;
		try {
			 dealerManagermentDao.saveBandDingInfo(dealerTemplateVo);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<DealerTemplateVo> getDealerTemplateList(String loginName) {
		List<DealerTemplateVo> list = null;
		try {
			list = dealerManagermentDao.getDealerTemplateList(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}*/

	
	
	
	
	
	
	
	
	
	
	
	
	
/////////////////////////////////////////
	
	
	
	@Override
	public Map<String, String> saveWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			//模板名称
			String modelName = wechatMaterialModelVo.getModelName();
			//模板编号
			String modelNo = wechatMaterialModelVo.getModelNo();
			//备注
			String remark = wechatMaterialModelVo.getRemark();
			//模板ID
			String[] arr_id = wechatMaterialModelVo.getArr_id();
			//二维码素材编号
			String[] arr_no = wechatMaterialModelVo.getArr_no();
			//创建时间
			Date[] arr_ct = wechatMaterialModelVo.getArr_ct();
			//排序
			Integer[] arr_st = wechatMaterialModelVo.getArr_st();
			Integer idLen=arr_id.length;
			Integer noLen=arr_no.length;
			Integer ctLen=arr_ct.length;
			Integer stLen=arr_st.length;
			List<Integer> lenths=new ArrayList<Integer>();
			lenths.add(idLen);
			lenths.add(noLen);
			lenths.add(ctLen);
			lenths.add(stLen);
			Integer max = Collections.max(lenths);
			
			Date createTime=new Date();
			for (int i = 0; i < arr_ct.length; i++) {
				if (null!=arr_ct[i]) {
					createTime=arr_ct[i];
					break;
				}
			}
			
			for (int i = 0; i < max; i++) {
				
				String no=arr_no[i];
				if (null==no||no.length()==0) {
					continue;
				}
				String id=arr_id[i];
				Date ct=arr_ct[i];
				Integer st=arr_st[i];
				
				//弄个新的出来，有ID就更新
				WechatMaterialModelVo newVo=new WechatMaterialModelVo();
				newVo.setModelName(modelName);
				newVo.setModelNo(modelNo);
				newVo.setRemark(remark);
				newVo.setCreateTime(createTime);
				
				/*if (null==ct) {
					newVo.setCreateTime(new Date());
				}else {
					newVo.setCreateTime(ct);
				}*/
				if (null!=st) {
					newVo.setSort(st);
				}
				newVo.setWechatNo(no);
				if (null==id||id.length()==0) {
					wechatMaterialModelDao.saveWechatMaterialModelVo(newVo);
				}else {
					newVo.setId(id);
					wechatMaterialModelDao.updateWechatMaterialModelVo(newVo);
				}
				
			}
			
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
	public Page<WechatMaterialModelVo> getWechatMaterialModelVoPage(Page<WechatMaterialModelVo> page,
			WechatMaterialModelVo wechatMaterialModelVo) {
		wechatMaterialModelVo.setPage(page);
		List<WechatMaterialModelVo> findWechatMaterialModelVoList = wechatMaterialModelDao.findWechatMaterialModelVoList(wechatMaterialModelVo);
		page.setList(findWechatMaterialModelVoList);
		return page;
	}

	@Override
	public List<WechatMaterialModelVo> getWechatMaterialModelVoAll() {
		List<WechatMaterialModelVo> list = null;
		try {
			WechatMaterialModelVo wechatMaterialModelVo=new WechatMaterialModelVo();
			list = wechatMaterialModelDao.findWechatMaterialModelVoList(wechatMaterialModelVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public WechatMaterialModelVo getWechatMaterialModelVo(String id) {
		return wechatMaterialModelDao.getWechatMaterialModelVo(id);
	}

	@Override
	public Map<String, String> updateWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
//			wechatMaterialModelVo.setCreateTime(new Date());
			wechatMaterialModelDao.updateWechatMaterialModelVo(wechatMaterialModelVo);
			result.put("code", "0");
			result.put("msg", "更新成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, String> deleteWechatMaterialModelVo(WechatMaterialModelVo wechatMaterialModelVo) {
		Map<String, String> map = new HashMap<String, String>();
		boolean flag = true;
		try {
			String modelNo = wechatMaterialModelVo.getModelNo();
			VendingMachineVo machineVo=new VendingMachineVo();
			machineVo.setOldWechatModelNo(modelNo);
			machineVo.setWechatModelNo(modelNo);
			List<VendingMachineVo> list=vendingMachineDao.getVendByWechatModelNo(machineVo);
			if (list!=null&&list.size()>0) {
				map.put("code", "1");
				map.put("msg", "该模板正在使用当中......不能删除.....");
				return map;
			}
			wechatMaterialModelDao.deleteWechatMaterialModelVo(wechatMaterialModelVo);
			map.put("code", "0");
			map.put("msg", "删除成功!");
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("code", "1");
			map.put("msg", "删除失败!");
			
		}
		return map;
	}
	
	
	@Override
	public boolean checkNameOfNo(WechatMaterialModelVo wechatMaterialModelVo) {
		boolean flag = true;
		try {
			int rseult = wechatMaterialModelDao.checkNameOfNo(wechatMaterialModelVo);
			if(rseult > 0)
				flag = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<WechatMaterialModelVo> getWechatMaterialModelListByWechatNo(
			WechatMaterialModelVo wechatMaterialModelVo) {
		List<WechatMaterialModelVo> list = null;
		try {
			list = wechatMaterialModelDao.findWechatMaterialModelVoList(wechatMaterialModelVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<WechatMaterialModelVo> getWechatMaterialModelVoListByModelNo(
			WechatMaterialModelVo wechatMaterialModelVo) {
		List<WechatMaterialModelVo> list = null;
		try {
			list = wechatMaterialModelDao.getWechatMaterialModelVoListByModelNo(wechatMaterialModelVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<DealerEqRelationVo> getDealerEqRelationVoList(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DealerEqRelationVo> getDealerEqRelationVoAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkLoginName(String loginName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDealerEqRelationVo(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRelevance(DealerEqRelationVo dealerEqRelationVo) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}
