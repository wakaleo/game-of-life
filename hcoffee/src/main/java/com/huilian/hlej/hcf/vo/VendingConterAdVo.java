package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 售货机广告实体
 * 
 * @author xiekangjian
 * @date 2017年1月24日 下午4:01:42
 *
 */
public class VendingConterAdVo extends BaseDataEntity<VendingConterAdVo> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;// 售货机编码
	private String vendCode;// 售货机编码
	private BigDecimal channel;// 渠道
	private Date createTime;
	private Date updateTime;
	private Date time;
	private String imgName;//
	private String linkUrl;
	private String startTimes;
	private String endTimes;
	private String playTime;
	private String num;// 编码顺序
	private String imgPath;
	private String vedioPath;
	private String adType; // 广告类型：1、图片，2、视频
	private String adStatus; // 广告状态：1、进行中，2、已结束
	private String typeName;
	private String statusName;
	private String adId;
	private String aDList;
	private String sumPlayTimes;
	private String sumPlayLongs;
	private String groundType;
	private Date startTime;//
	private Date endTime;//
	private int channelTotel;
	private int vendTotel;
	private int vendingTotel; // 机器总数
	private int palyTotel;
	private int playmins; // 总播放次数
	private int playLong; // 总播放时长

	private Integer coordinateX;// x坐标
	private Integer coordinateY;// y坐标
	private Integer qrCodeW;// 宽度
	private Integer qrCodeH;// 高度
	private Integer putType;// 摆放方式
	private String qrCodeValue;// 二维码的值

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	public BigDecimal getChannel() {
		return channel;
	}

	public void setChannel(BigDecimal channel) {
		this.channel = channel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@ExcelField(title = "广告名称", align = 2, sort = 30)
	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getVedioPath() {
		return vedioPath;
	}

	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getaDList() {
		return aDList;
	}

	public void setaDList(String aDList) {
		this.aDList = aDList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getGroundType() {
		return groundType;
	}

	public void setGroundType(String groundType) {
		this.groundType = groundType;
	}

	public String getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(String startTimes) {
		this.startTimes = startTimes;
	}

	public String getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}

	@ExcelField(title = "下发渠道总数", align = 2, sort = 30)
	public int getChannelTotel() {
		return channelTotel;
	}

	public void setChannelTotel(int channelTotel) {
		this.channelTotel = channelTotel;
	}

	@ExcelField(title = "下发终端总数", align = 2, sort = 30)
	public int getVendTotel() {
		return vendTotel;
	}

	public void setVendTotel(int vendTotel) {
		this.vendTotel = vendTotel;
	}

	@ExcelField(title = "下发终端在线总数", align = 2, sort = 30)
	public int getVendingTotel() {
		return vendingTotel;
	}

	public void setVendingTotel(int vendingTotel) {
		this.vendingTotel = vendingTotel;
	}

	public int getPalyTotel() {
		return palyTotel;
	}

	public void setPalyTotel(int palyTotel) {
		this.palyTotel = palyTotel;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getPlaymins() {
		return playmins;
	}

	public void setPlaymins(int playmins) {
		this.playmins = playmins;
	}

	public int getPlayLong() {
		return playLong;
	}

	public void setPlayLong(int playLong) {
		this.playLong = playLong;
	}

	@ExcelField(title = "总播放次数", align = 2, sort = 30)
	public String getSumPlayTimes() {
		return sumPlayTimes;
	}

	public void setSumPlayTimes(String sumPlayTimes) {
		this.sumPlayTimes = sumPlayTimes;
	}

	@ExcelField(title = "总播放时长", align = 2, sort = 30)
	public String getSumPlayLongs() {
		return sumPlayLongs;
	}

	public void setSumPlayLongs(String sumPlayLongs) {
		this.sumPlayLongs = sumPlayLongs;
	}

	public Integer getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}

	public Integer getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}

	public Integer getQrCodeW() {
		return qrCodeW;
	}

	public void setQrCodeW(Integer qrCodeW) {
		this.qrCodeW = qrCodeW;
	}

	public Integer getQrCodeH() {
		return qrCodeH;
	}

	public void setQrCodeH(Integer qrCodeH) {
		this.qrCodeH = qrCodeH;
	}

	public Integer getPutType() {
		return putType;
	}

	public void setPutType(Integer putType) {
		this.putType = putType;
	}

	public String getQrCodeValue() {
		return qrCodeValue;
	}

	public void setQrCodeValue(String qrCodeValue) {
		this.qrCodeValue = qrCodeValue;
	}

}
