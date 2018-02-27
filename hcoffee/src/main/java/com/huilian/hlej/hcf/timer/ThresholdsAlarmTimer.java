package com.huilian.hlej.hcf.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huilian.hlej.hcf.dao.DealerStockManagementDao;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerStockVo;

/**
 * 代理商库存阀值报警定时器
 * 
 * @author LongZhangWei
 * @date 2017年9月18日 下午6:07:11
 */
public class ThresholdsAlarmTimer {

	@Autowired
	DealerStockManagementDao dealerStockManagementDao;

	//存放代理商登录名及其机器数和其名下的所有机器编码
	private List<DealerStockVo> list = new ArrayList<DealerStockVo>();
	
	//存放代理商登录名及其库存数
	private List<DealerStockVo> list_1 = new ArrayList<DealerStockVo>();
	private Map<String, Object> map_1 = new HashMap<String,Object>();
	
	{
		
		list = dealerStockManagementDao.loginNameMachineNumVendCodsList();
		
		list_1 = dealerStockManagementDao.loginNameStockCountList();
		if(null != list_1 && list_1.size() >0){
			for(DealerStockVo dStockVo : list_1){
				map_1.put(dStockVo.getLoginName(), dStockVo.getSaleCount());
			}
		}
	}

	/*
	 * 备货报警规则：当 剩余总库存<总机器数*每个机器填满一次商品容量 时给代理商发提醒补货的短信。
	 * 剩余库存 = 总的库存量  - 总的销售量	
	 */

	@Scheduled(cron = "0 0 14 * * ?") // 每日下午2点
	//@Scheduled(cron="0/5 * * * * ? ")//隔5秒
	public void alarm() {
		if (null != list && list.size() > 0) {
			for (DealerStockVo dealerStockVo : list) {
				// 代理商登录名
				String loginName = dealerStockVo.getLoginName();
				// 代理商机器数
				int machineNum = dealerStockVo.getMachineNum();
				// 代理商所有的机器编码
				String vendCodes = dealerStockVo.getVendCodes(); 
				// 代理商姓名
				String dealerName = dealerStockVo.getDealerName();
				// 代理商手机号
				String phone = dealerStockVo.getCellPhone();
				
				//总库存量
				int stockAmount_1 = map_1 != null ? (int)map_1.get(loginName) : 0;
				
				//总销售量
				int saleCount = 0;
				if(vendCodes != null){
					Integer result = dealerStockManagementDao.getSaleCountByVendCodes(MethodUtil.getFormatStr(vendCodes));
					saleCount = result != null?result:0;
				}
				
				//剩余库存量
				int surplusStockAmount = 0;
				if(stockAmount_1 != 0)
					surplusStockAmount = stockAmount_1 - saleCount;
				
				//代理商所有机器一次填满商品的内容(库存量)
				int stockAmount_2 = getSumStockCount(vendCodes);
				
				if(surplusStockAmount < machineNum * stockAmount_2){
					String message = "【泛贩】尊敬的"+dealerName+"，您的库存商品已经不足，可能造成机器缺货，请尽快登录微信公众号：泛贩叮当mall——订货中心补货哦~";
					MethodUtil.hyfSendMsm(phone, message);
				}
			}
		}
	}
	
	/**
	 * 代理商所有机器填满一次商品容量
	 * @param vendCodes
	 * @return
	 */
	private int getSumStockCount(String vendCodes){
		int SumStockCount = 0;
		if(vendCodes.indexOf(",") != -1){//多台机器
			String[] vendCodeArr = vendCodes.split(",");
			if(null != vendCodeArr && vendCodeArr.length >0){
				for(String vendCode : vendCodeArr){
					SumStockCount += getStockCountByVendCOde(vendCode);
				}
			}
			
		}else{//一台机器
			SumStockCount += getStockCountByVendCOde(vendCodes);
		}
		return SumStockCount;
	}
	
	
	/**
	 * 调接口获取每一台机器的总库量
	 * @param vendCode
	 * @return
	 */
	private int getStockCountByVendCOde(String vendCode) {
		// 每一台机器的总库存量
		int sumStock = 0;
		// 时间戳（一分钟后请求失效）
		Long timestamp = System.currentTimeMillis();
		// 接口地址
		String url = "http://114.55.54.35:8088/xynet-web-third-pay/api/getMachineInventoryInfo.do";
		// 将密钥和请求参数进行MD5加密
		String Sign = MethodUtil
				.md5Password("18D96C060DB54773BC4928154D302104" + timestamp + "machineCode=" + vendCode + "");
		// 发送到服务器的请求参数
		String params = "key=20&sign=" + Sign + "&timestamp=" + timestamp + " &machineCode=" + vendCode + "";
		// POST方式发送请求
		String resultCheck = MethodUtil.sendPost(url, params);
		JSONObject jsonObject = JSON.parseObject(resultCheck);
		String code = jsonObject.getString("code");
		String jsonStr = jsonObject.getString("data");
		if ((null != jsonStr) && (jsonStr.length() > 2) && ("1".equals(code))) {
			JSONArray myJsonArray = new JSONArray(JSON.parseArray(jsonStr));
			for (int i = 0; i < myJsonArray.size(); i++) {
				JSONObject jsObject = myJsonArray.getJSONObject(i);
				String stockCount = jsObject.getString(" stockCount");
				if (null != stockCount && !"".equals(stockCount)) {
					sumStock += Integer.parseInt(stockCount);
				}
			}
		}
		return sumStock;
	}
}
