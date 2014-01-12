package lj.cmc.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lj.cmc.model.UserInfo;

import lj.cmc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ActivityManager {
	/*实例相关*/
	private ActivityManager(){activityList=new ArrayList<Activity>();}
	static ActivityManager am=null;
	public static ActivityManager getInstance()
	{
		if(am==null)
		{
			am=new ActivityManager();
		}
		return am;
	}
	
	/*list相关*/
	List<Activity> activityList=null;
	public void addActivity(Activity a)
	{
		activityList.add(a);
	}
	public void clearActivity()
	{
		activityList=new ArrayList<Activity>();
	}
	public void removeActivity(Activity a)
	{
		activityList.remove(a);
	}
	
	/*退出程序*/
	public void exitApp()
	{
//		//清除数据
//		if(activityList!=null&&activityList.size()>0)
//		{
//			UserInfo ui=new UserInfo();//清除用户信息
//			ui.clearFromSharedPreferences(activityList.get(0));
//			System.out.println("清除用户数据am");
//		}
		
		//关闭活动
		for (int i = 0; i < activityList.size(); i++) {
			activityList.get(i).finish();
		}
		System.exit(0);
	}
	/*************
	 *弹出对话框询问是否退出
	 *****************/
	public static void exitAppDlgShow(Activity a)
	{
		//弹出对话框确认是否退出
		AlertDialog.Builder adb=new AlertDialog.Builder(a);
		adb.setTitle(R.string.DgExitTitleTxt);
		adb.setMessage(R.string.DgExitInfoTxt);
		adb.setPositiveButton(R.string.DgPositiveBtTxt, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				am.exitApp();
			}
		});
		adb.setNegativeButton(R.string.DgNegativeBtTxt, null);
		adb.show();
	}
}
