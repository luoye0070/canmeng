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
	 *���캯��
	 *@param a,ע��㲥��Activity 
	 **********************/
	public BroadcastReceiverCustom(Activity a) {
		super();
		this.a = a;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		if(intent.getAction().equals(AppConstant.BroadcastActions.GOODS_CLASS_SELECTED))
//		{//��Ʒ��Ŀѡ��
//			if(this.a!=null)
//			{
//				
//			}
//			else
//			{//���activity�Ѿ����٣������ݴ浽SharedPreferences
//				
//			}
//		}
//		else if(intent.getAction().equals(AppConstant.BroadcastActions.GOODS_AREA_SELECTED))
//		{//��Ʒ����ѡ��
//			if(this.a!=null)
//			{
//				
//			}
//			else
//			{//���activity�Ѿ����٣������ݴ浽SharedPreferences
//				System.out.println("���activity�Ѿ����٣������ݴ浽SharedPreferences");
//			}
//		}
	}
}
