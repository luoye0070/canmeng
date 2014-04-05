package lj.cms.model;

import java.io.Serializable;
import java.util.ArrayList;

import lj.cms.constant.AppConstant;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo implements Serializable {
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", restaurantId=" + restaurantId
				+ ", username=" + username + ", password=" + password + "]";
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
	public long userId=0;
	public String restaurantId="";//����ID
	public UserInfo(long userId, String restaurantId, String username,
			String password) {
		super();
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.username = username;
		this.password = password;
	}
	public String username="";//ԭ�����е��û���
	public String password="";//ԭ�����е�����
//	private String appkey="";//ԭ�����е�appkey
//	private String userkey="";//�����½�ɹ��򷵻����ɵ�userkey,�����޴��ֶ�
//	private String usertoken="";//�����½�ɹ��򷵻����ɵ�usertoken,�����޴��ֶ�
	public ArrayList<Integer> positionTypeList=null;
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
	 *���ֶ�д��SharedPreferences 
	 *************************/
	public void writeToSharedPreferences(Context a)
	{
		SharedPreferences preferences = a.getSharedPreferences(AppConstant.SharedDataName.USER_INFO, Context.MODE_PRIVATE);  
		Editor editor=preferences.edit();
		editor.putLong(AppConstant.SharedDataItemName.USER_INFO_USERID, this.userId);
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_RESTAURANTID, this.restaurantId);
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_USERNAME, this.username);
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_PASSWORD, this.password);
		if(this.positionTypeList!=null){
			String positionTypeListStr="";
			int size=this.positionTypeList.size();
			for(int i=0;i<size;i++){
				positionTypeListStr+=positionTypeList.get(i)+",";
			}
			editor.putString(AppConstant.SharedDataItemName.USER_INFO_POSITION_TYPES, positionTypeListStr);
		}
		editor.commit();
	}
	/*******************
	 *��SharedPreferences�ж�ȡ�ֶ�
	 *********************/
	public void readFromSharedPreferences(Context a)
	{
		SharedPreferences preferences = a.getSharedPreferences(AppConstant.SharedDataName.USER_INFO, Context.MODE_PRIVATE);  
		this.userId=preferences.getLong(AppConstant.SharedDataItemName.USER_INFO_USERID, 0);
		this.restaurantId=preferences.getString(AppConstant.SharedDataItemName.USER_INFO_RESTAURANTID, "");
		this.username=preferences.getString(AppConstant.SharedDataItemName.USER_INFO_USERNAME, "");
		this.password=preferences.getString(AppConstant.SharedDataItemName.USER_INFO_PASSWORD, "");
		String positionTypeListStr=preferences.getString(AppConstant.SharedDataItemName.USER_INFO_POSITION_TYPES, "");
		String []strs=positionTypeListStr.split(",");
		if(strs!=null&&strs.length>1){
			this.positionTypeList=new ArrayList<Integer>();
			for(int i=0;i<strs.length-1;i++){
				int p=0;
				try{p=Integer.parseInt(strs[i]);}catch(Exception ex){}
				System.out.print(p+",");
				this.positionTypeList.add(p);
			}
			System.out.println();
		}
		
	}
	/*****************************
	 *����û���Ϣ�Ĺ������� 
	 *******************************/
	public void clearFromSharedPreferences(Context a)
	{
		SharedPreferences preferences = a.getSharedPreferences(AppConstant.SharedDataName.USER_INFO, Context.MODE_PRIVATE);  
		Editor editor=preferences.edit();
		editor.clear();
		editor.commit();
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_RESTAURANTID, this.restaurantId);//�����û���
		editor.putString(AppConstant.SharedDataItemName.USER_INFO_USERNAME, this.username);//�����û���
		editor.commit();
	}
}
