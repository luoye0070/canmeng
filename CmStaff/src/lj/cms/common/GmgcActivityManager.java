package lj.cms.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class GmgcActivityManager {
	/*实例相关*/
	private GmgcActivityManager(){activityList=new ArrayList<Activity>();}
	static GmgcActivityManager am=null;
	public static GmgcActivityManager getInstance()
	{
		if(am==null)
		{
			am=new GmgcActivityManager();
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
	
	/*退出购买过程*/
	public void exitGmgc()
	{
		//关闭活动
		for (int i = 0; i < activityList.size(); i++) {
			activityList.get(i).finish();
		}
	}
}
