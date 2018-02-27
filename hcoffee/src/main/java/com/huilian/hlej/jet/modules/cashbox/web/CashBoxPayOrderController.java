package com.huilian.hlej.jet.modules.cashbox.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.utils.StringUtils;
import com.huilian.hlej.jet.common.utils.excel.ExportExcel;
import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.cashbox.entity.CashBoxPayOrderModel;
import com.huilian.hlej.jet.modules.cashbox.service.CashBoxPayOrderService;

@Controller
@RequestMapping(value = "${adminPath}/cashbox/model")
public class CashBoxPayOrderController extends BaseController {

	@Autowired
	private CashBoxPayOrderService cashBoxPayOrderService;

	@ModelAttribute
	public CashBoxPayOrderModel get(@RequestParam(required = false) String id) {
		CashBoxPayOrderModel entity = null;
		if (StringUtils.isNoneEmpty(id)) {
			entity = cashBoxPayOrderService.get(id);
		}
		if (entity == null) {
			entity = new CashBoxPayOrderModel();
		}
		return entity;
	}

	/**
	 * 盒子支付订单列表
	 */
	@RequiresPermissions({ "cashbox:model:list" })
	@RequestMapping(value = { "list", "" })
	public String modelList(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("order") CashBoxPayOrderModel order) {
		Page<CashBoxPayOrderModel> page = cashBoxPayOrderService.getOrderList(new Page<CashBoxPayOrderModel>(request, response), order);
		model.addAttribute("page", page);
		return "modules/cashbox/cashboxList";
	}
	
	/**
     * 盒子支付订单列表
     */
    @RequiresPermissions({ "cashbox:model:list" })
    @RequestMapping(value = { "detail", "" })
    public String modelDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        CashBoxPayOrderModel obj = cashBoxPayOrderService.get(id);
        model.addAttribute("obj", obj);
        return "modules/cashbox/cashboxInfo";
    }

	/**
	 * 导出订单数据
	 *
	 * @param order
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//@RequiresPermissions({"cashbox:model:list"})
	@RequestMapping(value = { "export", "" })
	public String exportFile(@ModelAttribute("order") CashBoxPayOrderModel order, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "盒子支付订单数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<CashBoxPayOrderModel> page = cashBoxPayOrderService.getOrderList(new Page(request, response), order);
			new ExportExcel("盒子支付订单数据", CashBoxPayOrderModel.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/cashbox/model/list?repage";
	}
}
