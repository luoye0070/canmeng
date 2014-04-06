package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.broadcast.BroadcastReceiverCustom;
import lj.cms.common.ActivityManager;
import lj.cms.constant.AppConstant;
import lj.cms.model.MessageInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MessageActivity extends Activity {
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	//MainNavbarMenu mnm=null;//导航菜单管理类
	
	TextView msgAy_content=null;//消息内容
	String content=null;
	BroadcastReceiverCustom brc=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*将当前activity添加到activitymanager中*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //设置布局
		setContentView(R.layout.activity_message);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        //mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.OrderListActivity);
		msgAy_content=(TextView) findViewById(R.id.msgAy_content);
		Intent preIntent=getIntent();
		content=preIntent.getStringExtra(AppConstant.IntentExtraName.MESSAGE_CONTENT);
		if(content==null)
			content="内容已丢失";
		msgAy_content.setText(content);
        
        
      //注册广播监听器
		brc=new BroadcastReceiverCustom(this);
		registerReceiver(brc, new IntentFilter(AppConstant.BroadcastActions.UPDATE_MSG_VIEW));
	}
	public void refreshData(String content){
		this.content=content;
		if(this.content==null)
			this.content="内容已丢失";
		msgAy_content.setText(this.content);
	}
	@Override
	protected void onDestroy() {
		//am.removeActivity(this);
		
		unregisterReceiver(brc);
		super.onDestroy();
	}
}
