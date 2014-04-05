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
	
	ActivityManager am=null;//activity������
	
	TextView odAy_orderIdTv=null;//����ID
	TextView odAy_partakeCodeTv=null;//��˲�����
	TextView odAy_noNotifyTv=null;//�޵����ʾ
	ListView odAy_dishListLv=null;//����б�
	
	MultiDirectionSlidingDrawer odAy_orderDetailSd=null;
	Button odAy_sdHandle=null;
	ScrollView odAy_sdContent=null;
	
	TextView odAy_restaurantIdTv=null;//����ID
	TextView odAy_tableIdTv=null;//��λID
	TextView odAy_dateTv=null;//�ò�����
	TextView odAy_timeTv=null;//�ò�ʱ��
	TextView odAy_statusTv=null;//����״̬
	TextView odAy_validTv=null;//������Ч��
	TextView odAy_cancelReasonTv=null;//����ȡ��ԭ��
	TextView odAy_personCountTv=null;//�ò�����
	TextView odAy_remarkTv=null;//��ע
	
	LinearLayout odAy_odCzListLl=null;//���������б�
	
	long orderId=0;
//	String partakeCode=null;
//	boolean isOwner=false;
	
	ArrayList<HashMap<String,Object>> list=null;
	ITBCListAdapter iatclAdapter=null;
	
	ArrayList<DishesInfo> dataList=null;
	OrderInfo orderInfo=null;
	int visibleLastIndex=0;
	
	int pageNum;//ҳ��
	final static int PAGE_MAX=10;//ÿҳ��¼��
	int totalCount;//������
	
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
		//ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*����ǰactivity��ӵ�activitymanager��*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //���ò���
		setContentView(R.layout.activity_order_detail);
		
		//����һ��activity��ȡ����
		Intent preIntent=getIntent();
		orderId=preIntent.getLongExtra(AppConstant.IntentExtraName.ORDER_ID, 0);
		System.out.println("orderId-->"+orderId);
		
		initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        loadData();//��������
	}
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		list=null;
//		iatclAdapter=null;		
//		dataList=null;
//		visibleLastIndex=0;		
//		pageNum=0;//ҳ��
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
	 *��ʼ���ؼ� 
	 *********************/
	private void initInstance(){
		odAy_orderIdTv=(TextView) findViewById(R.id.odAy_orderIdTv);//����ID
		odAy_partakeCodeTv=(TextView) findViewById(R.id.odAy_partakeCodeTv);//��˲�����
		odAy_noNotifyTv=(TextView) findViewById(R.id.odAy_noNotifyTv);//�޵����ʾ
		odAy_dishListLv=(ListView) findViewById(R.id.odAy_dishListLv);//����б�
		
		odAy_orderDetailSd=(MultiDirectionSlidingDrawer) findViewById(R.id.odAy_orderDetailSd);
		odAy_sdHandle=(Button) findViewById(R.id.odAy_sdHandle);
		odAy_sdContent=(ScrollView) findViewById(R.id.odAy_sdContent);
		
		odAy_restaurantIdTv=(TextView) findViewById(R.id.odAy_restaurantIdTv);//����ID
		odAy_tableIdTv=(TextView) findViewById(R.id.odAy_tableIdTv);//��λID
		odAy_dateTv=(TextView) findViewById(R.id.odAy_dateTv);//�ò�����
		odAy_timeTv=(TextView) findViewById(R.id.odAy_timeTv);//�ò�ʱ��
		odAy_statusTv=(TextView) findViewById(R.id.odAy_statusTv);//����״̬
		odAy_validTv=(TextView) findViewById(R.id.odAy_validTv);//������Ч��
		odAy_cancelReasonTv=(TextView) findViewById(R.id.odAy_cancelReasonTv);//����ȡ��ԭ��
		odAy_personCountTv=(TextView) findViewById(R.id.odAy_personCountTv);//�ò�����
		odAy_remarkTv=(TextView) findViewById(R.id.odAy_remarkTv);//��ע
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		odAy_dishListLv.addFooterView(loadmoreView);//�����б�ײ���ͼ
		
		odAy_odCzListLl=(LinearLayout) findViewById(R.id.odAy_odCzListLl);//���������б�
		odAy_goback = (Button) findViewById(R.id.odAy_goback);
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
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

	/***********************����б�����¼�������**********************/
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
			int itemsLastIndex = dataList.size()-1;  //���ݼ����һ�������    
			int lastIndex = itemsLastIndex + 1;  
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  
					&& visibleLastIndex >= lastIndex) {  
				// ������Զ�����,��������������첽�������ݵĴ���  
				if(dataList.size()<totalCount)
					loadData();
			}  

		}
		
	}
	
	//����ListView FooterView��ʾû��
	void setNoMore()
	{
		loadingMoreTv.setText("û�и������Ŷ");
		progressBar1.setVisibility(View.GONE);
	}
	//����ListView FooterView��ʾ���ڼ�������
	void setHasMore()
	{
		loadingMoreTv.setText(getString(R.string.loadingMore));
		progressBar1.setVisibility(View.VISIBLE);
	}
	/*****************************************
	 *���ض����Ͳ�Ʒ�б�����
	 ****************************************/
	private void loadData(){
		String url_s=AppConstant.UrlStrs.URL_ORDER_DETAIL;
		ParamCollect pc=new ParamCollect();
		pc.addOrSetParam("orderId", orderId+"");//����ID
		pc.addOrSetParam("offset", (PAGE_MAX*pageNum)+"");
		pc.addOrSetParam("max", PAGE_MAX+"");
		DataOfOrderDetailAnalyzeHelper analyzeHelper=new DataOfOrderDetailAnalyzeHelper();
		LoadDataAfterAction afterAction=new LoadDataAfterAction(analyzeHelper, this);
		DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc, afterAction);		
		if(this.pageNum==0)
		{
			dataLoader.load("���ڼ��ص����Ϣ�����Ժ�...", true,true);	
		}
		else
		{
			dataLoader.load();
		}
	}
	/**********
	 * ���ݷ��ʺ��¼�����
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
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//�ɹ�,��������				
				
				if(dataList==null){
					dataList=new ArrayList<DishesInfo>();
					orderInfo=analyzeHelper.orderInfo;
					if(analyzeHelper.orderInfo!=null){
						SimpleDateFormat sdfDate = new SimpleDateFormat(
						"yyyy-MM-dd");
				SimpleDateFormat sdfTime = new SimpleDateFormat(
						"HH:mm:ss");
						System.out.println("analyzeHelper.orderInfo-->"+analyzeHelper.orderInfo);
						odAy_orderIdTv.setText("����ID:"+analyzeHelper.orderInfo.id);
						odAy_partakeCodeTv.setText("��˲�����:"+analyzeHelper.orderInfo.partakeCode);
						odAy_restaurantIdTv.setText("����ID:"+analyzeHelper.orderInfo.restaurantId);//����ID
						odAy_tableIdTv.setText("��λID:"+analyzeHelper.orderInfo.tableId);//��λID
						odAy_dateTv.setText("�ò�����:"+sdfDate.format(analyzeHelper.orderInfo.date));//�ò�����
						odAy_timeTv.setText("�ò�ʱ��:"+sdfTime.format(analyzeHelper.orderInfo.time));//�ò�ʱ��
						odAy_statusTv.setText("����״̬:"+OrderStatus.getLable(analyzeHelper.orderInfo.status));//����״̬
						odAy_validTv.setText("������Ч��:"+OrderValid.getLable(analyzeHelper.orderInfo.valid));//������Ч��
						odAy_cancelReasonTv.setText("����ȡ��ԭ��:"+analyzeHelper.orderInfo.cancelReason);//����ȡ��ԭ��
						odAy_personCountTv.setText("�ò�����:"+analyzeHelper.orderInfo.personCount);//�ò�����
						odAy_remarkTv.setText("��ע:"+analyzeHelper.orderInfo.remark);//��ע
						
						odAy_odCzListLl.removeAllViews();//����հ�ť
						FlowLayout lltemp=new FlowLayout(OrderDetailActivity.this,null);//����һ��������
//						if (orderInfo.valid < OrderValid.USER_CANCEL_VALID.code) { // δȡ���Ķ���
//		                    if (orderInfo.status <= OrderStatus.VERIFY_ORDERED_STATUS.code) {
//		                        Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("���");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_DC));
//		                    	lltemp.addView(bt);
//		                    }
//		                    if (orderInfo.status <= OrderStatus.ORDERED_STATUS.code) {
//		                        Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("ȡ��");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_CANCEL_ORDER));
//		                    	lltemp.addView(bt);
//		                    }
//		                    if (orderInfo.status <= OrderStatus.ORIGINAL_STATUS.code) {
//		                    	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("��ɵ��");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_COMPLETE_DISH));
//		                    	lltemp.addView(bt);
//		                    }
//		                    if (orderInfo.status == OrderStatus.CHECKOUTED_STATUS.code) {//������ɣ���������
//		                    	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//		                    	bt.setText("����");
//		                    	bt.setTextSize(18.0f);
//		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_PJ));
//		                    	lltemp.addView(bt);
//		                    }
//		                } else if (orderInfo.valid == OrderValid.USER_CANCEL_VALID.code) {//ȡ���Ķ�������ɾ��
//		                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
//	                    	bt.setText("ɾ��");
//	                    	bt.setTextSize(18.0f);
//	                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_DEL_ORDER));
//	                    	lltemp.addView(bt);
//		                }
						//StaticData sd=StaticData.getInstance();
						//UserInfo ui=sd.getUi();
						UserInfo ui=new UserInfo();
						ui.readFromSharedPreferences(getApplicationContext());
						ArrayList<Integer> positionList=ui.positionTypeList;
						if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.WAITER.code , positionList) || PositionType.isInList(PositionType.WAITER_HEADER.code , positionList)) {   //����Ա
	                        if (orderInfo!=null) {
	                            if (orderInfo.valid < OrderValid.USER_CANCEL_VALID.code) { // δȡ���Ķ���
	                                if (orderInfo.status <= OrderStatus.SERVED_STATUS.code) {
	                                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt.setText("���");
	    		                    	bt.setTextSize(14.0f);
	    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_DC));
	    		                    	lltemp.addView(bt);
	    		                    	Button bt1=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt1.setText("ȡ��");
	    		                    	bt1.setTextSize(14.0f);
	    								bt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt1.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_CANCEL_ORDER));
	    		                    	lltemp.addView(bt1);
//	                                    htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelOrder", params: [orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
//	                                    htmlTag += "<a href='" + createLink(controller: "staff", action: "doDish", params: [orderId: orderId]) + "'>���</a>&nbsp;&nbsp;";
	                                }
	                                if (orderInfo.status == OrderStatus.ORIGINAL_STATUS.code) {
	                                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt.setText("��ɵ��");
	    		                    	bt.setTextSize(14.0f);
	    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_COMPLETE_DISH));
	    		                    	lltemp.addView(bt);
	                                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "completeDish", params: [orderId: orderId]) + "'>��ɵ��</a>&nbsp;&nbsp;";
	                                    if (orderInfo.valid == OrderValid.ORIGINAL_VALID.code) {
	                                    	Button bt1=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
		    		                    	bt1.setText("ȷ����Ч");
		    		                    	bt1.setTextSize(14.0f);
		    								bt1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		    		                    	bt1.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_AFFIRM_VALID_ORDER));
		    		                    	lltemp.addView(bt1);
	                                    	//htmlTag += "<a href='" + createLink(controller: "staff", action: "affirmValid", params: [orderId: orderId]) + "'>ȷ����Ч</a>&nbsp;&nbsp;";
	                                    }
	                                }
	                                if (orderInfo.status == OrderStatus.ORDERED_STATUS.code) {
	                                	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
	    		                    	bt.setText("ȷ�ϵ�����");
	    		                    	bt.setTextSize(14.0f);
	    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, AppConstant.UrlStrs.URL_COMPLETE_AFFIRM_DISH_ORDER));
	    		                    	lltemp.addView(bt);
	                                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeAffirmDish", params: [orderId: orderId]) + "'>ȷ�ϵ�����</a>&nbsp;&nbsp;";
	                                }
	                            }
	                        }
	                    }
	                    if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.BAD_TYPE.code , positionList)) {
	                        //����Ա
	                        if (orderInfo!=null) {
	                            if (orderInfo.valid == OrderValid.EFFECTIVE_VALID.code &&orderInfo.status<OrderStatus.CHECKOUTED_STATUS.code&&orderInfo.status>=OrderStatus.VERIFY_ORDERED_STATUS.code) {//��Ч��ȷ�ϵ����ɵĶ������Խ���
	                            	Button bt=(Button) OrderDetailActivity.this.getLayoutInflater().inflate(R.layout.dynamicload_order_cz_bt, null);
    		                    	bt.setText("����");
    		                    	bt.setTextSize(14.0f);
    								bt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    		                    	bt.setOnClickListener(new BtOnClickListener(orderInfo.id, VIRTUAL_URL_JZ));
    		                    	lltemp.addView(bt);
	                            	//htmlTag += "<a href='" + createLink(controller: "staff", action: "settleAccounts", params: [orderId: orderId]) + "'>����</a>"
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
				
				
				/***************��ʾ����***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,Object>>();
				}
				//�������
				int size=dataListTemp.size();
				int idxOffset=list.size();
				for(int i=0;i<size;i++){
					DishesInfo dishesInfo=dataListTemp.get(i);
					HashMap<String,Object> hashTemp=new HashMap<String, Object>();
					hashTemp.put("lidli_foodImgIv", dishesInfo.foodImg);
					hashTemp.put("lidli_dishCzQxBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,0));
					hashTemp.put("lidli_dishCzBt", new LvItemBtOnClickListener(dishesInfo.id,dishesInfo.status,dishesInfo.valid,1));
					hashTemp.put("lidli_foodNameTv", new LvItemTvOnClickListener(dishesInfo.foodId,
							"����:" + dishesInfo.foodName + ""));
					hashTemp.put("lidli_priceTv", new SpannableString("�۸�:"+dishesInfo.foodPrice+""));
					hashTemp.put("lidli_countTv", new SpannableString("����:"+dishesInfo.num+""));
					hashTemp.put("lidli_statusTv", new SpannableString("״̬:"+DishesStatus.getLable(dishesInfo.status)));//
					hashTemp.put("lidli_validTv", new SpannableString("��Ч��:"+DishesValid.getLable(dishesInfo.valid)));//
					hashTemp.put("lidli_cancelReasonTv", new SpannableString("ȡ��ԭ��:"+dishesInfo.cancelReason));//
					list.add(hashTemp);
				}		
				//����������
				if(null==iatclAdapter){
					iatclAdapter=new ITBCListAdapter(OrderDetailActivity.this, odAy_dishListLv, list,R.layout.lvitem_dish_list_item, 
							new String[]{"lidli_foodImgIv","lidli_dishCzQxBt","lidli_dishCzBt","lidli_foodNameTv","lidli_priceTv","lidli_countTv","lidli_statusTv","lidli_validTv","lidli_cancelReasonTv"},
							new int[]{R.id.lidli_foodImgIv,R.id.lidli_dishCzQxBt,R.id.lidli_dishCzBt,R.id.lidli_foodNameTv,R.id.lidli_priceTv,R.id.lidli_countTv,R.id.lidli_statusTv,
							R.id.lidli_validTv,R.id.lidli_cancelReasonTv});
					OrderDetailActivity.this.odAy_dishListLv.setAdapter(iatclAdapter);
				}
				iatclAdapter.notifyDataSetChanged();
				
				//���µײ���ҳ��
				if(dataList.size()>=totalCount)
				{
					Log.i("doSome:", "setNoMore");
					setNoMore();
				}
				pageNum++;				
			}
			else if(analyzeHelper.recode==AppConstant.ReCodeFinal.NOT_LOGIN.code){//û�е�¼,��ת����¼����
				AcvivityLoginGoto.setAcbi(OrderDetailActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(OrderDetailActivity.this, LoginActivity.class);
				OrderDetailActivity.this.startActivity(loginIntent);
				return;
			}
			else{//����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(dataList==null)
					OrderDetailActivity.this.finish();
				return;
			}
		}
		
	}
	// ������ť
	class LvItemTvOnClickListener extends LvItemBtAttribute {
		long foodId = 0;// ����ID

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
			if(VIRTUAL_URL_DC.equals(url_str)){//�����˽���
				Intent intent=new Intent();
				intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
				intent.putExtra(AppConstant.IntentExtraName.IS_OWNER, true);
				intent.setClass(OrderDetailActivity.this, DishActivity.class);
				OrderDetailActivity.this.startActivity(intent);
			}
			else if(VIRTUAL_URL_JZ.equals(url_str)){//�����Ի�����н���
				AlertDialog dialog=new AlertDialog.Builder(OrderDetailActivity.this).create();
				dialog.setCanceledOnTouchOutside(false);
				View view=getLayoutInflater().inflate(R.layout.dialog_order_cast_accounts, null);
				
				final TextView ocaDg_totalAccountTv=(TextView) view.findViewById(R.id.ocaDg_totalAccountTv);//Ӧ�ս��
				final EditText ocaDg_realAccountEt=(EditText) view.findViewById(R.id.ocaDg_realAccountEt);//ʵ�ս��
				
				//���ÿؼ���ֵ
				if (orderInfo!=null){
					ocaDg_totalAccountTv.setText("�ܽ�"+orderInfo.totalAccount);
					ocaDg_realAccountEt.setText(orderInfo.totalAccount+"");
				}

				dialog.setTitle("����");
				dialog.setView(view);
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "����", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						String realAccount=ocaDg_realAccountEt.getText().toString();//ʵ�ս��
						
						//�ύ����
						String url_s=AppConstant.UrlStrs.URL_SETTLE_ACCOUNT_ORDER;
						ParamsCollect pc=new ParamsCollect();
						pc.addParam("orderId", orderId+"");//����ID
						pc.addParam("realAccount", realAccount+"");
						SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(OrderDetailActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
							@Override
							public void doAfterSuccess() {//�ύ�����ɹ���ˢ������
								// TODO Auto-generated method stub
								list=null;
								iatclAdapter=null;		
								dataList=null;
								visibleLastIndex=0;		
								pageNum=0;//ҳ��
								loadData();
							}				
							@Override
							public void doAfterFail() {}
						});
						DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
						//dataLoader.load("���ڵ�ˣ����Ժ�...", true);	
						dataLoader.load("�����ύ�����Ժ�...", true,true);
					}
				});
				dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ȡ��", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {}
				});
				dialog.show();
			}
			else{//ֱ���ύ
				String url_s=this.url_str;
				ParamsCollect pc=new ParamsCollect();
				pc.addParam("orderId", this.orderId+"");//����ID	
				SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(OrderDetailActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
					@Override
					public void doAfterSuccess() {//�ύ�����ɹ���ˢ������
						// TODO Auto-generated method stub
						list=null;
						iatclAdapter=null;		
						dataList=null;
						visibleLastIndex=0;		
						pageNum=0;//ҳ��
						loadData();
					}				
					@Override
					public void doAfterFail() {}
				});
				DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
				//dataLoader.load("���ڵ�ˣ����Ժ�...", true);	
				dataLoader.load("�����ύ�����Ժ�...", true,"ȷ�϶�������","��ȷ�϶Զ����������Ĳ�����",true);
			}
		}
		
	}
	/*********
	 * listView��Button�ĵ���¼�������
	 * ************/
	class LvItemBtOnClickListener extends LvItemBtAttribute{
		long dishIds=0;//����id
		int status=0;//״̬
		int valid=0;//��Ч��
		String url_str="";
		int type=0;//0ȡ����ť��1�ύ��ť
		public LvItemBtOnClickListener(long dishIds,int status,int valid,int type){
			this.dishIds=dishIds;
			this.status=status;
			this.valid=valid;
			this.type=type;
			//����״̬����Ч�����ð�ť��ʾ�ķ�ʽ�Լ��ύ�ĵ�ַ			
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
			if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.WAITER.code , positionList) || PositionType.isInList(PositionType.WAITER_HEADER.code , positionList)) {   //����Ա
                if (valid == DishesValid.ORIGINAL_VALID.code && status == DishesStatus.ORIGINAL_STATUS.code) {//��ʼ̬����ȡ����ȷ�ϵ��
                    if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
                    if(type==1){
                    	this.btTxt="ȷ�ϵ��";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_AFFIRM_DISH;
                    }
                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "affirmDish", params: [dishIds: id, orderId: orderId]) + "'>ȷ�ϵ��</a>&nbsp;&nbsp;";
                }
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.COOKED_STATUS.code) {//������ɿ����ϲ�
                	if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.GONE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	if(type==1){
                    	this.btTxt="�ϲ����";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_COMPLETE_SERVE_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeServe", params: [dishIds: id, orderId: orderId]) + "'>�ϲ����</a>&nbsp;&nbsp;";
                }
            }
            if (PositionType.isInList(PositionType.SHOPKEEPER.code , positionList) || PositionType.isInList(PositionType.COOK.code , positionList)) {
                //��ʦ
                //println("chushi");
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.VERIFYING_STATUS.code) {//��ʼ̬����ȡ���Ϳ�ʼ����
                	if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
                    if(type==1){
                    	this.btTxt="��ʼ����";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_BEGIN_COOK_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "cancelDish", params: [dishIds: id, orderId: orderId]) + "'>ȡ��</a>&nbsp;&nbsp;";
                    //htmlTag += "<a href='" + createLink(controller: "staff", action: "beginCook", params: [dishIds: id, orderId: orderId]) + "'>��ʼ����</a>&nbsp;&nbsp;";
                }
                if (valid == DishesValid.EFFECTIVE_VALID.code && status == DishesStatus.COOKING_ORDERED_STATUS.code) {//�����е���Ч�ĵ�˿���������ɲ���
                	if(type==0){
                    	this.btTxt="ȡ��";
                        this.btV=View.GONE;
                        this.url_str=AppConstant.UrlStrs.URL_CANCEL_DISH;
                    }
                	if(type==1){
                    	this.btTxt="�������";
                        this.btV=View.VISIBLE;
                        this.url_str=AppConstant.UrlStrs.URL_COMPLETE_COOK_DISH;
                    }
                	//htmlTag += "<a href='" + createLink(controller: "staff", action: "completeCook", params: [dishIds: id, orderId: orderId]) + "'>�������</a>&nbsp;&nbsp;";
                }
            }
            
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url_s=this.url_str;
			ParamsCollect pc=new ParamsCollect();
			pc.addParam("dishIds", this.dishIds+"");//���ID	
			SubmitDataAfterAction sAfterAction=new SubmitDataAfterAction(OrderDetailActivity.this,new SubmitDataAfterAction.SubmitCallBack() {				
				@Override
				public void doAfterSuccess() {//�ύ�����ɹ���ˢ������
					// TODO Auto-generated method stub
					list=null;
					iatclAdapter=null;		
					dataList=null;
					visibleLastIndex=0;		
					pageNum=0;//ҳ��
					loadData();
				}				
				@Override
				public void doAfterFail() {
					// TODO Auto-generated method stub
					
				}
			});
			DataLoadHelper dataLoader=new DataLoadHelper(url_s, pc,sAfterAction);
			//dataLoader.load("���ڵ�ˣ����Ժ�...", true);	
			dataLoader.load("�����ύ�����Ժ�...", true,"ȷ�ϵ�˲���","��ȷ�϶Ե���������Ĳ�����",true);
		}		
	} 
	@Override
	public void loginSuccessCallBack() {
		// TODO Auto-generated method stub
		list=null;
		iatclAdapter=null;		
		dataList=null;
		visibleLastIndex=0;		
		pageNum=0;//ҳ��
		loadData();
	}
}
