package lj.cmc.model;

import java.io.Serializable;

public class ApplicationVersionInfo implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appName;
	private String apkName;
	private String apkSize;
	private String verName;
	private String verCode;
	private String apkUrl;
	private String updateInfo;
	public ApplicationVersionInfo() {
		super();
		this.appName = "";
		this.apkName = "";
		this.apkSize = "";
		this.verName = "";
		this.verCode = "";
		this.apkUrl="";
		this.updateInfo="";
	}
	public ApplicationVersionInfo(String appName, String apkName,
			String apkSize, String verName, String verCode, String apkUrl,
			String updateInfo) {
		super();
		this.appName = appName;
		this.apkName = apkName;
		this.apkSize = apkSize;
		this.verName = verName;
		this.verCode = verCode;
		this.apkUrl = apkUrl;
		this.updateInfo = updateInfo;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public String getApkSize() {
		return apkSize;
	}
	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}
	public String getVerCode() {
		return verCode;
	}
	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	public String getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}
	
	@Override
	public String toString() {
		return "ApplicationVersionInfo [appName=" + appName + ", apkName="
				+ apkName + ", apkSize=" + apkSize + ", verName=" + verName
				+ ", verCode=" + verCode + ", apkUrl=" + apkUrl
				+ ", updateInfo=" + updateInfo + "]";
	}
	
	
	
}
