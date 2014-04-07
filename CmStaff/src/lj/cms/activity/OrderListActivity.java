package lj.cms.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.DishActivity.BTOnClickListener;
import lj.cms.activity.DishActivity.LoadDataAfterAction;
import lj.cms.activity.DishActivity.LvItemBtOnClickListener;
import lj.cms.activity.DishActivity.LvItemCbOnCheckedChangeListener;
import lj.cms.activity.DishActivity.LvOnScrollListener;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.OptionsMenuProcess;
import lj.cms.constant.AppConstant;
import lj.cms.constant.OrderStatus;
import lj.cms.constant.OrderValid;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.FoodInfo;
import lj.cms.model.OrderInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfDishAnalyzeHelper;
import lj.cms.string_analysis.DataOfOrdersAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class OrderListActivity extends Activity implements ActivityCallBackInterface {
	
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	MainNavbarMenu mnm=null;//导航菜单管理类
	
	TextView moAy_noNotifyTv=null;//无记录提示
	ListView moAy_orderListLv=null;//订单列表
	
	ArrayList<HashMap<String,SpannableString>> list=null;
	SimpleAdapter iatclAdapter=null;
	
	ArrayList<OrderInfo> dataList=null;
	int visibleLastIndex=0;
	
	int pageNum;//页号
	final static int PAGE_MAX=10;//每页记录数
	int totalCount;//总数量
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	
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
		setContentView(R.layout.activity_order_list);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.OrderListActivity);
        initInstance();//初始化控件
        registerEvent();//注册事件
        loadData();//加载数据
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
		moAy_noNotifyTv=(TextView) findViewById(R.id.moAy_noNotifyTv);//无记录提示
		moAy_orderListLv=(ListView) findViewById(R.id.moAy_orderListLv);//订单列表
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		moAy_orderListLv.addFooterView(loadmoreView);//设置列表底部视图
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	void registerEvent(){
		moAy_orderListLv.setOnItemClickListener(new LvOnItemClickListener());
		moAy_orderListLv.setOnScrollListener(new LvOnScrollListener());
	}
	/**************子项点击事件监听器***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//进入订单详细信息界面
			long orderId=dataList.get(arg2).id;
			Intent intent=new Intent();
			intent.setClass(OrderListActivity.this, OrderDetailActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
			startActivity(intent);
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
		loadingMoreTv.setText("没有更多订单哦");
		progressBar1.setVisibility(View.GONE);
	}
	//设置ListView FooterView显示正在加载数据
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *加载订单和菜品列表数据
	 ****************************************/
	void loadData(){
		String url_s=AppConstant.UrlStrs.URL_ORDER_LIST;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfOrdersAnalyzeHelper analyzeHelper=new DataOfOrdersAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("正在加载订单，请稍后...", true,true);	
		}
		else
		{
			dataLoader.load();
		}
	}
	class LoadDataAfterAction extends AfterAction{

		public LoadDataAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if(!this.isServerReturnOK())
			{
				if(OrderListActivity.this.list==null)
				{
					moAy_noNotifyTv.setVisibility(View.VISIBLE);
					moAy_orderListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfOrdersAnalyzeHelper analyzeHelper=(DataOfOrdersAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//成功,加载数据				
				ArrayList<OrderInfo> dataListTemp=analyzeHelper.orderList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(OrderListActivity.this.list==null)
					{
						moAy_noNotifyTv.setVisibility(View.VISIBLE);
						moAy_orderListLv.setVisibility(View.GONE);
					}
					return;
				}
				if(dataList==null){
					dataList=new ArrayList<OrderInfo>();
					totalCount=analyzeHelper.totalCount;
					System.out.println("totalCount-->"+totalCount);
				}
				dataList.addAll(dataListTemp);
				
				
				/***************显示数据***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,SpannableString>>();
				}
				//添加数据
				int size=dataListTemp.size();
				int idxOffset=list.size();
				for(int i=0;i<size;i++){
					SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
					OrderInfo orderInfo=dataListTemp.get(i);
					HashMap<String,SpannableString> hashTemp=new HashMap<String, SpannableString>();
					hashTemp.put("orderId", new SpannableString("订单ID:"+orderInfo.id+""));
					hashTemp.put("restaurantId", new SpannableString("饭店ID:"+orderInfo.restaurantId+""));
					hashTemp.put("tableId", new SpannableString("桌位ID:"+orderInfo.tableId+""));
					hashTemp.put("status", new SpannableString("状态:"+OrderStatus.getLable(orderInfo.status)+""));
					hashTemp.put("valid", new SpannableString("有效性:"+OrderValid.getLable(orderInfo.valid)+""));//
					hashTemp.put("date", new SpannableString("用餐日期:"+sdfDate.format(orderInfo.date)+""));//
					hashTemp.put("time", new SpannableString("用餐时间:"+sdfTime.format(orderInfo.time)+""));//
					list.add(hashTemp);
				}		
				//更新适配器
				if(null==iatclAdapter){
					iatclAdapter=new SimpleAdapter(OrderListActivity.this, list,R.layout.lvitem_order_list_item, 
							new String[]{"orderId","restaurantId","tableId","status","valid","date","time"},
							new int[]{R.id.lioli_orderIdTv,R.id.lioli_restaurantIdTv,R.id.lioli_tableIdTv,R.id.lioli_statusTv,R.id.lioli_validTv,
							R.id.lioli_dateTv,R.id.lioli_timeTv});
					OrderListActivity.this.moAy_orderListLv.setAdapter(iatclAdapter);
				}
				iatclAdapter.notifyDataSetChanged();
				
				//更新底部和页号
				if(dataList.size()>=totalCount)
				{
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;				
			}
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){//没有登录,跳转到登录界面
				AcvivityLoginGoto.setAcbi(OrderListActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(OrderListActivity.this, LoginActivity.class);
				OrderListActivity.this.startActivity(loginIntent);
				return;
			}
			else{//其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(OrderListActivity.this.list==null)
				{
					moAy_noNotifyTv.setVisibility(View.VISIBLE);
					moAy_orderListLv.setVisibility(View.GONE);
				}
				return;
			}
		}
		
	}
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		list=null;
		iatclAdapter=null;		
		dataList=null;
		visibleLastIndex=0;		
		pageNum=0;//页号
		loadData();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(AcvivityLoginGoto.getAcbi()==this){
			AcvivityLoginGoto.setAcbi(null);
		}
		super.onDestroy();
	}
}
