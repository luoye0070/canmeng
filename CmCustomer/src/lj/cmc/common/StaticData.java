package lj.cmc.common;

import java.util.ArrayList;

import lj.cmc.model.AdInfo;
import lj.cmc.model.UserInfo;


/************��̬����*************/
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
	/***************************�û�����***********************************/
	/*****************
	 * ��ȡ�û���Ϣ 
	 **********************/
	public UserInfo getUi() {
		return ui;
	}
	/********************
	 * �����û���Ϣ
	 * ***********************/
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	private UserInfo ui=null; 	

	/*****************************��ҳ2�����ݴ�********************************/
	public boolean index2HaveSd=false;
	public ArrayList<AdInfo> aiList1=null;
	public ArrayList<AdInfo> aiList2=null;
	
}
