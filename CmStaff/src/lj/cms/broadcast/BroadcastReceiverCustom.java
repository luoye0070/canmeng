package lj.cms.broadcast;

import java.lang.ref.Reference;

import lj.cms.activity.MessageActivity;
import lj.cms.activity.MessageListActivity;
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
		if(intent.getAction().equals(AppConstant.BroadcastActions.UPDATE_MSG_VIEW))
		{//������ҳ��ͼ
			if(this.a!=null&&this.a instanceof MessageListActivity){
				MessageListActivity ma=(MessageListActivity)this.a;
				System.out.println("���յ��㲥��ˢ�½�������");
				ma.refreshData();
			}else if(this.a!=null&&this.a instanceof MessageActivity){
				MessageActivity ma=(MessageActivity)this.a;
				System.out.println("���յ��㲥��ˢ�½�������");
				ma.refreshData(intent.getStringExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT));
			}
		}
	}
}
