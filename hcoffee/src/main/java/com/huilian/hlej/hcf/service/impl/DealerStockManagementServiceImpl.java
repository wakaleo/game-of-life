package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huilian.hlej.hcf.dao.DealerStockManagementDao;
import com.huilian.hlej.hcf.service.DealerStockManagementService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerStockDetailVo;
import com.huilian.hlej.hcf.vo.DealerStockVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.StringUtils;
/**
 * 代理商库存管理Service实现类
 * @author LongZhangWei
 * @date 2017年9月4日 下午2:38:33
 */
@Service
@Transactional
public class DealerStockManagementServiceImpl implements DealerStockManagementService {

	@Autowired
	private DealerStockManagementDao dealerStockManagementDao;
	
	@Override
	public List<DealerStockVo> getDealerStockVoList(DealerStockVo dealerStockVo) {
		List<DealerStockVo> list = null;
		try {
			list = dealerStockManagementDao.getDealerStockVoList(dealerStockVo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Page<DealerStockVo> getDealerStockVoPage(Page<DealerStockVo> page, DealerStockVo dealerStockVo) {
		dealerStockVo.setPage(page);
		page.setList(dealerStockManagementDao.getDealerStockVoList(dealerStockVo));
		return page;
	}

	@Override
	public String getVendCodesByLoginName(String loginName) {
		String vendCodes = "";
		try {
			vendCodes = dealerStockManagementDao.getVendCodesByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendCodes;
	}

	@Override
	public List<DealerStockDetailVo> getDealerStockDetailVoList(DealerStockDetailVo dealerStockDetailVo) {
		List<DealerStockDetailVo> list = null;
		try {
			list = dealerStockManagementDao.getDealerStockDetailVoList(dealerStockDetailVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Page<DealerStockDetailVo> getDealerStockDetailVoPage(Page<DealerStockDetailVo> page,
			DealerStockDetailVo dealerStockDetailVo) {
		dealerStockDetailVo.setPage(page);
		String vendCodesTranBefore = dealerStockDetailVo.getVendCodes();
		String vendCodesTranAfter = StringUtils.getFormatStr(vendCodesTranBefore);
		dealerStockDetailVo.setVendCodes(vendCodesTranAfter);
		List<DealerStockDetailVo> list = dealerStockManagementDao.getDealerStockDetailVoList(dealerStockDetailVo);
		
		//代理商所拥有的机械数
		int machineNum = dealerStockDetailVo.getVendCodes() !=null ? dealerStockDetailVo.getVendCodes().split(",").length : 0;
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		if(null != vendCodesTranBefore && !"".equals(vendCodesTranBefore)){
			map = getMachineInventoryInfo(vendCodesTranBefore);
		}
		
		for(DealerStockDetailVo ds : list){
			int suggestProcurementAmount = calculateValue(machineNum, ds,"setSugges",map); 
			ds.setSuggestProcurementAmount(suggestProcurementAmount);
		}
		page.setList(list);
		return page;
	}
	
	/**
	 * 获取每台机械各个商品的货道数
	 * @param vendCode 机械编码
	 * @return 返回一个商品ID为key,该商品ID对应的货道数为value的Map
	 */
	private Map<String, Integer> getMachineInventoryInfo(String vendCodes){
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(vendCodes.indexOf(",") != -1){//多台机器
			String[] vendCodeArr = vendCodes.split(",");
			for(String vendCode : vendCodeArr){
				getShelfNumMap(map, vendCode);
			}
		}else{//单台机器
			getShelfNumMap(map, vendCodes);
		}
		return map;
	}

	/**
	 * 设置每台机械每个商品对应的货道数
	 * @param map
	 * @param vendCode
	 */
	private void getShelfNumMap(Map<String, Integer> map, String vendCode) {
		//时间戳（一分钟后请求失效）
		Long timestamp = System.currentTimeMillis();
		//接口地址
		String url="http://114.55.54.35:8088/xynet-web-third-pay/api/getMachineInventoryInfo.do";
		//将密钥和请求参数进行MD5加密
		String Sign = MethodUtil.md5Password("18D96C060DB54773BC4928154D302104"
				+ timestamp + "machineCode="+vendCode+"");
		//发送到服务器的请求参数
		String params = "key=20&sign=" + Sign + "&timestamp=" +timestamp
		+" &machineCode="+vendCode+"";
		//POST方式发送请求
		String resultCheck = MethodUtil.sendPost(url, params);
		JSONObject jsonObject = JSON.parseObject(resultCheck);
		String code = jsonObject.getString("code");
		String jsonStr = jsonObject.getString("data");
		if((null !=jsonStr) && (jsonStr.length()>2) && ("1".equals(code))){
			JSONArray myJsonArray = new JSONArray(JSON.parseArray(jsonStr));
			for(int i=0;i<myJsonArray.size();i++){
				JSONObject jsObject = myJsonArray.getJSONObject(i);
				String goodsCode = jsObject.getString("goodsCode");
				if(null != goodsCode && !"".equals(goodsCode)){
					if(map.containsKey(goodsCode)){
						map.put(goodsCode, map.get(goodsCode) + 1);
					}else{
						map.put(goodsCode, 1);
					}
				}
			}
		}
	}
	

	/**
	 * 计算建议采购量和报警阈值
	 * 1.报警阈值设置百分比 = (采购量 + 采购在订量  - 已销售量)/机器数*货道数
	 * 2.建议采购量 = 机器数*货道数 - 库存数 - 采购在定量 + 报警阈值
	 * @param machineNum
	 * @param ds
	 * @return
	 */
	private int calculateValue(int machineNum, DealerStockDetailVo ds,String flag,Map<String, Integer> map) {
		int resultValue = 0;
		int procurementAmount = ds.getProcurementAmount() != null ? ds.getProcurementAmount() : 0;//采购在订量
		int procuAmount = ds.getProcuAmount() != null ? ds.getProcuAmount() : 0;//采购量
		int count = ds.getCount() != null ? ds.getCount() : 0;//库存数
		ds.setCount(count);
		String goodsId = ds.getGoodsId();
		//商品货道数
		int shelfNum = 0;
		if(null != map && map.size() >0){
			if(null != goodsId &&  !"".equals(goodsId)){
				shelfNum = map.get(goodsId)!=null?map.get(goodsId)*10*3:0;
			}
		}
		int saledAmount = ds.getSaledAmount();//已销售量
		if(machineNum != 0){//机器数为不为0才进行计算
			if("threshold".equals(flag)){//报警阈值
				resultValue = (procuAmount + procurementAmount - saledAmount)/shelfNum;//报警阈值
			}
			if("setSugges".equals(flag)){//计算建议采购量
				int threadV = ds.getThresholdValue() != null ? ds.getThresholdValue() : 0;
				resultValue = shelfNum - count - procurementAmount;//建议采购量
			}
		}
		return resultValue;
	}
	
}
