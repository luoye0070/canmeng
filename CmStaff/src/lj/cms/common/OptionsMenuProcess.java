package lj.cms.common;

import lj.cms.constant.AppConstant;
import lj.cms.R;
import android.app.Activity;
import android.content.Intent;

public class OptionsMenuProcess {
	private Activity a=null;

	public OptionsMenuProcess(Activity a) {
		super();
		this.a = a;
	}
	
	public void mainOptionsDosome(int menuId,ActivityCallBackInterface acbi)
	{
		switch(menuId)
    	{
//    	case R.id.menu_login:
//    		Intent loginIntent=new Intent();
//			loginIntent.setClass(a, LoginActivity.class);
//			loginIntent.putExtra(AppConstant.IntentExtraName.IN_LOGIN_FROM_BT, true);
//			a.startActivity(loginIntent);
//    		break;
//    	case R.id.menu_refresh:
//    		acbi.loginSuccessCallBack();
//    		break;
//    	case R.id.menu_exit:
//    		ActivityManager.exitAppDlgShow(a);
//    		break;
    	}
	}
}
