package lj.cms.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class GmgcActivityManager {
	/*ʵ�����*/
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
	
	/*list���*/
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
	
	/*�˳��������*/
	public void exitGmgc()
	{
		//�رջ
		for (int i = 0; i < activityList.size(); i++) {
			activityList.get(i).finish();
		}
	}
}
