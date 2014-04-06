package lj.cms.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.OrderListActivity.LoadDataAfterAction;
import lj.cms.activity.OrderListActivity.LvOnItemClickListener;
import lj.cms.activity.OrderListActivity.LvOnScrollListener;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.broadcast.BroadcastReceiverCustom;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.OptionsMenuProcess;
import lj.cms.constant.AppConstant;
import lj.cms.constant.OrderStatus;
import lj.cms.constant.OrderValid;
import lj.cms.database.DatabaseHelper;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.MessageInfo;
import lj.cms.model.OrderInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfOrdersAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MessageListActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	MainNavbarMenu mnm=null;//导航菜单管理类
	
	TextView mlAy_noNotifyTv=null;//无记录提示
	ListView mlAy_msgListLv=null;//订单列表
	
	ArrayList<HashMap<String, Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<MessageInfo> dataList=null;
	int visibleLastIndex=0;
	
	int pageNum;//页号
	final static int PAGE_MAX=10;//每页记录数
	int totalCount;//总数量
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	
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
		setContentView(R.layout.activity_message_list);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        //mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.OrderListActivity);
        initInstance();//初始化控件
        registerEvent();//注册事件
        loadData();//加载数据
        
        
      //注册广播监听器
		brc=new BroadcastReceiverCustom(this);
		registerReceiver(brc, new IntentFilter(AppConstant.BroadcastActions.UPDATE_MSG_VIEW));
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	int menuId=item.getItemId();
    	OptionsMenuProcess omp=new OptionsMenuProcess(this);
    	omp.mainOptionsDosome(menuId, this);
		return super.onOptionsItemSelected(item);
	}
	void initInstance(){
		mlAy_noNotifyTv=(TextView) findViewById(R.id.mlAy_noNotifyTv);//无记录提示
		mlAy_msgListLv=(ListView) findViewById(R.id.mlAy_msgListLv);//消息列表
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		//mlAy_msgListLv.addFooterView(loadmoreView);//设置列表底部视图
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	void registerEvent(){
		mlAy_msgListLv.setOnItemClickListener(new LvOnItemClickListener());
		mlAy_msgListLv.setOnScrollListener(new LvOnScrollListener());
	}
	/**************子项点击事件监听器***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub						
			//进入订单详细信息界面
			long orderId=dataList.get(arg2).orderId;
			Intent intent=new Intent();
			intent.setClass(MessageListActivity.this, OrderDetailActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
			startActivity(intent);
			//更新数据库中消息状态
			long id=dataList.get(arg2).id;
			DatabaseHelper dh=new DatabaseHelper(getApplicationContext());
			dh.updateStatus(id);
			//发出广播通知界面更新
			Intent intent1=new Intent(AppConstant.BroadcastActions.UPDATE_MSG_VIEW);
			sendBroadcast(intent1);
		}
		
	}
	/***********************订单列表滚动事件监听器**********************/
	class LvOnScrollListener implements OnScrollListener{
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			visibleLastIndex=firstVisibleItem+visibleItemCount-1;
		}
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if(dataList==null)
			{
				return;
			}
			int itemsLastIndex = dataList.size()-1;  //数据集最后一项的索引    
			int lastIndex = itemsLastIndex + 1;  
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  
					&& visibleLastIndex >= lastIndex) {  
				// 如果是自动加载,可以在这里放置异步加载数据的代码  
				if(dataList.size()<totalCount)
					loadData();
			}  

		}
		
	}	
	//设置ListView FooterView显示没了
	void setNoMore()
	{
		loadingMoreTv.setText("没有更多消息了哦");
		progressBar1.setVisibility(View.GONE);
	}
	//设置ListView FooterView显示正在加载数据
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *加载消息列表数据，从数据库加载
	 ****************************************/
	void loadData(){
		System.out.println("加载数据");
		UserInfo ui=new UserInfo();
		ui.readFromSharedPreferences(getApplicationContext());
		if(ui.getUserId()==0)
		{//没有登录，转到登录界面
			Intent intent = new Intent(MessageListActivity.this,LoginActivity.class);
			startActivity(intent);
			return;
		}
		//从数据库中读取未读消息 
		DatabaseHelper dh=new DatabaseHelper(getApplicationContext());
		dataList=dh.getMsgNoReadList(ui.getUserId());
		if(dataList==null||dataList.isEmpty()){//没有未读消息
			mlAy_noNotifyTv.setVisibility(View.VISIBLE);//无记录提示
			mlAy_msgListLv.setVisibility(View.GONE);//消息列表
		}
		else{
			mlAy_noNotifyTv.setVisibility(View.GONE);//无记录提示
			mlAy_msgListLv.setVisibility(View.VISIBLE);//消息列表
			int size=dataList.size();
			/***************显示数据***********************/
			if(list==null){
				list=new ArrayList<HashMap<String,Object>>();
			}
			//添加数据
			//int idxOffset=list.size();
			for(int i=0;i<size;i++){
				SimpleDateFormat sdfDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				MessageInfo msgInfo=dataList.get(i);
				HashMap<String, Object> hashTemp=new HashMap<String, Object>();
				hashTemp.put("recTime", new SpannableString(sdfDateTime.format(msgInfo.recTime)));
				hashTemp.put("content", new SpannableString(Html.fromHtml(msgInfo.content)));
				list.add(hashTemp);
			}		
			//更新适配器
			if(null==iatclAdapter){
				iatclAdapter=new ITBCListAdapter(MessageListActivity.this,MessageListActivity.this.mlAy_msgListLv, list,R.layout.lvitem_msg_list_item, 
						new String[]{"recTime","content"},
						new int[]{R.id.lmli_rtimeTv,R.id.lmli_contentTv});
				MessageListActivity.this.mlAy_msgListLv.setAdapter(iatclAdapter);
			}
			iatclAdapter.notifyDataSetChanged();			
		}
	}
	
	public void refreshData(){
		list=null;
		iatclAdapter=null;		
		dataList=null;
		visibleLastIndex=0;		
		pageNum=0;//页号
		loadData();
	}
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		refreshData();
	}
	@Override
	protected void onDestroy() {
		//am.removeActivity(this);
		
		unregisterReceiver(brc);
		super.onDestroy();
	}
}
