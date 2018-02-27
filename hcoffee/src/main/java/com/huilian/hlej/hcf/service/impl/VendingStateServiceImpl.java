package com.huilian.hlej.hcf.service.impl;

 
 
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huilian.hlej.hcf.dao.VendingQrder;
import com.huilian.hlej.hcf.dao.VendingStateDao;
import com.huilian.hlej.hcf.service.VendingStateService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.TranQueryVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author liujian
 *
 */
@Service
@Transactional
public class VendingStateServiceImpl implements VendingStateService{
	
	@Autowired
	private VendingStateDao vendingStateDao;
	
	@Autowired
	private VendingQrder vransactionQuery;
	

	@Override
	public Page<VendingStartVo> getVendingStatisticsPage(Page<VendingStartVo> page,
			VendingStartVo vendingStartVo) {
		
		vendingStartVo.setPage(page);
		page.setList(vendingStateDao.getVendingStatisticsPage(vendingStartVo));
		return page;
	}


	@Override
	public Page<TranQueryVo> getTransactionQueryPage(Page<TranQueryVo> page, TranQueryVo tranQueryVo) {
		tranQueryVo.setPage(page);
		
		page.setList(vransactionQuery.getTransactionQueryPage(tranQueryVo));
		return page;
	}


	@Override
	public Map<String, String> updateOrderStauts(TranQueryVo tranQueryVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String orderNo = tranQueryVo.getOrderNo();
			String price = String.valueOf(tranQueryVo.getAmount().intValue());
//			//时间戳（一分钟后请求失效）
//			Long timestamp = System.currentTimeMillis();
//			//接口地址
//			String url="http://www.xyvend.cn/api/deliveryRecordQuery.do";
//			//将密钥和请求参数进行MD5加密
//			String Sign = MethodUtil.md5Password("18D96C060DB54773BC4928154D302104"
//					+ "jyid="+orderNo);
//			//发送到服务器的请求参数
//			String params = "key=20&sign=" + Sign + "&timestamp=" +timestamp
//			+"&jyid="+orderNo;
//			//调用凯欣达订单信息查询接口查询订单最新状态
//			System.out.println("手动查询订单状态请求参数："+params);
//			String refundResult = MethodUtil.sendPost(url, params);
//			System.out.println("手动查询订单状态返回结果："+refundResult);
//		    JSONObject obj = JSONObject.parseObject(refundResult);
//	        if("1".equals(obj.getString("code"))){
//	        	//如果查询成功
//		        if (obj.containsKey("data")) {
//		        	System.out.println(obj.getString("data"));
//		        	JSONArray arrayList = new JSONArray();
//					arrayList = JSONObject.parseArray(obj.getString("data"));
//					for (int i = 0; i < arrayList.size(); i++) {
//						String data = arrayList.getString(i);
//						JSONObject dataJson = JSONObject.parseObject(data);
//						String jyid = dataJson.getString("jyid");
//						String status = dataJson.getString("status");
//						String meg = "凯欣达未回调,后台手动更新";
//			            System.out.println(dataJson.getString("jyid"));
//			            System.out.println(dataJson.getString("status"));
//			            if("2".equals(status) || "3".equals(status)){
//			            	//如果出货成功或者出货失败，调用出货结果上报接口
			    			String bjsPaymentConfirm = "{'hsh_txn_id':'"+orderNo+"','status':'"+3+"','price':'"+price+"','msg':'出货失败手动申请退款'}";
			    			System.out.println("手动调用出货结果接口,请求参数："+bjsPaymentConfirm);
			    			String payUrl = "https://www.huicoffee.com.cn/hkf/vendinginterface/bjsPaymentConfirm.action";
			            	String PaymentConfirm = MethodUtil.sendHttp(payUrl, bjsPaymentConfirm);
			            	System.out.println("手动调用出货结果接口,返回结果："+PaymentConfirm);
//			            }
//			         }
//		        }
//	        }
			result.put("code", "0");
			result.put("msg", "操作成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}
}



