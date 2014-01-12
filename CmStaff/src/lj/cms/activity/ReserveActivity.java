package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.common.ActivityManager;
import lj.cms.constant.AppConstant;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.internet.ParamCollect;
import lj.cms.model.RestaurantInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfShopsAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
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

public class ReserveActivity extends Activity {
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	//MainNavbarMenu mnm=null;//导航菜单管理类
	
	TextView rAy_noNotifyTv=null;//无记录提示
	ListView rAy_shopListLv=null;//订单列表
	
	ArrayList<HashMap<String,SpannableString>> list=null;
	SimpleAdapter iatclAdapter=null;
	
	ArrayList<RestaurantInfo> dataList=null;
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
		setContentView(R.layout.activity_reserve);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        //mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.ReserveActivity);
        initInstance();//初始化控件
        registerEvent();//注册事件
        loadData();//加载数据
	}
	void initInstance(){
		rAy_noNotifyTv=(TextView) findViewById(R.id.rAy_noNotifyTv);//无记录提示
		rAy_shopListLv=(ListView) findViewById(R.id.rAy_shopListLv);//订单列表
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		rAy_shopListLv.addFooterView(loadmoreView);//设置列表底部视图
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	void registerEvent(){
		rAy_shopListLv.setOnItemClickListener(new LvOnItemClickListener());
		rAy_shopListLv.setOnScrollListener(new LvOnScrollListener());
	}
	/**************子项点击事件监听器***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//进入预定界面
			long shopId=dataList.get(arg2).id;
			Intent intent=new Intent();
			intent.setClass(ReserveActivity.this, ReserveTablesActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.RESTAURANT_ID, shopId);
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
		loadingMoreTv.setText("没有更多饭店哦");
		progressBar1.setVisibility(View.GONE);
	}
	//设置ListView FooterView显示正在加载数据
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *加载饭店列表数据
	 ****************************************/
	void loadData(){
		String url_s=AppConstant.UrlStrs.URL_SHOP_LIST;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfShopsAnalyzeHelper analyzeHelper=new DataOfShopsAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("正在加载饭店，请稍后...", true);	
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
				if(ReserveActivity.this.list==null)
				{
					rAy_noNotifyTv.setVisibility(View.VISIBLE);
					rAy_shopListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfShopsAnalyzeHelper analyzeHelper=(DataOfShopsAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//成功,加载数据				
				ArrayList<RestaurantInfo> dataListTemp=analyzeHelper.shopList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(ReserveActivity.this.list==null)
					{
						rAy_noNotifyTv.setVisibility(View.VISIBLE);
						rAy_shopListLv.setVisibility(View.GONE);
					}
					return;
				}
				if(dataList==null){
					dataList=new ArrayList<RestaurantInfo>();
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
					RestaurantInfo shopInfo=dataListTemp.get(i);
					HashMap<String,SpannableString> hashTemp=new HashMap<String, SpannableString>();
					hashTemp.put("lisli_nameTv", new SpannableString(""+shopInfo.name+""));
					hashTemp.put("lisli_addressTv", new SpannableString("饭店地址:"+shopInfo.province+" "+shopInfo.city+" "+shopInfo.area+" "+shopInfo.street));
					hashTemp.put("lisli_phoneTv", new SpannableString("联系电话:"+shopInfo.phone+""));
					hashTemp.put("lisli_shopHoursTv", new SpannableString("营业时间:"+shopInfo.shopHoursBeginTime+"-"+shopInfo.shopHoursEndTime));
					list.add(hashTemp);
				}		
				//更新适配器
				if(null==iatclAdapter){
					iatclAdapter=new SimpleAdapter(ReserveActivity.this, list,R.layout.lvitem_shop_list_item, 
							new String[]{"lisli_nameTv","lisli_addressTv","lisli_phoneTv","lisli_shopHoursTv"},
							new int[]{R.id.lisli_nameTv,R.id.lisli_addressTv,R.id.lisli_phoneTv,R.id.lisli_shopHoursTv});
					ReserveActivity.this.rAy_shopListLv.setAdapter(iatclAdapter);
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
			else{//其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(ReserveActivity.this.list==null)
				{
					rAy_noNotifyTv.setVisibility(View.VISIBLE);
					rAy_shopListLv.setVisibility(View.GONE);
				}
				return;
			}
		}
		
	}

}
