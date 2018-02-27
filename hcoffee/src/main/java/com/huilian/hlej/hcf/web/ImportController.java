package com.huilian.hlej.hcf.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huilian.hlej.hcf.service.ImportService;
import com.huilian.hlej.hcf.util.ExcelReader;
import com.huilian.hlej.hcf.vo.OrderImportVo;
import com.huilian.hlej.jet.common.utils.StringUtils;

@Controller
@RequestMapping(value = "/user")
public class ImportController {

	private static final String importPage = "/hcoffee/vending/excel/importPage";
	
	@Autowired
	private ImportService importService;
	
	@RequestMapping("/jumpPage")
	public String jumpPage(){
		return importPage;
	}
	
	@RequestMapping(value="/upload",method = RequestMethod.POST) 
    @ResponseBody  
    public String upload(@RequestParam(value="file",required = false)MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception{  
		
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem diskFileItem = (DiskFileItem) cf.getFileItem();
		File file2 = diskFileItem.getStoreLocation();
		
		List<OrderImportVo> list = new ArrayList<OrderImportVo>();
		
		ExcelReader reader = new ExcelReader() {
			
			@Override
			public void getRows(int sheetIndex, int curRow, List<String> rowList) {
				System.out.println("Sheet:" + sheetIndex + ", Row:" + curRow + ", Data:" + rowList + "列数:" + rowList.size());
				//[日期, 支付方式, 社区名称, 商品名称, 利润, 类目, 渠道, 楼盘, 订单来源, 流水号, 商品别名, 商品代码, 商品编码, 发生时间, 对方账号, 点位编号, 机器编号, 线路, 支付渠道, 价格]
				if(curRow > 0){
					OrderImportVo ivo = new OrderImportVo();
					for(int i=0;i<rowList.size();i++){
						String value = rowList.get(i);
						/*if(i == 0){
							//日期
							Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							value = dateFormat.format(date);
							ivo.setOrderCreateTime(value);
						}*/
						if(i == 1){
							//支付方式,1-微信支付 2-支付宝支付 3-现金 4-雅支付 5-取货码
							if("微信支付".equals(value)){
								ivo.setPayType(1);
							}
							if("支付宝支付".equals(value)){
								ivo.setPayType(2);
							}
							if("现金支付".equals(value)){
								ivo.setPayType(3);
							}
							if("雅支付".equals(value)){
								ivo.setPayType(4);
							}
							if("取货码支付".equals(value)){
								ivo.setPayType(5);
							}
						}
						if(i == 3){
							//商品名称
							ivo.setGoodsName(value);
						}
						if(i == 5){
							ivo.setType(value);
						}
						if(i == 8){
							//订单来源  1-便捷神 2-凯欣达 3-乐科 4-零壹比特
							if("便捷神".equals(value)){
								ivo.setSource(1);
							}
							if("凯欣达 ".equals(value)){
								ivo.setSource(2);
							}
							if("乐科".equals(value)){
								ivo.setSource(3);
							}
							if("零壹比特".equals(value)){
								ivo.setSource(4);
							}
						}
						if(i == 9){
							//流水号
							ivo.setRunningAccount(value);
							ivo.setOrderNo(value);
						}
						if(i == 12){
							//商品编码
							ivo.setGoodsId(value);
						}
						if(i == 13){
							//日期
							if(value.length() == value.getBytes().length){//判是否有汉字
								if(isTimeLegal(value)){
									ivo.setOrderCreateTime(value);
									ivo.setShipTime(value);
									ivo.setUpdateTime(value);
									ivo.setPayTime(value);
								}else{
									Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
									value = dateFormat.format(date);
									ivo.setOrderCreateTime(value);
									ivo.setShipTime(value);
									ivo.setUpdateTime(value);
									ivo.setPayTime(value);
								}
							}
						}
						if(i == 15){
							//机器编码
							if(value.length() == 5){
								ivo.setVendCode("000"+value);
							}else if(value.length()>5){
								ivo.setVendCode(value);
							}
							
						}
						if(i == 18){
							int price = (int) (Float.parseFloat(value)*100);
							ivo.setAmount(price);
						}
						ivo.setShipStatus(1);
						ivo.setPayStatus(2);
						ivo.setOrderStatus(1);
						//System.out.print(value + "\t");
					}
					list.add(ivo);
					//System.out.println();
					
				}
				
			}
			
		};
		reader.process(file2,1);
		for(OrderImportVo vo : list){
			importService.saveVo(vo);
		}
		/*if(list.size()>0){
			importService.save(list);
		}*/
        return null;  
        
    }  
	
	@RequestMapping(value="/upload1")
	public void up(){
		System.out.println("-----------");
	}
	
	 public static boolean isTimeLegal(String patternString) {
         
         Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$"); 
         Matcher b=a.matcher(patternString); 
         if(b.matches()) {
               return true;
         } else {
               return false;
         }
    }
	
}
