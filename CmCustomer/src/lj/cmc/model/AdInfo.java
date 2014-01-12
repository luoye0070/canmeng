package lj.cmc.model;

import java.io.Serializable;

public class AdInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "AdInfo [adName=" + adName + ", resUrl=" + resUrl
				+ ", resWidth=" + resWidth + ", resHeight=" + resHeight
				+ ", linkTo=" + linkTo+ ", adType=" + adType + ", adContent=" + adContent+ "]";
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public int getResWidth() {
		return resWidth;
	}
	public void setResWidth(int resWidth) {
		this.resWidth = resWidth;
	}
	public int getResHeight() {
		return resHeight;
	}
	public void setResHeight(int resHeight) {
		this.resHeight = resHeight;
	}
	public String getLinkTo() {
		return linkTo;
	}
	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
	}
        public int getAdType() {
		return adType;
	}
	public void setAdType(int adType) {
		this.adType = adType;
	}
        public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	public String adName="";
	public String resUrl="";
	public int resWidth=0;
	public int resHeight=0;
	public String linkTo="";
	public int adType=0;//广告类型，0商品列表，1店铺广告，2商品广告,3网页
	public String adContent="";//广告内容，根据不同广告类型有不同形式的内容，商品列表类型广告内容是控制器地址，店铺和商品类型广告内容是对应ID,网页类型广告内容是网页URL
	public AdInfo(String adName, String resUrl, int resWidth, int resHeight,
			String linkTo,int adType,String adContent) {
		super();
		this.adName = adName;
		this.resUrl = resUrl;
		this.resWidth = resWidth;
		this.resHeight = resHeight;
		this.linkTo = linkTo;
                this.adType=adType;
                this.adContent=adContent;
	}
	public AdInfo() {
		super();
	}
	
}
