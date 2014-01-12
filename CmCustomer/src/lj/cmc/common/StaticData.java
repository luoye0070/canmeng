package lj.cmc.common;

import java.util.ArrayList;

import lj.cmc.model.AdInfo;
import lj.cmc.model.UserInfo;


/************静态数据*************/
public class StaticData {
	
	private static StaticData sd=null;
	public static StaticData getInstance()
	{
		if(sd==null)
		{
			sd=new StaticData();
		}
		return sd;
	}
	/***************************用户数据***********************************/
	/*****************
	 * 获取用户信息 
	 **********************/
	public UserInfo getUi() {
		return ui;
	}
	/********************
	 * 设置用户信息
	 * ***********************/
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	private UserInfo ui=null; 	

	/*****************************首页2数据暂存********************************/
	public boolean index2HaveSd=false;
	public ArrayList<AdInfo> aiList1=null;
	public ArrayList<AdInfo> aiList2=null;
	
}
