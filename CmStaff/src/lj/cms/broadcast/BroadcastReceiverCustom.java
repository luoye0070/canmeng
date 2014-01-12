package lj.cms.broadcast;

import java.lang.ref.Reference;

import lj.cms.constant.AppConstant;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiverCustom extends BroadcastReceiver {

	Activity a=null;
	/******************
	 *构造函数
	 *@param a,注册广播的Activity 
	 **********************/
	public BroadcastReceiverCustom(Activity a) {
		super();
		this.a = a;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		if(intent.getAction().equals(AppConstant.BroadcastActions.GOODS_CLASS_SELECTED))
//		{//商品类目选中
//			if(this.a!=null)
//			{
//				
//			}
//			else
//			{//如果activity已经销毁，则将数据存到SharedPreferences
//				
//			}
//		}
//		else if(intent.getAction().equals(AppConstant.BroadcastActions.GOODS_AREA_SELECTED))
//		{//商品地区选中
//			if(this.a!=null)
//			{
//				
//			}
//			else
//			{//如果activity已经销毁，则将数据存到SharedPreferences
//				System.out.println("如果activity已经销毁，则将数据存到SharedPreferences");
//			}
//		}
	}
}
