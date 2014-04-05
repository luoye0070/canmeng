package lj.cms.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import lj.cms.custom_view.FlowLayout;
import lj.cms.custom_view.MultiDirectionSlidingDrawer.OnDrawerCloseListener;
import lj.cms.custom_view.MultiDirectionSlidingDrawer.OnDrawerOpenListener;

import lj.cms.R;
import lj.cms.activity.DishActivity.BTOnClickListener;
import lj.cms.activity.DishActivity.LoadDataAfterAction;
import lj.cms.activity.DishActivity.LvItemBtOnClickListener;
import lj.cms.activity.DishActivity.LvItemCbOnCheckedChangeListener;
import lj.cms.activity.DishActivity.LvOnScrollListener;
import lj.cms.adapter.ITBCListAdapter;
import lj.cms.adapter.LvItemBtAttribute;
import lj.cms.adapter.LvItemCbAttribute;
import lj.cms.common.ActivityCallBackInterface;
import lj.cms.common.ActivityManager;
import lj.cms.common.AcvivityLoginGoto;
import lj.cms.common.OptionsMenuProcess;
import lj.cms.common.StaticData;
import lj.cms.constant.AppConstant;
import lj.cms.constant.AppraiseType;
import lj.cms.constant.DishesStatus;
import lj.cms.constant.DishesValid;
import lj.cms.constant.OrderStatus;
import lj.cms.constant.OrderValid;
import lj.cms.constant.PositionType;
import lj.cms.custom_view.MultiDirectionSlidingDrawer;
import lj.cms.dataload.AfterAction;
import lj.cms.dataload.DataLoadHelper;
import lj.cms.dataload.SubmitDataAfterAction;
import lj.cms.internet.ParamCollect;
import lj.cms.internet.ParamsCollect;
import lj.cms.model.DishesInfo;
import lj.cms.model.FoodInfo;
import lj.cms.model.OrderInfo;
import lj.cms.model.UserInfo;
import lj.cms.string_analysis.AnalyzeHelper;
import lj.cms.string_analysis.DataOfDishAnalyzeHelper;
import lj.cms.string_analysis.DataOfOrderDetailAnalyzeHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class OrderDetailActivity extends Activity implements ActivityCallBackInterface {
	
	ActivityManager am=null;//activity管理类
	
	TextView odAy_orderIdTv=null;//订单ID
	TextView odAy_partakeCodeTv=null;//点菜参与码
	TextView odAy_noNotifyTv=null;//无点菜提示
	ListView odAy_dishListLv=null;//点菜列表
	
	MultiDirectionSlidingDrawer odAy_orderDetailSd=null;
	Button odAy_sdHandle=null;
	ScrollView odAy_sdContent=null;
	
	TextView odAy_restaurantIdTv=null;//饭店ID
	TextView odAy_tableIdTv=null;//桌位ID
	TextView odAy_dateTv=null;//用餐日期
	TextView odAy_timeTv=null;//用餐时间
	TextView odAy_statusTv=null;//订单状态
	TextView odAy_validTv=null;//订单有效性
	TextView odAy_cancelReasonTv=null;//饭店取消原因
	TextView odAy_personCountTv=null;//用餐人数
	TextView odAy_remarkTv=null;//备注
	
	LinearLayout odAy_odCzListLl=null;//订单操作列表
	
	long orderId=0;
//	String partakeCode=null;
//	boolean isOwner=false;
	
	ArrayList<HashMap<String,Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<DishesInfo> dataList=null;
	OrderInfo orderInfo=null;
	int visibleLastIndex=0;
	
	int pageNum;//页号
	final static int PAGE_MAX=10;//每页记录数
	int totalCount;//总数量
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	Button odAy_goback = null;
	
	final String VIRTUAL_URL_DC="diancai";
	final String VIRTUAL_URL_JZ="jiezhang";
	
	//ArrayList<HashMap<String,Long>> dishList=null;
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
		setContentView(R.layout.activity_order_detail);
		
		//从上一个activity获取数据
		Intent preIntent=getIntent();
		orderId=preIntent.getLongExtra(AppConstant.IntentExtraName.ORDER_ID, 0);
		System.out.println("orderId-->"+orderId);
		
		initInstance();//初始化控件
        registerEvent();//注册事件
        loadData();//加载数据
	}
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		list=null;
//		iatclAdapter=null;		
//		dataList=null;
//		visibleLastIndex=0;		
//		pageNum=0;//页号
//		loadData();
//	}
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
		odAy_orderIdTv=(TextView) findViewById(R.id.odAy_orderIdTv);//订单ID
		odAy_partakeCodeTv=(TextView) findViewById(R.id.odAy_partakeCodeTv);//点菜参与码
		odAy_noNotifyTv=(TextView) findViewById(R.id.odAy_noNotifyTv);//无点菜提示
		odAy_dishListLv=(ListView) findViewById(R.id.odAy_dishListLv);//点菜列表
		
		odAy_orderDetailSd=(MultiDirectionSlidingDrawer) findViewById(R.id.odAy_orderDetailSd);
		odAy_sdHandle=(Button) findViewById(R.id.odAy_sdHandle);
		odAy_sdContent=(ScrollView) findViewById(R.id.odAy_sdContent);
		
		odAy_restaurantIdTv=(TextView) findViewById(R.id.odAy_restaurantIdTv);//饭店ID
		odAy_tableIdTv=(TextView) findViewById(R.id.odAy_tableIdTv);//桌位ID
		odAy_dateTv=(TextView) findViewById(R.id.odAy_dateTv);//用餐日期
		odAy_timeTv=(TextView) findViewById(R.id.odAy_timeTv);//用餐时间
		odAy_statusTv=(TextView) findViewById(R.id.odAy_statusTv);//订单状态
		odAy_validTv=(TextView) findViewById(R.id.odAy_validTv);//订单有效性
		odAy_cancelReasonTv=(TextView) findViewById(R.id.odAy_cancelReasonTv);//饭店取消原因
		odAy_personCountTv=(TextView) findViewById(R.id.odAy_personCountTv);//用餐人数
		odAy_remarkTv=(TextView) findViewById(R.id.odAy_remarkTv);//备注
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		odAy_dishListLv.addFooterView(loadmoreView);//设置列表底部视图
		
		odAy_odCzListLl=(LinearLayout) findViewById(R.id.odAy_odCzListLl);//订单操作列表
		odAy_goback = (Button) findViewById(R.id.odAy_goback);
	}
	/*****************************************
	 *注册控件事件响应函数
	 ****************************************/
	private void registerEvent(){
		odAy_dishListLv.setOnScrollListener(new LvOnScrollListener());
		odAy_orderDetailSd
		.setOnDrawerOpenListener(new SlidingDrawerOnClickListener());
odAy_orderDetailSd
		.setOnDrawerCloseListener(new SlidingDrawerOnClickListener());
		odAy_goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OrderDetailActivity.this.finish();
			}
		});
	}
	class SlidingDrawerOnClickListener implements OnDrawerOpenListener,
	OnDrawerCloseListener {

@Override
public void onDrawerClosed() {
	// TODO Auto-generated method stub
	Drawable img_down = getResources().getDrawable(
			R.drawable.sliding_down_bt_bk_selector);
	img_down.setBounds(0, 0, img_down.getMinimumWidth(),
			img_down.getMinimumHeight());
	odAy_sdHandle.setCompoundDrawables(null, null, img_down, null);
	odAy_sdHandle.setText(R.string.odAy_slidingBtDownTxt);
}

@Override
public void onDrawerOpened() {
	// TODO Auto-generated method stub
	// odAy_sdHandle.setImageResource(R.drawable.sliding_up_bt_bk_selector);
	Drawable img_up = getResources().getDrawable(
			R.drawable.sliding_up_bt_bk_selector);
	img_up.setBounds(0, 0, img_up.getMinimumWidth(),
			img_up.getMinimumHeight());
	odAy_sdHandle.setCompoundDrawables(null, null, img_up, null);
	odAy_sdHandle.setText(R.string.odAy_slidingBtUpTxt);
}

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
		String url_s=AppConstant.UrlStrs.URL_ORDER_DETAIL;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("orderId", orderId+"");//订单ID
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfOrderDetailAnalyzeHelper analyzeHelper=new DataOfOrderDetailAnalyzeHelper();
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
				if(OrderDetailActivity.this.list==null)
				{
					odAy_noNotifyTv.setVisibility(View.VISIBLE);
					odAy_dishListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfOrderDetailAnalyzeHelper analyzeHelper=(DataOfOrderDetailAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//成功,加载数据				
				
				if(dataList==null){
					dataList=new ArrayList<DishesInfo>();
					orderInfo=analyzeHelper.orderInfo;
					if(analyzeHelper.orderInfo!=null){
						SimpleDateFormat sdfDate = new SimpleDateFormat(
						"yyyy-MM-dd");
				SimpleDateFormat sdfTime = new SimpleDateFormat(
						"HH:mm:ss");
						System.out.println("analyzeHelper.orderInfo-->"+analyzeHelper.orderInfo);
						odAy_orderIdTv.setText("订单ID:"+analyzeHelper.orderInfo.id);
						odAy_partakeCodeTv.setText("点菜参与码:"+analyzeHelper.orderInfo.partakeCode);
						odAy_restaurantIdTv.setText("饭店ID:"+analyzeHelper.orderInfo.restaurantId);//饭店ID
						odAy_tableIdTv.setText("桌位ID:"+analyzeHelper.orderInfo.tableId);//桌位ID
						odAy_dateTv.setText("用餐日期:"+sdfDate.format(analyzeHelper.orderInfo.date));//用餐日期
						odAy_timeTv.setText("用餐时间:"+sdfTime.format(analyzeHelper.orderInfo.time));//用餐时间
						odAy_statusTv.setText("订单状态:"+OrderStatus.getLable(analyzeHelper.orderInfo.status));//订单状态
						odAy_validTv.setText("订单有效性:"+OrderValid.getLable(analyzeHelper.orderInfo.valid));//订单有效性
						odAy_cancelReasonTv.setText("饭店取消原因:"+analyzeHelper.orderInfo.cancelReason);//饭店取消原因
						odAy_personCountTv.setText("用餐人数:"+analyzeHelper.orderInfo.personCount);//用餐人数
						odAy_remarkTv.setText("备注:"+analyzeHelper.orderInfo.remark);//备注
						
						odAy_odCzListLl.removeAllViews();//先清空按钮
						FlowLayout lltemp=new FlowLayout(OrderDetailActivity.this,null);//创建一个流布局
//						if (orderInfo.valid < OrderValid.USER_CANCEL_VALID.code) { // 未取消的订单
//		                    if (orderInfo.status <= OrderStatus.VERIFY_ORDERED_STATUS.code) {
//		                        Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("点菜");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_DC));
//		                    	lltemp.addView(bt);
//		                    }
//		                    if (orderInfo.status <= OrderStatus.ORDERED_STATUS.code) {
//		                        Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("取消");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_CANCEL_ORDER));
//		                    	lltemp.addView(bt);
//		                    }
//		                    if (orderInfo.status <= OrderStatus.ORIGINAL_STATUS.code) {
//		                    	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("完成点菜");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_COMPLETE_DISH));
//		                    	lltemp.addView(bt);
//		                    }
//		                    if (orderInfo.status == OrderStatus.CHECKOUTED_STATUS.code) {//结账完成，可以评价
//		                    	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("评价");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_PJ));
//		                    	lltemp.addView(bt);
//		                    }
//		                } else if (orderInfo.valid == OrderValid.USER_CANCEL_VALID.code) {//取消的订单可以删除
//		                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//	                    	bt.setText("删除");
//	                    	bt.setTextSize(18.0f);
//	                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_DEL_ORDER));
//	                    	lltemp.addView(bt);
//		                }
						//StaticData sd=StaticData.getInstance();
						//UserInfo ui=sd.getUi();
						UserInfo ui=new UserInfo();
						ui.readFromSharedPreferences(getApplicationContext());
						ArrayList<Integer> positionList=ui.positionTypeList;
						if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.WAITER.code , positionList) || PositionType.isInList(PositionType.WAITER_HEADER.code , positionList)) {   //服务员
	                        if (orderInfo!=null) {
	                            if (orderInfo.valid < OrderValid.USER_CANCEL_VALID.code) { // 未取消的订单
	                                if (orderInfo.status <= OrderStatus.SERVED_STATUS.code) {
	                                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt.setText("点菜");
	    		                    	bt.setTextSize(14.0f);
	    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_DC));
	    		                    	lltemp.addView(bt);
	    		                    	Button bt1=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt1.setText("取消");
	    		                    	bt1.setTextSize(14.0f);
	    								bt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt1.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_CANCEL_ORDER));
	    		                    	lltemp.addView(bt1);
//	                                    htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelOrder", params: [orderId: orderId]) + "'>取消</a>&nbsp;&nbsp;";
//	                                    htmlTag += "<a href='" + createLink(controller: "staff", action: "doDish", params: [orderId: orderId]) + "'>点菜</a>&nbsp;&nbsp;";
	                                }
	                                if (orderInfo.status == OrderStatus.ORIGINAL_STATUS.code) {
	                                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt.setText("完成点菜");
	    		                    	bt.setTextSize(14.0f);
	    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_COMPLETE_DISH));
	    		                    	lltemp.addView(bt);
	                                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "completeDish", params: [orderId: orderId]) + "'>完成点菜</a>&nbsp;&nbsp;";
	                                    if (orderInfo.valid == OrderValid.ORIGINAL_VALID.code) {
	                                    	Button bt1=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
		    		                    	bt1.setText("确认有效");
		    		                    	bt1.setTextSize(14.0f);
		    								bt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		    		                    	bt1.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_AFFIRM_VALID_ORDER));
		    		                    	lltemp.addView(bt1);
	                                    	//htmlTag += "<a href='" + createLink(controller: "staff", action: "affirmValid", params: [orderId: orderId]) + "'>确认有效</a>&nbsp;&nbsp;";
	                                    }
	                                }
	                                if (orderInfo.status == OrderStatus.ORDERED_STATUS.code) {
	                                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt.setText("确认点菜完成");
	    		                    	bt.setTextSize(14.0f);
	    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_COMPLETE_AFFIRM_DISH_ORDER));
	    		                    	lltemp.addView(bt);
	                                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeAffirmDish", params: [orderId: orderId]) + "'>确认点菜完成</a>&nbsp;&nbsp;";
	                                }
	                            }
	                        }
	                    }
	                    if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.BAD_TYPE.code , positionList)) {
	                        //收银员
	                        if (orderInfo!=null) {
	                            if (orderInfo.valid == OrderValid.EFFECTIVE_VALID.code &&orderInfo.status<OrderStatus.CHECKOUTED_STATUS.code&&orderInfo.status>=OrderStatus.VERIFY_ORDERED_STATUS.code) {//有效且确认点菜完成的订单可以结账
	                            	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
    		                    	bt.setText("结账");
    		                    	bt.setTextSize(14.0f);
    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_JZ));
    		                    	lltemp.addView(bt);
	                            	//htmlTag += "<a href='" + createLink(controller: "staff", action: "settleAccounts", params: [orderId: orderId]) + "'>结账</a>"
	                            }
	                        }
	                    }
						odAy_odCzListLl.addView(lltemp);
					}
					totalCount=analyzeHelper.totalCount;
					System.out.println("totalCount-->"+totalCount);
				}
				
				ArrayList<DishesInfo> dataListTemp=analyzeHelper.dishesList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(OrderDetailActivity.this.list==null)
					{
						odAy_noNotifyTv.setVisibility(View.VISIBLE);
						odAy_dishListLv.setVisibility(View.GONE);
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
				int idxOffset=list.size();
				for(int i=0;i<size;i++){
					DishesInfo dishesInfo=dataListTemp.get(i);
					HashMap<String,Object> hashTemp=new HashMap<String, Object>();
					hashTemp.put("lidli_foodImgIv", dishesInfo.foodImg);
					hashTemp.put("lidli_dishCzQxBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,0));
					hashTemp.put("lidli_dishCzBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,1));
					hashTemp.put("lidli_foodNameTv", new LvItemTvOnClickListener(dishesInfo.foodId,
							"菜名:" + dishesInfo.foodName + ""));
					hashTemp.put("lidli_priceTv", new SpannableString("价格:"+dishesInfo.foodPrice+""));
					hashTemp.put("lidli_countTv", new SpannableString("份数:"+dishesInfo.num+""));
					hashTemp.put("lidli_statusTv", new SpannableString("状态:"+DishesStatus.getLable(dishesInfo.status)));//
					hashTemp.put("lidli_validTv", new SpannableString("有效性:"+DishesValid.getLable(dishesInfo.valid)));//
					hashTemp.put("lidli_cancelReasonTv", new SpannableString("取消原因:"+dishesInfo.cancelReason));//
					list.add(hashTemp);
				}		
				//更新适配器
				if(null==iatclAdapter){
					iatclAdapter=new ITBCListAdapter(OrderDetailActivity.this, odAy_dishListLv, list,R.layout.lvitem_dish_list_item, 
							new String[]{"lidli_foodImgIv","lidli_dishCzQxBt","lidli_dishCzBt","lidli_foodNameTv","lidli_priceTv","lidli_countTv","lidli_statusTv","lidli_validTv","lidli_cancelReasonTv"},
							new int[]{R.id.lidli_foodImgIv,R.id.lidli_dishCzQxBt,R.id.lidli_dishCzBt,R.id.lidli_foodNameTv,R.id.lidli_priceTv,R.id.lidli_countTv,R.id.lidli_statusTv,
							R.id.lidli_validTv,R.id.lidli_cancelReasonTv});
					OrderDetailActivity.this.odAy_dishListLv.setAdapter(iatclAdapter);
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
				AcvivityLoginGoto.setAcbi(OrderDetailActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(OrderDetailActivity.this, LoginActivity.class);
				OrderDetailActivity.this.startActivity(loginIntent);
				return;
			}
			else{//其他错误显示错误
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(dataList==null)
					OrderDetailActivity.this.finish();
				return;
			}
		}
		
	}
	// 菜名按钮
	class LvItemTvOnClickListener extends LvItemBtAttribute {
		long foodId = 0;// 菜谱ID

		public LvItemTvOnClickListener(long foodId, String txt) {
			super(txt);
			this.foodId = foodId;
			this.pFlag = Paint.UNDERLINE_TEXT_FLAG;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(OrderDetailActivity.this, FoodDetailActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.FOOD_ID, foodId);
			startActivity(intent);
		}

	}
	class BtOnClickListener implements OnClickListener{
		long orderId=0;
		String url_str="";
		public BtOnClickListener(long orderId,String url_str){
			this.orderId=orderId;
			this.url_str=url_str;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(VIRTUAL_URL_DC.equals(url_str)){//进入点菜界面
				Intent intent=new Intent();
				intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
				intent.putExtra(AppConstant.IntentExtraName.IS_OWNER, true);
				intent.setClass(OrderDetailActivity.this, DishActivity.class);
				OrderDetailActivity.this.startActivity(intent);
			}
			else if(VIRTUAL_URL_JZ.equals(url_str)){//弹出对话框进行结账
				AlertDialog dialog=new AlertDialog.Builder(OrderDetailActivity.this).create();
				dialog.setCanceledOnTouchOutside(false);
				View view=getLayoutInflater().inflate(R.layout.dialog_order_cast_accounts, null);
				
				final TextView ocaDg_totalAccountTv=(TextView) view.findViewById(R.id.ocaDg_totalAccountTv);//应收金额
				final EditText ocaDg_realAccountEt=(EditText) view.findViewById(R.id.ocaDg_realAccountEt);//实收金额
				
				//设置控件的值
				if (orderInfo!=null){
					ocaDg_totalAccountTv.setText("总金额："+orderInfo.totalAccount);
					ocaDg_realAccountEt.setText(orderInfo.totalAccount+"");
				}

				dialog.setTitle("结账");
				dialog.setView(view);
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "结账", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						String realAccount=ocaDg_realAccountEt.getText().toString();//实收金额
						
						//提交数据
						String url_s=AppConstant.UrlStrs.URL_SETTLE_ACCOUNT_ORDER;
						ParamsCollect pc=new ParamsCollect();
						pc.addParam("orderId", orderId+"");//订单ID
						pc.addParam("realAccount", realAccount+"");
						SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(OrderDetailActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
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
							public void doAfterFail() {}
						});
						DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
						//dataLoader.load("正在点菜，请稍后...", true);	
						dataLoader.load("正在提交，请稍后...", true,true);
					}
				});
				dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {}
				});
				dialog.show();
			}
			else{//直接提交
				String url_s=this.url_str;
				ParamsCollect pc=new ParamsCollect();
				pc.addParam("orderId", this.orderId+"");//订单ID	
				SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(OrderDetailActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
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
					public void doAfterFail() {}
				});
				DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
				//dataLoader.load("正在点菜，请稍后...", true);	
				dataLoader.load("正在提交，请稍后...", true,"确认订单操作","您确认对订单做这样的操作吗？",true);
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
			if(orderInfo==null){
				return;
			}
//			if(orderInfo.valid>=OrderValid.USER_CANCEL_VALID.code||orderInfo.status>=OrderStatus.SHIPPING_STATUS.code){
//				return;
//			}
			//StaticData sd=StaticData.getInstance();
			//UserInfo ui=sd.getUi();
			UserInfo ui=new UserInfo();
			ui.readFromSharedPreferences(getApplicationContext());
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
			SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(OrderDetailActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
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
