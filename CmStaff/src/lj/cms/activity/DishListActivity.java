package lj.cms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.R;
import lj.cms.activity.include.MainNavbarMenu;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.adapter.LvItemBtAttribute;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.constant.DishesStatus;
import lj.cms.constant.DishesValid;
import lj.cms.constant.PositionType;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.dataload.SubmitDataAfterAction;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.DishesInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfDishListAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class DishListActivity extends Activity implements ActivityCallBackInterface {
	ActivityManager am=null;//activity管理类
	//MainSearchBar msb=null;//搜索栏
	MainNavbarMenu mnm=null;//导航菜单管理类
	
	TextView dlAy_noNotifyTv=null;//无记录提示
	ListView dlAy_dishListLv=null;//订单列表
	
	ArrayList<HashMap<String, Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<DishesInfo> dataList=null;
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
		setContentView(R.layout.activity_dish_list);
		
		//初始化一个搜索栏
        //msb=new MainSearchBar(this);
        //初始化一个导航菜单
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.DishListActivity);
        initInstance();//初始化控件
        registerEvent();//注册事件
        loadData();//加载数据
	}
	void initInstance(){
		dlAy_noNotifyTv=(TextView) findViewById(R.id.dlAy_noNotifyTv);//无记录提示
		dlAy_dishListLv=(ListView) findViewById(R.id.dlAy_dishListLv);//订单列表
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		dlAy_dishListLv.addFooterView(loadmoreView);//设置列表底部视图
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	private void registerEvent(){
		dlAy_dishListLv.setOnScrollListener(new LvOnScrollListener());
	}

	/***********************点菜列表滚动事件监听器**********************/
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
		loadingMoreTv.setText("没有更多点菜哦");
		progressBar1.setVisibility(View.GONE);
	}
	//设置ListView FooterView显示正在加载数据
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *加载点菜列表数据
	 ****************************************/
	private void loadData(){
		String url_s=AppConstant.UrlStrs.URL_DISH_LIST;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfDishListAnalyzeHelper analyzeHelper=new DataOfDishListAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("正在加载点菜信息，请稍后...", true,true);	
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
				if(DishListActivity.this.list==null)
				{
					dlAy_noNotifyTv.setVisibility(View.VISIBLE);
					dlAy_dishListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfDishListAnalyzeHelper analyzeHelper=(DataOfDishListAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//成功,加载数据				
				
				if(dataList==null){
					dataList=new ArrayList<DishesInfo>();					
					totalCount=analyzeHelper.totalCount;
					System.out.println("totalCount-->"+totalCount);
				}
				
				ArrayList<DishesInfo> dataListTemp=analyzeHelper.dishesList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(DishListActivity.this.list==null)
					{
						dlAy_noNotifyTv.setVisibility(View.VISIBLE);
						dlAy_dishListLv.setVisibility(View.GONE);
					}
					return;
				}
				dataList.addAll(dataListTemp);
				
				
				/***************显示数据***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,Object>>();
				}
				//添加数据
				int size=dataListTemp.size();
				for(int i=0;i<size;i++){
					DishesInfo dishesInfo=dataListTemp.get(i);
					HashMap<String,Object> hashTemp=new HashMap<String, Object>();
					hashTemp.put("lidlis_dishCzQxBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,0));
					hashTemp.put("lidlis_dishCzBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,1));
					hashTemp.put("lidlis_foodNameTv", new SpannableString("菜名:"+dishesInfo.foodName+""));
					hashTemp.put("lidlis_countTv", new SpannableString("份数:"+dishesInfo.num+""));
					hashTemp.put("lidlis_statusTv", new SpannableString("状态:"+DishesStatus.getLable(dishesInfo.status)));//
					list.add(hashTemp);
				}		
				//更新适配器
				if(null==iatclAdapter){
					iatclAdapter=new ITBCListAdapter(DishListActivity.this, dlAy_dishListLv, list,R.layout.lvitem_dish_list_item_simple, 
							new String[]{"lidlis_dishCzQxBt","lidlis_dishCzBt","lidlis_foodNameTv","lidlis_countTv","lidlis_statusTv"},
							new int[]{R.id.lidlis_dishCzQxBt,R.id.lidlis_dishCzBt,R.id.lidlis_foodNameTv,R.id.lidlis_countTv,R.id.lidlis_statusTv});
					DishListActivity.this.dlAy_dishListLv.setAdapter(iatclAdapter);
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
				AcvivityLoginGoto.setAcbi(DishListActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(DishListActivity.this, LoginActivity.class);
				DishListActivity.this.startActivity(loginIntent);
				return;
			}
			else{//其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(dataList==null)
					DishListActivity.this.finish();
				return;
			}
		}
		
	}
	/*********
	 * listView中Button的点击事件监听器
	 * ************/
	class LvItemBtOnClickListener extends LvItemBtAttribute{
		long dishIds=0;//订单id
		int status=0;//状态
		int valid=0;//有效性
		String url_str="";
		int type=0;//0取消按钮，1提交按钮
		public LvItemBtOnClickListener(long dishIds,int status,int valid,int type){
			this.dishIds=dishIds;
			this.status=status;
			this.valid=valid;
			this.type=type;
			//根据状态和有效性设置按钮显示的方式以及提交的地址			
			this.btV=View.GONE;
//			if(orderInfo.valid>=OrderValid.USER_CANCEL_VALID.code||orderInfo.status>=OrderStatus.SHIPPING_STATUS.code){
//				return;
//			}
			StaticData sd=StaticData.getInstance();
			UserInfo ui=sd.getUi();
			ArrayList<Integer> positionList=ui.positionTypeList;
			if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.WAITER.code , positionList) || PositionType.isInList(PositionType.WAITER_HEADER.code , positionList)) {   //服务员
                if (valid == DishesValid.ORIGINAL_VALID.code && status == DishesStatus.ORIGINAL_STATUS.code) {//初始态可以取消和确认点菜
                    if(type==0){
                    	this.btTxt="取消";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>取消</a>&nbsp;&nbsp;";
                    if(type==1){
                    	this.btTxt="确认点菜";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_AFFIRM_DISH;
                    }
                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "affirmDish", params: [dishIds: id, orderId: orderId]) + "'>确认点菜</a>&nbsp;&nbsp;";
                }
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.COOKED_STATUS.code) {//做菜完成可以上菜
                	if(type==0){
                    	this.btTxt="取消";
                        this.btV=View.GONE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	if(type==1){
                    	this.btTxt="上菜完成";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_COMPLETE_SERVE_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeServe", params: [dishIds: id, orderId: orderId]) + "'>上菜完成</a>&nbsp;&nbsp;";
                }
            }
            if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.COOK.code , positionList)) {
                //厨师
                //println("chushi");
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.VERIFYING_STATUS.code) {//初始态可以取消和开始做菜
                	if(type==0){
                    	this.btTxt="取消";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>取消</a>&nbsp;&nbsp;";
                    if(type==1){
                    	this.btTxt="开始做菜";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_BEGIN_COOK_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>取消</a>&nbsp;&nbsp;";
                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "beginCook", params: [dishIds: id, orderId: orderId]) + "'>开始做菜</a>&nbsp;&nbsp;";
                }
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.COOKING_ORDERED_STATUS.code) {//做菜中的有效的点菜可以做菜完成操作
                	if(type==0){
                    	this.btTxt="取消";
                        this.btV=View.GONE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	if(type==1){
                    	this.btTxt="做菜完成";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_COMPLETE_COOK_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeCook", params: [dishIds: id, orderId: orderId]) + "'>做菜完成</a>&nbsp;&nbsp;";
                }
            }
            
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url_s=this.url_str;
			ParamsCollect pc=new ParamsCollect();
			pc.addParam("dishIds", this.dishIds+"");//点菜ID	
			SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(DishListActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
				@Override
				public void doAfterSuccess() {//提交操作成功后，刷新数据
					// TODO Auto-generated method stub
					list=null;
					iatclAdapter=null;		
					dataList=null;
					visibleLastIndex=0;		
					pageNum=0;//页号
					loadData();
				}				
				@Override
				public void doAfterFail() {
					// TODO Auto-generated method stub
					
				}
			});
			DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
			//dataLoader.load("正在点菜，请稍后...", true);	
			dataLoader.load("正在提交，请稍后...", true,"确认点菜操作","您确认对点菜做这样的操作吗？",true);
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
}
