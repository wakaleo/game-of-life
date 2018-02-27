package com.huilian.hlej.hcf.entity;

 
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
 

/**
 * 售货机发送给客户礼品领取实体
 * 
 
 *  update liujian
 *
 */
 
public class VendReward implements Serializable {

	/**
	 * 售货机发送给客户礼品信息实体
	 */
	private static final long serialVersionUID = 1L;

 
	private BigInteger id;

	private  String  schemeNo   ;//活动方案表 编码  
	
	private  String   openId  ;  //微信号
	
	private  String  drawCode   ; //获取礼品的验证码6位随机字母加数字
	
	private int   status  ;  // 活动礼品状态（0，待领取，1，领取成功 2，领取失败 3，失效）
	
	private Date  acquireDate   ;  //领取时间
	
	private Date   createDate   ;//创建时间
	
	private  Date  invalidDate  ;  //失效时间
	
	private  int  rewardFlage   ; //注册送 1/看广告送 2
	
	private  String  phoneNo  ; //用户手机号码
	
	private  String  userId  ;  //用户表ID
	
	private String   goodsId    ; //领取商品id
	
	private String   goodsName  ;   // 领取商品名称
	
    private  BigDecimal   price  ;   //领取商品价格
    
    private  String  remark  ;   //备注
    
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getDrawCode() {
		return drawCode;
	}

	public void setDrawCode(String drawCode) {
		this.drawCode = drawCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAcquireDate() {
		return acquireDate;
	}

	public void setAcquireDate(Date acquireDate) {
		this.acquireDate = acquireDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public int getRewardFlage() {
		return rewardFlage;
	}

	public void setRewardFlage(int rewardFlage) {
		this.rewardFlage = rewardFlage;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
