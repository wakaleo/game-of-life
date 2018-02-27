package com.huilian.hlej.jet.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.sys.entity.Province;
import com.huilian.hlej.jet.modules.sys.service.ProvinceService;


/**
 * 
 * 城市,省份操作类
 * @author luowenyan
 * 2016年1月15日下午4:56:26
 * version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/province")
public class ProvinceController extends BaseController {

	@Autowired
	private ProvinceService provinceService;
	
	/**
	 * 通过国家ID获取省份
	 * @param area
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String getCitysByCountryId(Long countryId, Model model,HttpServletRequest request,HttpServletResponse response) {
		List<Province> provinces = provinceService.getProvincesByCountryId(countryId);
		return renderString(response, provinces);
	}
	
	
			
}
