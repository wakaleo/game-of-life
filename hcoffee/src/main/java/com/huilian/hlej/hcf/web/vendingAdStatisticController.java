package com.huilian.hlej.hcf.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.VendingAdConterService;
import com.huilian.hlej.hcf.service.VendingAdStatisticService;
import com.huilian.hlej.hcf.vo.StatistTotelVo;
import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingAdStatisticVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 广告统计
 * 
 * @author yangbo
 * @date 2017年8月4日 上午10:11:14
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingAdStatistic")
public class vendingAdStatisticController extends BaseController {
	
	@Autowired
	private VendingAdStatisticService vendingAdStatistic;
	
	@Autowired
	private VendingAdConterService vendingAdConterService;
	

	@Autowired
	FileHandler fileHandler;

	@RequestMapping(value = { "list" })
	public String list(VendingConterAdVo vendingConterAdVo, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
	
		Page<VendingConterAdVo> page = vendingAdStatistic.getVendingAdStatisticPage(new Page<VendingConterAdVo>(request, response),
				vendingConterAdVo);
		
	//   List<VendingConterAdVo> vendingConterAd=vendingAdStatistic.getVendingConterAd();
	  //获取最后一次更新时间
	  

		List<VendingConterAdVo> list = page.getList();
		
		for(VendingConterAdVo sb : list){
			 int i=1;
			 int sumPlayTimes=0;
			  int sumPlayLongs=0;
			//List<VendingAdStatisticVo> vending=	vendingAdStatistic.getVendByImgName(sb.getImgName());
			List<StatistTotelVo> vending=	vendingAdStatistic.getVendByAdId(sb.getAdId());
			sb.setVendTotel(vending.size());
			 List<StatistTotelVo> channel=vendingAdStatistic.getChanelByimgName(sb.getAdId());
			 sb.setChannelTotel(channel.size());
			
			 if(vending!=null){
				 for(StatistTotelVo sbb : vending){
					 
					 if(sbb.getPlaymins()!=null && sbb.getPlayLong()!=null){
						 sumPlayTimes+=Integer.parseInt(sbb.getPlaymins()); 
						 sumPlayLongs+= Integer.parseInt(sbb.getPlayLong());
					 }
				     sb.setTime(sbb.getUpdateTime());
				    
				    VendingStartVo vendingStartVo  = vendingAdStatistic.getVendmotorByVendCode(sbb.getVendCode());
				     if(vendingStartVo!=null){
			    		   if("0".equals(vendingStartVo.getStatus())){
				    		   sb.setVendingTotel(i);
				    		   i++;
				    	   }
				    }
		         }
				 
			 }
			
			 sb.setSumPlayTimes(String.valueOf(sumPlayTimes)); 
			 sb.setSumPlayLongs(String.valueOf(sumPlayLongs));
	 }
		StatistTotelVo updateTime = vendingAdStatistic.getUpdateTime();
		Date addTime = null ;
		if(updateTime!=null){
			addTime = updateTime.getAddTime();
		}
		
		model.addAttribute("updateTime", addTime);
		model.addAttribute("page", page);
		model.addAttribute("vendingConterAdVo", vendingConterAdVo);
	   return "/hcoffee/vending/vendingAdStatistic";
	   
	   
	}
	
	

	 @RequestMapping(value = "rewardList")
	  public  String rewardRecordList(StatistTotelVo statistTotelVo,HttpServletRequest request, HttpServletResponse response, Model model ) throws UnsupportedEncodingException {
		 Page<StatistTotelVo> page = vendingAdStatistic.getRewardRecordListPage(new Page<StatistTotelVo>(request, response), statistTotelVo);
		 
		 String imgName = null ;
		 String vendTotel = statistTotelVo.getVendTotel();
		 String vendingTotel = statistTotelVo.getVendingTotel();
		 String adId = statistTotelVo.getAdId();
		 VendingConterAdVo vendAdByADId = vendingAdConterService.getVendAdByADId(adId);
		 
		 if(vendAdByADId!=null){
			 imgName = vendAdByADId.getImgName();
			 
		 }
		 
		  List<StatistTotelVo> list = page.getList();
		  for(StatistTotelVo sd : list){
			  VendingStartVo vendingStartVo  = vendingAdStatistic.getVendmotorByVendCode(sd.getVendCode());	  
			  if(vendingStartVo!=null){
				  if("1".equals(vendingStartVo.getStatus())){
					  sd.setCommunityName(null);
				  } 
			  }
		  }
		  
			StatistTotelVo updateTime = vendingAdStatistic.getUpdateTime();
			Date addTime = null ;
			if(updateTime!=null){
				addTime = updateTime.getAddTime();
			}
			
		  model.addAttribute("updateTime", addTime);
		  model.addAttribute("page", page);
		  model.addAttribute("imgName", imgName);
		  model.addAttribute("vendTotel", vendTotel);
		  model.addAttribute("vendingTotel", vendingTotel);
		  model.addAttribute("adId", adId);
		  model.addAttribute("StatistTotelVo", statistTotelVo);
		  
		  return "/hcoffee/vending/statistRecordList";
	  }
	 
	 
	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 public String exportFile(StatistTotelVo statistVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "广告统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		  Page<StatistTotelVo> page = vendingAdStatistic.getRewardRecordListPage(new Page<StatistTotelVo>(request, response), statistVo);
		  
		  List<StatistTotelVo> list = page.getList();
		  for(StatistTotelVo sd : list){
			  VendingConterAdVo vendAdByADId = vendingAdConterService.getVendAdByADId(statistVo.getAdId());
			  sd.setVendTotel(statistVo.getVendTotel());
			  sd.setVendingTotel(statistVo.getVendingTotel());
			  sd.setImgName(vendAdByADId.getImgName());
			  sd.setUpdateTime(statistVo.getUpdateTime());
			  VendingStartVo vendingStartVo  = vendingAdStatistic.getVendmotorByVendCode(sd.getVendCode());	  
			  if(vendingStartVo!=null){
				  if("1".equals(vendingStartVo.getStatus())){
					  sd.setCommunityName(null);
				  } 
			  }
		  }
		  
		  
		  List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400,0,0,0,0 );
	     new HCFExportExcel("广告统计", StatistTotelVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/vendingAdStatistic/rewardList?repage";
	 }
	 
  
	 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	 public String exportQbFile(StatistTotelVo statistVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "广告统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<StatistTotelVo> page = vendingAdStatistic.getRewardRecordListPage(new Page<StatistTotelVo>(request, response, "str"), statistVo);
			
			  List<StatistTotelVo> list = page.getList();
			  for(StatistTotelVo sd : list){
				  VendingConterAdVo vendAdByADId = vendingAdConterService.getVendAdByADId(statistVo.getAdId());
				  sd.setVendTotel(statistVo.getVendTotel());
				  sd.setVendingTotel(statistVo.getVendingTotel());
				  sd.setImgName(vendAdByADId.getImgName());
				  sd.setUpdateTime(statistVo.getUpdateTime());
				  VendingStartVo vendingStartVo  = vendingAdStatistic.getVendmotorByVendCode(sd.getVendCode());	  
				  if(vendingStartVo!=null){
					  if("1".equals(vendingStartVo.getStatus())){
						  sd.setCommunityName(null);
					  } 
				  }
			  }
			 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400,0,0,0,0);
			 new HCFExportExcel("广告统计", StatistTotelVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/vendingAdStatistic/rewardList?repage";
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "exports", method = RequestMethod.POST)
	 public String exportFiles(VendingConterAdVo vendingConterAdVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "广告统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<VendingConterAdVo> page = vendingAdStatistic.getVendingAdStatisticPage(new Page<VendingConterAdVo>(request, response), vendingConterAdVo);

				List<VendingConterAdVo> list = page.getList();
				
				for(VendingConterAdVo sb : list){
					 int i=1;
					 int sumPlayTimes=0;
					  int sumPlayLongs=0;
					//List<VendingAdStatisticVo> vending=	vendingAdStatistic.getVendByImgName(sb.getImgName());
					List<StatistTotelVo> vending=	vendingAdStatistic.getVendByAdId(sb.getAdId());
					sb.setVendTotel(vending.size());
					
					
					 for(StatistTotelVo sbb : vending){
						 
						 if(sbb.getPlaymins()!=null && sbb.getPlayLong()!=null){
							 sumPlayTimes+=Integer.parseInt(sbb.getPlaymins()); 
							 sumPlayLongs+= Integer.parseInt(sbb.getPlayLong());
						 }
					     sb.setTime(sbb.getUpdateTime());
					    
					    VendingStartVo vendingStartVo  = vendingAdStatistic.getVendmotorByVendCode(sbb.getVendCode());
					     if(vendingStartVo!=null){
					    		   if("0".equals(vendingStartVo.getStatus())){
						    		   sb.setVendingTotel(i);
						    		   i++;
						    	   }
					    	   }
			         }
					 
					 
					 
			         List<StatistTotelVo> channel=vendingAdStatistic.getChanelByimgName(sb.getAdId());
					 sb.setChannelTotel(channel.size());
					 
					sb.setSumPlayTimes(String.valueOf(sumPlayTimes)); 
				    sb.setSumPlayLongs(String.valueOf(sumPlayLongs));
					 
			 }
				
			 
			 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400 );
			 new HCFExportExcel("广告统计", VendingConterAdVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/vendingAdStatistic/list?repage";
	 }
	 
	 
	 @RequestMapping(value = "qbexports", method = RequestMethod.POST)
	 public String exportQbFiles(VendingConterAdVo vendingConterAdVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "广告统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<VendingConterAdVo> page = vendingAdStatistic.getVendingAdStatisticPage(new Page<VendingConterAdVo>(request, response, "str"), vendingConterAdVo);

				List<VendingConterAdVo> list = page.getList();
				
				for(VendingConterAdVo sb : list){
					 int i=1;
					 int sumPlayTimes=0;
					  int sumPlayLongs=0;
					//List<VendingAdStatisticVo> vending=	vendingAdStatistic.getVendByImgName(sb.getImgName());
					List<StatistTotelVo> vending=	vendingAdStatistic.getVendByAdId(sb.getAdId());
					sb.setVendTotel(vending.size());
					
					
					 for(StatistTotelVo sbb : vending){
						 
						 if(sbb.getPlaymins()!=null && sbb.getPlayLong()!=null){
							 sumPlayTimes+=Integer.parseInt(sbb.getPlaymins()); 
							 sumPlayLongs+= Integer.parseInt(sbb.getPlayLong());
						 }
					     sb.setTime(sbb.getUpdateTime());
					    
					    VendingStartVo vendingStartVo  = vendingAdStatistic.getVendmotorByVendCode(sbb.getVendCode());
					     if(vendingStartVo!=null){
					    		   if("0".equals(vendingStartVo.getStatus())){
						    		   sb.setVendingTotel(i);
						    		   i++;
						    	   }
					    	   }
			         }
					 
					 
					 
			         List<StatistTotelVo> channel=vendingAdStatistic.getChanelByimgName(sb.getAdId());
					 sb.setChannelTotel(channel.size());
					 
					sb.setSumPlayTimes(String.valueOf(sumPlayTimes)); 
				    sb.setSumPlayLongs(String.valueOf(sumPlayLongs));
					 
			 }
				
			 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400 );
			 new HCFExportExcel("广告统计", VendingConterAdVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/vendingAdStatistic/list?repage";
	 }
  

}
