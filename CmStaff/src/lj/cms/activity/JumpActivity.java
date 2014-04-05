package lj.cms.activity;

import lj.cms.constant.AppConstant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class JumpActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent preIntent=getIntent();
		long orderId=preIntent.getLongExtra(AppConstant.IntentExtraName.ORDER_ID, 0);
		Intent intent=new Intent();
		intent.setClass(getApplicationContext(), OrderDetailActivity.class);
		intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
		startActivity(intent);
		System.out.println("i am jump");
		//finish();
	}

}
