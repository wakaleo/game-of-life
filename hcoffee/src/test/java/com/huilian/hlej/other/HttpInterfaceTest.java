package com.huilian.hlej.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huilian.hlej.hcf.util.MethodUtil;

public class HttpInterfaceTest {

	private static String httpUrl = "http://114.55.54.35:8088/xynet-web-third-pay/api/getMachineInventoryInfo.do";

	public static void main(String[] args) {
		//getMachineStatus();
		
		/*Map<String, Integer> map = getMachineInventoryInfo("123123");
		for(String key : map.keySet()){
			System.out.println("商品ID:" + key + "\t" + "货道道: " + map.get(key));
		}*/
		
		boolean t = false && true;
		if(t){
			System.err.println(3);
		}
		
	}
	
	static void getMachineStatus(){
		//时间戳（一分钟后请求失效）
		Long timestamp = System.currentTimeMillis();
		//接口地址
		String url="http://114.55.54.35:8088/xynet-web-third-pay/api/getMachinesStatus.do";
		//将密钥和请求参数进行MD5加密
		String Sign = MethodUtil.md5Password("18D96C060DB54773BC4928154D302104"
				+ timestamp + "machineCode=1703200160,1704200154");
		//发送到服务器的请求参数
		String params = "key=20&sign=" + Sign + "&timestamp=" +timestamp
		+" &machineCode=1703200160,1704200154";
		//POST方式发送请求
		String resultCheck = MethodUtil.sendPost(url, params);
		
		System.out.println(resultCheck);

	}
	
	/**
	 * 获取机器库存信息接口(货道信息)
	 */
	public static Map<String, Integer> getMachineInventoryInfo(String vendCode){
		Map<String, Integer> map = new HashMap<String, Integer>();
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
		if("1".equals(code)){
			String jsonStr = jsonObject.get("data").toString();
			JSONArray myJsonArray = new JSONArray(JSON.parseArray(jsonStr));
			for(int i=0;i<myJsonArray.size();i++){
				JSONObject jsObject = myJsonArray.getJSONObject(i);
				//System.out.println("商品编码：" + jsObject.getString("goodsCode") + "\t" + "货道：" + jsObject.getString("channelId"));
				System.out.println();
				String goodsCode = jsObject.getString("goodsCode");
				if(map.containsKey(goodsCode)){
					map.put(goodsCode, map.get(goodsCode) + 1);
				}else{
					map.put(goodsCode, 1);
				}
			}
		}
		return map;
	}
	
	static void getMachineInventoryInfos(){
		//时间戳（一分钟后请求失效）
		Long timestamp = System.currentTimeMillis();
		//接口地址
		String url="http://114.55.54.35:8088/xynet-web-third-pay/api/getMachineInventoryInfo.do";
		//将密钥和请求参数进行MD5加密
		String Sign = MethodUtil.md5Password("18D96C060DB54773BC4928154D302104"
				+ timestamp + "machineCode=1706200026");
		//发送到服务器的请求参数
		String params = "key=20&sign=" + Sign + "&timestamp=" +timestamp
				+" &machineCode=1706200026";
//				String bjsShipmentConfirm = "{'key':'20','sign':'"+Sign+"','timestamp':"+timestamp+",'machineCode':'1703200339'}";
		
		//POST方式发送请求
		String resultCheck = MethodUtil.sendPost(url, params);
		JSONObject jsonObject = JSON.parseObject(resultCheck);
		String str = jsonObject.get("data").toString();
		JSONArray myJsonArray = new JSONArray(JSON.parseArray(str));
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(int i=0;i<myJsonArray.size();i++){
			JSONObject jsObject = myJsonArray.getJSONObject(i);
			System.out.println("商品编码：" + jsObject.getString("goodsCode") + "\t" + "货道：" + jsObject.getString("channelId"));
			System.out.println();
			String goodsCode = jsObject.getString("goodsCode");
			if(map.containsKey(goodsCode)){
				map.put(goodsCode, map.get(goodsCode) + 1);
			}else{
				map.put(goodsCode, 1);
			}
		}
		
		for(String key : map.keySet()){
			System.out.println("商品ID: " + key + "\t" + "货道数: " + map.get(key));
		}
		
		
		System.out.println("--------------------------------------------------");
		
		//货道编号、库存容量、货道状态（0-正常 1卡货 2-齿轮盒故障）、商品ID、库存数、商品价格、商品名称、
		System.out.println(str);
	}
	
}
