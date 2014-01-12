package lj.cmc.activity;

import lj.cmc.R;
import lj.cmc.activity.include.MainNavbarMenu;
import lj.cmc.common.ActivityManager;
import lj.cmc.constant.AppConstant;
import lj.cmc.common.ActivityCallBackInterface;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyCollectActivity extends FragmentActivity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity������
	MainNavbarMenu mnm=null;//�����˵�������
	
	boolean gobackAy=false;
	boolean gobackFromLoginAy=false;
	
	Button mcAy_shopCBt=null;
	Button mcAy_foodCBt=null;
	MyCollectShopFragment mcsf=null;
	MyCollectFoodFragment mcff=null;
	int fragmentIdx=0;
	
	boolean isActive=true;
	//Fragment sellerCAy_fragmentContainer=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//ȥ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*����ǰactivity��ӵ�activitymanager��*/
        am=ActivityManager.getInstance();
        am.addActivity(this);
		//���ý��沼���ļ�
        setContentView(R.layout.activity_mycollect);
		
		//��ʼ��һ�������˵�
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.MyCollectActivity);   
		
        initInstance();
        registerEvent();
        
        mcsf=null;
    	mcff=null;    	
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		if(mcsf==null)
			mcsf=new MyCollectShopFragment();
		ft.add(R.id.mcAy_fragmentContainerLl, mcsf);
		ft.commit();
		fragmentIdx=0;
		isActive=true;
	}

	/*************************************
	 *��ʼ���ؼ����� 
	 *********************************/
	private void initInstance(){
		mcAy_shopCBt=(Button) findViewById(R.id.mcAy_shopCBt);
		mcAy_foodCBt=(Button) findViewById(R.id.mcAy_foodCBt);
		
		//��ȡǰһ���������������
		//Intent preIntent=getIntent();
		//gobackAy=preIntent.getBooleanExtra(AppConstant.IntentExtraName.MYXJMEI_GOBACK_AY, false);
	}
	/*************************
	 *ע���¼�������
	 **************************/
	private void registerEvent(){
		mcAy_shopCBt.setOnClickListener(new BtnOnClickListener());
		mcAy_foodCBt.setOnClickListener(new BtnOnClickListener());
	}
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FragmentManager fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			
			switch(v.getId()){
			case R.id.mcAy_shopCBt:
				if(mcsf==null)
					mcsf=new MyCollectShopFragment();
				ft.replace(R.id.mcAy_fragmentContainerLl, mcsf);
				//mcAy_shopCBt.setBackgroundResource(R.drawable.myxjmei_tab_bt_selected_bk_bmp);
				//mcAy_foodCBt.setBackgroundResource(R.drawable.myxjmei_tab_bt_unselected_bk_bmp);
				fragmentIdx=0;
				break;
			case R.id.mcAy_foodCBt:
				if(mcff==null)
					mcff=new MyCollectFoodFragment();
				ft.replace(R.id.mcAy_fragmentContainerLl, mcff);
				//mcAy_shopCBt.setBackgroundResource(R.drawable.myxjmei_tab_bt_selected_bk_bmp);
				//mcAy_foodCBt.setBackgroundResource(R.drawable.myxjmei_tab_bt_unselected_bk_bmp);
				fragmentIdx=1;
				break;
			}
			ft.commit();
		}
		
	}
	
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		if(!isActive)
			return;
		switch(fragmentIdx){
		case 0:
			if(mcsf!=null)
				mcsf.refreshData();
			break;
		case 1:
			if(mcff!=null)
				//mcff.refreshData();
			break;
		}
		
		if(mnm!=null)
			mnm.refreshView();
	}
}
