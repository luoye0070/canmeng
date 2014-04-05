package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lj.cms.R;
import lj.cms.activity.MainActivity.BTOnClickListener;
import lj.cms.activity.MainActivity.CreateOrderAfterAction;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.adapter.LvItemBtAttribute;
import lj.cms.adapter.LvItemCbAttribute;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.OptionsMenuProcess;
import lj.cms.constant.AppConstant;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.dataload.SubmitDataAfterAction;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.FailedDish;
import lj.cms.model.FoodInfo;
import lj.cms.model.OrderInfo;
import lj.cms.string_analysis.AddDishesReDataAnalyzeHelper;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.CreateOrderResponseDataAnalyzeHelper;
import lj.cms.string_analysis.DataOfDishAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AbsListView.OnScrollListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//点菜的活动
public class DishActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity管理类
	
	TextView dishAy_orderIdTv=null;//订单号
	TextView dishAy_partakeCodeTv=null;//点菜参与码
	Button dishAy_orderDetailBt=null;//显示订单详情按钮
	TextView dishAy_noNotifyTv=null;//无记录提示
	ListView dishAy_foodListLv=null;//菜品列表
	Button dishAy_submitBt=null;//提交按钮
	Button dishAy_cancelBt=null;//取消按钮
	Button dishAy_completeDishBt=null;//完成点菜按钮
	
	long orderId=0;
	//String partakeCode=null;
	boolean isOwner=false;
	
	ArrayList<HashMap<String,Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<FoodInfo> dataList=null;
	OrderInfo orderInfo=null;
	int visibleLastIndex=0;
	
	int pageNum;//页号
	final static int PAGE_MAX=10;//每页记录数
	int totalCount;//总数量
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	
	ArrayList<HashMap<String,Long>> dishList=null;
	
	Button dishAy_goback = null;// 返回按钮
	
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
		setContentView(R.layout.activity_dish);
		
		//从上一个activity获取数据
		Intent preIntent=getIntent();
		orderId=preIntent.getLongExtra(AppConstant.IntentExtraName.ORDER_ID, 0);
		//partakeCode=preIntent.getStringExtra(AppConstant.IntentExtraName.PARTAKE_CODE);
		isOwner=preIntent.getBooleanExtra(AppConstant.IntentExtraName.IS_OWNER, false);
		
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
	/********************
	 *初始化控件 
	 *********************/
	private void initInstance(){
		dishAy_orderIdTv=(TextView) findViewById(R.id.dishAy_orderIdTv);//订单号
		dishAy_partakeCodeTv=(TextView) findViewById(R.id.dishAy_partakeCodeTv);//点菜参与码
		dishAy_orderDetailBt=(Button) findViewById(R.id.dishAy_orderDetailBt);//显示订单详情按钮
		dishAy_noNotifyTv=(TextView) findViewById(R.id.dishAy_noNotifyTv);//无记录提示
		dishAy_foodListLv=(ListView) findViewById(R.id.dishAy_foodListLv);//菜品列表
		dishAy_submitBt=(Button) findViewById(R.id.dishAy_submitBt);//提交按钮
		dishAy_cancelBt=(Button) findViewById(R.id.dishAy_cancelBt);//取消按钮
		dishAy_completeDishBt=(Button) findViewById(R.id.dishAy_completeDishBt);//完成点菜按钮
		if(!isOwner){
			dishAy_completeDishBt.setVisibility(View.GONE);
		}
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		dishAy_foodListLv.addFooterView(loadmoreView);//设置列表底部视图
		dishAy_goback = (Button) findViewById(R.id.dishAy_goback);
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	private void registerEvent(){
		dishAy_orderDetailBt.setOnClickListener(new BTOnClickListener());
		dishAy_submitBt.setOnClickListener(new BTOnClickListener());
		dishAy_cancelBt.setOnClickListener(new BTOnClickListener());
		dishAy_completeDishBt.setOnClickListener(new BTOnClickListener());
		dishAy_foodListLv.setOnScrollListener(new LvOnScrollListener());
		dishAy_goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DishActivity.this.finish();
			}
		});
	}
	/********
	 * 获取点菜列表中的点菜map
	 * *********************/
	HashMap<String, Long> getDish(long idx){
		HashMap<String, Long> hm=null;
		if(dishList!=null){
			int size=dishList.size();
			for(int i=0;i<size;i++){
				HashMap<String, Long> hmt=dishList.get(i);
				if(hmt.get("idx")==idx){
					hm=hmt;
					break;
				}
			}
		}
		return hm;
	}
	class BTOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.dishAy_orderDetailBt:
				
				break;
			case R.id.dishAy_submitBt:
				submitDish();
				break;
			case R.id.dishAy_cancelBt:
				cancelAll();
				break;
			case R.id.dishAy_completeDishBt:
				submitCompleteDish();
				break;
			}
		}		
	}
	/******取消所有点菜选择*******/
	void cancelAll(){
		if(dishList!=null){
			int size=dishList.size();
			for(int i=0;i<size;i++){
				HashMap<String, Long> hmt=dishList.get(i);
				int idx=hmt.get("idx").intValue();
				list.get(idx).put("food_dishCount", new SpannableString(""));
				((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=false;
			}
		}
		if(iatclAdapter!=null)
			iatclAdapter.notifyDataSetChanged();
		dishList=null;
	}
	/************设置所有点菜为点菜成功**************/
	void setDishesSuccess(){
		if(dishList!=null){
			int size=dishList.size();
			for(int i=0;i<size;i++){
				HashMap<String, Long> hm = (HashMap<String, Long>) dishList.get(i);			
				int idx=hm.get("idx").intValue();
				list.get(idx).put("food_dishCount", new SpannableString(Html.fromHtml("<font color=green>已成功点了"+hm.get("count")+"份</font>")));
				((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=false;
			}
		}
		if(iatclAdapter!=null)
			iatclAdapter.notifyDataSetChanged();
		dishList=null;
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
		loadingMoreTv.setText("没有更多菜谱哦");
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
	private void loadData(){
		String url_s=AppConstant.UrlStrs.URL_DATA_OF_DISH;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("orderId", orderId+"");//订单ID
		//if(partakeCode!=null)
		//	pc.addOrSetParam("partakeCode", partakeCode);//点菜参与码
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfDishAnalyzeHelper analyzeHelper=new DataOfDishAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, this, pc, null, analyzeHelper, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("正在加载菜谱，请稍后...", true);	
		}
		else
		{
			dataLoader.load();
		}
	}
	/**********
	 * 数据访问后事件处理
	 * ******/
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
				if(DishActivity.this.list==null)
				{
					dishAy_noNotifyTv.setVisibility(View.VISIBLE);
					dishAy_foodListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfDishAnalyzeHelper analyzeHelper=(DataOfDishAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//成功,加载数据				
				
				if(dataList==null){
					dataList=new ArrayList<FoodInfo>();
					orderInfo=analyzeHelper.orderInfo;
					if(analyzeHelper.orderInfo!=null){
						System.out.println("analyzeHelper.orderInfo-->"+analyzeHelper.orderInfo);
						dishAy_orderIdTv.setText("订单ID:"+analyzeHelper.orderInfo.id);
						dishAy_partakeCodeTv.setText("点菜参与码:"+analyzeHelper.orderInfo.partakeCode);
					}
					totalCount=analyzeHelper.totalCount;
					System.out.println("totalCount-->"+totalCount);
				}
				
				ArrayList<FoodInfo> foodListTemp=analyzeHelper.foodInfoList;				
				if(foodListTemp==null||foodListTemp.size()==0)
				{
					if(DishActivity.this.list==null)
					{
						dishAy_noNotifyTv.setVisibility(View.VISIBLE);
						dishAy_foodListLv.setVisibility(View.GONE);
					}
					return;
				}
				dataList.addAll(foodListTemp);
				
				
				/***************显示数据***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,Object>>();
				}
				//添加数据
				int size=foodListTemp.size();
				int idxOffset=list.size();
				for(int i=0;i<size;i++){
					FoodInfo foodInfo=analyzeHelper.foodInfoList.get(i);
					HashMap<String,Object> hashTemp=new HashMap<String, Object>();
					hashTemp.put("food_name", new LvItemBtOnClickListener(foodInfo.id,idxOffset+i,foodInfo.name));
					hashTemp.put("food_price", new SpannableString("￥"+foodInfo.price+""));
					hashTemp.put("food_img", foodInfo.image);
					hashTemp.put("food_isDished", new LvItemCbOnCheckedChangeListener(foodInfo.id,idxOffset+i));
					hashTemp.put("food_dishCount", new SpannableString(""));//
					hashTemp.put("food_dish", new LvItemBtOnClickListener(foodInfo.id,idxOffset+i));//
					hashTemp.put("food_cancel", new LvItemBtOnClickListener(foodInfo.id,idxOffset+i));//
					list.add(hashTemp);
				}		
				//更新适配器
				if(null==iatclAdapter){
					iatclAdapter=new ITBCListAdapter(DishActivity.this, dishAy_foodListLv, list,R.layout.lvitem_dish_food, 
							new String[]{"food_name","food_price","food_img","food_isDished","food_dishCount","food_dish","food_cancel"},
							new int[]{R.id.lidf_foodNameBt,R.id.lidf_priceTv,R.id.lidf_foodImgIv,R.id.lidf_dishedCb,R.id.lidf_countTv,
							R.id.lidf_dishBt,R.id.lidf_notDishBt});
					DishActivity.this.dishAy_foodListLv.setAdapter(iatclAdapter);
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
				AcvivityLoginGoto.setAcbi(DishActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(DishActivity.this, LoginActivity.class);
				DishActivity.this.startActivity(loginIntent);
				return;
			}
			else{//其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				DishActivity.this.finish();
				return;
			}
		}
		
	}
	/*********
	 * listView中Button的点击事件监听器
	 * ************/
	class LvItemBtOnClickListener extends LvItemBtAttribute{
		long foodId=0;
		int idx=0;
		public LvItemBtOnClickListener(long foodId,int idx,String txt){
			this.foodId=foodId;
			this.idx=idx;
			this.btTxt=txt;
		}
		public LvItemBtOnClickListener(long foodId,int idx){
			this.foodId=foodId;
			this.idx=idx;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			HashMap<String, Long> dishMap=null;
			switch(v.getId()){
			case R.id.lidf_dishBt:				
				if(dishList==null){
					dishList=new ArrayList<HashMap<String,Long>>();
					dishMap=new HashMap<String, Long>();
					dishMap.put("idx", (long) idx);
					dishMap.put("foodId", (long) foodId);
					dishMap.put("count", 1l);
					dishList.add(dishMap);
				}
				else{
					dishMap=getDish(idx);
					if(dishMap==null){
						dishMap=new HashMap<String, Long>();
						dishMap.put("idx", (long) idx);
						dishMap.put("foodId", (long) foodId);
						dishMap.put("count", 1l);
						dishList.add(dishMap);
					}
					else{
						dishMap.put("count", dishMap.get("count")+1);
					}					
				}
				list.get(idx).put("food_dishCount", new SpannableString("您点了"+dishMap.get("count")+"份"));
				((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=true;
				break;
			case R.id.lidf_notDishBt:
				if(dishList!=null){
					dishMap=getDish(idx);
					if(dishMap!=null){
						dishMap.put("count", dishMap.get("count")-1);
						if(dishMap.get("count")<=0){
							dishList.remove(dishMap);
							list.get(idx).put("food_dishCount", new SpannableString(""));
							((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=false;
						}
						else{
							list.get(idx).put("food_dishCount", new SpannableString("您点了"+dishMap.get("count")+"份"));
							((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=true;
						}
					}
				}				
				break;
			case R.id.lidf_foodNameBt:
				Intent intent=new Intent();
				intent.setClass(DishActivity.this, FoodDetailActivity.class);
				intent.putExtra(AppConstant.IntentExtraName.FOOD_ID, foodId);
				startActivity(intent);
				break;
			}
			System.out.println("bt--i-->"+idx);
			iatclAdapter.notifyDataSetChanged();
		}
		
	}
	/*********
	 * listView中CheckBox的点击事件监听器
	 * ************/
	class LvItemCbOnCheckedChangeListener extends LvItemCbAttribute implements OnClickListener{
		long foodId=0;
		int idx=0;
		LvItemCbOnCheckedChangeListener(long foodId,int idx){
			this.foodId=foodId;
			this.idx=idx;
		}
		LvItemCbOnCheckedChangeListener(long foodId,int idx,boolean checked){
			this.foodId=foodId;
			this.idx=idx;
			this.isChecked=checked;
		}
		@Override
		//public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		public void onClick(View v){	// TODO Auto-generated method stub
			boolean arg1=((CheckBox)v).isChecked();
			if(arg1==true){
				HashMap<String, Long> dishMap=null;
				if(dishList==null){
					dishList=new ArrayList<HashMap<String,Long>>();
					dishMap=new HashMap<String, Long>();
					dishMap.put("idx", (long) idx);
					dishMap.put("foodId", (long) foodId);
					dishMap.put("count", 1l);
					dishList.add(dishMap);
				}
				else{
					dishMap=getDish(idx);
					if(dishMap==null){
						dishMap=new HashMap<String, Long>();
						dishMap.put("idx", (long) idx);
						dishMap.put("foodId", (long) foodId);
						dishMap.put("count", 1l);
						dishList.add(dishMap);
					}
					else{
						dishMap.put("count", dishMap.get("count")+1);
					}					
				}
				list.get(idx).put("food_dishCount", new SpannableString("您点了"+dishMap.get("count")+"份"));
				((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=true;
			}
			else{
				if(dishList!=null){
					HashMap<String, Long> dishMap=getDish(idx);
					if(dishMap!=null){
						dishList.remove(dishMap);
					}
				}
				list.get(idx).put("food_dishCount", new SpannableString(""));
				((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=false;
			}
			System.out.println("cb--i-->"+idx);
			iatclAdapter.notifyDataSetChanged();
		}
		
	}
	
	/*********************提交点菜数据*******************************/
	void submitDish(){
		if(dishList==null||dishList.size()==0){
			Toast.makeText(this, "你还没有点任何菜", Toast.LENGTH_LONG).show();
			return;
		}
		
		String url_s=AppConstant.UrlStrs.URL_ADD_DISHS;
		ParamsCollect pc=new ParamsCollect();
		pc.addParam("orderId", orderId+"");//订单ID
		//if(partakeCode!=null)
		//	pc.addParam("partakeCode", partakeCode);//点菜参与码
		for(int i=0;i<dishList.size();i++){
			HashMap<String, Long> hm=dishList.get(i);
			pc.addParam("foodIds", hm.get("foodId").toString());
			pc.addParam("counts", hm.get("count").toString());
			pc.addParam("remarks", "");
		}		
		
		AddDishesReDataAnalyzeHelper analyzeHelper=new AddDishesReDataAnalyzeHelper();
		SubmitDishAfterAction afterAction=new SubmitDishAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, this, pc, null, analyzeHelper, afterAction);		
		//dataLoader.load("正在点菜，请稍后...", true);	
		dataLoader.load("正在点菜，请稍后...", true,"确认提交","您确认提交你的点菜吗？");
	}
	class SubmitDishAfterAction extends AfterAction{
		public SubmitDishAfterAction(AnalyzeHelper analyzeHelper, Activity a) {
			super(analyzeHelper, a);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doSome() {
			// TODO Auto-generated method stub
			if(!this.isServerReturnOK())
			{
				return;
			}
			AddDishesReDataAnalyzeHelper analyzeHelper=(AddDishesReDataAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//成功,加载数据
				Toast.makeText(a, "点菜成功", Toast.LENGTH_LONG).show();
				setDishesSuccess();
			}
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.DISH_HAVEERROR.code){//部分失败				
				if(analyzeHelper.failedList!=null&&analyzeHelper.failedList.size()>0){
					Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_LONG).show();
					for (Iterator iterator = dishList.iterator(); iterator
							.hasNext();) {
						HashMap<String, Long> hm = (HashMap<String, Long>) iterator.next();
						FailedDish failedDish=null;
						for(int i=0;i<analyzeHelper.failedList.size();i++){
							if(analyzeHelper.failedList.get(i).foodId==hm.get("foodId")){
								failedDish=analyzeHelper.failedList.get(i);
								break;
							}
						}
						if(failedDish==null){
							int idx=hm.get("idx").intValue();
							list.get(idx).put("food_dishCount", new SpannableString(Html.fromHtml("<font color=green>已成功点了"+hm.get("count")+"份</font>")));
							((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=false;
						}
						else{
							int idx=hm.get("idx").intValue();
							list.get(idx).put("food_dishCount", new SpannableString(Html.fromHtml("<font color=red>"+failedDish.msg+"</font>")));
							((LvItemCbAttribute)list.get(idx).get("food_isDished")).isChecked=false;
						}
					}
					if(iatclAdapter!=null)
						iatclAdapter.notifyDataSetChanged();
					dishList=null;
				}
				else{
					Toast.makeText(a, "点菜成功", Toast.LENGTH_LONG).show();
					setDishesSuccess();
				}
			}
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){//没有登录
				AcvivityLoginGoto.setAcbi(DishActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(DishActivity.this, LoginActivity.class);
				DishActivity.this.startActivity(loginIntent);
				return;
			}
			else{
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	/*********************提交完成点菜请求*******************************/
	void submitCompleteDish(){
		String url_s=AppConstant.UrlStrs.URL_COMPLETE_DISH;
		ParamsCollect pc=new ParamsCollect();
		pc.addParam("orderId", orderId+"");//订单ID	
		//SubmitDataAfterAction afterAction=new SubmitDataAfterAction(this);
		//DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, null, afterAction);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s,this, pc);
		//dataLoader.load("正在点菜，请稍后...", true);	
		dataLoader.load("正在提交完成点菜，请稍后...", true,"确认点菜完成","您确认点菜都完成了吗？");
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
	
}
