package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.AlreadyAccountVo;
import com.huilian.hlej.hcf.vo.CheckUpAccountVo;
import com.huilian.hlej.hcf.vo.DealerFoundFlowVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hcf.vo.OutAccountVo;
import com.huilian.hlej.hcf.vo.WaitingAccountVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface FinancialCenterDao extends CrudDao<DivideAccountVo> {

	/**
	 * 保存代理商模版参数
	 * @param divideAccountVo
	 */
	void saveDivideAccountVo(DivideAccountVo divideAccountVo);
	
	/**
	 * 修改代理商模版参数
	 * @param divideAccountVo
	 */
	void updateDivideAccountVo(DivideAccountVo divideAccountVo);
	
	/**
	 * 查询所有的模版列表
	 * @param divideAccountVo
	 * @return
	 */
	List<DivideAccountVo> getDivideAccountQueryPage(DivideAccountVo divideAccountVo);
	
	/**
	 * 查询单个模版信息
	 * @param id
	 * @return
	 */
	DivideAccountVo getDivideAccountVo(String id);
	
	/**
	 * 查询关联该模版信息
	 * @param id
	 * @return
	 */
	List<DealerTemplateVo> getDealerTemplateVoList(String templateId);
	
	/**
	 * 查询代理商登录与其所有的机器码
	 * @return
	 */
	List<Map<String,Object>> loginNameAndVendsMap();
	//-----分帐--------start--//
	/**
	 * 获取代理商上一个月的销售额
	 * @param vendCodes
	 * @return
	 */
	Integer beforeMonthSaleMoney(Map<String, String> params); 
	
	/**
	 * 获取代理商上一个月的销售额
	 * @param vendCodes
	 * @return
	 */
	Integer currentMonthSaleMoney(Map<String, String> params); 
	
	/**
	 * 根据机器码查询月份绑定的机器数量
	 * @param vendCodes
	 * @return
	 */
	List<DealerFoundFlowVo> monthVendNumList(Map<String, String> params);
	
	/**
	 * 根据代理商登录名查询最新模版的应用时间
	 * @param loginName
	 * @return
	 */
    Map<String, Object> getUseTimeByLoginName(String loginName);
	
  //---售货机销售分成-------第一区间-----------------start-------//
    
    /**
     * 判断销售额是否处于销售区间第一个区间
     * @param parms
     * @return
     */
    Integer isSaleOne(Map<String, Object> parms);
    
    /**
     * 判断是否属于销售分成的第一月份区间
     * @param parms
     * @return 返回1属于 返回0不属于
     */
    Integer isFristMonthSale(Map<String, Object> parms);
    
    /**
     *  查询属第一个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getFristMonthSaleValue_1(String templateId);
    /**
     * 判断是否属于销售分成的第二月份区间
     * @param parms
     * @return 返回1属于 返回0不属于
     */
    Integer isSecondMonthSale(Map<String, Object> parms);
    
    /**
     *  查询属第二个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getSecondMonthSaleValue_1(String templateId);
    /**
     * 判断是否属于销售分成的第三月份区间
     * @param parms
     * @return 返回1属于 返回0不属于
     */
    Integer isThreeMonthSale(Map<String, Object> parms);
    
    /**
     *  查询属第三个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getThreeMonthSaleValue_1(String templateId);
    /**
     * 判断是否属于销售分成的第三月份区间
     * @param parms
     * @return 返回1属于 返回0不属于
     */
    Integer isFourthMonthSale(Map<String, Object> parms);
    
    /**
     *  查询属第三个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getFourthMonthSaleValue_1(String templateId);
    
  //---售货机销售分成-------第一区间-----------------end-------//
    
  //---售货机销售分成-------第二区间-----------------start-------//  
    /**
     * 判断销售额是否处于销售区间第二个区间
     * @param parms
     * @return
     */
    Integer isSaleTwo(Map<String, Object> parms); 
    
    /**
     *  查询第一个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getFristMonthSaleValue_2(String templateId);
    
    /**
     *  查询第二个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getSecondMonthSaleValue_2(String templateId);
    
    /**
     *  查询第三个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getThreeMonthSaleValue_2(String templateId);
    
    /**
     *  查询第四个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getFourthMonthSaleValue_2(String templateId);
    
    
  //---售货机销售分成-------第二区间-----------------end-------//  
    
    //---售货机销售分成-------第 三区间-----------------start-------//  
    /**
     * 判断销售额是否处于销售区间第三个区间
     * @param parms
     * @return
     */
    Integer isSaleThree(Map<String, Object> parms); 
    
    /**
     *  查询第一个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getFristMonthSaleValue_3(String templateId);
    
    /**
     *  查询第二个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getSecondMonthSaleValue_3(String templateId);
    
    /**
     *  查询第三个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getThreeMonthSaleValue_3(String templateId);
    
    /**
     *  查询第四个月份区间的参数值  销售分成
     * @param templateId
     * @return
     */
    Integer getFourthMonthSaleValue_3(String templateId);
    
    
    //---售货机销售分成-------第 三区间-----------------end-------//  
    
    //---机器补贴---------start------------------//
    /**
     * 判断机器补贴是否属于第一个月区间
     * @param parms
     * @return
     */
    Integer isFristMonth_vend(Map<String, Object> parms);
    
    /**
     * 判断机器补贴是否属于第二个月区间
     * @param parms
     * @return
     */
    Integer isSecondMonth_vend(Map<String, Object> parms);
    
    /**
     * 判断机器补贴是否属于第三个月区间
     * @param parms
     * @return
     */
    Integer isThreeMonth_vend(Map<String, Object> parms);
    
    /**
     * 判断机器补贴是否属于第四个月区间
     * @param parms
     * @return
     */
    Integer isFourthMonth_vend(Map<String, Object> parms);
    
    /**
     * 查询机器补贴第一个月区间的值
     * @param templateId
     * @return
     */
    Integer getFristMonthVendValue(String templateId);
    /**
     * 查询机器补贴第二个月区间的值
     * @param templateId
     * @return
     */
    Integer getSecondMonthVendValue(String templateId);
    /**
     * 查询机器补贴第三个月区间的值
     * @param templateId
     * @return
     */
    Integer getThreeMonthVendValue(String templateId);
    /**
     * 查询机器补贴第四个月区间的值
     * @param templateId
     * @return
     */
    Integer getFourthMonthVendValue(String templateId);
    
    //---机器补贴---------end------------------//
    //---广告分成---------start----------//
    /**
     * 查询模版的广告费
     * @param templateId
     * @return
     */
    Integer getAdMoney(String templateId);
    
    /**
     * 查询所有机器上个月的广告记录
     * @param vendCodes
     * @return
     */
    List<Map<String, Object>> beforeAdListMap(Map<String, String> parms);
    /**
     * 查询所有机器当前月的广告记录
     * @param vendCodes
     * @return
     */
    List<Map<String, Object>> currentAdListMap(Map<String, String> parms);
    //---广告分成---------end----------//
    
    /**
     * 保存代理商的补贴
     * @param dealerFoundFlowVo
     */
    void saveDealerFoundFlowVo(DealerFoundFlowVo dealerFoundFlowVo);
    
    /**
     * 更新代理商的补贴
     * @param dealerFoundFlowVo
     */
    void updateDealerFoundFlowVo(DealerFoundFlowVo dealerFoundFlowVo);
    
    /**
     * 查询该代理商的该类型的补贴记录是否已存在
     * @param parms
     * @return
     */
    Integer isExistFoundByLoginName(Map<String, Object> parms);
    
    /**
     * 更新补贴状态
     * @param parms
     */
    void updateAmountStatus(Map<String, Object> parms);
	//-----分帐--------end--//
    
    /**
     * 查询对帐单列表
     * @param checkUpAccountVo
     * @return
     */
    List<CheckUpAccountVo> getCheckUpAccountList(CheckUpAccountVo checkUpAccountVo);
    
    /**
     * 查询结算的时间段
     * @param params
     * @return
     */
    String queryTimeDuan(Map<String, Object> params);
    
    /**
     * 查询收入
     * @param params
     * @return
     */
    String queryIncomeMoney(Map<String, Object> params);
    
    /**
     * 查询支出
     * @param params
     * @return
     */
    String queryExpenditureMoney(Map<String, Object> params);
    
    /**
     * 查询当天生成的流水号的个数
     * @param serialsNumber
     * @return
     */
    Integer countBySerialsNumber(String serialsNumber);
    
    /**
     * 查询模版的结算方式
     * @param templateId
     * @return
     */
    Integer querySettlementWay(String templateId);
    
    /**
     * 根据登录名查询已结算/待结算列表
     * @param loginName
     * @return
     */
    List<AlreadyAccountVo> amountList(AlreadyAccountVo alreadyAccountVo);

    /**
     * 查询代理商已结算的记录
     * @param maps
     * @return
     */
	Integer queryClosedAccountMoney(Map<String, String> maps);
	
	/**
	 * 根据登录查询其代理商名下的所有机器编码
	 * @param loginName
	 * @return
	 */
	String getAllVendCodeByLoginName(String loginName);

	/**
	 * 根据机器编码和日期查询销售额
	 * @param map
	 * @return
	 */
	double querySaleMoneyByDate(Map<String, Object> map);

	/**
	 * 根据登录名查询模版Id
	 * @param loginName
	 * @return
	 */
	Map<String, Object> queryParmaIdByLoginName(String loginName);

	/**
	 * 查询总的机器补贴
	 * @param parm
	 * @return
	 */
	double queryTotalSubsidy(Map<String, Object> parm);

	/**
	 * 查询机器补贴的期数
	 * @param parm
	 * @return
	 */
	int queryPeriods(Map<String, Object> parm);

	/**
	 * 查询已结算的机器补贴
	 * @param parm
	 * @return
	 */
	double queryAcaMoney(Map<String, Object> parm);

	/**
	 * 查询某个月的广告数
	 * @param parm
	 * @return
	 */
	List<Map<String, Object>> queryAdvertisNum(Map<String, Object> parm);

	/**
	 * 查询已推送的广告机器数
	 * @param vendCodes
	 * @return
	 */
	int aleardyPushNum(Map<String, Object> parm);

	/**
	 * 查询支出列表
	 * @param checkUpAccountVo
	 * @return
	 */
	List<OutAccountVo> outAmountList(OutAccountVo outAccountVo);

	/**
     * 根据登录名查询待结算列表
     * @param loginName
     * @return
     */
	List<WaitingAccountVo> amountListW(WaitingAccountVo waitingAccountVo);

	/**
	 * 查询已结算列表
	 * @param alreadyAccountVo
	 * @return
	 */
	List<AlreadyAccountVo> amountListA(AlreadyAccountVo alreadyAccountVo);
}
