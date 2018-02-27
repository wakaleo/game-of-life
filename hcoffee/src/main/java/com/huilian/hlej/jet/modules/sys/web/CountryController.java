package com.huilian.hlej.jet.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.sys.entity.Country;
import com.huilian.hlej.jet.modules.sys.service.CountryService;

/**
 * 
 * 国家操作类
 * @author luowenyan
 * 2016年1月15日下午4:56:26
 * version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/country")
public class CountryController extends BaseController{

	@Autowired
	private CountryService countryService;
	
	/**
	 * 获取国家列表
	 * @param area
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String getCountryList(Model model,HttpServletRequest request,HttpServletResponse response) {
		List<Country> countrys = countryService.getCountryList();
		return renderString(response, countrys);
	}
}

