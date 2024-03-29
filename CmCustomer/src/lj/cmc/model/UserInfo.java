package lj.cmc.model;

import java.io.Serializable;

import lj.cmc.constant.AppConstant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", username=" + username
				+ ", password=" + password + "]";
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private long userId=0;
	private String username="";//原参数中的用户名
	private String password="";//原参数中的密码
//	private String appkey="";//原参数中的appkey
//	private String userkey="";//如果登陆成功则返回生成的userkey,否则无此字段
//	private String usertoken="";//如果登陆成功则返回生成的usertoken,否则无此字段
	public UserInfo(long userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	public UserInfo() {
		super();
	}
	/***********************
	 *将字段写入SharedPreferences 
	 *************************/
	public void writeToSharedPreferences(Activity a)
	{
		SharedPreferences preferences = a.getSharedPreferences(AppConstant.SharedDataName.USER_INFO, Activity.MODE_PRIVATE);  
		Editor editor=preferences.edit();
		editor.putLong(AppConstant.SharedDataItemName.USER_INFO_USERID, this.userId);
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_USERNAME, this.username);
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_PASSWORD, this.password);
		editor.commit();
	}
	/*******************
	 *从SharedPreferences中读取字段
	 *********************/
	public void readFromSharedPreferences(Activity a)
	{
		SharedPreferences preferences = a.getSharedPreferences(AppConstant.SharedDataName.USER_INFO, Activity.MODE_PRIVATE);  
		this.userId=preferences.getLong(AppConstant.SharedDataItemName.USER_INFO_USERID, 0);
		this.username=preferences.getString(AppConstant.SharedDataItemName.USER_INFO_USERNAME, "");
		this.password=preferences.getString(AppConstant.SharedDataItemName.USER_INFO_PASSWORD, "");
	}
	/*****************************
	 *清除用户信息的共享数据 
	 *******************************/
	public void clearFromSharedPreferences(Activity a)
	{
		SharedPreferences preferences = a.getSharedPreferences(AppConstant.SharedDataName.USER_INFO, Activity.MODE_PRIVATE);  
		Editor editor=preferences.edit();
		editor.clear();
		editor.commit();
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_USERNAME, this.username);//保留用户名
		editor.commit();
	}
}
