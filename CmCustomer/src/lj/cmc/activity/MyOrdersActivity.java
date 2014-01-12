package lj.cmc.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import lj.cmc.R;
import lj.cmc.activity.DishActivity.BTOnClickListener;
import lj.cmc.activity.DishActivity.LoadDataAfterAction;
import lj.cmc.activity.DishActivity.LvItemBtOnClickListener;
import lj.cmc.activity.DishActivity.LvItemCbOnCheckedChangeListener;
import lj.cmc.activity.DishActivity.LvOnScrollListener;
import lj.cmc.activity.include.MainNavbarMenu;
import lj.cmc.adapter.ITBCListAdapter;
import lj.cmc.common.ActivityCallBackInterface;
import lj.cmc.common.ActivityManager;
import lj.cmc.common.AcvivityLoginGoto;
import lj.cmc.constant.AppConstant;
import lj.cmc.constant.OrderStatus;
import lj.cmc.constant.OrderValid;
import lj.cmc.dataload.AfterAction;
import lj.cmc.dataload.DataLoadHelper;
import lj.cmc.internet.ParamCollect;
import lj.cmc.model.FoodInfo;
import lj.cmc.model.OrderInfo;
import lj.cmc.string_analysis.AnalyzeHelper;
import lj.cmc.string_analysis.DataOfDishAnalyzeHelper;
import lj.cmc.string_analysis.DataOfOrdersAnalyzeHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
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

public class MyOrdersActivity extends Activity implements ActivityCallBackInterface {
	
	ActivityManager am=null;//activity������
	//MainSearchBar msb=null;//������
	MainNavbarMenu mnm=null;//�����˵�������
	
	TextView moAy_noNotifyTv=null;//�޼�¼��ʾ
	ListView moAy_orderListLv=null;//�����б�
	
	ArrayList<HashMap<String,SpannableString>> list=null;
	SimpleAdapter iatclAdapter=null;
	
	ArrayList<OrderInfo> dataList=null;
	int visibleLastIndex=0;
	
	int pageNum;//ҳ��
	final static int PAGE_MAX=10;//ÿҳ��¼��
	int totalCount;//������
	
	View loadmoreView=null;
	TextView loadingMoreTv=null;
	ProgressBar progressBar1=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*����ǰactivity���ӵ�activitymanager��*/
        //am=ActivityManager.getInstance();
        //am.addActivity(this);
        //���ò���
		setContentView(R.layout.activity_myorders);
		
		//��ʼ��һ��������
        //msb=new MainSearchBar(this);
        //��ʼ��һ�������˵�
        mnm=new MainNavbarMenu(this, AppConstant.MNMAIndex.MyOrdersActivity);
        initInstance();//��ʼ���ؼ�
        registerEvent();//ע���¼�
        loadData();//��������
	}
	void initInstance(){
		moAy_noNotifyTv=(TextView) findViewById(R.id.moAy_noNotifyTv);//�޼�¼��ʾ
		moAy_orderListLv=(ListView) findViewById(R.id.moAy_orderListLv);//�����б�
		
		loadmoreView=getLayoutInflater().inflate(R.layout.dynamicload_list_loadmore, null);
		loadingMoreTv=(TextView) loadmoreView.findViewById(R.id.loadingMoreTv);
		progressBar1=(ProgressBar) loadmoreView.findViewById(R.id.progressBar1);
		moAy_orderListLv.addFooterView(loadmoreView);//�����б��ײ���ͼ
	}
	/*****************************************
	 *ע��ؼ��¼���Ӧ����
	 ****************************************/
	void registerEvent(){
		moAy_orderListLv.setOnItemClickListener(new LvOnItemClickListener());
		moAy_orderListLv.setOnScrollListener(new LvOnScrollListener());
	}
	/**************�������¼�������***************/
	class LvOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//���붩����ϸ��Ϣ����
			long orderId=dataList.get(arg2).id;
			Intent intent=new Intent();
			intent.setClass(MyOrdersActivity.this, OrderDetailActivity.class);
			intent.putExtra(AppConstant.IntentExtraName.ORDER_ID, orderId);
			startActivity(intent);
		}
		
	}
	/***********************�����б������¼�������**********************/
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
		loadingMoreTv.setText("û�и��ඩ��Ŷ");
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
			dataLoader.load("���ڼ��ض��������Ժ�...", true,true);	
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
				if(MyOrdersActivity.this.list==null)
				{
					moAy_noNotifyTv.setVisibility(View.VISIBLE);
					moAy_orderListLv.setVisibility(View.GONE);
				}
				return;
			}
			DataOfOrdersAnalyzeHelper analyzeHelper=(DataOfOrdersAnalyzeHelper)this.analyzeHelper;
			if(analyzeHelper.recode==AppConstant.ReCodeFinal.OK.code){//�ɹ�,��������				
				ArrayList<OrderInfo> dataListTemp=analyzeHelper.orderList;				
				if(dataListTemp==null||dataListTemp.size()==0)
				{
					if(MyOrdersActivity.this.list==null)
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
				
				
				/***************��ʾ����***********************/
				if(list==null){
					list=new ArrayList<HashMap<String,SpannableString>>();
				}
				//��������
				int size=dataListTemp.size();
				int idxOffset=list.size();
				for(int i=0;i<size;i++){
					OrderInfo orderInfo=dataListTemp.get(i);
					SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
					HashMap<String,SpannableString> hashTemp=new HashMap<String, SpannableString>();
					hashTemp.put("orderId", new SpannableString("������:"+orderInfo.id+""));
					hashTemp.put("restaurantId", new SpannableString("�����:"+orderInfo.restaurantId+""));
					hashTemp.put("tableId", new SpannableString("��λ��:"+orderInfo.tableId+""));
					hashTemp.put("status", new SpannableString("״̬:"+OrderStatus.getLable(orderInfo.status)+""));
					hashTemp.put("valid", new SpannableString("��Ч��:"+OrderValid.getLable(orderInfo.valid)+""));//
					hashTemp.put("date", new SpannableString("�ò�����:"+sdfDate.format(orderInfo.date)+""));//
					hashTemp.put("time", new SpannableString("�ò�ʱ��:"+sdfTime.format(orderInfo.time)+""));//
					list.add(hashTemp);
				}		
				//����������
				if(null==iatclAdapter){
					iatclAdapter=new SimpleAdapter(MyOrdersActivity.this, list,R.layout.lvitem_order_list_item, 
							new String[]{"orderId","restaurantId","tableId","status","valid","date","time"},
							new int[]{R.id.lioli_orderIdTv,R.id.lioli_restaurantIdTv,R.id.lioli_tableIdTv,R.id.lioli_statusTv,R.id.lioli_validTv,
							R.id.lioli_dateTv,R.id.lioli_timeTv});
					MyOrdersActivity.this.moAy_orderListLv.setAdapter(iatclAdapter);
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
				AcvivityLoginGoto.setAcbi(MyOrdersActivity.this);
				Intent loginIntent=new Intent();
				loginIntent.setClass(MyOrdersActivity.this, LoginActivity.class);
				MyOrdersActivity.this.startActivity(loginIntent);
				return;
			}
			else{//����������ʾ����
				Toast.makeText(a, analyzeHelper.remsg, Toast.LENGTH_SHORT).show();
				if(MyOrdersActivity.this.list==null)
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
		pageNum=0;//ҳ��
		loadData();
	}
	
}