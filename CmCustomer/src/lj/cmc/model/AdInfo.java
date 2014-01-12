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
	public int adType=0;//������ͣ�0��Ʒ�б�1���̹�棬2��Ʒ���,3��ҳ
	public String adContent="";//������ݣ����ݲ�ͬ��������в�ͬ��ʽ�����ݣ���Ʒ�б����͹�������ǿ�������ַ�����̺���Ʒ���͹�������Ƕ�ӦID,��ҳ���͹����������ҳURL
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
